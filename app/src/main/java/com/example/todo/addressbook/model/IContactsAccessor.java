package com.example.todo.addressbook.model;

import java.util.List;

public interface IContactsAccessor {

	public List<Contact> readAllContacts();
	
	public boolean createContact(Contact contact);

	public boolean deleteContact(Contact contact);
	
}
