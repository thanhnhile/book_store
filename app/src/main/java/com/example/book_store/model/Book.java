package com.example.book_store.model;

public class Book {
    private String id;
    private String title;
    private String author;
    private String category;
    private String imgURL;
    private int year;
    private int price;
    private int inStock;
    private String description;
    private int isActive;

    public Book(String id, String title, String author, String category, String imgURL, int year, int price, int inStock, String description, int isActive) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.imgURL = imgURL;
        this.year = year;
        this.price = price;
        this.inStock = inStock;
        this.description = description;
        this.isActive = isActive;
    }

    public Book() {
    }


    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
}
