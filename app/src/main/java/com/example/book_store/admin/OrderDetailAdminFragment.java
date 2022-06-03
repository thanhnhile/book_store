package com.example.book_store.admin;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.book_store.R;
import com.example.book_store.customadapter.OrderedItemAdapter;
import com.example.book_store.model.OrderedItem;
import com.example.book_store.sharedpreferences.Constants;
import com.example.book_store.sharedpreferences.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


public class OrderDetailAdminFragment extends Fragment {
    private String orderId, orderBy;
    private TextView txtOrderId, txtDate, txtOrderStatus, txtTotalItems, txtAmount, txtOrderBy;
    private RecyclerView itemsRv;
    private String phone;
    private Context context;
    private ArrayList<OrderedItem> orderedItemList;
    private OrderedItemAdapter orderedItemAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_detail_admin, container, false);
        txtOrderId = view.findViewById(R.id.txtOrderId);
        txtDate = view.findViewById(R.id.txtDate);
        txtAmount = view.findViewById(R.id.txtAmount);
        txtOrderBy = view.findViewById(R.id.txtOrderBy);
        txtTotalItems = view.findViewById(R.id.txtTotalItems);
        txtOrderStatus = view.findViewById(R.id.txtOrderStatus);
        itemsRv = (RecyclerView) view.findViewById(R.id.itemsRv);
        Bundle bundle = getArguments();
        if(bundle != null){
            orderId = bundle.getString("orderId");
            orderBy = bundle.getString("orderBy");
        }
        PreferenceManager preferenceManager = new PreferenceManager(getContext(), Constants.LOGIN_KEY_PREFERENCE_NAME);
        phone = preferenceManager.getString(Constants.LOGIN_PHONE);
        loadOrderDetail();
//        loadOrderedItems();
        return view;
    }

    private void loadOrderedItems() {
        orderedItemList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders");
        ref.child(orderId).child("Items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderedItemList.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    OrderedItem orderedItem = ds.getValue(OrderedItem.class);
                    orderedItemList.add(orderedItem);
                }
                orderedItemAdapter = new OrderedItemAdapter(getContext(),orderedItemList);
                itemsRv.setAdapter(orderedItemAdapter);
                txtTotalItems.setText(""+snapshot.getChildrenCount());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadOrderDetail() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders");
        ref.child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String orderBy = ""+snapshot.child("orderBy").getValue();
                String orderCost = ""+snapshot.child("orderCost").getValue();
                String orderId = ""+snapshot.child("orderId").getValue();
                String orderStatus = ""+snapshot.child("orderStatus").getValue();
                String orderTime = ""+snapshot.child("orderTime").getValue();

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.parseLong(orderTime));
                String formatedTime = DateFormat.format("dd/MM/yyyy hh:mm a",calendar).toString();

                if (orderStatus.equals("In progress")){
                    txtOrderStatus.setTextColor(ContextCompat.getColor(context,R.color.primary_color));
                } else if (orderStatus.equals("Completed")){
                    txtOrderStatus.setTextColor(ContextCompat.getColor(context,R.color.second_color));
                } else if (orderStatus.equals("Cancelled")){
                    txtOrderStatus.setTextColor(ContextCompat.getColor(context,R.color.text_second));
                }

                txtOrderId.setText(orderId);
                txtDate.setText(formatedTime);
                txtOrderStatus.setText(orderStatus);
                txtAmount.setText(orderCost);
                txtOrderBy.setText(orderBy);
                loadOrderedItems();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}