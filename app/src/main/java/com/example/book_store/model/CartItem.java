package com.example.book_store.model;

public class CartItem {
    private Book book;
    private int num;

    public CartItem(Book book, int num) {
        this.book = book;
        this.num = num;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "book=" + book +
                ", num=" + num +
                '}';
    }
}
