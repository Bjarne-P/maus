package com.example.todo.ROOM.accessors;

import android.util.Log;
import de.thb.fbi.msr.maus.einkaufsliste.model.DataItem;
import de.thb.fbi.msr.maus.einkaufsliste.model.DataItemCRUDAccessor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;

import java.util.List;

public class ResteasyDataItemCRUDAccessor implements DataItemCRUDAccessor {
	
	protected static String logger = ResteasyDataItemCRUDAccessor.class.getSimpleName();

	/**
	 * the client via which we access the rest web interface provided by the server
	 */
	private DataItemCRUDAccessor restClient;
	
	public ResteasyDataItemCRUDAccessor(String baseUrl) {

		Log.i(logger,"initialising restClient for baseUrl: " + baseUrl);
		
		// create a client for the server-side implementation of the interface
		this.restClient = ProxyFactory.create(DataItemCRUDAccessor.class,
				baseUrl,
				new ApacheHttpClient4Executor());
		
		Log.i(logger,"initialised restClient: " + restClient + " of class " + restClient.getClass());
	}

	@Override
	public List<DataItem> readAllItems() {
		Log.i(logger, "readAllItems()");

		List<DataItem> itemlist = restClient.readAllItems();
		
		Log.i(logger, "readAllItems(): got: " + itemlist);
	
		return itemlist;
	}

	@Override
	public DataItem createItem(DataItem item) {
		Log.i(logger, "createItem(): send: " + item);

		item = restClient.createItem(item);
		
		Log.i(logger, "createItem(): got: " + item);
	
		return item;
	}

	@Override
	public boolean deleteItem(long itemId) {
		Log.i(logger, "deleteItem(): send: " + itemId);

		boolean deleted = restClient.deleteItem(itemId);
		
		Log.i(logger, "deleteItem(): got: " + deleted);
	
		return deleted;
	}

	@Override
	public DataItem updateItem(DataItem item) {
		Log.i(logger, "updateItem(): send: " + item);

		item = restClient.updateItem(item);
		
		Log.i(logger, "updateItem(): got: " + item);
	
		return item;
	}

}
