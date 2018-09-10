package com.example.d.dinislamtestandroid.list;


import com.example.d.dinislamtestandroid.ListFragment;

public class Support {
    private static ListFragment listFragment;

    public Support(ListFragment listFragment) {
        this.listFragment = listFragment;
    }

    public static void updateRecycleList(){
        listFragment.updateRecycleList();
    }

    public static void insertInDBList(String text){
        listFragment.insertInDBList(text);
    }

    public static void fastClick(long idItem, String nameItem, boolean checkbox){
        listFragment.fastClick(idItem, nameItem, checkbox);
    }

    public static void longClick(long idItem, String nameItem, boolean checkbox){
        listFragment.longClick(idItem, nameItem, checkbox);
    }

    public static void checkboxClick(final long idItem, final String nameItem, final Boolean isChecked){
        listFragment.checkboxClick(idItem, nameItem, isChecked);
    }
}
