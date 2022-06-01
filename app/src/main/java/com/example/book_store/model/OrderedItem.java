package com.example.book_store.model;

public class OrderedItem {
    private String Id, BookId, Quantity;

    public OrderedItem() {

    }

    public OrderedItem(String id, String bookId, String quantity) {
        Id = id;
        BookId = bookId;
        Quantity = quantity;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getBookId() {
        return BookId;
    }

    public void setBookId(String bookId) {
        BookId = bookId;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
