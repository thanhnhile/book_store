package com.example.book_store;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.PerformanceHintManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.book_store.customadapter.PhotoAdapter;
import com.example.book_store.model.Photo;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment {
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    private List<Photo> mListPhoto;
    private final static int timeDelay = 5000;
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
       return  view;
    }
    private List<Photo> getListPhotos(){
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.img1));
        list.add(new Photo(R.drawable.img2));
        list.add(new Photo(R.drawable.img3));
        list.add(new Photo(R.drawable.img4));
        return list;
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