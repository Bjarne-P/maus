package com.example.todo.webview;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;
import de.thb.fbi.msr.maus.einkaufsliste.DataAccessRemoteApplication;
import de.thb.fbi.msr.maus.einkaufsliste.ItemDetailsActivity;
import de.thb.fbi.msr.maus.einkaufsliste.R;

/**
 * Show the details of an item
 * 
 * @author Joern Kreutel
 * 
 */
public class WebViewItemDetailsActivity extends ItemDetailsActivity {
	
	/**
	 * the webview
	 */
	private WebView webview;
	
	/**
	 * the clear button for clearing the text on the webview
	 */
	private Button clearButton;

	/**
	 * Called when the activity is first created.
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// get the clear button
		this.clearButton = (Button) findViewById(R.id.clearButton);
		// and set myself as the onclicklistener
		this.clearButton.setOnClickListener(this);
		
		// get the web view
		this.webview = (WebView) findViewById(R.id.item_webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setBlockNetworkLoads(false);

		// add JavaScriptExtensions
		webview.addJavascriptInterface(new MyJavaScriptExtensions(),
				"androidjs");

		// load the uri
		try {
			webview.loadUrl(((DataAccessRemoteApplication) getApplication())
					.getMediaResourceAccessor().getBaseUrl() + "formattedtext.html");
		} catch (Exception e) {
			Log.e(getClass().getName(), "got exception: " + e, e);
			((DataAccessRemoteApplication) getApplication()).reportError(this,
					"got exception: " + e);
		}
	}

	@Override
	/**
	 * we ignore the content view that is actually supposed to be set by a superclass and use our own one
	 */
	public void setContentView(int id) {
		super.setContentView(R.layout.itemwebview);
	}

	/**
	 * this class is passed to the webview for allowing callbacks from
	 * javascript to this activity
	 * 
	 * @author joern
	 * 
	 */
	public class MyJavaScriptExtensions {

		public static final int TOAST_LONG = Toast.LENGTH_LONG;
		public static final int TOAST_SHORT = Toast.LENGTH_SHORT;

		@JavascriptInterface
		public void toast(String message, int length) {
			Log.i(getClass().getName(), "toast has been requested: " + message
					+ ", with length: " + length);
			Toast.makeText(WebViewItemDetailsActivity.this, message, length)
					.show();
		}

	}
	
	@Override
	public void onClick(View view) {
		if (view == this.clearButton) {
			Log.i(logger, "got onClick() on clearButton");
			// this shows how to call a javascript method from a native application component
			this.webview.loadUrl("javascript:clear()");
		} else  {
			super.onClick(view);
		}
	}

}
