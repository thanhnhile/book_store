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
    public void readData(){
        myRef.child(book.getId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot data = task.getResult();
                        book = data.getValue(Book.class);
                    }
                }
            }
        });
    }
    public  void updataBook(Book book){

    }
    public void toggleActiveBook(Book book){


    }
}
