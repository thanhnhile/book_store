package com.example.book_store;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.PerformanceHintManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.book_store.customadapter.CategoryAdapter;
import com.example.book_store.customadapter.PhotoAdapter;
import com.example.book_store.model.Book;
import com.example.book_store.model.Category;
import com.example.book_store.model.Photo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment {
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    //hash map list books by categiry
    List<Category> mListCategorys;
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
        //getListCategorys();
        //categoryAdapter.setData(mListCategorys);
        testRec();
       return  view;
    }
    private void testRec(){
        List<Book> listBooks = new ArrayList<>();
        listBooks.add(new Book("1","aaa","aaa","aaa","https://firebasestorage.googleapis.com/v0/b/bookstore-3a6ce.appspot.com/o/images%2F2022_05_06_13_07_31?alt=media&token=6c1bf9e3-d25f-4314-aaa1-e837f734997b",1,1,1,"aaa",1));
        listBooks.add(new Book("1","aaa","aaa","aaa","https://firebasestorage.googleapis.com/v0/b/bookstore-3a6ce.appspot.com/o/images%2F2022_05_06_13_07_31?alt=media&token=6c1bf9e3-d25f-4314-aaa1-e837f734997b",1,1,1,"aaa",1));
        listBooks.add(new Book("1","aaa","aaa","aaa","https://firebasestorage.googleapis.com/v0/b/bookstore-3a6ce.appspot.com/o/images%2F2022_05_06_13_07_31?alt=media&token=6c1bf9e3-d25f-4314-aaa1-e837f734997b",1,1,1,"aaa",1));
        listBooks.add(new Book("1","aaa","aaa","aaa","https://firebasestorage.googleapis.com/v0/b/bookstore-3a6ce.appspot.com/o/images%2F2022_05_06_13_07_31?alt=media&token=6c1bf9e3-d25f-4314-aaa1-e837f734997b",1,1,1,"aaa",1));
        mListCategorys.add(new Category("A",listBooks));
        mListCategorys.add(new Category("C",listBooks));
        mListCategorys.add(new Category("B",listBooks));
        mListCategorys.add(new Category("D",listBooks));
        categoryAdapter.setData(mListCategorys);
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
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    String category = data.getValue(String.class);
                    Category e = new Category(category);
                    mListCategorys.add(e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private  void getListBook(){
        myRef = database.getReference("Books");
        for(Category category:mListCategorys){
            Query query = myRef.orderByChild("category").equalTo(category.getCategory());
            List<Book> listBooks = new ArrayList<>();
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        for(DataSnapshot data:snapshot.getChildren()){
                            listBooks.add(data.getValue(Book.class));
                        }
                        category.setList(listBooks);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        System.out.println(mListCategorys.get(0).getList().get(0).getTitle());
    }
    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunable);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunable,timeDelay);
    }
}