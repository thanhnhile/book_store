package com.example.book_store;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.book_store.customadapter.CartItemAdapter;
import com.example.book_store.database.CartDao;
import com.example.book_store.model.Book;
import com.example.book_store.model.CartItem;
import com.example.book_store.ui.FormatCurrency;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    public static RecyclerView cartRecyclerView;
    public static TextView txtCartValue;
    List<CartItem> cart;
    CartItemAdapter cartItemAdapter;
    CartDao cartDao;
    List<Integer>cartItemValues;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cartDao = new CartDao(getContext());
        cart = new ArrayList<>();
        //biding
        cartRecyclerView = (RecyclerView) view.findViewById(R.id.cart_recyclerview);
        txtCartValue = (TextView) view.findViewById(R.id.txtTongGioHang);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        cartRecyclerView.setLayoutManager(linearLayoutManager);
        FragmentManager fragmentManager = getParentFragmentManager();
        cartItemValues = new ArrayList<>();
        cartItemAdapter = new CartItemAdapter(getContext(),fragmentManager);
        cartRecyclerView.setAdapter(cartItemAdapter);
        getListCarts();
        return view;
    }
    private void getListCarts(){
        cart = cartDao.getAll();
        cartItemAdapter.setData(cart);
        updateCartValue();

    }
    public static void updateCartValue(){
        int sum = 0;
        View child;
        for(int i=0;i<cartRecyclerView.getChildCount();i++){
            child = cartRecyclerView.getChildAt(i);
            RecyclerView.ViewHolder holder = cartRecyclerView.getChildViewHolder(child);
            TextView txtPrice = holder.itemView.findViewById(R.id.lv_txt_price);
            int price = Integer.parseInt(txtPrice.getText().toString());
            sum += price;
        }
        txtCartValue.setText(FormatCurrency.formatVND(sum));

    }
//    private void testData(){
//
//        Book b = new Book("1","a","a","a","https://firebasestorage.googleapis.com/v0/b/bookstore-3a6ce.appspot.com/o/images%2F2022_05_06_13_07_31?alt=media&token=6c1bf9e3-d25f-4314-aaa1-e837f734997b",
//                1,1,1,"a",1);
//        Book b2 = new Book("2","b","a","a","https://firebasestorage.googleapis.com/v0/b/bookstore-3a6ce.appspot.com/o/images%2F2022_05_06_13_07_31?alt=media&token=6c1bf9e3-d25f-4314-aaa1-e837f734997b",
//                1,1,1,"a",1);
//        cart.add(new CartItem(b,1));
//        cart.add(new CartItem(b,1));
//        cart.add(new CartItem(b2,2));
//        cart.add(new CartItem(b2,2));
//        cart.add(new CartItem(b2,5));
//        cartItemAdapter.setData(cart);
//        MenuActivity.setCountProductInCart(cart.size());
//
//    }
}