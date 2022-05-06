package com.example.book_store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.book_store.admin.AdminMenuActivity;

public class MainActivity extends AppCompatActivity {
    private static int TIME_OUT = 4000; //Time to launch the another activity
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("book_store", Context.MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //check login state
                String phone = sharedPreferences.getString("phone",null);
                int isAdmin = sharedPreferences.getInt("isAdmin",-1);
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