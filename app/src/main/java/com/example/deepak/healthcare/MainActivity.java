package com.example.deepak.healthcare;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {


    public static final String MYPrefrences="MyprefsFile";
    public static final String email_pref="emailKey";
    public static final String pass_pref="passKey";
    SharedPreferences sharedpreferences;
    String got_pass="";
    DatabaseHelper helper=new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        Stetho.initializeWithDefaults(this);

        setContentView(R.layout.activity_main);




        sharedpreferences=getSharedPreferences(MYPrefrences, Context.MODE_PRIVATE);

        SharedPreferences pref = getSharedPreferences("MyprefsFile",MODE_PRIVATE);
        String get_usr = pref.getString("emailKey", null);
         String get_pass = pref.getString("passKey", null);

         got_pass=helper.searchPass(get_usr);


        if(get_usr==null||get_pass==null||got_pass==null){
            get_usr="a";
            get_pass="b";
            got_pass="c";
        }

        if(get_pass.equals(got_pass)){

            Intent i = new Intent(getApplicationContext(),main_page.class);
            finishAffinity();
            startActivity(i);
        }

        Button butt=(Button)findViewById(R.id.log_in);

        butt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                 EditText email=(EditText)findViewById(R.id.email_id);
                 EditText pass=(EditText)findViewById(R.id.pass_word);





                String emailstr=email.getText().toString();
                String passstr=pass.getText().toString();



                if(!emailstr.isEmpty()&&!passstr.isEmpty()){

                    String   password =helper.searchPass(emailstr.trim());

                    if(passstr.trim().equals(password.trim())){

                        Toast temp=Toast.makeText(MainActivity.this,"LogIn Successful",Toast.LENGTH_SHORT);
                        temp.show();

                        SharedPreferences.Editor editor=sharedpreferences.edit();
                        editor.putString(email_pref,emailstr);
                        editor.putString(pass_pref,passstr);
                        editor.commit();

                        Intent i = new Intent(getApplicationContext(),main_page.class);
                        finishAffinity();
                        startActivity(i);
                    }
                    else{
                        Toast temp=Toast.makeText(MainActivity.this,"UserName Password incorrect ",Toast.LENGTH_SHORT);
                        temp.show();

                    }
                }
                else{
                    Toast temp=Toast.makeText(MainActivity.this,"Enter Credentials",Toast.LENGTH_SHORT);
                    temp.show();
                }





            }
        });

        TextView forgt_pass=(TextView)findViewById(R.id.forgot_pass);
        forgt_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast temp=Toast.makeText(MainActivity.this,"Please Contact Developer",Toast.LENGTH_SHORT);
                temp.show();
            }
        });

        TextView sign_up=(TextView)findViewById(R.id.sign_up);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),sign_up.class);
                startActivity(i);
            }
        });

    }
}
