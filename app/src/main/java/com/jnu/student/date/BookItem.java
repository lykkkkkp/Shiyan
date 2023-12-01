package com.jnu.student.data;

public class BookItem {
    public int getImageId() {
        return imageId;
    }

    private final int imageId;

    public String getName() {
        return name;
    }

    private final String name;

    public BookItem(String name_, int bookId_) {
        this.name=name_;
        this.imageId=bookId_;
    }
}