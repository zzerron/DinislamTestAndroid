package com.example.d.dinislamtestandroid;


//Данный класс имеет только статические методы для перенаправления задачи другой активити
public class SupportChoiceLang {
    private static MainActivity mainActivity;


    public SupportChoiceLang(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public static void changeLang(String lang){
        mainActivity.changeLang(lang);
    }
    public static void changeLangRu(){
        mainActivity.changeLangRu();
    }
}
