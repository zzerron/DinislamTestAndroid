package com.example.d.dinislamtestandroid.parsing;

import android.text.Html;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Quote {


    private String id;

    private String description;

    private String time;

    private String rating;

    public Quote() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Html.fromHtml(description).toString();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

}