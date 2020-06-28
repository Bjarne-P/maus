package com.example.todo.addressbook.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * some class to represent a contact
 */
public class Contact implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2139670243307764033L;

	private long id;
	
	private String name;
	
	private List<String> emails = new ArrayList<String>();
	private List<String> phoneNumbers = new ArrayList<String>();
	
	
	public void addEmailAddress(String email) {
		this.emails.add(email);
	}

	public void addPhoneNumber(String phone) {
		this.phoneNumbers.add(phone);
	}
	
	public List<String> getEmails() {
		return this.emails;
	}

	public List<String> getPhoneNumbers() {
		return this.phoneNumbers;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean equals(Object other) {
		if (other.getClass() == this.getClass()) {
			return this.getId() == ((Contact)other).getId();
		}
		return false;
	}

}
