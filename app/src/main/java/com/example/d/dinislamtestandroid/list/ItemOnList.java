package com.example.d.dinislamtestandroid.list;

public class ItemOnList {
    private Long id;
    private String nameItem;
    private Boolean checkboxItem;


    public ItemOnList(String nameItem, Boolean checkboxItem, Long id) {
        this.id = id;
        this.nameItem = nameItem;
        this.checkboxItem = checkboxItem;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public Boolean getCheckboxItem() {
        return checkboxItem;
    }

    public void setCheckboxItem(Boolean checkboxItem) {
        this.checkboxItem = checkboxItem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
