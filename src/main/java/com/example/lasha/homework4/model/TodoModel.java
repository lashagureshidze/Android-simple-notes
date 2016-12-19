package com.example.lasha.homework4.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by lasha on 4/20/2015.
 */
public class TodoModel implements Serializable{
    private static final long serialVersionUID = 1L;

    //id is time in millisecond when this model is create. it's not best solution , but quick....
    private Long id;
    private String text;

    public TodoModel() {}

    public TodoModel(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

}
