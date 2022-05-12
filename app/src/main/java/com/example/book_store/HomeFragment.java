package com.example.book_store;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import java.util.concurrent.CountDownLatch;
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
        FragmentManager fragmentManager = getParentFragmentManager();
        categoryAdapter = new CategoryAdapter(getContext(),fragmentManager);
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
//    private void test(){
//        bookList.add(new Book("1","a","a","a","https://firebasestorage.googleapis.com/v0/b/bookstore-3a6ce.appspot.com/o/images%2F2022_05_06_13_07_31?alt=media&token=6c1bf9e3-d25f-4314-aaa1-e837f734997b",
//                1,1,1,"a",1));
//        bookList.add(new Book("2","a","a","a","https://firebasestorage.googleapis.com/v0/b/bookstore-3a6ce.appspot.com/o/images%2F2022_05_06_13_07_31?alt=media&token=6c1bf9e3-d25f-4314-aaa1-e837f734997b",
//                1,1,1,"a",1));
//        bookList.add(new Book("3","a","a","a","https://firebasestorage.googleapis.com/v0/b/bookstore-3a6ce.appspot.com/o/images%2F2022_05_06_13_07_31?alt=media&token=6c1bf9e3-d25f-4314-aaa1-e837f734997b",
//                1,1,1,"a",1));
//        bookList.add(new Book("4","a","a","a","https://firebasestorage.googleapis.com/v0/b/bookstore-3a6ce.appspot.com/o/images%2F2022_05_06_13_07_31?alt=media&token=6c1bf9e3-d25f-4314-aaa1-e837f734997b",
//                1,1,1,"a",1));
//        if(listCates != null){
//            for (String category:listCates){
//                mListCategorys.add(new Category(category,bookList));
//                categoryAdapter.setData(mListCategorys);
//            }
//        }else Log.e("categorys","null");
//
//    }
    //get list books by category
    private void getListCategorys(){
        CountDownLatch done = new CountDownLatch(1);
        myRef = database.getReference("Categorys");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String cate = dataSnapshot.getValue(String.class);
                    listCates.add(cate);
                }
                done.countDown();
                try {
                    done.await(); //it will wait till the response is received from firebase.
                    getListBooks();
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                //getListBooks();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Get Categorys errors",error.getMessage());
            }
        });
    }
    private void getListBooks(){
        for (String category:listCates){
            Log.e("category",category);
            Query query = database.getReference("Books")
                    .orderByChild("category").equalTo(category)
                    .limitToFirst(4);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(bookList != null){
                        bookList.clear();
                    }
                    if(snapshot.exists()) {
                        CountDownLatch done = new CountDownLatch(2);
                        for (DataSnapshot data : snapshot.getChildren()) {
                            Book book = data.getValue(Book.class);
                            if(book.getIsActive() == 1){
                                bookList.add(book);
                            }
                        }
                        done.countDown();
                        Category cate = new Category(category,bookList);
                        mListCategorys.add(cate);
                        categoryAdapter.setData(mListCategorys);
                        done.countDown();
                        try {
                            done.await(); //it will wait till the response is received from firebase.
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.e("Chay vao day", Integer.toString(bookList.size()));
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Get boooks errors",error.getMessage());
                }
            });

        }
    }
//    private void getListBooks(String category){
//        Log.e("So lan chay ham","Chay ham get List book");
//        Query query = database.getReference("Books").orderByChild("category").equalTo(category).limitToLast(4);
//        readData(query, new OnGetDataListener() {
//            @Override
//            public void onSuccess(DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    for(DataSnapshot data:dataSnapshot.getChildren()){
//                        Book book = data.getValue(Book.class);
//                        bookList.add(book);
//                    }
//                    Category e = new Category(category,bookList);
//                    mListCategorys.add(e);
//                    categoryAdapter.setData(mListCategorys);
//
//                }
//            }
//
//            @Override
//            public void onStart() {
//                //when starting
//                Log.d("ONSTART", "Started");
//            }
//
//            @Override
//            public void onFailure() {
//                Log.d("onFailure", "Failed");
//            }
//        });
//    }
    public void readData(Query ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listener.onSuccess((snapshot));
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