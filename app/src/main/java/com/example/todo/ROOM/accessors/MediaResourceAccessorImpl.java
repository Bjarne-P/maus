package com.example.todo.ROOM.accessors;

import android.util.Log;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MediaResourceAccessorImpl {

	protected static String logger = MediaResourceAccessorImpl.class
			.getSimpleName();

	private String baseUrl;

	public MediaResourceAccessorImpl(String baseUrl) {
		Log.i(logger, "<constructor>: using baseUrl: " + baseUrl);
		this.baseUrl = baseUrl.trim();
		if (!baseUrl.endsWith("/")) {
			this.baseUrl += "/";
		}
	}

	public InputStream readMediaResource(String url) throws IOException {
		Log.i(logger, "readMediaResource(): " + url);

		// trim the string
		url = url.trim();

		// create the full url
		URL fullUrl = new URL(this.baseUrl
				+ (url.startsWith("/") ? url.substring(1) : url));

		// access the url
		return fullUrl.openStream();
	}
	
	/**
	 * get the base url for the media resources
	 * @return
	 */

	public String getBaseUrl() {
		return this.baseUrl;
	}

}