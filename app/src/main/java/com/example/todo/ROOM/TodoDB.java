package com.example.todo.ROOM;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = Todo.class, version = 4)
public abstract class TodoDB extends RoomDatabase {

    private static TodoDB DB;

    public abstract TodoDAO todoDAO();

    public static synchronized TodoDB getDB(Context context) {
        if (DB == null){
            DB = Room.databaseBuilder(context.getApplicationContext(),
                    TodoDB.class, "todo_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return DB;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

}
