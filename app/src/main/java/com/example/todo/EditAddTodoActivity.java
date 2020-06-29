package com.example.todo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import com.example.todo.Widgets.DatePickerFragment;
import com.example.todo.Widgets.TimePickerFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class EditAddTodoActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    public static final String EXTRA_ID = "com.example.todo.extra_id";
    public static final String EXTRA_TITLE = "com.example.todo.extra_title";
    public static final String EXTRA_CONTENT = "com.example.todo.extra_content";
    public static final String EXTRA_IMPORTAINT = "com.example.todo.extra_importaint";
    public static final String EXTRA_YEAR = "com.example.todo.extra_year";
    public static final String EXTRA_MONTH = "com.example.todo.extra_month";
    public static final String EXTRA_DAY = "com.example.todo.extra_day";
    public static final String EXTRA_HOUR = "com.example.todo.extra_hour";
    public static final String EXTRA_MINUTE = "com.example.todo.extra_minute";
    public static final String EXTRA_DONE = "com.example.todo.extra_done";
    public static final String EXTRA_DELETE_FLAG = "com.example.todo.extra_flag";



    private Button setTime;
    private Button setDate;
    private EditText edit_name;
    private EditText edit_content;
    private CheckBox set_importaint;
    private CheckBox set_done;
    private Calendar calendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indialog_layout);
        setTime = findViewById(R.id.set_due_time);
        setDate = findViewById(R.id.set_due_date);
        edit_name = findViewById(R.id.edit_Name);
        edit_content = findViewById(R.id.edit_Beschreibung);
        set_importaint = findViewById(R.id.set_important);
        set_done = findViewById(R.id.set_done);



        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Todo");

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {

            setTitle("Edit Todo");
            Bundle args = intent.getExtras();
            edit_name.setText(args.getString(EXTRA_TITLE));
            edit_content.setText(args.getString(EXTRA_CONTENT));
            set_importaint.setChecked(args.getBoolean(EXTRA_IMPORTAINT));
            set_done.setChecked(args.getBoolean(EXTRA_DONE));
            Log.d("ZUTUN Angekommen",String.valueOf(args.getInt(EXTRA_HOUR)));
            Log.d("Zutun 3", String.valueOf(args.getInt(EXTRA_HOUR)));

            calendar.set(args.getInt(EXTRA_YEAR), args.getInt(EXTRA_MONTH),
                    args.getInt(EXTRA_DAY), args.getInt(EXTRA_HOUR),
                    args.getInt(EXTRA_MINUTE));

            Log.d("Zutun Kalender" , calendar.toString());

            Log.d("Zutun JAHR", String.valueOf(calendar.get(calendar.YEAR)) );


            Toast.makeText(this, DateFormat.getDateInstance().format(calendar.getTime()), Toast.LENGTH_SHORT).show();
        }
        else{
            setTitle("Add Todo");
            set_done.setVisibility(View.GONE);
        }

        if(intent.hasExtra(EXTRA_ID)){

            setTime.setText(String.format("%02d",calendar.get(calendar.HOUR))  + ":" + String.format("%02d", calendar.get(calendar.MINUTE)));
            setDate.setText(DateFormat.getDateInstance().format(calendar.getTime()));
        }else{
            setTime.setText("Set Due Time");
            setDate.setText("Set Due Date");
        }

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment(calendar.get(calendar.HOUR_OF_DAY),
                        calendar.get(calendar.MINUTE));
                timePicker.show(getSupportFragmentManager(), "Timepicker");
            }
        });

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment(calendar.get(calendar.DAY_OF_MONTH), calendar.get(calendar.MONTH),
                        calendar.get(calendar.YEAR));
                datePicker.show(getSupportFragmentManager(), "Datepicker");
            }
        });


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        Toast.makeText(this,  String.format("%02d", calendar.HOUR) ,Toast.LENGTH_SHORT).show();
        setTime.setText(String.format("%02d", calendar.HOUR) + ":" + String.format("%02d", calendar.MINUTE));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.d("zutun year", String.valueOf(year));
        Log.d("zutun mont", String.valueOf(month));
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setDate.setText(DateFormat.getDateInstance().format(calendar.getTime()));
    }

    private void saveTodo(boolean flag) {
        String title = edit_name.getText().toString();
        String content = edit_content.getText().toString();
        boolean importaint = set_importaint.isChecked();
        boolean done = set_done.isChecked();

        if (title.trim().isEmpty() || content.trim().isEmpty()) {
            Toast.makeText(this, "Please add Title and Content", Toast.LENGTH_LONG).show();
            return;
        }

        Intent i = new Intent();
        i.putExtra(EXTRA_TITLE, title);
        i.putExtra(EXTRA_CONTENT, content);
        i.putExtra(EXTRA_IMPORTAINT, importaint);
        i.putExtra(EXTRA_YEAR, calendar.get(Calendar.YEAR));
        i.putExtra(EXTRA_MONTH, calendar.get(Calendar.MONTH));
        i.putExtra(EXTRA_DAY, calendar.get(Calendar.DAY_OF_MONTH));
        i.putExtra(EXTRA_HOUR, calendar.get(Calendar.HOUR));
        i.putExtra(EXTRA_MINUTE, calendar.get(Calendar.MINUTE));
        i.putExtra(EXTRA_DONE, done);
        if (flag)
            i.putExtra(EXTRA_DELETE_FLAG, true);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            i.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, i);
        finish();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_todo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                saveTodo(false);
                return true;
            case R.id.delete2:
                saveTodo(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
