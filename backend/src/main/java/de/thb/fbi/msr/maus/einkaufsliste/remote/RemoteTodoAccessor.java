package de.thb.fbi.msr.maus.einkaufsliste.remote;

import java.util.ArrayList;
import java.util.List;

import de.thb.fbi.msr.maus.einkaufsliste.model.Todo;
import org.apache.log4j.Logger;
import de.thb.fbi.msr.maus.einkaufsliste.model.TodoCRUDAccessor;

public class RemoteTodoAccessor implements TodoCRUDAccessor {

	protected static Logger logger = Logger
			.getLogger(RemoteTodoAccessor.class);

	/**
	 * the list of data items, note that the list is *static* as for each client
	 * request a new instance of this class will be created!
	 */
	private static List<Todo> itemlist = new ArrayList<Todo>();

	/**
	 * we assign the ids here
	 */
	private static int idCount = 0;
	
	@Override
	public List<Todo> readAllItems() {
		logger.info("readAllItems(): " + itemlist);

		return itemlist;
	}

	@Override
	public Todo createItem(Todo item) {
		logger.info("createItem(): " + item);
		item.setId(idCount++);

		itemlist.add(item);
		return item;
	}

	@Override
	public boolean deleteItem(final int itemId) {
		logger.info("deleteItem(): " + itemId);

		boolean removed = itemlist.remove(new Todo() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 71193783355593985L;

			@Override
			public int getId() {
				return itemId;
			}
		});

		return removed;
	}

	@Override
	public Todo updateItem(Todo item) {
		logger.info("updateItem(): " + item);

		itemlist.add(itemlist.indexOf(item), item);
		return item;
	}
}
