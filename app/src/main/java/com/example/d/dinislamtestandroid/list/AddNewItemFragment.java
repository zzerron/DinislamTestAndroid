package com.example.d.dinislamtestandroid.list;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.d.dinislamtestandroid.R;

// Диологовое окно
public class AddNewItemFragment extends DialogFragment{
    private String nameNewItem;
    private EditText editText;

    private DBListHelper dbListHelper;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_on_list, null);


        dbListHelper = new DBListHelper(this.getActivity().getBaseContext(), "myDBList", null, 1);

        editText = view.findViewById(R.id.editText);

        builder.setView(view)
                .setPositiveButton(R.string.done, new DialogInterface.OnClickListener() { //При нажатии кнопки Done
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        nameNewItem = editText.getText().toString();

                        if (nameNewItem.length() != 0){
                            Support.insertInDBList(nameNewItem);
                            Support.updateRecycleList();
                        }
                        else {
                            Toast.makeText(getActivity().getBaseContext(),"Ничего не добавлено", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(R.string.revert, new DialogInterface.OnClickListener() { //При нажатии Revert
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }

    //При нажатии "Назад" если строка не пустая вызывает AlertDialog
    //С предрожением сохранить или нет
    //Метод сохроняет изменения и обновляет
    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        nameNewItem = editText.getText().toString();
        if (nameNewItem.length() != 0){
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle(R.string.savev)
                    .setCancelable(false)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Support.insertInDBList(nameNewItem);
                            Support.updateRecycleList();
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        }
    }
}