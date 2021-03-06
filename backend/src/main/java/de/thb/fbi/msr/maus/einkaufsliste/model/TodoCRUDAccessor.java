package de.thb.fbi.msr.maus.einkaufsliste.model;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


@Path("/todos")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface TodoCRUDAccessor {
	
	@GET
	public List<Todo> readAllItems();
	
	@POST
	public Todo createItem(Todo item);

	@DELETE
	@Path("/{itemId}")
	public boolean deleteItem(@PathParam("itemId") int itemId);

	@PUT
	public Todo updateItem(Todo item);
}
