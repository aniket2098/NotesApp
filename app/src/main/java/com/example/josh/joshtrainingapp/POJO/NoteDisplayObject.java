package com.example.josh.joshtrainingapp.POJO;

import android.content.Context;
import android.database.Cursor;

import com.example.josh.joshtrainingapp.Database.DBManager;

import java.util.ArrayList;
import java.util.List;

public class NoteDisplayObject {

    String note;
    String date;
    String desc;
    String tag;
    Integer position;

    public NoteDisplayObject() {
    }

    public NoteDisplayObject(String note, String date, Integer position, String desc, String tag) {

        this.note = note;
        this.date = date;
        this.position = position;
        this.desc = desc;
        this.tag = tag;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }

    public Integer getPosition() {
        return position;
    }

    public String getDesc() {
        return desc;
    }

    public String getTag() {
        return tag;
    }

    public List<NoteDisplayObject> getNotes(Context context) {
        List<NoteDisplayObject> notes = new ArrayList<>();
        DBManager dbManager = new DBManager(context);
        dbManager.open();
        Cursor cursor = dbManager.fetch();
        do {
            try {
                notes.add(new NoteDisplayObject(cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("date")),
                        cursor.getInt(cursor.getColumnIndex("_id")),
                        cursor.getString(cursor.getColumnIndex("note")),
                        cursor.getString(cursor.getColumnIndex("tag"))));
            } catch(Exception e) {
                break;
            }
        } while (cursor.moveToNext());
        return notes;
    }
}