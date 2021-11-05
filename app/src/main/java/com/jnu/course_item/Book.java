package com.jnu.course_item;

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

}
