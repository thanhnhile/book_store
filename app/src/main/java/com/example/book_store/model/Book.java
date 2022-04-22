package com.example.book_store.model;

import java.util.ArrayList;

public class Book {
    private String id;
    private String title;
    private ArrayList<Author> authors;
    private String category;
    private String imgURL;
    private int pageCount;
    private String description;

    public Book() {
    }

    public Book(String id, String title, ArrayList<Author> authors, String category, String imgURL, int pageCount, String description) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.category = category;
        this.imgURL = imgURL;
        this.pageCount = pageCount;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
