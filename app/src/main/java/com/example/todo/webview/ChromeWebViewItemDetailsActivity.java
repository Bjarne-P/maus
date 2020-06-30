package com.example.todo.webview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.example.todo.R;


/**
 * Show the details of an item
 * 
 * @author Joern Kreutel
 * 
 */
public class ChromeWebViewItemDetailsActivity extends
		WebViewItemDetailsActivity {

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// get the web view and set a WebChromeClient on it
		WebView webview = (WebView) webview.findViewById(R.id.item_webview);

		try {

			// add a chrome client to the webview that is able to handle alert
			// and confirm messages and feeds back the result to the browser using the 
			// cancel() and confirm() methods
			WebChromeClient client = new WebChromeClient() {
				@Override
				public boolean onJsAlert(WebView view, String url,
						String message, final JsResult result) {

					Log.i(ChromeWebViewItemDetailsActivity.this.getClass()
							.getName(), "got alert: " + message
							+ " result object is: " + result);
					new AlertDialog.Builder(ChromeWebViewItemDetailsActivity.this)
							.setTitle("JS Alert Message")
							.setMessage(message)
							.setPositiveButton(android.R.string.ok,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											result.confirm();
										}
									}).create().show();

					return true;
				}

				@Override
				public boolean onJsConfirm(WebView view, String url,
						String message, final JsResult result) {

					Log.i(ChromeWebViewItemDetailsActivity.this.getClass()
							.getName(), "got alert: " + message
							+ " result object is: " + result);
					new AlertDialog.Builder(
							ChromeWebViewItemDetailsActivity.this)
							.setTitle("JS Alert Message")
							.setMessage(message)
							.setPositiveButton(android.R.string.ok,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											result.confirm();
										}
									})
							.setNegativeButton(android.R.string.cancel,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											result.cancel();
										}
									}).create().show();

					return true;
				}
			};

			webview.setWebChromeClient(client);

		} catch (Exception e) {
			Log.e(getClass().getName(), "got exception: " + e, e);
			((DataAccessRemoteApplication) getApplication()).reportError(this,
					"got exception: " + e);
		}
	}

}
