package com.example.weatherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "historydb";
    public static final int DB_VERSION = 1;
    public static final String QUERY_CREATE_STUDENT_TABLE =
            "create table History(latitude real, longitude real)";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(QUERY_CREATE_STUDENT_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists History");
        onCreate(sqLiteDatabase);
    }
    long insertData(double latitude, double longitude) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("latitude", latitude);
        content.put("longitude", longitude);
        long id = db.insert("History", null, content);
        db.close();
        return id;
    }
    Cursor getAllData() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from History order by rowid desc", null);
        return cursor;
    }

    public void delete(double lat){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM History WHERE latitude = "+lat);
        db.close();
    }
}
