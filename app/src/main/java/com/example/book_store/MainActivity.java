package com.example.book_store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.ea.async.Async;
import com.example.book_store.admin.AdminMenuActivity;
import com.example.book_store.sharedpreferences.Constants;
import com.example.book_store.sharedpreferences.PreferenceManager;

public class MainActivity extends AppCompatActivity {
    private static int TIME_OUT = 4000; //Time to launch the another activity
    PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //sharedPreferences = getSharedPreferences("book_store", Context.MODE_PRIVATE);
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