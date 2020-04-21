package com.zaph.loginsignupdesign.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelperClass extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "student.db";
    public static final String TABLE_NAME = "class01_students";
    public static final String COL_ID = "ID";
    public static final String COL_NAME = "NAME";
    public static final String COL_GENDER = "GENDER";
    public static final String COL_PHONE = "PHONE";
    public static final String COL_EMAIL = "EMAIL";
    public static final String COL_BRANCH = "BRANCH";
    public static final String COL_YEAR = "YEAR";
    public static final String COL_ATTENDANCE = "ATTENDANCE";

    public DatabaseHelperClass(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable="CREATE TABLE "+TABLE_NAME+" ("+
                COL_ID +" VARCHAR(20) PRIMARY KEY,"+
                COL_NAME+" TEXT,"+
                COL_GENDER+ " TEXT,"+
                COL_PHONE+ " TEXT,"+
                COL_EMAIL+ " TEXT,"+
                COL_BRANCH+ " TEXT,"+
                COL_YEAR+ " TEXT,"+
                COL_ATTENDANCE+ " TEXT);";

        Log.i("DB",createTable);
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" drop table if exists "+TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String id,String name,String gender,String phone,String email,String branch,String year,String attendance){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID,id);
        contentValues.put(COL_NAME,name);
        contentValues.put(COL_GENDER,gender);
        contentValues.put(COL_PHONE,phone);
        contentValues.put(COL_EMAIL,email);
        contentValues.put(COL_BRANCH,branch);
        contentValues.put(COL_YEAR,year);
        contentValues.put(COL_ATTENDANCE,attendance);

        long result = db.insert(TABLE_NAME,null, contentValues);
        db.close();
        if(result == -1){
            return false;
        }else
            return true;
    }


    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(" select * from " + TABLE_NAME,null);
        //new String[]{COL_1,COL_2,COL_3,COL_4,COL_5,COL_6,COL_7,COL_8 in null
        return res;

    }

    public Boolean updateData(String id,String name,String gender,String phone,String email,String branch,String year,String attendance){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID,id);
        contentValues.put(COL_NAME,name);
        contentValues.put(COL_GENDER,gender);
        contentValues.put(COL_PHONE,phone);
        contentValues.put(COL_EMAIL,email);
        contentValues.put(COL_BRANCH,branch);
        contentValues.put(COL_YEAR,year);
        contentValues.put(COL_ATTENDANCE,attendance);
        long result = db.update(TABLE_NAME,contentValues," ID = ?", new String[] { id });

        db.close();
        if(result == -1){
            return false;
        }else
            return true;

    }

    public int deleteData(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        return  db.delete(TABLE_NAME," ID = ? " , new String[] { id });

    }

}
