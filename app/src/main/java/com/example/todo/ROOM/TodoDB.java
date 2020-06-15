package com.example.todo.ROOM;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = Todo.class, version = 1)
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
            new PopulateDbAsyncTask(DB).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private TodoDAO todoDAO;

        private PopulateDbAsyncTask(TodoDB tododb){
            todoDAO = tododb.todoDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            todoDAO.insert(new Todo("Title1", "Content1", true, 2,12,23,10,2020));
            todoDAO.insert(new Todo("Title2", "Content2", false, 3,13,23,10,2020));
            todoDAO.insert(new Todo("Title3", "Content3", true, 4,14,23,10,2020));
            return null;
        }
    }
}
