package com.example.todo.ROOM;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.todo.ROOM.accessors.TodoRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class TodoRepository {
    private TodoDAO todoDAO;
    private Retrofit retrofit;
    private LiveData<List<Todo>> allTodos;

    public TodoRepository(Application application) {

        TodoDB db = TodoDB.getDB(application);
        todoDAO = db.todoDAO();
        allTodos = todoDAO.getAllTodosDone();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/backend-1.0-SNAPSHOT/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TodoRetrofit todoRetrofit = retrofit.create(TodoRetrofit.class);

        Call<List<Todo>> call = todoRetrofit.getTodo();

        call.enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if (!response.isSuccessful())
                    return;

                List<Todo> retrotodos = response.body();

                for (Todo todo : retrotodos){
                    insert(todo);
                }
               /* for (Todo todo : retrotodos) {
                    String content = "";
                    content += "id: " + todo.getId() + "\n";
                    content += "title: " + todo.getTitle() + "\n";
                    content += "content: " + todo.getContent()+ "\n";
                    content += "important" + todo.isImportaint() + "\n";
                    content += "due_month" + todo.getDue_month() + "\n";
                    content += "due_day: " + todo.getDue_day() + "\n";
                    content += "due_hour: " + todo.getDue_hour() + "\n";
                    content += "due_minute: " + todo.getDue_minute() + "\n";
                    content += "done: " + todo.isDone() + "\n\n";

                }
                */
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable throwable) {
                //Todo
            }
        });
    }

    public void insert(Todo todo) {
        new InsertTodoAsyncTask(todoDAO).execute(todo);
    }

    public void update(Todo todo) {
        new UpdateTodoAsyncTask(todoDAO).execute(todo);
    }

    public void delete(Todo todo) {
        new DeleteTodoAsyncTask(todoDAO).execute(todo);
    }

    public void deleteAllTodos() {
        new DeleteAllAsyncTask(todoDAO).execute();
    }


    public LiveData<List<Todo>> getAllTodosImportant() {
        return allTodos;
    }


    private static class InsertTodoAsyncTask extends AsyncTask<Todo, Void, Void> {
        private TodoDAO todoDAO;

        private InsertTodoAsyncTask(TodoDAO todoDAO) {
            this.todoDAO = todoDAO;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDAO.insert(todos[0]);
            return null;
        }
    }

    private static class UpdateTodoAsyncTask extends AsyncTask<Todo, Void, Void> {
        private TodoDAO todoDAO;

        private UpdateTodoAsyncTask(TodoDAO todoDAO) {
            this.todoDAO = todoDAO;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDAO.update(todos[0]);
            return null;
        }
    }

    private static class DeleteTodoAsyncTask extends AsyncTask<Todo, Void, Void> {
        private TodoDAO todoDAO;

        private DeleteTodoAsyncTask(TodoDAO todoDAO) {
            this.todoDAO = todoDAO;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDAO.delete(todos[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Todo, Void, Void> {
        private TodoDAO todoDAO;

        private DeleteAllAsyncTask(TodoDAO todoDAO) {
            this.todoDAO = todoDAO;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDAO.deleteAll();
            return null;
        }
    }
}
