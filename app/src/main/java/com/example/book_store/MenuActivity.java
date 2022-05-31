package com.example.book_store;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.book_store.model.Book;
import com.example.book_store.model.User;
import com.example.book_store.sharedpreferences.Constants;
import com.example.book_store.sharedpreferences.PreferenceManager;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class MenuActivity extends AppCompatActivity {


    public static BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    CartFragment cartFragment = new CartFragment();
    AccountFragment accountFragment = new AccountFragment();
    TextView btnAcc;
    //Firebase
    FirebaseDatabase db;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference("Books");
        initUI();
        if(getIntent().getExtras() != null){
            Book newBook = getIntent().getExtras().getParcelable("book-target");
            Log.e("Book",newBook.getTitle());
            ShowDetailFragment showDetailFragment = new ShowDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("book-target",newBook);
            showDetailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,showDetailFragment).commit();
            return;
        }
        else{
            handleEventBookAdded();
            getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
        }

    }
    private void initUI(){
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.menu_nav);
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
    private void handleEventBookAdded(){
        myRef.limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()){
                    Book newBook = snapshot.getValue(Book.class);
                    sendNotification(newBook);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void sendNotification(Book newBook){

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext(),Constants.NOTIFICATION_PREFERENCE_NAME);
        String newBookId = preferenceManager.getString(Constants.NEW_BOOK_ID);
        if(newBookId != null && newBook.getId().equals(newBookId)){
            return;
        }
        preferenceManager.putString(Constants.NEW_BOOK_ID,newBook.getId());
        NotificationManager nm = getSystemService(NotificationManager.class);
        NotificationChannel channel = new NotificationChannel(Constants.CHANNEL_ID,Constants.CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription(Constants.CHANNEL_DESCRIPTION);
        nm.createNotificationChannel(channel);
        //Tao thong bao
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),Constants.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_book_foreground)
                .setContentTitle(Constants.TITLE)
                .setContentText(newBook.getTitle().toUpperCase(Locale.ROOT)+" đã có mặt trên BookStore, MUA NGAY!!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        //
        Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
        intent.putExtra("book-target",newBook);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        nm.notify(0,notification);

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