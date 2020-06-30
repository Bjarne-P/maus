package com.example.todo.ROOM.accessors;

import com.example.todo.ROOM.Todo;
import com.example.todo.ROOM.TodoDAO;
import de.thb.fbi.msr.maus.einkaufsliste.model.DataItem;
import de.thb.fbi.msr.maus.einkaufsliste.model.DataItemCRUDAccessor;

import java.util.ArrayList;
import java.util.List;

public class LocalDataItemCRUDAccessor implements TodoDAO {
	
	/**
	 * the list of data items(non-Javadoc)
	 */
	private List<Todo> itemlist = new ArrayList<Todo>();

	@Override
	public List<Todo> readAllItems() {
		return itemlist;
	}

	@Override
	public Todo createItem(Todo item) {
		this.itemlist.add(item);
		return item;
	}

	@Override
	public boolean deleteItem(final long itemId) {
		boolean removed = this.itemlist.remove(new Todo() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 5186750365614757801L;

			@Override
			public long getId() {
				return itemId;
			}			
		});
		
		return removed;
	}

	@Override
	public Todo updateItem(Todo item) {
		return this.itemlist.get(itemlist.indexOf(item)).updateFrom(item);
	}

}
