package com.comp259.contactlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Activity2 extends Activity implements View.OnClickListener {


    public EditText ETfName, ETlName, ETphoneNumber, ETemail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);

        Bundle bundle = getIntent().getExtras();
        int ref = bundle.getInt("Ref");

        if(ref >= 0) {
            ETfName = (EditText) findViewById(R.id.txtFName);
            ETlName = (EditText) findViewById(R.id.txtLName);
            ETphoneNumber = (EditText) findViewById(R.id.txtPNumber);
            ETemail = (EditText) findViewById(R.id.txtEmail);

            ETfName.setText(bundle.getString("fName"));
            ETlName.setText(bundle.getString("lName"));
            ETphoneNumber.setText(bundle.getString("pNumber"));
            ETemail.setText(bundle.getString("email"));
        }
        else if(ref == -1){
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

        Toast.makeText(this, "Contact Deleted", Toast.LENGTH_LONG).show();

    }

    public void onSaveClick(View v) {

        Bundle bundle = getIntent().getExtras();
        int ID = bundle.getInt("Ref");

        EditText txtNewFname = (EditText)findViewById(R.id.txtFName);
        String newFname = txtNewFname.getText().toString();

        EditText txtNewLname = (EditText)findViewById(R.id.txtLName);
        String newLname = txtNewLname.getText().toString();

        EditText txtNewPNumber = (EditText)findViewById(R.id.txtPNumber);
        String newPNumber = txtNewPNumber.getText().toString();

        EditText txtnewEmail = (EditText)findViewById(R.id.txtEmail);
        String newEmail = txtnewEmail.getText().toString();

        Intent intent = new Intent("android.intent.action.MAIN");

        Bundle newBundle = new Bundle();
        newBundle.putInt("Ref", ID);
        newBundle.putString("fName", newFname);
        newBundle.putString("lName", newLname);
        newBundle.putString("pNumber", newPNumber);
        newBundle.putString("email", newEmail);


        intent.putExtras(newBundle);

        if (ID == -1){
            setResult(-1,intent);
        }
        else {
            setResult(1, intent);
        }
        finish();
        Toast.makeText(this, "Contact Saved", Toast.LENGTH_LONG).show();
    }

    public void onClick(View view) {
    }
}
