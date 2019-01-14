package com.example.josh.joshtrainingapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

    public class DatabaseHelper extends SQLiteOpenHelper {

        // Table Name
        public static final String TABLE_NAME = "DIARY";

        // Table columns
        public static final String _ID = "_id";
        public static final String NOTE = "note";
        public static final String TITLE = "title";
        public static final String DATE = "date";

        // Database Information
        public static final String DB_NAME = "DIARY.DB";

        // database version
        public static final int DB_VERSION = 1;

        // Creating table query
        private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOTE + " TEXT, " + TITLE +" TEXT, " + DATE + " TEXT);";

        public DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }