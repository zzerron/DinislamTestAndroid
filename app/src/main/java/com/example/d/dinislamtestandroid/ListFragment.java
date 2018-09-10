package com.example.d.dinislamtestandroid;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.d.dinislamtestandroid.list.AddNewItemFragment;
import com.example.d.dinislamtestandroid.list.DBListHelper;
import com.example.d.dinislamtestandroid.list.ItemOnList;
import com.example.d.dinislamtestandroid.list.ListAdapter;
import com.example.d.dinislamtestandroid.list.Support;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<ItemOnList> itemsOnList;
    private LinearLayoutManager layoutManager;
    private Support support;
    private DBListHelper dbListHelper;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Получаем главный вид фрагмента
        final View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        //Инициализация списка для списка пунктов
        itemsOnList = new ArrayList<>();

        //Задаем соотвествие recycleView c id'View
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list_recycle_view);
        layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        dbListHelper = new DBListHelper(this.getActivity().getBaseContext(), "myDBList", null, 1);

        //Данный класс имеет только статические методы, которые вызывают методы данного фрагмента
        support = new Support(this);

        //Задаем соотвествие floatingButton c id'View, определяем обработчика нажатия
        FloatingActionButton floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            //При нажатии. Создаем диалоговое окно,  запускаем его
            //Используем AddNewItemFragment, в нем вся логика ввода нового пункта и оброботчика кнопок Done, Revert
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                DialogFragment dialogFragment = new AddNewItemFragment();
                dialogFragment.show(fragmentManager, "dialog");
            }
        });
        return rootView;
    }

//    Полностью обновляем список, данные берем с БД
    @Override
    public void onResume() {
    updateRecycleList();
    super.onResume();
    }
//   Данный метод реализует считывание всех значений из БД в список
    public void updateRecycleList(){
        SQLiteDatabase sqLiteDatabase = dbListHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("mytable", null, null, null, null, null, null);
        itemsOnList = new ArrayList<>();

        if (cursor.moveToFirst()){
            Integer nameNu = cursor.getColumnIndex("name");
            Integer boleNu = cursor.getColumnIndex("checkbox");
            Integer id = cursor.getColumnIndex("id");
            do {
                int boolNumber = cursor.getInt(boleNu);
                Boolean cheB;
                if (boolNumber == 1)
                    cheB = true;
                else cheB = false;
                String nameitem = cursor.getString(nameNu);
                Long iditem = cursor.getLong(id);
                ItemOnList itemOnList = new ItemOnList(nameitem, cheB, iditem);
                itemsOnList.add(itemOnList);
            }while (cursor.moveToNext());
        }
        cursor.close();
        ListAdapter listAdapter = new ListAdapter(itemsOnList);
        recyclerView.setAdapter(listAdapter);
    }

//    Данный метод реализует вставку нового значения в БД
    public void insertInDBList(String text){
        String textName = text;
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase sqLiteDatabase = dbListHelper.getWritableDatabase();
        contentValues.put("name", textName);
        contentValues.put("checkbox", 0);
        long IDBD = sqLiteDatabase.insert("mytable", null, contentValues);
    }

//   Данный метод реализует обновление элемента с известным id в БД
    public void updateInDBList(Long id, String text, boolean checkbox){
        String textName = text;
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase sqLiteDatabase = dbListHelper.getWritableDatabase();
        contentValues.put("name", textName);
        if (checkbox == true)
            contentValues.put("checkbox", 1);
        else
            contentValues.put("checkbox", 0);
        int upint = sqLiteDatabase.update("mytable", contentValues,"id = ?", new String[]{id.toString()});
    }

//    Данный метод удоляет элемент с известным id из БД
    public void deleteInDBList(Long id, String text){
        SQLiteDatabase sqLiteDatabase = dbListHelper.getWritableDatabase();
        int delint = sqLiteDatabase.delete("mytable", "id = " + id, null);
    }

//    Реализуем необходимую логику при быстром клики на элемент списка
    public void fastClick(final long idItem, final String nameItem, final boolean checkbox){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_on_list, null);
        final EditText editText = view.findViewById(R.id.editText);
        editText.setText(nameItem);
        builder.setView(view)
                .setCancelable(true)
                .setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateInDBList(idItem, editText.getText().toString(), checkbox);
                        updateRecycleList();
                    }
                })
                .setNegativeButton(R.string.revert, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog alertDialog = builder.create();

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                final String text = editText.getText().toString();
                if ((text.length() != 0) && (!text.equals(nameItem))){
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle(R.string.savev)
                            .setCancelable(false)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    updateInDBList(idItem, text, checkbox);
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
        });

        alertDialog.show();
    }

//    Реализуем необходимую логику при долгом клики на элемент списка
    public void longClick(final long idItem, final String nameItem, final boolean checkbox){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_on_list, null);
        EditText editText = view.findViewById(R.id.editText);
        editText.setText(nameItem);
        builder.setCancelable(false)
                .setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fastClick(idItem, nameItem, checkbox);
                    }
                })
                .setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteInDBList(idItem, nameItem);
                        updateRecycleList();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

//    Реализуем необходимую логику при клики на чекбокс элемента
    public void checkboxClick(final long idItem, final String nameItem, final Boolean isChecked){
        int bolInt;
        if (isChecked)
            bolInt = 1;
        else
            bolInt = 0;
        Long id = idItem;
        SQLiteDatabase sqLiteDatabase = dbListHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", nameItem);
        contentValues.put("checkbox", bolInt);
        int upint = sqLiteDatabase.update("mytable", contentValues,"id = ?", new String[]{id.toString()});
    }
}
