package com.example.todo;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import com.example.todo.Widgets.DatePickerFragment;
import com.example.todo.Widgets.TimePickerFragment;
import com.example.todo.addressbook.AddressbookSelectActivity;
import com.example.todo.addressbook.model.Contact;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditAddTodoActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private static final int CONTACT_SELECTED = 0;
    protected static String logger = EditAddTodoActivity.class.getSimpleName();
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


    private static final int MY_PERMISSION_REQUEST_READ_CONTACTS = 17;
    private List<Contact> contactsList = new ArrayList<Contact>();

    private Button setTime;
    private Button setDate;
    private EditText edit_name;
    private EditText edit_content;
    private CheckBox set_importaint;
    private CheckBox set_done;
    private Calendar calendar = Calendar.getInstance();
    private Button add_Contact;
    private ListView contactsListView;
    private ArrayAdapter<Contact> contactsListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indialog_layout);
        setTime = findViewById(R.id.set_due_time);
        setDate = findViewById(R.id.set_due_date);
        add_Contact = findViewById(R.id.add_contact);
        edit_name = findViewById(R.id.edit_Name);
        edit_content = findViewById(R.id.edit_Beschreibung);
        set_importaint = findViewById(R.id.set_important);
        set_done = findViewById(R.id.set_done);


        contactsListView = findViewById(R.id.embeddedContactsList);

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
            Log.d("ZUTUN Angekommen", String.valueOf(args.getInt(EXTRA_HOUR)));
            Log.d("Zutun 3", String.valueOf(args.getInt(EXTRA_HOUR)));

            calendar.set(args.getInt(EXTRA_YEAR), args.getInt(EXTRA_MONTH),
                    args.getInt(EXTRA_DAY), args.getInt(EXTRA_HOUR),
                    args.getInt(EXTRA_MINUTE));

            Log.d("Zutun Kalender", String.valueOf(calendar.get(calendar.MINUTE)));

            Log.d("Zutun JAHR", String.valueOf(calendar.get(calendar.YEAR)));


            Toast.makeText(this, DateFormat.getDateInstance().format(calendar.getTime()), Toast.LENGTH_SHORT).show();
        } else {
            setTitle("Add Todo");
            set_done.setVisibility(View.GONE);
        }

        if (intent.hasExtra(EXTRA_ID)) {

            setTime.setText(String.format("%02d", calendar.get(calendar.HOUR)) + ":" + String.format("%02d", calendar.get(calendar.MINUTE)));
            setDate.setText(DateFormat.getDateInstance().format(calendar.getTime()));
        } else {
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

        add_Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestContactReadPermission();
            }
        });

        //contactsList.add(new Contact());
        contactsListAdapter = new ArrayAdapter<Contact>(this, R.layout.addressbookitem_in_listview, contactsList) {
            @Override
            public View getView(final int position, View view, ViewGroup parent) {

                final ViewGroup listItemView = (ViewGroup) (view == null
                        ? getLayoutInflater().inflate(R.layout.addressbookitem_in_listview, null) : view);

                TextView nameView = (TextView) listItemView.findViewById(R.id.contactName);

                final Contact contactItem = contactsList.get(position);

                if (contactItem.getName() != null) {
                    nameView.setText(contactItem.getName());
                } else if (contactItem.getEmails().size() > 0) {
                    nameView.setText(String.valueOf(contactItem.getEmails().get(0)));
                    ;
                } else {
                    nameView.setText("N.N.");
                }
                return listItemView;
            }
        };

        // set the adapter on the list view
        contactsListView.setAdapter(contactsListAdapter);

        // set a listener that clicks on an item from the list
        contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View itemView, int itemPosition, long itemId) {
                Log.i(logger, "onItemClick: position is: " + itemPosition + ", id is: " + itemId);
                Contact item = contactsList.get(itemPosition);
                contactsListAdapter.remove(item);
            }
        });
    }


    private void requestContactReadPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSION_REQUEST_READ_CONTACTS);
            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an app-defined int constant ("request-code"). The callback method gets the result of the request.
        } else {
            // already granted, or old runtime without individual permissions
            //initContacts();
            Intent intent = new Intent(this, AddressbookSelectActivity.class);
            startActivityForResult(intent, AddressbookSelectActivity.SELECT_CONTACT);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // proceed as required by app logic
                    Intent intent = new Intent(this, AddressbookSelectActivity.class);
                    startActivityForResult(intent, AddressbookSelectActivity.SELECT_CONTACT);
                } else {
                    // do something reasonable if permissions are denied
                    Toast toast = Toast.makeText(getApplicationContext(), "Permission not granted", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        Log.d("Stunden", String.valueOf(calendar.get(calendar.HOUR_OF_DAY)));
        setTime.setText(String.format("%02d", calendar.get(calendar.HOUR)) + ":" + String.format("%02d", calendar.
                get(calendar.MINUTE)));
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

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            Bundle bundle = data.getExtras();

            if ((requestCode == CONTACT_SELECTED) && (resultCode == RESULT_OK)) {
                Contact c = (Contact) bundle.get(AddressbookSelectActivity.RESPONSE_ENTRY);
                if (!contactsList.contains(c)) {
                    contactsListAdapter.add(c);
                }
            }
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
    public boolean onOptionsItemSelected (@NonNull MenuItem item){
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