package com.example.book_store.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.book_store.R;
import com.example.book_store.admin.CRUDFragment;
import com.example.book_store.admin.CategoryFragment;
import com.example.book_store.admin.OrderListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AdminMenuActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    CRUDFragment crudFragment = new CRUDFragment();
    CategoryFragment categoryFragment = new CategoryFragment();
    OrderListFragment orderListFragment = new OrderListFragment();
    AdminListbook adminListbook = new AdminListbook();
    UserListFragment userListFragment = new UserListFragment();
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
                    case R.id.category:
                        getSupportFragmentManager().beginTransaction().replace(R.id.admin_menu_container,categoryFragment).commit();
                        return true;
                    case R.id.order:
                        getSupportFragmentManager().beginTransaction().replace(R.id.admin_menu_container,orderListFragment).commit();
                        return true;
                    case R.id.list_book:
                        getSupportFragmentManager().beginTransaction().replace(R.id.admin_menu_container,adminListbook).commit();
                        return true;
                    case R.id.users:
                        getSupportFragmentManager().beginTransaction().replace(R.id.admin_menu_container,userListFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}