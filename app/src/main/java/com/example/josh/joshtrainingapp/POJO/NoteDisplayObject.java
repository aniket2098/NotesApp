package com.example.josh.joshtrainingapp.POJO;

public class NoteDisplayObject {

    String note;
    String date;
    String desc;
    String tag;
    Integer position;

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
}