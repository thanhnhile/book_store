package com.example.book_store;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.PerformanceHintManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;



import com.example.book_store.customadapter.CategoryAdapter;
import com.example.book_store.customadapter.PhotoAdapter;
import com.example.book_store.database.OnGetDataListener;
import com.example.book_store.model.Book;
import com.example.book_store.model.Category;
import com.example.book_store.model.Photo;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment {
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    //list books by categiry
    List<Category> mListCategorys;
    List<String> listCates;
    List<Book> bookList;
    String currentCate;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    RecyclerView mainRecyclerView;
    CategoryAdapter categoryAdapter;
    private List<Photo> mListPhoto;
    private final static int timeDelay = 4000;
    private Handler mHandler = new Handler();
    private Runnable mRunable = new Runnable() {
        @Override
        public void run() {
            if(viewPager.getCurrentItem() == mListPhoto.size() - 1){
                viewPager.setCurrentItem(0);
            }else{
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_home, container, false);
       viewPager = (ViewPager) view.findViewById(R.id.view_pager);
       circleIndicator = view.findViewById(R.id.circle_indicator);
       mListPhoto = getListPhotos();
       PhotoAdapter photoAdapter = new PhotoAdapter(mListPhoto);
       viewPager.setAdapter(photoAdapter);
       circleIndicator.setViewPager(viewPager);
       mHandler.postDelayed(mRunable,timeDelay);
       viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int position) {
               mHandler.removeCallbacks(mRunable);
               mHandler.postDelayed(mRunable,timeDelay);
           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });
        //Firebase
        database = FirebaseDatabase.getInstance();
        mListCategorys = new ArrayList<Category>();
       //recycler view
        mainRecyclerView = view.findViewById(R.id.main_recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        mainRecyclerView.setLayoutManager(linearLayoutManager);
        categoryAdapter = new CategoryAdapter(getContext());
        mainRecyclerView.setAdapter(categoryAdapter);
        bookList = new ArrayList<>();
        listCates = new ArrayList<>();
        getListCategorys();
        return view;
    }
    private List<Photo> getListPhotos(){
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.img1));
        list.add(new Photo(R.drawable.img2));
        list.add(new Photo(R.drawable.img3));
        list.add(new Photo(R.drawable.img4));
        return list;
    }
    //get list books by category
    private void getListCategorys(){
        myRef = database.getReference("Categorys");
        getData(myRef, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String cate = snapshot.getValue(String.class);
                    getListBooks(cate);
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });
    }
    private void getListBooks(String category){
        Log.e("So lan chay ham","Chay ham get List book");
        Query query = database.getReference("Books").orderByChild("category").equalTo(category).limitToLast(4);
        readData(query, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot data:dataSnapshot.getChildren()){
                        Book book = data.getValue(Book.class);
                        bookList.add(book);
                    }
                    Category e = new Category(category,bookList);
                    mListCategorys.add(e);
                    categoryAdapter.setData(mListCategorys);

                }
            }

            @Override
            public void onStart() {
                //when starting
                Log.d("ONSTART", "Started");
            }

            @Override
            public void onFailure() {
                Log.d("onFailure", "Failed");
            }
        });
    }
    public void readData(Query ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listener.onSuccess(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailure();
            }
        });

    }
    public  void getData(DatabaseReference ref,final  OnGetDataListener listener){
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listener.onSuccess(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailure();
            }
        });
    }

}