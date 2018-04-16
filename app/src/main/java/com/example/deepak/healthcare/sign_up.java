package com.example.deepak.healthcare;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

public class sign_up extends AppCompatActivity {

      DatabaseHelper helper=new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_sign_up);

        Button butt=(Button)findViewById(R.id.button);
        butt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText name =(EditText)findViewById(R.id.name);
                EditText email =(EditText)findViewById(R.id.email);
                EditText pass =(EditText)findViewById(R.id.password);
                EditText age =(EditText)findViewById(R.id.age);

                Spinner bg_spinner = (Spinner)findViewById(R.id.blood_group);
                Spinner sex_spinner = (Spinner)findViewById(R.id.sex);

                String blood_gr = bg_spinner.getSelectedItem().toString();
                String sex =sex_spinner.getSelectedItem().toString();
                String namestr =name.getText().toString();
                String emailstr=email.getText().toString();
                String passstr=pass.getText().toString();
                String agestr=age.getText().toString();
                if(!blood_gr.equals("")&&!sex.equals("")&&!namestr.equals("")&&!emailstr.equals("")&&!passstr.equals("")&&!agestr.equals("")){

                    Contact c=new Contact();
                    c.setName(namestr);
                    c.setEmail(emailstr.trim());
                    c.setPass(passstr.trim());
                    c.setAge(agestr);
                    c.setBg(blood_gr);
                    c.setSex(sex);

                    helper.insertContact(c);

                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                }
                else{
                    Toast temp=Toast.makeText(sign_up.this,"Please fill all fields",Toast.LENGTH_SHORT);
                    temp.show();

                }


            }
        });
    }
}
