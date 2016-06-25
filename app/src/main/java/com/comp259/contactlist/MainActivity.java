package com.comp259.contactlist;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
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

    public DBAdapter db;
    public Cursor dbCursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBAdapter(this);

        try {
            String destPath = "/data/data/" + getPackageName() +
                    "/databases";
            File f = new File(destPath);
            if (!f.exists()) {
                f.mkdirs();
                f.createNewFile();

                CopyDB(getBaseContext().getAssets().open("mydb"),
                        new FileOutputStream(destPath + "/MyDB"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        db.open();
        UpdateDisplay();
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


    public void onClickNew(View v) {

        Intent intent = new Intent("com.comp259.contactlist.Activity2");
        Bundle bundle = new Bundle();
        bundle.putInt("requestCode", -1);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data ) {

        UpdateDisplay();

    }


    @Override
    protected void onListItemClick(ListView lv, View v, int pos, long id ){
        super.onListItemClick(lv, v, pos, id);

        dbCursor.moveToPosition(pos);
        Intent i = new Intent("com.comp259.contactlist.Activity2");
        Bundle b = new Bundle();
        int requestCode = 1;

        b.putInt("requestCode", requestCode);
        b.putInt(db.KEY_ROWID, dbCursor.getInt(dbCursor.getColumnIndexOrThrow(db.KEY_ROWID)));
        b.putString(db.KEY_FNAME, dbCursor.getString(dbCursor.getColumnIndexOrThrow(db.KEY_FNAME)));
        b.putString(db.KEY_LNAME, dbCursor.getString(dbCursor.getColumnIndexOrThrow(db.KEY_LNAME)));
        b.putString(db.KEY_PNUMBER, dbCursor.getString(dbCursor.getColumnIndexOrThrow(db.KEY_PNUMBER)));
        b.putString(db.KEY_EMAIL, dbCursor.getString(dbCursor.getColumnIndexOrThrow(db.KEY_EMAIL)));

        i.putExtras(b);
        startActivity(i);


    }

    private void UpdateDisplay(){
        dbCursor = db.getAllContacts();
        startManagingCursor(dbCursor);
        String[] from = new String[]{DBAdapter.KEY_FNAME, DBAdapter.KEY_LNAME};
        int[] to = new int[]{R.id.display_fname, R.id.display_lname};
        SimpleCursorAdapter contacts = new SimpleCursorAdapter(this, R.layout.display_contact, dbCursor, from, to);
        setListAdapter(contacts);
    }
}
