package com.example.todo.addressbook.model;

import java.util.Collections;
import java.util.List;

public class NoopContactsAccessor implements IContactsAccessor {

	@Override
	public List<Contact> readAllContacts() {
		return Collections.emptyList();
	}

	@Override
	public boolean createContact(Contact contact) {
		return true;
	}

	@Override
	public boolean deleteContact(Contact contact) {
		return true;
	}

}
