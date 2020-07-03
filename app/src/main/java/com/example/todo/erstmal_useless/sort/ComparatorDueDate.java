package com.example.todo.erstmal_useless.sort;

import com.example.todo.ROOM.Todo;

import java.util.Comparator;

public class ComparatorDueDate implements Comparator<Todo> {

    public int compare(Todo a, Todo b){
        if (a.getDue_year() != b.getDue_year())
            return a.getDue_year()-b.getDue_year();
        else if (a.getDue_month() != b.getDue_month())
            return a.getDue_month()-b.getDue_month();
        else if (a.getDue_day() != b.getDue_day())
            return a.getDue_day()-b.getDue_day();
        else if (a.getDue_hour() != b.getDue_hour())
            return a.getDue_hour()-b.getDue_hour();
        else return a.getDue_minute()-b.getDue_minute();
    }
}
