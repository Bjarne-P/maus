package com.example.todo.ROOM;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoRepository {
    private TodoDAO todoDAO;
    private LiveData<List<Todo>> allTodos;

    public TodoRepository(Application application){
        TodoDB db = TodoDB.getDB(application);
        todoDAO = db.todoDAO();
        allTodos = todoDAO.getAllTodos();
    }

    public void insert(Todo todo){
        new InsertTodoAsyncTask(todoDAO).execute(todo);
    }

    public void update(Todo todo){
        new UpdateTodoAsyncTask(todoDAO).execute(todo);
    }

    public void delete(Todo todo){
        new DeleteTodoAsyncTask(todoDAO).execute(todo);
    }

    public void deleteAllTodos(){
        new DeleteAllAsyncTask(todoDAO).execute();
    }

    public LiveData<List<Todo>> getAllTodos() {
        return allTodos;
    }


    private static class InsertTodoAsyncTask extends AsyncTask<Todo, Void, Void>{
        private TodoDAO todoDAO;

        private InsertTodoAsyncTask(TodoDAO todoDAO){
            this.todoDAO = todoDAO;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDAO.insert(todos[0]);
            return null;
        }
    }

    private static class UpdateTodoAsyncTask extends AsyncTask<Todo, Void, Void>{
        private TodoDAO todoDAO;

        private UpdateTodoAsyncTask(TodoDAO todoDAO){
            this.todoDAO = todoDAO;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDAO.update(todos[0]);
            return null;
        }
    }

    private static class DeleteTodoAsyncTask extends AsyncTask<Todo, Void, Void>{
        private TodoDAO todoDAO;

        private DeleteTodoAsyncTask(TodoDAO todoDAO){
            this.todoDAO = todoDAO;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDAO.delete(todos[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Todo, Void, Void>{
        private TodoDAO todoDAO;

        private DeleteAllAsyncTask(TodoDAO todoDAO){
            this.todoDAO = todoDAO;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDAO.deleteAll();
            return null;
        }
    }
}
