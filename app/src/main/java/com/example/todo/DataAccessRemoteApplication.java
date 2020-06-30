package com.example.todo;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.widget.Toast;
import com.example.todo.ROOM.accessors.*;


/**
 * a local application class providing centralised functionality, in particular
 * the CRUD functionality that uses the remote data storage on the server
 * 
 * @author Joern Kreutel
 */
public class DataAccessRemoteApplication extends Application {

	protected static String logger = DataAccessRemoteApplication.class
			.getSimpleName();

	/**
	 * the accessors that implement the different alternatives for accessing the
	 * item list
	 */
	private LocalTodoCRUDAccessor localAccessor;

	private ResteasyTodoCRUDAccessor resteasyAccessor;

	private URLTodoCRUDAccessor urlAccessor;

	private HttpClientTodoCRUDAccessor httpClientAccessor;

	private HttpURLConnectionTodoCRUDAccessor httpURLConnectionAccessor;

	private MediaResourceAccessorImpl mediaResourceAccessor;

	public DataAccessRemoteApplication() {
		Log.i(logger, "<constructor>()");

		// initialise the accessors
		this.localAccessor = new LocalTodoCRUDAccessor();

		this.resteasyAccessor = new ResteasyTodoCRUDAccessor(
				getRestBaseUrl());

		this.urlAccessor = new URLTodoCRUDAccessor(
				getRestBaseUrl() + "/todos");

		this.httpClientAccessor = new HttpClientTodoCRUDAccessor(
				getRestBaseUrl() + "/todos");

		this.httpURLConnectionAccessor = new HttpURLConnectionTodoCRUDAccessor(
				getRestBaseUrl() + "/todos");

		this.mediaResourceAccessor = new MediaResourceAccessorImpl(
				getWebappBaseUrl() + "dataaccessContent");

		Log.i(logger, "<constructor>(): created accessors.");
	}

	public LocalTodoCRUDAccessor getLocalAccessor(int accessorId) {
		TodoCRUDAccessor accessor;

		switch (accessorId) {
		case R.integer.localAccessor:
			accessor = (TodoCRUDAccessor) this.localAccessor;
			break;
		case R.integer.resteasyFramework:
			accessor = (TodoCRUDAccessor) this.resteasyAccessor;
			break;
		case R.integer.urlClass:
			accessor = (TodoCRUDAccessor) this.urlAccessor;
			break;
		case R.integer.apacheHttpClient:
			accessor = (TodoCRUDAccessor) this.httpClientAccessor;
			break;
		case R.integer.urlConnection:
			accessor = (TodoCRUDAccessor) this.httpURLConnectionAccessor;
			break;
		default:
			Log.e(logger, "got unknown accessor id: " + accessorId
					+ ". Will use local accessor...");
			accessor = (TodoCRUDAccessor) localAccessor;
		}

		Log.i(logger, "will provide accessor: " + accessor);

		return (LocalTodoCRUDAccessor) accessor;
	}

	public MediaResourceAccessorImpl getMediaResourceAccessor() {
		return this.mediaResourceAccessor;
	}

	public void reportError(Activity activity, String error) {
		Toast.makeText(activity, error, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * get the baseUrl of the webapp used as data source and media resource provider
	 * @return
	 */
	private String getWebappBaseUrl() {
		return "http://10.0.2.2:8080/backend-1.0-SNAPSHOT/";
	}

	private String getRestBaseUrl()
	{
		return getWebappBaseUrl() + "rest";
	}
}
