package com.example.todo.ROOM;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface TodoDAO {

    @Insert
    void insert(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

    @Query("DELETE FROM todo_table")
    void deleteAll();

    @Query("SELECT * FROM todo_table ORDER BY done DESC")
    List<Todo> getAllTodosStatic();

    @Query("Select Count(*) from todo_table")
    LiveData<Integer> getItemCount();

    @Query("SELECT * FROM todo_table ORDER BY done DESC")
    LiveData<List<Todo>> getAllTodosDone();


    @Query("SELECT * FROM todo_table ORDER BY title")
    LiveData<List<Todo>> getAllTodosTitle();


}