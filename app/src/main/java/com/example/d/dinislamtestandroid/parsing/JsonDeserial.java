package com.example.d.dinislamtestandroid.parsing;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringReader;

public class JsonDeserial {
    public static Something read(String s){
        StringReader stringReader = new StringReader(s);
        ObjectMapper mapper = new ObjectMapper();
        Something something = null;
        try{
            something = mapper.readValue(stringReader, Something.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return something;
    }
}
