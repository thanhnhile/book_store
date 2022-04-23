package com.example.book_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AdminMenuActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    CRUDFragment crudFragment = new CRUDFragment();
    OrderListFragment orderListFragment = new OrderListFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.admin_menu_nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.admin_menu_container,crudFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.crud:
                        getSupportFragmentManager().beginTransaction().replace(R.id.admin_menu_container,crudFragment).commit();
                        return true;
                    case R.id.order:
                        getSupportFragmentManager().beginTransaction().replace(R.id.admin_menu_container,orderListFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }
}