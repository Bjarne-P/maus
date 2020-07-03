package com.example.todo.ROOM;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import androidx.lifecycle.LiveData;
import com.example.todo.ROOM.accessors.TodoRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TodoRepository {
    private boolean onlineFlag = false;
    private TodoDAO todoDAO;
    private Retrofit retrofit;
    private LiveData<List<Todo>> allTodos;
    private List<Todo> allTodosStatic;
    TodoRetrofit todoRetrofit;


    public TodoRepository(Application application) {

        TodoDB db = TodoDB.getDB(application);
        todoDAO = db.todoDAO();
        allTodos = todoDAO.getAllTodosDone();

        new Thread(new Runnable(){

            @Override
            public void run() {
                allTodosStatic = todoDAO.getAll();
            }
        }).start();




        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.178.69:8080/backend-1.0-SNAPSHOT/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        todoRetrofit = retrofit.create(TodoRetrofit.class);

        Call<List<Todo>> call = todoRetrofit.getTodos();


        if (allTodosStatic.size() == 0) {

            call.enqueue(new Callback<List<Todo>>() {
                @Override
                public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                    if (!response.isSuccessful()) {
                        Log.d("Flagge", "negativ");
                        return;
                    } else {
                        Log.d("Flagge", "positiv");
                    }
                    List<Todo> retrotodos = response.body();


                    for (Todo todo : retrotodos) {
                        new InsertTodoAsyncTask(todoDAO).execute(todo);
                    }
                }

                @Override
                public void onFailure(Call<List<Todo>> call, Throwable throwable) {
                    //Todo
                }
            });
        } else {
            todoRetrofit.deleteAllTodos();
            for (Todo todo : allTodosStatic) {
                insert(todo);
            }
        }


    }




    public boolean getOnline(){
        Call call = todoRetrofit.getTodos();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful()) {
                    Log.d("Fehler: ", String.valueOf(response.code()));
                    onlineFlag = false;
                    return;
                }else onlineFlag= true;
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("Fehler2: ", t.toString());
                onlineFlag = false;
            }

        });
        Log.d("Fehler", String.valueOf(onlineFlag));
        return  onlineFlag;
    }

    public void insert(Todo todo) {
        Call call = todoRetrofit.postTodo(todo);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful()) {
                    Log.d("Fehler: ", String.valueOf(response.code()));
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
        Call call = todoRetrofit.putTodo(todo.getId(), todo);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful()) {

                    return;
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
        new UpdateTodoAsyncTask(todoDAO).execute(todo);

    }

    public void delete(Todo todo) {
        Call call = todoRetrofit.deleteTodo(todo.getId());
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful()) {
                    return;
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
            }
        });
        new DeleteTodoAsyncTask(todoDAO).execute(todo);
    }

    public void deleteAllTodos() {
        Call call = todoRetrofit.deleteAllTodos();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful()) {

                    return;
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                new DeleteAllAsyncTask(todoDAO).execute();
            }
        });
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
