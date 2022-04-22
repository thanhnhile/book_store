package com.example.book_store.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String fullName;
    private String phone;
    private String password;
    private String address;
    private int isAdmin;

    public User() {
    }

    public User(String fullName, String phone, String password, String address) {
        this.fullName = fullName;
        this.phone = phone;
        this.password = password;
        this.address = address;
        this.isAdmin = 0;
    }

    protected User(Parcel in) {
        fullName = in.readString();
        phone = in.readString();
        password = in.readString();
        address = in.readString();
        isAdmin = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fullName);
        parcel.writeString(phone);
        parcel.writeString(password);
        parcel.writeString(address);
        parcel.writeInt(isAdmin);
    }
}
