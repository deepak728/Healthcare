package com.example.deepak.healthcare;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by deepak on 9/4/18.
 */

public class DBhelper2 extends SQLiteOpenHelper{
    String a,b;
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME ="symptoms.db";
    private static final String TABLE_NAME ="symptoms";
    private static final String COLUMN_ID ="id";
    private static final String COLUMN_SYMPTOM ="symp";
    private static final String COLUMN_DISEASE ="dis";

    SQLiteDatabase db;

    private static final String TABLE_CREATE= " create table symptoms (id text not null , " +
            "symp text not null, dis text not null); ";



    public DBhelper2(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
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

        db = this.getWritableDatabase();

        String mCSVfile = "symp.csv";
        AssetManager manager = c.getAssets();
        InputStream inStream = null;
        try {
            inStream = manager.open(mCSVfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line = "";

        String columns = "id,symp,dis";
        String str1 = "INSERT INTO " + TABLE_NAME + " (" + columns + ") values(";
        String str2 = ");";

        db.beginTransaction();
        while ((line = buffer.readLine()) != null) {
            StringBuilder sb = new StringBuilder(str1);
            String[] str = line.split(",");
            sb.append("'" + str[0] + "',");
            sb.append("'" + str[1] + "',");
            sb.append("'" + str[2]+ "'");
            sb.append(str2);
            db.execSQL(sb.toString());
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    public String getInfo(String symptom){
        db=this.getReadableDatabase();
        String query= " select symp ,dis from "+ TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                a=cursor.getString(0);
                if(a.trim().equalsIgnoreCase(symptom.trim())){
                    b=cursor.getString(1);
                    break;
                }
                else
                    b="Not Found";
            }
            while(cursor.moveToNext());

        }

        return b;

}
}
