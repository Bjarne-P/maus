package de.thb.fbi.msr.maus.einkaufsliste.model;


import java.io.Serializable;
import java.util.ArrayList;

public class Todo implements Serializable {

	private int id;

	private String title;

	private String content;

	private  boolean done;

	private boolean importaint;

	private int due_minute;

	private int due_hour;

	private int due_day;

	private int due_month;

	private int due_year;

	private String contacts;


	public Todo(String title, String content, boolean importaint, boolean done, int due_minute, int due_hour, int due_day, int due_month, int due_year, String contacts) {
		this.title = title;
		this.content = content;
		this.importaint = importaint;
		this.due_minute = due_minute;
		this.due_hour = due_hour;
		this.due_day = due_day;
		this.due_month = due_month;
		this.due_year = due_year;
		this.done = done;
		this.setContacts(contacts);
	}


	public Todo(Todo todo){
		this.id = todo.id;
		this.title = todo.title;
		this.content = todo.content;
		this.importaint = todo.importaint;
		this.due_minute = todo.due_minute;
		this.due_hour = todo.due_hour;
		this.due_day = todo.due_day;
		this.due_month = todo.due_month;
		this.due_year = todo.due_year;
		this.done = todo.done;
		this.setContacts(todo.contacts);
	}

	public Todo() {

	}


	public void setContacts(String contacts) { this.contacts = contacts; }


	public String getContacts() { return contacts; }

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public boolean isImportaint() {
		return importaint;
	}

	public int getDue_year() {
		return due_year;
	}

	public int getDue_month() {
		return due_month;
	}

	public int getDue_day() {
		return due_day;
	}

	public int getDue_hour() {
		return due_hour;
	}

	public int getDue_minute() {
		return due_minute;
	}

	public void setImportaint(boolean importaint) {
		this.importaint = importaint;
	}
}