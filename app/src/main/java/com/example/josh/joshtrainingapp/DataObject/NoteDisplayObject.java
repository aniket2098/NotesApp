package com.example.josh.joshtrainingapp.DataObject;

public class NoteDisplayObject {

    String note;
    String date;
    Integer position;

    public NoteDisplayObject(String note, String date, Integer position) {

        this.note = note;
        this.date = date;
        this.position = position;
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
}