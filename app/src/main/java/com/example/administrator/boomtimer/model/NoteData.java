package com.example.administrator.boomtimer.model;

/**
 * Created by shady on 2017/5/21.
 */
public class NoteData {
    private String name;
    private int Tagid;

    public void setTagid(int tagid) {
        Tagid = tagid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;

    }

    public int getTagid() {
        return Tagid;
    }
}
