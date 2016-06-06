package com.comp259.contactlist;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class MainActivity extends ListActivity{

	ArrayList<Contact> contactList = new ArrayList<Contact>();
	ArrayAdapter<Contact> adapter;

	private final static String NOTES="notes.txt";
	ListView listView;

	Context myContext=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myContext = this.getApplicationContext();

		try {
			InputStream in=openFileInput(NOTES);

			if (in!=null) {
				InputStreamReader tmp=new InputStreamReader(in);
				BufferedReader reader=new BufferedReader(tmp);
				String str;
				StringBuilder buf=new StringBuilder();

				while ((str = reader.readLine()) != null) {
					String[] info = str.split(";", 4);
					String fn = info[0];
					String ln = info[1];
					String ph = info[2];
					String em = info[3];

					Contact c = new Contact(fn,ln,ph,em);
					contactList.add(c);
				}

				in.close();
			}
		}

		catch (java.io.FileNotFoundException e) {
		}

		catch (Throwable t) {
			Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
		}

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

			contactList.add(c);
			saveChanges();
		}

		else if(resultCode == 1){

			contactList.set(Id, c);
			saveChanges();
		}

		else if(resultCode == 2){

			contactList.remove(Id);
			saveChanges();
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

	public void saveChanges(){

		try {
			OutputStreamWriter out = new OutputStreamWriter(openFileOutput(NOTES, 0));

			for(int i = 0; i < contactList.size(); i++) {
				out.write(contactList.get(i).getFirstName() + ";" + contactList.get(i).getLastName()
						+ ";" + contactList.get(i).getPhoneNumber() + ";" + contactList.get(i).getEmailAddress()
						+ "\r\n");

			}
			listView.setAdapter(adapter);

			out.flush();
			out.close();
		}
		catch (Throwable t) {
			Toast.makeText(this, "Exception: "+t.toString(), Toast.LENGTH_LONG).show();
		}
	}
}
