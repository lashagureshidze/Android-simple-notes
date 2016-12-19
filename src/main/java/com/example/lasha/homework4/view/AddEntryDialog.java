package com.example.lasha.homework4.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.lasha.homework4.R;
import com.example.lasha.homework4.model.TodoModel;


/**
 * Created by lasha on 4/20/2015.
 */
public class AddEntryDialog extends DialogFragment{

    public interface AddEntryDialogListener {
        public void onPositiveClicked(TodoModel model);
    }

    //listener to send dialog result
    private AddEntryDialogListener listener;

    private TodoModel model;

    /**
     * creates new DialogFragment
     * @param model that will be edit, or null if new entry is added
     * @return
     */
    public static AddEntryDialog getInstance(TodoModel model) {
        Bundle args = new Bundle();
        args.putSerializable("model", model);

        AddEntryDialog dialog = new AddEntryDialog();
        dialog.setArguments(args);

        return dialog;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (AddEntryDialogListener) activity;
        } catch (ClassCastException ex) {
            throw new ClassCastException(activity.toString()
                    + " must implement AddEntryDialogListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null && bundle.getSerializable("model") != null) {
            model = (TodoModel)bundle.getSerializable("model");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog, null);
        final EditText editText = (EditText)view.findViewById(R.id.dialog_edit_text);
        editText.setText(model.getText());


        builder.setView(view)
                .setTitle(R.string.entry_name)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("", "ok clicked in dialog");
                        model.setText(editText.getText().toString());
                        listener.onPositiveClicked(model);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("", "cancel clicked in dialog");
                    }
                });

        return builder.create();
    }
}
