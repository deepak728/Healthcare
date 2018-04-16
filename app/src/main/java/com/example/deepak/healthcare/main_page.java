package com.example.deepak.healthcare;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;

public class main_page extends AppCompatActivity {

    DBhelper1 helper=new DBhelper1(this);
    DBhelper2 helper2=new DBhelper2(this);



    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    LinearLayout popUp;
    ImageView inside_popUp_close;
    TextView popUp_text;
    private PopupWindow pwindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main_page);

        int no_of_data=helper.getdatacount();
        if(no_of_data==0){
            try {
                helper.insertDisease(this);
                helper2.insertDisease(this);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        ImageView dis_search=(ImageView) findViewById(R.id.dis_search);
        ImageView symp_search= (ImageView) findViewById(R.id.symp_search);
        ImageView hospitals=(ImageView)findViewById(R.id.hospital);
        ImageView profile=(ImageView) findViewById(R.id.profile);
        ImageView search_history= (ImageView) findViewById(R.id.search_history);
        ImageView about=(ImageView) findViewById(R.id.about);
        ImageView feedback= (ImageView) findViewById(R.id.feedback);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),profile.class);
                startActivity(i);
            }
        });
        hospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q=hospitals+near+me"));
                startActivity(browserIntent);
            }
        });
        search_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),add_update.class);
                startActivity(i);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),About.class);
                startActivity(i);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(),feedback.class);
//                startActivity(i);
                shareToGMail("HealthCare App Feedback"," ");
            }
        });

        final ListView listview=(ListView)findViewById(R.id.list);

        String[] items ={""};
        arrayList=new ArrayList<>(Arrays.asList(items));


        dis_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText dis=(EditText)findViewById(R.id.dis);
                 String disease=dis.getText().toString();
                adapter=new ArrayAdapter<String>(main_page.this,R.layout.listview_content,R.id.disease,arrayList);
                listview.setAdapter(adapter);
                String data=helper.getInfo(disease);
                adapter.clear();
                adapter.notifyDataSetChanged();
                StringBuilder sb1 = getStringBuilder(data);

                arrayList.add(String.valueOf(sb1));
                adapter.notifyDataSetChanged();

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                       String listView_string= String.valueOf((listview.getItemAtPosition(position)));
                        inti_dia(listView_string);


                    }});

            }
        });

        symp_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText sym=(EditText)findViewById(R.id.symp);
                String symptoms=sym.getText().toString();
                String[] symptoms_array= symptoms.split(",");
                List temp_list=new ArrayList();
                List list1=new ArrayList();
                List list2=new ArrayList();


                String[] items1 = {""};
                ArrayList<String> dis_array = new ArrayList<>(Arrays.asList(items1));
                adapter = new ArrayAdapter<String>(main_page.this, R.layout.listview_content, R.id.disease, dis_array);
                listview.setAdapter(adapter);


                for(int k=0;k<symptoms_array.length;k++) {

                    String symp_data = helper2.getInfo(symptoms_array[k]);
                    String  splitted[] = symp_data.split(";");

                    for(int i = 0; i < splitted.length; i++){
                        list2.add(splitted[i]);
                    }

                    if(list1.size()==0)
                        list1.addAll(list2);

                        temp_list.clear();
                        temp_list.addAll(list1);
                        temp_list.retainAll(list2);
                    if(temp_list.size()!=0){
                        list1.retainAll(list2);
                    }


                   list2.clear();
                }

                for (int m = 0; m< list1.size(); m++) {
                    String dis_data = helper.getInfoByDisIndex((String) list1.get(m));
                    StringBuilder sb = getStringBuilder(dis_data);

                        dis_array.add(String.valueOf(sb));
                    listview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        String listView_string= String.valueOf((listview.getItemAtPosition(position)));
                        inti_dia(listView_string);


                    }});
            }
        });




    }

    @NonNull
    private StringBuilder getStringBuilder(String dis_data) {
        StringBuilder sb=new StringBuilder();
        SpannableStringBuilder builder = new SpannableStringBuilder();

        sb.append("");
        String ddata[]=dis_data.split(",");

        for(int n=0;n<ddata.length;n++){
            String temp_[]=ddata[n].split(";");
            for(int p=0;p<temp_.length;p++){
                if(p==temp_.length-1)
                sb.append(""+temp_[p]+" ");
                else
                    sb.append(""+temp_[p]+", ");


            }
            if((n==0&&temp_[n]!="Data Not Found")){
                sb.append("\n");
                String red="Symptoms: ";
                SpannableString redSpannable= new SpannableString(red);
                redSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, red.length(), 0);
                sb.append(red);
            }
            else if(n==1){
                sb.append("\n");
                String red="Medicines: ";
                SpannableString redSpannable= new SpannableString(red);
                redSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, red.length(), 0);
                sb.append(red);
            }
            else if (n==2){
                sb.append("\n");
                String red="Precautions: ";
                SpannableString redSpannable= new SpannableString(red);
                redSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, red.length(), 0);
                sb.append(red);
            }

        }
        return sb;
    }


    public void inti_dia(String str){
        final Dialog dialog3 = new Dialog(main_page.this);
        dialog3.setContentView(R.layout.pop_up_layout);
        dialog3.setCancelable(true);
        dialog3.show();
        TextView tx=(TextView) dialog3.findViewById(R.id.text_dis);
        tx.setText(str, TextView.BufferType.SPANNABLE);
        ImageView cancel=(ImageView) dialog3.findViewById(R.id.close);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog3.dismiss();
            }
        });
    }


    public void shareToGMail( String subject, String content) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"npe.deepak@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        final PackageManager pm = this.getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
        ResolveInfo best = null;
        for(final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                best = info;
        if (best != null)
            emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
        this.startActivity(emailIntent);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();

    }
}
