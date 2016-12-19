package com.example.lasha.homework4.model;

import com.example.lasha.homework4.adapter.ListViewCustomAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lasha on 4/20/2015.
 */
public class DB implements Serializable{
    private static final long serialVersionUID = 1L;


    private List<TodoModel> data;

    public DB(){
        data = new ArrayList<>();
    }

    public DB(List<TodoModel> data){
        this.data = data;
    }

    public List<TodoModel> getData() {

//        TodoModel model = new TodoModel();
//        model.setId(1L);
//        model.setText("pfmsnfjfg.,sndg.mn;rej");
//        data.add(model);
//
//        model = new TodoModel();
//        model.setId(2L);
//        model.setText("kjndknfndslknfg lkmf lkmndk jnbijnk m,okmnokm");
//        data.add(model);

        return data;
    }
}
