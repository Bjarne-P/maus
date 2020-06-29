package com.example.todo.sort;

import com.example.todo.ROOM.Todo;

import java.util.Comparator;

public class CompareRecent implements  Comparator<Todo>{
    @Override
    public int compare(Todo o1, Todo o2) {
        return o2.getId()-o1.getId();
    }


}
