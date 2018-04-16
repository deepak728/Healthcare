package com.example.deepak.healthcare;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class add_update extends AppCompatActivity {

    DBhelper1 helper=new DBhelper1(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_update);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Add Data");


        Button add_butt=(Button)findViewById(R.id.button);



        add_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText dis_text=(EditText)findViewById(R.id.add_dis);
                EditText symp_text=(EditText)findViewById(R.id.add_symp);
                EditText med_text=(EditText)findViewById(R.id.add_med);
                EditText pre_text=(EditText)findViewById(R.id.add_pre);


                String dis_add=dis_text.getText().toString();
                String symp_add=symp_text.getText().toString();
                String med_add=med_text.getText().toString();
                String pre_add=pre_text.getText().toString();

                if(!dis_add.matches("")&&!symp_add.matches("")&&!med_add.matches("")&&!pre_add.matches("")){

                    String msg="Your data is submitted and it will be added after verification.";
                    inti_dia(msg);
                }
                else{
                    Toast temp=Toast.makeText(add_update.this,"Please fill all fields",Toast.LENGTH_LONG);
                    temp.show();
                }


            }
        });


    }

    public void inti_dia(String msg){
        final Dialog dialog3 = new Dialog(add_update.this);
        dialog3.setContentView(R.layout.pop_up_layout);
        dialog3.setCancelable(true);

        dialog3.show();
        TextView tx=(TextView) dialog3.findViewById(R.id.text_dis);
        tx.setText(msg, TextView.BufferType.SPANNABLE);
        ImageView cancel=(ImageView) dialog3.findViewById(R.id.close);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog3.dismiss();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
