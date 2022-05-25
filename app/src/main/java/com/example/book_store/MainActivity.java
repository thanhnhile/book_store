package com.example.book_store;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.book_store.admin.AdminMenuActivity;
import com.example.book_store.model.Book;
import com.example.book_store.sharedpreferences.Constants;
import com.example.book_store.sharedpreferences.PreferenceManager;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static int TIME_OUT = 4000; //Time to launch the another activity
    PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferenceManager = new PreferenceManager(getApplicationContext(), Constants.LOGIN_KEY_PREFERENCE_NAME);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //check login state
                String phone = preferenceManager.getString(Constants.LOGIN_PHONE);
                int isAdmin = preferenceManager.getInt(Constants.LOGIN_IS_ADMIN);
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                if(phone != null){
                    if(isAdmin == 1){
                        i = new Intent(MainActivity.this,AdminMenuActivity.class);

                    }
                    else if(isAdmin == 0){
                        i = new Intent(MainActivity.this,MenuActivity.class);
                    }
                }
                startActivity(i);
                finish();
            }
        },TIME_OUT);
    }

}