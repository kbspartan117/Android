package com.comp259.contactlist;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Activity2 extends Activity implements View.OnClickListener {


    public EditText ETfName,ETlName,ETphoneNumber,ETemail;
    public DBAdapter db = new DBAdapter(this);
    public Cursor dbCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);
        initializeBoxes();
        Bundle bundle = getIntent().getExtras();
        int req = bundle.getInt("requestCode");

        if(req >= 0) {
            ETfName.setText(bundle.getString("fname"));
            ETlName.setText(bundle.getString("lname"));
            ETphoneNumber.setText(bundle.getString("pnumber"));
            ETemail.setText(bundle.getString("email"));
        }
        else if(req == -1){

            ETfName.setText("");
            ETlName.setText("");
            ETphoneNumber.setText("");
            ETemail.setText("");
        }

    }


    public void onDeleteClick(View v){

        Bundle b = getIntent().getExtras();
        int ID = b.getInt("_id");
        db.open();
        db.deleteContact(ID);
        db.close();
        finish();

        }

    public void onSaveClick(View v) {



        Bundle bundle = getIntent().getExtras();
        int ID = bundle.getInt("_id");
        int req = bundle.getInt("requestCode");


        String newFname = ETfName.getText().toString();
        String newLname = ETlName.getText().toString();
        String newPNumber = ETphoneNumber.getText().toString();
        String newEmail = ETemail.getText().toString();



        if (req == -1){
            db.open();
            db.insertContact(newFname, newLname, newPNumber, newEmail);
            db.close();
        }
        else if(req == 1) {
            db.open();
            db.updateContact(ID, newFname, newLname, newPNumber, newEmail);
            db.close();
        }

        finish();
    }

    public void onClick(View view) {
    }
    public void initializeBoxes(){

        ETfName = (EditText) findViewById(R.id.txtFName);
        ETlName = (EditText) findViewById(R.id.txtLName);
        ETphoneNumber = (EditText) findViewById(R.id.txtPNumber);
        ETemail = (EditText) findViewById(R.id.txtEmail);
    }
}
