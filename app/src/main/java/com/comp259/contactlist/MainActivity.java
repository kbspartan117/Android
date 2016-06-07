package com.comp259.contactlist;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class MainActivity extends ListActivity{

	DBAdapter db = new DBAdapter(this);
	ListView listView;
	Context myContext=null;
    ArrayList<Contact> contactList = new ArrayList<Contact>();
    ArrayAdapter<Contact> adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myContext = this.getApplicationContext();


		try {
			String destPath = "/data/data/" + getPackageName() +
					"/databases";
			File f = new File(destPath);
			if (!f.exists()) {
				f.mkdirs();
				f.createNewFile();

				//---copy the db from the assets folder into
				// the databases folder---
				CopyDB(getBaseContext().getAssets().open("mydb"),
						new FileOutputStream(destPath + "/MyDB"));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//---get all contacts---
		db.open();
		Cursor c = db.getAllContacts();
		if (c.moveToFirst())
		{
			do {
				DisplayContact(c);
			} while (c.moveToNext());
		}
		//db.close();
	}

	public void CopyDB(InputStream inputStream,
					   OutputStream outputStream) throws IOException {
		//---copy 1K bytes at a time---
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, length);
		}
		inputStream.close();
		outputStream.close();
	}
	public void DisplayContact(Cursor c)
	{
		/*Toast.makeText(this, "id: " + c.getString(0) + "\n" + "Name: " + c.getString(1) + "\n" +
				"Email:  " + c.getString(2), Toast.LENGTH_LONG).show();*/
        Contact contact = new Contact(c.getString(1), c.getString(2), c.getString(3), c.getString(4));
        contactList.add(contact);
        listView = getListView();
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,contactList);
        listView.setAdapter(adapter);
	}



	public void onClickNew(View v) {

            Intent intent = new Intent("com.comp259.contactlist.Activity2");
            Bundle bundle = new Bundle();
			bundle.putInt("Ref", -1);
			intent.putExtras(bundle);
			startActivityForResult(intent, 1);

		}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data ) {

        Bundle updateBundle = data.getExtras();
        int Id = updateBundle.getInt("Ref");
        String newFname = updateBundle.getString("fName");
        String newLname = updateBundle.getString("lName");
        String newpNumber = updateBundle.getString("pNumber");
        String newEmail = updateBundle.getString("email");
        Contact c = new Contact(newFname, newLname, newpNumber, newEmail);

        if (resultCode == -1) {

            db.insertContact(newFname, newLname, newpNumber, newEmail);
            updateDisplay();

        } else if (resultCode == 1) {

            db.updateContact(Id, newFname, newLname, newpNumber, newEmail);
            updateDisplay();

        } else if (resultCode == 2) {

            db.deleteContact(Id);
            updateDisplay();

        }
    }


	@Override
	protected void onListItemClick(ListView lv, View v, int pos, long id ){
		super.onListItemClick(lv, v, pos, id);

        Intent i = new Intent("com.comp259.contactlist.Activity2");
        Bundle bundle = new Bundle();

        bundle.putInt("Ref", pos);
        bundle.putString("fName", contactList.get(pos).getFirstName());
        bundle.putString("lName", contactList.get(pos).getLastName());
        bundle.putString("pNumber", contactList.get(pos).getPhoneNumber());
        bundle.putString("email", contactList.get(pos).getEmailAddress());

        i.putExtras(bundle);

        startActivityForResult(i, 1);

	}

	public void updateDisplay() {

        db.open();
        Cursor c = db.getAllContacts();
        if (c.moveToFirst())
        {
            do {
                DisplayContact(c);
            } while (c.moveToNext());
        }
        db.close();

        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,contactList);
        listView.setAdapter(adapter);

	}
}
