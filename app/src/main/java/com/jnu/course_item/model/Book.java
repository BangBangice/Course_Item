package com.jnu.course_item.model;

public class Book {
    private String name;
    private int pictureId;

    public Book(String name, int pictureId) {
        this.name=name;
        this.pictureId=pictureId;
    }

    public String getTitle() {
        return name;
    }

    public int getCoverResourceId() {
        return pictureId;
    }

    public void setName(String name) {
        this.name = name;
    }

}
