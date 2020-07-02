package com.example.todo.ROOM.accessors;

import androidx.room.Delete;
import retrofit2.Call;
import com.example.todo.ROOM.Todo;
import retrofit2.http.*;

import java.util.List;

public interface TodoRetrofit {

    @GET("todos/")
    Call<List<Todo>> getTodos();

    @POST("todos/")
    Call<Todo> postTodo(@Body Todo todo);

    @PUT("todos/{id}/")
    Call<Todo> putTodo(@Path("id") int id, @Body Todo todo);

    @PATCH("todos/{id}/")
    Call<Todo> patchTodo(@Path("id") int id, @Body Todo todo);

    @DELETE("todos/{id}/")
    Call<Todo> deleteTodo(@Path("id") int id);

    @DELETE("todos/")
    Call<Todo> deleteAllTodos();
}
