package com.comp259.contactlist;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


public class MainActivity extends ListActivity{


	ListView listView;

	Context myContext=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myContext = this.getApplicationContext();


		listView = getListView();

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


	}


	@Override
	protected void onListItemClick(ListView lv, View v, int pos, long id ){
		super.onListItemClick(lv, v, pos, id);


	}

	public void saveChanges() {

	}
}
