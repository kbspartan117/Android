package com.comp259.contactlist;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Activity2 extends Activity implements View.OnClickListener {


    public EditText ETfName, ETlName, ETphoneNumber, ETemail;
    public DBAdapter db;
    public Cursor dbCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);

        Bundle bundle = getIntent().getExtras();
        int req = bundle.getInt("requestCode");

        if(req >= 0) {
            ETfName = (EditText) findViewById(R.id.txtFName);
            ETlName = (EditText) findViewById(R.id.txtLName);
            ETphoneNumber = (EditText) findViewById(R.id.txtPNumber);
            ETemail = (EditText) findViewById(R.id.txtEmail);

            ETfName.setText(bundle.getString("fname"));
            ETlName.setText(bundle.getString("lname"));
            ETphoneNumber.setText(bundle.getString("pnumber"));
            ETemail.setText(bundle.getString("email"));
        }
        else if(req == -1){
            ETfName = (EditText) findViewById(R.id.txtFName);
            ETlName = (EditText) findViewById(R.id.txtLName);
            ETphoneNumber = (EditText) findViewById(R.id.txtPNumber);
            ETemail = (EditText) findViewById(R.id.txtEmail);

            ETfName.setText("");
            ETlName.setText("");
            ETphoneNumber.setText("");
            ETemail.setText("");
        }

    }


    public void onDeleteClick(View v){

        Bundle b = getIntent().getExtras();
        Intent i = new Intent();
        i.putExtras(b);

        setResult(2, i);

        finish();

        }

    public void onSaveClick(View v) {


        Bundle bundle = getIntent().getExtras();
        int ID = bundle.getInt("_id");
        int req = bundle.getInt("requestCode");

        EditText txtNewFname = (EditText) findViewById(R.id.txtFName);
        String newFname = txtNewFname.getText().toString();

        EditText txtNewLname = (EditText) findViewById(R.id.txtLName);
        String newLname = txtNewLname.getText().toString();

        EditText txtNewPNumber = (EditText) findViewById(R.id.txtPNumber);
        String newPNumber = txtNewPNumber.getText().toString();

        EditText txtnewEmail = (EditText) findViewById(R.id.txtEmail);
        String newEmail = txtnewEmail.getText().toString();

        Intent intent = new Intent("android.intent.action.MAIN");

        //dbCursor = db.getAllContacts();
        //db.updateContact(ID, newFname, newLname, newPNumber, newEmail);

        Bundle newBundle = new Bundle();
        newBundle.putInt("_id", ID);
        newBundle.putString("fname", newFname);
        newBundle.putString("lname", newLname);
        newBundle.putString("pnumber", newPNumber);
        newBundle.putString("email", newEmail);
        intent.putExtras(newBundle);

        if (req == -1){
            setResult(-1,intent);
        }
        else if(req == 1) {
            setResult(1, intent);
        }
        else if (req == 2){
            setResult(2, intent);
        }
        finish();
    }

    public void onClick(View view) {
    }
}
