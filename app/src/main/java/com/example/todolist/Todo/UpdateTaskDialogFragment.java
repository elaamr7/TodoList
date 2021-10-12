package com.example.todolist.Todo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.todolist.R;

public class UpdateTaskDialogFragment extends AppCompatDialogFragment {

    public UpdateDialogListener listener;
    private EditText etTask = null;

    public void setOnUpdateDialogListener(UpdateDialogListener listener){
        this.listener = listener;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstance){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.form_add_task,null);

        builder.setView(view)
                .setTitle("Ubah Task")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String task = etTask.getText().toString();
                        listener.updateData(task);
                    }
                });
        etTask = view.findViewById(R.id.et_task);
        etTask.setText(listener.sendData());
        return builder.create();
    }
    public interface UpdateDialogListener{
        String sendData();
        void updateData(String task);
    }
}
