package com.example.todo;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.todo.ROOM.Todo;
import com.example.todo.ROOM.TodoRepository;

import java.util.List;

public class TodoViewmodel extends AndroidViewModel {
    private TodoRepository repository;
    private LiveData<List<Todo>> allTodos;

    public TodoViewmodel(@NonNull Application application) {
        super(application);
        repository = new TodoRepository(application);
        allTodos = repository.getAllTodos();
    }

    public void insert(Todo todo){
        repository.insert(todo);
    }

    public void update(Todo todo){
        repository.update(todo);
    }

    public void deleteAll(){
        repository.deleteAllTodos();
    }

    public void delete(Todo todo){
        repository.delete(todo);
    }

    public LiveData<List<Todo>> getAllTodos(){
        return allTodos;
    }
}
