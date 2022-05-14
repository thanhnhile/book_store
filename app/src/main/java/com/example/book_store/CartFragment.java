package com.example.book_store;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.book_store.customadapter.CartItemAdapter;
import com.example.book_store.model.Book;
import com.example.book_store.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    RecyclerView cartRecyclerView;
    List<CartItem> cart;
    CartItemAdapter cartItemAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cart = new ArrayList<>();
        cartRecyclerView = (RecyclerView) view.findViewById(R.id.cart_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        cartRecyclerView.setLayoutManager(linearLayoutManager);
        FragmentManager fragmentManager = getParentFragmentManager();
        cartItemAdapter = new CartItemAdapter(getContext(),fragmentManager);
        cartRecyclerView.setAdapter(cartItemAdapter);
        testData();
        Toast.makeText(getContext(), Integer.toString(cart.size()), Toast.LENGTH_SHORT).show();
        return view;
    }
    private void testData(){

        Book b = new Book("1","a","a","a","https://firebasestorage.googleapis.com/v0/b/bookstore-3a6ce.appspot.com/o/images%2F2022_05_06_13_07_31?alt=media&token=6c1bf9e3-d25f-4314-aaa1-e837f734997b",
                1,1,1,"a",1);
        Book b2 = new Book("2","b","a","a","https://firebasestorage.googleapis.com/v0/b/bookstore-3a6ce.appspot.com/o/images%2F2022_05_06_13_07_31?alt=media&token=6c1bf9e3-d25f-4314-aaa1-e837f734997b",
                1,1,1,"a",1);
        cart.add(new CartItem(b,1));
        cart.add(new CartItem(b,1));
        cart.add(new CartItem(b2,2));
        cart.add(new CartItem(b2,2));
        cart.add(new CartItem(b2,5));
        cartItemAdapter.setData(cart);

    }
}