package com.example.todo.addressbook;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.todo.R;
import com.example.todo.addressbook.model.Contact;

public class AddressbookEntryActivity extends AppCompatActivity {

	/**
	 * the logger
	 */
	protected static String logger = AddressbookEntryActivity.class.getSimpleName();

	/**
	 * the argument for an addressbook entry to be edited
	 */
	public static final String ARG_ENTRY = "entry";

	/**
	 * the result param for an edited entry
	 */
	public static final String RESPONSE_ENTRY = ARG_ENTRY;

	/**
	 * the status for a successful edit
	 */
	public static final int RESULT_CODE_EDITED = 2;

	/**
	 * the entry to be edited
	 */
	private Contact editContact;

	/**
	 * the ui elements
	 */
	private EditText entryName;
	private EditText entryEmail;
	private EditText entryPhone;
	private Button saveEntry;

	/**
	 * the mode (edit vs. create)
	 */
	boolean editMode = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(logger, "onCreate()...");
		super.onCreate(savedInstanceState);

		// set the layout
		setContentView(R.layout.addressbookitemview);

		// initialise the ui elements
		entryName = (EditText) findViewById(R.id.entryName);
		entryEmail = (EditText) findViewById(R.id.entryEmail);
		entryPhone = (EditText) findViewById(R.id.entryPhone);
		saveEntry = (Button) findViewById(R.id.saveEntry);

		// try to read out the contact
		this.editContact = (Contact) getIntent().getSerializableExtra(ARG_ENTRY);
		if (this.editContact == null) {
			this.editContact = new Contact();
		} else {
			this.editMode = true;
		}

		// set a listener on the saveEntry button (we do not do any validation
		// here!)
		saveEntry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveEntry();
			}

		});

		// populate the ui elements
		if (editContact.getName() != null) {
			entryName.setText(editContact.getName());
		}
		if (editContact.getEmails().size() > 0) {
			entryEmail.setText(editContact.getEmails().get(0));
		}
		if (editContact.getPhoneNumbers().size() > 0) {
			entryPhone.setText(editContact.getPhoneNumbers().get(0));
		}

		// if we have editMode, we deactivate all inputs
		if (this.editMode) {
			entryName.setEnabled(false);
			entryEmail.setEnabled(false);
			entryPhone.setEnabled(false);
			saveEntry.setEnabled(false);
		}

	}

	private void saveEntry() {
		String editedName = entryName.getText().toString();
		if ("".equals(editedName)) {
			Toast.makeText(AddressbookEntryActivity.this, "Ein Name muss eingegeben werden!", Toast.LENGTH_SHORT)
					.show();
		} else {
			editContact.setName(editedName);
			String editedEmail = entryEmail.getText().toString();
			String editedPhone = entryPhone.getText().toString();
			if (!"".equals(editedEmail)) {
				editContact.addEmailAddress(entryEmail.getText().toString());
			}
			if (!"".equals(editedPhone)) {
				editContact.addPhoneNumber(entryPhone.getText().toString());
			}

			// create a result intent
			Intent resultIntent = new Intent();
			resultIntent.putExtra(RESPONSE_ENTRY, editContact);

			setResult(RESULT_CODE_EDITED, resultIntent);
			finish();
		}
	}
}
