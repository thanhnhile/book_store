package com.example.book_store;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OrderDetailFragment extends Fragment {
    private String orderId, orderBy;


    public OrderDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);

        Intent intent = getActivity().getIntent();
        orderBy = intent.getStringExtra("orderBy");
        orderId = intent.getStringExtra("orderId");


        return view;
    }
}