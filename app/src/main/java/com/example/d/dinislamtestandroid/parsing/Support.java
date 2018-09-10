package com.example.d.dinislamtestandroid.parsing;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class Support {
    private static final String URL_AD = "http://quotes.zennex.ru/api/v3/bash/quotes?sort=time";

    public static String getText(){
        String string = "";
        try {
            Connection connection = Jsoup.connect(URL_AD);
            Connection connection1 = connection.ignoreContentType(true);
            Connection.Response response = connection1.execute();
            string = response.body();
        }catch (IOException e){
            e.printStackTrace();
        }
        return string;
    }
}
