package com.example.todo.addressbook.model;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactsAccessorImpl implements IContactsAccessor {

	protected static String logger = ContactsAccessorImpl.class.getSimpleName();

	/**
	 * the content resolver that is obtained from an activity
	 */
	private ContentResolver resolver;

	public ContactsAccessorImpl(ContentResolver resolver) {
		this.resolver = resolver;
	}

	@Override
	public List<Contact> readAllContacts() {
		// the list of contact objects
		List<Contact> contactObjs = new ArrayList<Contact>();

		/*
		 * query all contacts
		 */
		// we iterate over the contacts
		Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
				null, null, null, null);

		Log.i(logger, "queried contacts...");

		while (cursor.moveToNext()) {
			String contactId = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));
			Contact currentContact = new Contact();

			currentContact.setId(Long.parseLong(contactId));

			Log.i(logger, "got contactId: " + contactId);

			// set the contact name
			currentContact.setName(cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

			// this is for demonstrating the structure of the contacts
			// datamodel: we try to find out the raw contacts for the given
			// contact id (the implementation does not use this information!)
			Cursor rawContactsCursor = resolver.query(
					ContactsContract.RawContacts.CONTENT_URI, null,
					ContactsContract.Contacts._ID + " = " + contactId, null,
					null);

			// iterate over the rawContact entries that are associated with the
			// given contact, just to log the account_type and account_name
			// information associated with it
			while (rawContactsCursor.moveToNext()) {
				Log.d(logger,
						"raw account id for contact id "
								+ contactId
								+ " is: "
								+ rawContactsCursor.getString(rawContactsCursor
										.getColumnIndex(ContactsContract.RawContacts._ID)));
				Log.d(logger,
						"raw account type is: "
								+ rawContactsCursor.getString(rawContactsCursor
										.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_TYPE)));
				Log.d(logger,
						"raw account name is: "
								+ rawContactsCursor.getString(rawContactsCursor
										.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_NAME)));

			}

			contactObjs.add(currentContact);

			/*
			 * query the phones for each contact
			 */
			Cursor phones = resolver.query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							+ contactId, null, null);

			while (phones.moveToNext()) {
				String phoneNumber = phones
						.getString(phones
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				int phoneType = phones
						.getInt(phones
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA2));

				currentContact.addPhoneNumber(phoneNumber);

				Log.i(logger, "got phoneNumber: " + phoneNumber + " of type "
						+ phoneType);
			}

			phones.close();

			/*
			 * query the emails for each contact
			 */
			Cursor emails = resolver.query(
					ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = "
							+ contactId, null, null);
			while (emails.moveToNext()) {
				// This would allow you get several email addresses
				String emailAddress = emails
						.getString(emails
								.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA1));

				Log.i(logger, "got emailAddress: " + emailAddress);

				currentContact.addEmailAddress(emailAddress);
			}
			emails.close();
		}

		cursor.close();

		Log.i(logger, "contacts are: " + contactObjs);

		return contactObjs;
	}

	@Override
	public boolean createContact(Contact contact) {
		Log.i(logger, "saving new item: " + contact);

		// Prepare contact creation request
		//
		// Note: We use RawContacts because this data must be associated with a
		// particular account.
		// The system will aggregate this with any other data for this contact
		// and create a
		// coresponding entry in the ContactsContract.Contacts provider for us.
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

		// add the account_type and account_name
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.RawContacts.CONTENT_URI)
				// for account_name and account_type we use our own classes'
				// names
				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE,
						contact.getClass().getName())
				.withValue(ContactsContract.RawContacts.ACCOUNT_NAME,
						this.getClass().getName()).build());

		// add the display name, note that here and below we refer back to the
		// contact id that has been created by the first element of the
		// operations list (identified by '0'). With this id the data will be
		// associated.
		
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				.withValue(
						ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
						contact.getName()).build());

		// add the phone number(s)
		if (contact.getPhoneNumbers().size() > 0) {
			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(
							ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(
							ContactsContract.Data.MIMETYPE,
							ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
							String.valueOf(contact.getPhoneNumbers().get(0)))
					.withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
							ContactsContract.CommonDataKinds.Phone.TYPE_OTHER)
					.build());
		}

		// add the email address(es)
		if (contact.getEmails().size() > 0) {
			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(
							ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(
							ContactsContract.Data.MIMETYPE,
							ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Email.DATA,
							String.valueOf(contact.getEmails().get(0)))
					.withValue(ContactsContract.CommonDataKinds.Email.TYPE,
							ContactsContract.CommonDataKinds.Email.TYPE_OTHER)
					.build());
		}

		// then execute the query
		try {
			ContentProviderResult[] results = this.resolver.applyBatch(
					ContactsContract.AUTHORITY, ops);
			Log.i(logger,
					"results from inserting entry are: "
							+ Arrays.toString(results));

			// for determining the id that has been assigned to the object we
			// need to read out the uri and parse it
			long rawContactId = ContentUris.parseId(results[0].uri);
			Log.i(logger, "rawContactId assigned by content provider is: "
					+ rawContactId + ". Set it on the contact object");

			contact.setId(rawContactId);

			return true;
		} catch (Exception e) {
			Log.e(logger, e.getMessage(), e);

			return false;
		}
	}

	@Override
	public boolean deleteContact(Contact contact) {
		Log.i(logger, "trying to delete contact: " + contact);

		// we retrieve all raw contacts for the given contact id and delete
		// those ones
		List<String> rawContacts = new ArrayList<String>();
		Cursor rawContactsCursor = this.resolver.query(
				ContactsContract.RawContacts.CONTENT_URI, null,
				ContactsContract.Contacts._ID + " = " + contact.getId(), null,
				null);

		while (rawContactsCursor.moveToNext()) {
			Log.d(logger,
					"raw account id for contact id "
							+ contact.getId()
							+ " is: "
							+ rawContactsCursor.getString(rawContactsCursor
									.getColumnIndex(ContactsContract.RawContacts._ID)));
			Log.d(logger,
					"raw account type is: "
							+ rawContactsCursor.getString(rawContactsCursor
									.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_TYPE)));
			Log.d(logger,
					"raw account name is: "
							+ rawContactsCursor.getString(rawContactsCursor
									.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_NAME)));
			rawContacts.add(rawContactsCursor.getString(rawContactsCursor
					.getColumnIndex(ContactsContract.RawContacts._ID)));
		}
		rawContactsCursor.close();

		Log.i(logger,
				"about to delete raw contacts for contact id "
						+ contact.getId() + ": " + rawContacts);

		// as in entry creation, create a list of content provider operations
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		for (String currentDeleteId : rawContacts) {
			// we delete the raw contact entry
			ops.add(ContentProviderOperation
					.newDelete(ContactsContract.RawContacts.CONTENT_URI)
					.withSelection(ContactsContract.RawContacts._ID + "=?",
							new String[] { currentDeleteId }).build());
		}
		// also add the contact id itself
		ops.add(ContentProviderOperation
				.newDelete(ContactsContract.Contacts.CONTENT_URI)
				.withSelection(ContactsContract.Contacts._ID + "=?",
						new String[] { String.valueOf(contact.getId()) })
				.build());

		try {
			// then execute the query
			ContentProviderResult[] results = this.resolver.applyBatch(
					ContactsContract.AUTHORITY, ops);
			Log.i(logger,
					"got feedback from entry deletion: "
							+ Arrays.toString(results));

			if (results.length > 0 && results[0].count > 0) {
				return true;
			}

		} catch (Exception e) {
			Log.e(logger, e.getMessage(), e);
		}

		return false;

	}

}
