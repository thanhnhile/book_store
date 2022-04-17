package com.example.book_store;

public class Book {
    private int id;
    private String title;
    private int price;
    private int img;

    public Book(int id, String title, int price,int img) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
