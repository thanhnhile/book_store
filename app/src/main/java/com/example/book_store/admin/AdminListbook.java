package com.example.book_store.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.book_store.CustomAdapter.BookListViewAdapter;
import com.example.book_store.R;
import com.example.book_store.model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AdminListbook extends Fragment {
    //Realtime Database
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    //biding
    ListView listView;
    ArrayList<Book> listBook;
    BookListViewAdapter adapter;
    FragmentManager fragmentManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_listbook, container, false);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Books");
        listView = (ListView) view.findViewById(R.id.admin_lv);
        fragmentManager = getParentFragmentManager();
        listBook = new ArrayList<>();
        adapter = new BookListViewAdapter(getContext(),listBook,fragmentManager);
        listView.setAdapter(adapter);
        getData();
        return view;
    }
    private void getData(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(listBook != null){
                    listBook.clear();
                }
                for(DataSnapshot data:snapshot.getChildren()){
                    Book book = data.getValue(Book.class);
                    listBook.add(book);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}