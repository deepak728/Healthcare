package com.example.deepak.healthcare;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by deepak on 9/4/18.
 */

public class DBhelper1 extends SQLiteOpenHelper {
    String a,b;
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME ="disease.db";
    private static final String TABLE_NAME ="disease_db";
    private static final String COLUMN_ID ="id";
    private static final String COLUMN_DISEASE ="dis";
    private static final String COLUMN_SYMPTOMS ="symptoms";
    private static final String COLUMN_MEDICINE ="medicine";
    private static final String COLUMN_PRECAUTION ="precaution";

    SQLiteDatabase db;

    private static final String TABLE_CREATE= " create table disease_db (id text not null , " +
            "dis text not null, symptoms text not null , medicine text not null, precaution text not null ); ";






    public DBhelper1(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db=db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query =" DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }
    public void insertDisease(Context c) throws IOException {

        db=this.getWritableDatabase();

        String mCSVfile = "dis.csv";
        AssetManager manager = c.getAssets();
        InputStream inStream = null;
        try {
            inStream = manager.open(mCSVfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line = "";

        String columns = "id,dis,symptoms,medicine,precaution";
        String str1 = "INSERT INTO " + TABLE_NAME + " (" + columns + ") values(";
        String str2 = ");";

        db.beginTransaction();
        while ((line = buffer.readLine()) != null) {
            StringBuilder sb = new StringBuilder(str1);
            String[] str = line.split(",");
            sb.append("'" + str[0] + "',");
            sb.append("'" +str[1] + "',");
            sb.append("'" +str[2] + "',");
            sb.append("'" +str[3] + "',");
            sb.append("'" +str[4] + "'");
            sb.append(str2);
            db.execSQL(sb.toString());
        }
        db.setTransactionSuccessful();
        db.endTransaction();

    }

//     public void add_data( String add_dis,String add_symp,String add_medicine,String add_precaution){
////         db = this.getWritableDatabase();
////         ContentValues contentValues = new ContentValues();
////         contentValues.put(COLUMN_DISEASE, add_dis);
////         contentValues.put(COLUMN_SYMPTOMS, add_symp);
////         contentValues.put(COLUMN_MEDICINE, add_medicine);
////         contentValues.put(COLUMN_PRECAUTION, add_precaution);
////         db.insert(TABLE_NAME, null, contentValues);
////         db.close();
//
////         db = this.getReadableDatabase();
////         db.execSQL("INSERT INTO " + TABLE_NAME + "(" + COLUMN_DISEASE + "," + COLUMN_SYMPTOMS + "," + COLUMN_MEDICINE + "," + COLUMN_PRECAUTION + ") VALUES('" + add_dis + "','" + add_medicine + "')");
////         db.close();
//     }



    public String getInfo(String disease_name){
        db=this.getReadableDatabase();
        String query= " select dis,symptoms,medicine,precaution from "+ TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                a=cursor.getString(0);
                if(a.trim().equalsIgnoreCase(disease_name.trim())){
                    b="* "+cursor.getString(0)+","+cursor.getString(1)+","+cursor.getString(2)+","+cursor.getString(3);

                    break;
                }
                else
                    b="Data Not Found";
            }
            while(cursor.moveToNext());

        }

        return b;
    }

    public String getInfoByDisIndex(String disease_index){
             db=this.getReadableDatabase();
             String query="select id,dis,symptoms,medicine,precaution from "+TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                a=cursor.getString(0);
                if(a.trim().equals(disease_index.trim())){
                    b="* "+cursor.getString(1).trim()+","+cursor.getString(2).trim()+","+cursor.getString(3).trim()+","+cursor.getString(4).trim();
                    break;
                }
                else
                    b="Data Not Found";
            }
            while (cursor.moveToNext());
        }


     return b;
    }
  public int getdatacount(){
      int no=0;
      db=this.getReadableDatabase();
      String query="select id from "+TABLE_NAME;
      Cursor cursor =db.rawQuery(query,null);
      if(cursor.getCount()==0)
            no=0;
      else
          no=cursor.getCount();

      return no;
  }
}
