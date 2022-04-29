package com.example.book_store.dao;

import androidx.annotation.NonNull;

import com.example.book_store.model.Book;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class BookDao {
    FirebaseDatabase database;
    DatabaseReference myRef;
    Book book;
    public BookDao(Book book) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Books");
        this.book = book;
    }

}
