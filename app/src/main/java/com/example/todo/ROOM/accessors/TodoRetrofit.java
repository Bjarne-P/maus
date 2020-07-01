package com.example.todo.ROOM.accessors;

import retrofit2.Call;
import com.example.todo.ROOM.Todo;
import retrofit2.http.GET;

import java.util.List;

public interface TodoRetrofit {

    @GET("todos")
    Call<List<Todo>> getTodo();




}
