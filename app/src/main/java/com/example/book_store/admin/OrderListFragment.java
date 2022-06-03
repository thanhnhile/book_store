package com.example.book_store.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.book_store.R;
import com.example.book_store.customadapter.OrderAdapter;
import com.example.book_store.model.Order;
import com.example.book_store.sharedpreferences.Constants;
import com.example.book_store.sharedpreferences.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderListFragment extends Fragment {
    private RecyclerView ordersRv;
    private ArrayList<Order> orderList;
    private OrderAdapter orderAdapter;
    private String phone;
    PreferenceManager preferenceManager;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_order_list, container, false);
        ordersRv = view.findViewById(R.id.ordersRv);
        preferenceManager = new PreferenceManager(getContext(), Constants.LOGIN_KEY_PREFERENCE_NAME);
        fragmentManager = getParentFragmentManager();
        loadOrders();
        return view;
    }
    private void loadOrders() {
        orderList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderList.clear();
                phone = preferenceManager.getString(Constants.LOGIN_PHONE);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for( DataSnapshot ds : snapshot.getChildren()){
                                Order order = ds.getValue(Order.class);
                                orderList.add(order);
                            }
                            orderAdapter = new OrderAdapter(getContext(),orderList,fragmentManager);
                            ordersRv.setAdapter(orderAdapter);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}