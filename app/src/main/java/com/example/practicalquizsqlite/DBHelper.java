package com.example.practicalquizsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VER = 2;
    private static final String DATABASE_NAME = "schools.db";
    private static final String TABLE_SCHOOL = "school";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NUM_OF_STU = "num_of_stu";
    private static final String COLUMN_SCHOOL_NAME = "school_name";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_SCHOOL + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SCHOOL_NAME + " TEXT,"
                + COLUMN_NUM_OF_STU + " INTEGER)";
        db.execSQL(createNoteTableSql);
        Log.i("info", "created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHOOL);
        onCreate(db);
    }

    public void insertSchool(int numOfStudent, String schoolName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCHOOL_NAME, schoolName);
        values.put(COLUMN_NUM_OF_STU, numOfStudent);
        db.insert(TABLE_SCHOOL, null, values);
        db.close();
    }

    public ArrayList<School> getSchools() {
        ArrayList<School> schools = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_SCHOOL_NAME, COLUMN_NUM_OF_STU};
        Cursor cursor = db.query(TABLE_SCHOOL, columns, null, null,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String schName = cursor.getString(1);
                int noStudents = cursor.getInt(2);
                School school  = new School(id, noStudents, schName);
                schools.add(school);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return schools;
    }
}
