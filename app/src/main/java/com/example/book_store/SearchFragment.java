package com.example.book_store;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.book_store.customadapter.CategoryAdapter;
import com.example.book_store.model.Book;
import com.example.book_store.model.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    List<Category> mListCategorys;
    List<Book> listBooks;
    CategoryAdapter categoryAdapter;
    SearchView searchView;
    RecyclerView mainRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        //Firebase
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Books");
        mListCategorys = new ArrayList<Category>();
        listBooks = new ArrayList<>();
        searchView = rootView.findViewById(R.id.search);
        mainRecyclerView = (RecyclerView) rootView.findViewById(R.id.main_recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        mainRecyclerView.setLayoutManager(linearLayoutManager);
        FragmentManager fragmentManager = getParentFragmentManager();
        categoryAdapter = new CategoryAdapter(getContext(),fragmentManager);
        mainRecyclerView.setAdapter(categoryAdapter);
        handleSearch();
        return rootView;
    }
    private void queryDB(String search){
        Query query = database.getReference("Books")
                .orderByChild("title").startAt(search);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(listBooks != null){
                    listBooks.clear();
                }
                if(mListCategorys != null){
                    mListCategorys.clear();
                }
                if(snapshot.exists()){
                    for (DataSnapshot data:snapshot.getChildren()){
                        Book b = data.getValue(Book.class);
                        if(b.getIsActive() == 1)
                            listBooks.add(b);
                    }
                    mListCategorys.add(new Category(null,listBooks));
                    categoryAdapter.setData(mListCategorys);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void handleSearch(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                queryDB(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }


}