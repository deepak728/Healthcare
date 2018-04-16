package com.example.deepak.healthcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class profile extends AppCompatActivity {

    DatabaseHelper helper=new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Profile");
        SharedPreferences pref = getSharedPreferences("MyprefsFile",MODE_PRIVATE);
        String username = pref.getString("emailKey", null);
        String password = pref.getString("passKey", null);

        ImageView edit =(ImageView) findViewById(R.id.edit_image);
        TextView get_name=(TextView)findViewById(R.id.name_about);
        TextView get_email=(TextView)findViewById(R.id.email_about);
        TextView get_age=(TextView)findViewById(R.id.age_about);
        TextView get_bg=(TextView)findViewById(R.id.bg_about);
        TextView get_sex=(TextView)findViewById(R.id.sex_about);

        String data=helper.getUserDetails(username);

        String[] info= data.split(",");

        get_name.setText(info[1]);
        get_email.setText(info[0]);
        get_age.setText(info[2]);
        get_bg.setText(info[3]);
        get_sex.setText(info[4]);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),sign_up.class);
                startActivity(i);
            }
        });

//        log_out.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                 boolean lv=true;
//                Intent i = new Intent(getApplicationContext(),MainActivity.class);
//                i.putExtra("log_out_val", lv);
//                startActivity(i);
//            }
//        });
//
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
