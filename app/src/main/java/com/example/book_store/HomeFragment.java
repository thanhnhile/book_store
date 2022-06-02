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
    //List<Book> bookList;
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
        categoryAdapter = new CategoryAdapter(getContext(),fragmentManager,true);
        mainRecyclerView.setAdapter(categoryAdapter);
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
        CountDownLatch done = new CountDownLatch(1);
        myRef = database.getReference("Categorys").orderByValue().getRef();
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Get Categorys errors",error.getMessage());
            }
        });
    }
    private void getListBooks(){
        for (String category:listCates){
            Query query = database.getReference("Books")
                    .orderByChild("category").equalTo(category)
                    .limitToFirst(4);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List bookList = new ArrayList<>();
                    if(snapshot.exists()) {
                        CountDownLatch done = new CountDownLatch(2);
                        for (DataSnapshot data : snapshot.getChildren()) {
                            Book book = data.getValue(Book.class);
                            //Kiem tra dieu kien
                            if(book.getIsActive() == 1 && book.getInStock() > 0){
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
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Get boooks errors",error.getMessage());
                }
            });

        }
    }

}