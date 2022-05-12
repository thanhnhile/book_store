package com.example.book_store.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
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

    protected Book(Parcel in) {
        id = in.readString();
        title = in.readString();
        author = in.readString();
        category = in.readString();
        imgURL = in.readString();
        year = in.readInt();
        price = in.readInt();
        inStock = in.readInt();
        description = in.readString();
        isActive = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", imgURL='" + imgURL + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", inStock=" + inStock +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                '}';
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(category);
        parcel.writeString(imgURL);
        parcel.writeInt(year);
        parcel.writeInt(price);
        parcel.writeInt(inStock);
        parcel.writeString(description);
        parcel.writeInt(isActive);
    }
}
