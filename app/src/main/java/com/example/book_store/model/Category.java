package com.example.book_store.model;

import java.util.List;

public class Category {
    private String category;
    private List<Book> list;

    public Category(String category) {
        this.category = category;
    }

    public Category(String category, List<Book> list) {
        this.category = category;
        this.list = list;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Book> getList() {
        return list;
    }

    public void setList(List<Book> list) {
        this.list = list;
    }
}
