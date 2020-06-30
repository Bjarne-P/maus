package com.example.todo.ROOM.accessors;

import android.util.Log;
import de.thb.fbi.msr.maus.einkaufsliste.model.DataItem;
import de.thb.fbi.msr.maus.einkaufsliste.model.DataItemCRUDAccessor;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class URLDataItemCRUDAccessor implements DataItemCRUDAccessor {

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
	public List<DataItem> readAllItems() {
		try {
			// access the url
			InputStream is = this.url.openStream();
			return mObjectMapper.readValue(is, new TypeReference<List<DataItem>>() {});
		} catch (Exception e) {
			Log.e(logger, "readAllItems(): got exception: " + e, e);
			return new ArrayList<DataItem>();
		}
	}

	@Override
	public DataItem createItem(DataItem item) {
		Log.e(logger, "createItem(): cannot execute action...");

		return null;
	}

	@Override
	public boolean deleteItem(long itemId) {
		Log.e(logger, "deleteItem(): cannot execute action...");

		return false;
	}

	@Override
	public DataItem updateItem(DataItem item) {
		Log.e(logger, "updateItem(): cannot execute action...");

		return null;
	}

}
