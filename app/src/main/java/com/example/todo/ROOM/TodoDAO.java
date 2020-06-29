package com.example.todo.ROOM;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface TodoDAO {

    @Insert
    void insert(Todo note);

    @Update
    void update(Todo note);

    @Delete
    void delete(Todo note);

    @Query("DELETE FROM todo_table")
    void deleteAll();

    @Query("SELECT * FROM todo_table ORDER BY importaint DESC")
    LiveData<List<Todo>> getAllTodosImportant();


    @Query("SELECT * FROM todo_table ORDER BY title")
    LiveData<List<Todo>> getAllTodosTitle();


}