package com.example.todo.ROOM.accessors;

import retrofit2.Call;
import com.example.todo.ROOM.Todo;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface TodoRetrofit {

    @GET("todos/")
    Call<List<Todo>> getTodos();

    @POST("todos/")
    Call<Todo> createTodo(@Body Todo todo);

}
