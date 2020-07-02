package com.example.todo.ROOM;


import android.util.Log;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.Calendar;
import java.lang.reflect.Type;
import java.util.ArrayList;

@Entity(tableName = "todo_table")
public class Todo {

    public enum ItemTypes {
        TYPE1, TYPE2, TYPE3
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    private boolean done;

    private boolean importaint;

    private int due_minute;

    private int due_hour;

    private int due_day;

    private int due_month;

    private int due_year;

    private String contacts;

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", done=" + done +
                ", importaint=" + importaint +
                ", due_minute=" + due_minute +
                ", due_hour=" + due_hour +
                ", due_day=" + due_day +
                ", due_month=" + due_month +
                ", due_year=" + due_year +
                ", contacts='" + contacts + '\'' +
                '}';
    }

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
    /*
    public Todo(String title, String content, boolean importaint, boolean done, int due_minute, int due_hour, int due_day, int due_month, int due_year) {
        this.title = title;
        this.content = content;
        this.importaint = importaint;
        this.due_minute = due_minute;
        this.due_hour = due_hour;
        this.due_day = due_day;
        this.due_month = due_month;
        this.due_year = due_year;
        this.done = done;

    }
    */
    @Ignore
    public Todo() {

    }
    @Ignore
    public Todo(String title, String content, boolean importaint){
        Calendar c = Calendar.getInstance();
        this.title = title;
        this.content = content;
        this.importaint = importaint;
        this.due_minute=(c.get(c.MINUTE));
        this.due_hour=(c.get(c.HOUR_OF_DAY));
        this.due_day=(c.get(c.DAY_OF_MONTH));
        this.due_month=(c.get(c.MONTH));
        this.due_year=(c.get(c.YEAR));

    }

    public Todo updateFrom(Todo item) {
            this.setTitle(item.getTitle());
            this.setContent(item.getContent());
            this.setImportaint(item.isImportaint());
            this.setDue_minute(item.getDue_minute());
            this.setDue_hour(item.getDue_hour());
            this.setDue_day(item.getDue_day());
            this.setDue_month(item.getDue_month());
            this.setDue_year(item.getDue_year());
        return this;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setDue_minute(int due_minute) {
        this.due_minute = due_minute;
    }

    public void setDue_hour(int due_hour) {
        this.due_hour = due_hour;
    }

    public void setDue_day(int due_day) {
        this.due_day = due_day;
    }

    public void setDue_month(int due_month) {
        this.due_month = due_month;
    }

    public void setDue_year(int due_year) {
        this.due_year = due_year;
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

    public boolean isDone() {
        return done;
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

    public String getContacts() { return contacts; }

    public ArrayList<Integer> getContactsAsAL() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>() {}.getType();

        return gson.fromJson(contacts, type);
    }

    public void setContacts(String contacts) { this.contacts = contacts; }

    public void setContacts(ArrayList<Integer> cs) {
        Gson gson = new Gson();
        //System.out.println("inputString= " + inputString);
        this.contacts = gson.toJson(cs);
    }
}