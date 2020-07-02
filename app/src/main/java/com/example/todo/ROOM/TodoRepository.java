package com.example.todo.ROOM;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import com.example.todo.ROOM.accessors.TodoRetrofit;
import com.example.todo.TodoListActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.ws.rs.POST;
import java.util.List;

public class TodoRepository {
    private TodoDAO todoDAO;
    private Retrofit retrofit;
    private LiveData<List<Todo>> allTodos;
    TodoRetrofit todoRetrofit;

    public TodoRepository(Application application) {

        TodoDB db = TodoDB.getDB(application);
        todoDAO = db.todoDAO();
        allTodos = todoDAO.getAllTodosDone();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.178.69:8080/backend-1.0-SNAPSHOT/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        todoRetrofit = retrofit.create(TodoRetrofit.class);



        Call<List<Todo>> call = todoRetrofit.getTodos();

        call.enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if (!response.isSuccessful())
                    return;
                else {
                    deleteAllTodos();
                }
                List<Todo> retrotodos = response.body();


                for (Todo todo : retrotodos){
                    Log.d("Zutun", todo.toString());
                    new InsertTodoAsyncTask(todoDAO).execute(todo);
                }
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable throwable) {
                //Todo
            }
        });
    }



    public void insert(Todo todo) {
        Call call = todoRetrofit.createTodo(todo);
        Log.d("Testfeld", todo.toString());
        Log.d("FehlerErzeuger: ",  call.request().toString());
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful()){
                    Log.d("Fehler: " , String.valueOf(response.code()));
                    return;
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("Fehler2: ", t.toString());
            }
        });
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


    public LiveData<List<Todo>> getAllTodos() {
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
