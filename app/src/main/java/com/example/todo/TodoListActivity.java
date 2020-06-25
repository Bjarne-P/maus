package com.example.todo;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todo.ROOM.Todo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class TodoListActivity extends AppCompatActivity {
    public final static int add_todo_request = 1;
    public final static int edit_todo_request = 2;

    private TodoViewmodel todoViewmodel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        setTitle("Todolist by Bjarne Peuker");

        FloatingActionButton btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TodoListActivity.this, EditAddTodoActivity.class);
                startActivityForResult(intent, add_todo_request);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TodoAdapter adapter = new TodoAdapter();
        recyclerView.setAdapter(adapter);

        todoViewmodel = new ViewModelProvider(this).get(TodoViewmodel.class);
        todoViewmodel.getAllTodos().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                adapter.setTodos(todos);
            }
        });




        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,  ItemTouchHelper.RIGHT) {
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                todoViewmodel.delete(adapter.getTodoAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0 ,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Todo todo = adapter.getTodoAt(viewHolder.getAdapterPosition());
                if (todo.isImportaint())
                    todo.setImportaint(false);
                else todo.setImportaint(true);
                todoViewmodel.update(adapter.getTodoAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new TodoAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Todo todo) {
                Intent intent = new Intent(TodoListActivity.this, EditAddTodoActivity.class);
                intent.putExtra(EditAddTodoActivity.EXTRA_ID, todo.getId());
                intent.putExtra(EditAddTodoActivity.EXTRA_TITLE, todo.getTitle());
                intent.putExtra(EditAddTodoActivity.EXTRA_CONTENT, todo.getContent());
                intent.putExtra(EditAddTodoActivity.EXTRA_IMPORTAINT, todo.isImportaint());
                intent.putExtra(EditAddTodoActivity.EXTRA_YEAR, todo.getDue_year());
                intent.putExtra(EditAddTodoActivity.EXTRA_MONTH, todo.getDue_month());
                intent.putExtra(EditAddTodoActivity.EXTRA_DAY, todo.getDue_day());
                intent.putExtra(EditAddTodoActivity.EXTRA_HOUR, todo.getDue_hour());
                intent.putExtra(EditAddTodoActivity.EXTRA_DAY, todo.getDue_day());
                startActivityForResult(intent, edit_todo_request);
            }
        });


        //nachher neu machen
        /*ListView list = findViewById(R.id.list);
        Button b = findViewById(R.id.addButton);
        ArrayList<String> liste = new ArrayList<>();
        liste.add("1");
        liste.add("2");
        liste.add("3");

        ArrayAdapter listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, liste);
        list.setAdapter(listAdapter);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(TODO_List.this, EDIT_ADD_TODO.class);
               startActivity(intent);
            }
        });*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        if ((requestCode == add_todo_request) && (resultCode == RESULT_OK)) {
            String title = data.getStringExtra(EditAddTodoActivity.EXTRA_TITLE);
            String content = data.getStringExtra(EditAddTodoActivity.EXTRA_CONTENT);
            boolean importaint = bundle.getBoolean(EditAddTodoActivity.EXTRA_IMPORTAINT, false);
            int year = bundle.getInt(EditAddTodoActivity.EXTRA_YEAR, 2020);
            int month = bundle.getInt(EditAddTodoActivity.EXTRA_MONTH, 1);
            int day = bundle.getInt(EditAddTodoActivity.EXTRA_DAY, 1);
            int hour = bundle.getInt(EditAddTodoActivity.EXTRA_HOUR, 12);
            int minute = bundle.getInt(EditAddTodoActivity.EXTRA_MINUTE, 0);

            Todo todo = new Todo(title, content, importaint, minute, hour, day, month, year);
            todoViewmodel.insert(todo);

            Toast.makeText(this, "Todo saved", Toast.LENGTH_LONG).show();
        } else if ((requestCode == edit_todo_request) && (resultCode == RESULT_OK)) {
            int id = data.getIntExtra(EditAddTodoActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Couldn't update", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(EditAddTodoActivity.EXTRA_TITLE);
            String content = data.getStringExtra(EditAddTodoActivity.EXTRA_CONTENT);
            boolean importaint = bundle.getBoolean(EditAddTodoActivity.EXTRA_IMPORTAINT, false);
            int year = bundle.getInt(EditAddTodoActivity.EXTRA_YEAR, 2020);
            int month = bundle.getInt(EditAddTodoActivity.EXTRA_MONTH, 1);
            int day = bundle.getInt(EditAddTodoActivity.EXTRA_DAY, 1);
            int hour = bundle.getInt(EditAddTodoActivity.EXTRA_HOUR, 12);
            int minute = bundle.getInt(EditAddTodoActivity.EXTRA_MINUTE, 0);

            Todo todo = new Todo(title, content, importaint, minute, hour, day, month, year);
            todo.setId(id);
            todoViewmodel.update(todo);

            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "not saved", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater a = getMenuInflater();
        a.inflate(R.menu.delete_all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all:
                todoViewmodel.deleteAll();
                Toast.makeText(this, "All Todos deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
