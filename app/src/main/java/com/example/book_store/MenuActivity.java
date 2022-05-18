package com.example.book_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.book_store.model.User;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MenuActivity extends AppCompatActivity {


    public static BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    CartFragment cartFragment = new CartFragment();
    AccountFragment accountFragment = new AccountFragment();
    TextView btnAcc;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initUI();

    }
    private void initUI(){
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.menu_nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;
                    case R.id.search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,searchFragment).commit();
                        return true;
                    case R.id.cart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,cartFragment).commit();
                        return true;
                }
                return false;
            }
        });
        btnAcc = (TextView) findViewById(R.id.menu_btnAccount);
        btnAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container,accountFragment).commit();
            }
        });
    }
//    public static void setCountProductInCart(int count){
//        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(2);
//        if (badgeDrawable != null) {
//            badgeDrawable.setVisible(true);
//            badgeDrawable.setNumber(count);
//        }
//    }
//    public static void clearCountInProductCart(){
//        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(2);
//        if (badgeDrawable != null) {
//            badgeDrawable.setVisible(false);
//            badgeDrawable.clearNumber();
//        }
//    }
}