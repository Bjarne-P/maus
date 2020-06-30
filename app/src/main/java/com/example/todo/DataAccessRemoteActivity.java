package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todo.webview.ChromeWebViewItemDetailsActivity;
import com.example.todo.webview.WebViewItemDetailsActivity;

/**
 * Provide the main menu for this demo application and allow to select the
 * particular data access way
 * 
 * @author Joern Kreutel
 * 
 */
public class DataAccessRemoteActivity extends AppCompatActivity {

	/**
	 * the logger
	 */
	protected static final String logger = DataAccessRemoteActivity.class
			.getName();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			// set the list view as content view
			setContentView(R.layout.listview);

			/*
			 * access the list view for the options to be displayed
			 */
			ListView listview = findViewById(R.id.list);

			// read out the options
			final String[] menuItems = getResources().getStringArray(
					R.array.main_menu);

			/*
			 * create an adapter that allows for the view to access the list's
			 * content and that holds information about the visual
			 * representation of the list items
			 */
			ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
					android.R.layout.simple_list_item_1, menuItems);

			// set the adapter on the list view
			listview.setAdapter(adapter);

			// set a listener that reacts to the selection of an element
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> adapterView,
						View selectedView, int itemPosition, long arg3) {

					Log.i(this.getClass().getName(),
							"got itemPosition selected: " + itemPosition);

					/*
					 * depending on the item position we set an integer value on
					 * the intent that allows the ItemListActivity to identify
					 * the remote access implementation
					 * 
					 * in two cases (selection of 2 and 3), we call the
					 * itemdetails view that uses web resources
					 */

					// the identifier of the remote access implementation
					int accessorId = -1;

					switch (itemPosition) {
					case 0:
						accessorId = R.integer.localAccessor;
						break;
					case 4:
						accessorId = R.integer.urlClass;
						break;
					case 5:
						accessorId = R.integer.apacheHttpClient;
						break;
					case 6:
						accessorId = R.integer.urlConnection;
						break;
					case 1:
						accessorId = R.integer.resteasyFramework;
						break;
					}
					
					Log.i(logger, "determined accessorId: " + accessorId);

					Intent intent = null;

					if (accessorId != -1) {
						intent = new Intent(DataAccessRemoteActivity.this,
								TodoListActivity.class);

					// put the accessor as an argument on the intent
					intent.putExtra("accessorId", accessorId);
					} else if (itemPosition == 2) {
						intent = new Intent(DataAccessRemoteActivity.this,
								WebViewItemDetailsActivity.class);
					} else {
						intent = new Intent(DataAccessRemoteActivity.this,
								ChromeWebViewItemDetailsActivity.class);
					}

					/*
					 * start the activity
					 */
					startActivity(intent);
				}

			});
		} catch (Exception e) {
			String err = "got an exception: " + e;
			Log.e(logger, err, e);
			((DataAccessRemoteApplication) getApplication()).reportError(this,
					err);
		}
	}

}