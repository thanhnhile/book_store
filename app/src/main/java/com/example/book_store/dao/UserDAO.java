package com.example.book_store.dao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.book_store.model.User;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDAO {
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public UserDAO() {
        this.database = FirebaseDatabase.getInstance();
        this.myRef = database.getReference("Users");
    }
    public void addUser(User user){
        final boolean[] isSuccess = {false};
        myRef.child(user.getPhone()).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null){
                    isSuccess[0] = true;
                }
            }
        });
    }
}
