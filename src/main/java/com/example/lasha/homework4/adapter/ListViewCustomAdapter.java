package com.example.lasha.homework4.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lasha.homework4.R;
import com.example.lasha.homework4.model.TodoModel;

import java.util.List;

/**
 * Created by lasha on 4/20/2015.
 */
public class ListViewCustomAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<TodoModel> data;

    public ListViewCustomAdapter(LayoutInflater inflater, List<TodoModel> data) {
        this.inflater = inflater;
        this.data = data;
    }

    public List<TodoModel> getData() {
        return data;
    }

    public TodoModel getItemById(long id) {
        for (TodoModel model : data) {
            if (model.getId() == id) return model;
        }
        return null;
    }

    public void deleteItem(long id) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == id) {
                data.remove(i);

                notifyDataSetChanged();
                return;
            }
        }
    }

    /**
     * add or edit model
     * @param model
     */
    public void setItem(TodoModel model) {
        TodoModel existing = getItemById(model.getId());
        if (existing == null) {
            data.add(model);
        } else {
            existing.setText(model.getText());
        }

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ((TodoModel) getItem(position) ).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        Holder holder;

        //accelerate getView
        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item, null);

            holder = new Holder();
            holder.rowNumber = (TextView) view.findViewById(R.id.item_row_number);
            holder.text = (TextView) view.findViewById(R.id.item_text);


            view.setTag(holder);
        } else {
            view = convertView;
            holder = (Holder) view.getTag();
        }

        //draw model
        TodoModel model = (TodoModel) getItem(position);
        holder.rowNumber.setText( position+ ".  ");
        holder.text.setText(model.getText());

        return view;
    }

    private class Holder {
        public TextView rowNumber;
        public TextView text;
    }
}
