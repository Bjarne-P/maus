package com.example.todo.ROOM.accessors;

import android.util.Log;
import com.example.todo.ROOM.Todo;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class URLDataItemCRUDAccessor extends LocalDataItemCRUDAccessor {

	protected static String logger = URLDataItemCRUDAccessor.class
			.getSimpleName();

	// the url from which we read the items
	private URL url;

	private ObjectMapper mObjectMapper = new ObjectMapper();

	public URLDataItemCRUDAccessor(String urlstring) {
		try {
			this.url = new URL(urlstring);
			Log.i(logger, "created url: " + url);
		} catch (Exception e) {
			Log.e(logger, "got exceotion trying to create url from string "
					+ urlstring + ": " + e, e);
		}
	}

	@Override
	public List<Todo> readAllItems() {
		try {
			// access the url
			InputStream is = this.url.openStream();
			return mObjectMapper.readValue(is, new TypeReference<List<Todo>>() {});
		} catch (Exception e) {
			Log.e(logger, "readAllItems(): got exception: " + e, e);
			return new ArrayList<Todo>();
		}
	}

	@Override
	public Todo createItem(Todo item) {
		Log.e(logger, "createItem(): cannot execute action...");

		return null;
	}

	@Override
	public boolean deleteItem(int itemId) {
		Log.e(logger, "deleteItem(): cannot execute action...");

		return false;
	}

	@Override
	public Todo updateItem(Todo item) {
		Log.e(logger, "updateItem(): cannot execute action...");

		return null;
	}

}
