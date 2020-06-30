package com.example.todo.webview;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.todo.DataAccessRemoteApplication;
import com.example.todo.EditAddTodoActivity;
import com.example.todo.R;


/**
 * Show the details of an item
 * 
 * @author Joern Kreutel
 * 
 */
public class WebViewItemDetailsActivity extends EditAddTodoActivity {

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
		this.clearButton.setOnClickListener((View.OnClickListener) this);
		
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
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

}
