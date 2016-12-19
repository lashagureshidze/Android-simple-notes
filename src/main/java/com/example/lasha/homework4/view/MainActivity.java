package com.example.lasha.homework4.view;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lasha.homework4.R;
import com.example.lasha.homework4.adapter.ListViewCustomAdapter;
import com.example.lasha.homework4.model.DB;
import com.example.lasha.homework4.model.TodoModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AddEntryDialog.AddEntryDialogListener{
    private ListViewCustomAdapter adapter;
    private List<TodoModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null && savedInstanceState.getSerializable("data") != null) {
            data = ((DB) savedInstanceState.getSerializable("data")).getData();
        } else {
            if (data == null) data = new ArrayList<>();
        }

        //create listView
        ListView listView = (ListView) findViewById(R.id.list_view);
        adapter = new ListViewCustomAdapter(getLayoutInflater(), data);
        listView.setAdapter(adapter);

        //add contextual menu
        final MyActionModeCallback callback = new MyActionModeCallback();
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("", "on item clicked...." + position);

                ActionMode aMode = startSupportActionMode(callback);
                aMode.setTag(id);       //pass parameter
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("data", new DB(adapter.getData()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.menu_add_new :
                showDialog(new TodoModel(new Date().getTime(), ""));
                break;
            case R.id.menu_save :
                saveData();
                break;
            case R.id.menu_close_app :
                closeApp();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void closeApp() {
        Toast.makeText(this, getResources().getText(R.string.exiting_app), Toast.LENGTH_LONG).show();

        finish();
        System.exit(0);
    }

    private void saveData() {
        Toast.makeText(this, getResources().getText(R.string.list_saved), Toast.LENGTH_LONG).show();
    }

    private void showDialog(TodoModel model) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        AddEntryDialog.getInstance(model).show(ft, "dialog");
    }


    private class MyActionModeCallback implements android.support.v7.view.ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(android.support.v7.view.ActionMode actionMode, Menu menu) {
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.menu_contextual, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(android.support.v7.view.ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(android.support.v7.view.ActionMode actionMode, MenuItem menuItem) {

            long itemId = (Long) actionMode.getTag();
            switch (menuItem.getItemId()) {
                case R.id.menu_update:
                    showDialog(adapter.getItemById(itemId));
                    actionMode.finish();
                    return true;
                case R.id.menu_delete :
                    adapter.deleteItem(itemId);
                    actionMode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(android.support.v7.view.ActionMode actionMode) {

        }
    }

    @Override
    public void onPositiveClicked(TodoModel model) {
        adapter.setItem(model);
    }
}
