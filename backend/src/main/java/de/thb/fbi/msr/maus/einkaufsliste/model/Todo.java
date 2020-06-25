package de.thb.fbi.msr.maus.einkaufsliste.model;


import java.io.Serializable;

public class Todo implements Serializable {

	private int id;

	private String title;

	private String content;


	private boolean importaint;

	private int due_minute;

	private int due_hour;

	private int due_day;

	private int due_month;

	private int due_year;

	public Todo(String title, String content, boolean importaint, int due_minute, int due_hour, int due_day, int due_month, int due_year) {
		this.title = title;
		this.content = content;
		this.importaint = importaint;
		this.due_minute = due_minute;
		this.due_hour = due_hour;
		this.due_day = due_day;
		this.due_month = due_month;
		this.due_year = due_year;
	}

	public Todo() {

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