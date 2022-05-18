package com.example.book_store.model;

public class CartItem {
    private int id;
    private String bookId;
    private int num;

    public CartItem(int id, String bookId, int num) {
        this.id = id;
        this.bookId = bookId;
        this.num = num;
    }

    public CartItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
