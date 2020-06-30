package com.example.todo.ROOM.accessors;

import android.util.Log;
import com.example.todo.ROOM.Todo;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;

import java.util.List;

public class ResteasyTodoCRUDAccessor implements TodoCRUDAccessor {
	
	protected static String logger = ResteasyTodoCRUDAccessor.class.getSimpleName();

	/**
	 * the client via which we access the rest web interface provided by the server
	 */
	private TodoCRUDAccessor restClient;
	
	public ResteasyTodoCRUDAccessor(String baseUrl) {

		Log.i(logger,"initialising restClient for baseUrl: " + baseUrl);
		
		// create a client for the server-side implementation of the interface
		this.restClient = ProxyFactory.create(TodoCRUDAccessor.class,
				baseUrl,
				new ApacheHttpClient4Executor());
		
		Log.i(logger,"initialised restClient: " + restClient + " of class " + restClient.getClass());
	}

	@Override
	public List<Todo> readAllItems() {
		Log.i(logger, "readAllItems()");

		List<Todo> itemlist = restClient.readAllItems();
		
		Log.i(logger, "readAllItems(): got: " + itemlist);
	
		return itemlist;
	}

	@Override
	public Todo createItem(Todo item) {
		Log.i(logger, "createItem(): send: " + item);

		item = restClient.createItem(item);
		
		Log.i(logger, "createItem(): got: " + item);
	
		return item;
	}

	@Override
	public boolean deleteItem(int itemId) {
		Log.i(logger, "deleteItem(): send: " + itemId);

		boolean deleted = restClient.deleteItem(itemId);
		
		Log.i(logger, "deleteItem(): got: " + deleted);
	
		return deleted;
	}

	@Override
	public Todo updateItem(Todo item) {
		Log.i(logger, "updateItem(): send: " + item);

		item = restClient.updateItem(item);
		
		Log.i(logger, "updateItem(): got: " + item);
	
		return item;
	}

}
