package com.example.josh.joshtrainingapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.josh.joshtrainingapp.Database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DBManager {

    private static DatabaseHelper dbHelper;

    private static Context context;

    private static SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public static void open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String note, String title) {
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.NOTE, note);
        contentValue.put(DatabaseHelper.TITLE, title);
        contentValue.put(DatabaseHelper.DATE, formattedDate);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.NOTE, DatabaseHelper.TITLE, DatabaseHelper.DATE };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchRow(int position) {

        String columns[] = new String[] { DatabaseHelper._ID, DatabaseHelper.NOTE, DatabaseHelper.TITLE, DatabaseHelper.DATE };
        String id[] = new String[] {Integer.toString(position)};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, "_id = ?"    , id, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(int _id, String note, String title) {
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NOTE, note);
        contentValues.put(DatabaseHelper.TITLE, title);
        contentValues.put(DatabaseHelper.DATE, formattedDate);
        return database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);

    }

    public void delete(int _id) {

        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

}
