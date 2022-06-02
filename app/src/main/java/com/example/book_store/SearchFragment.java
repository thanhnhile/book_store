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
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.book_store.customadapter.CategoryAdapter;
import com.example.book_store.model.Book;
import com.example.book_store.model.Category;
import com.example.book_store.ui.FilterCustomDialog;
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
    LinearLayout filter;
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
        //data biding
        searchView = rootView.findViewById(R.id.search);
        mainRecyclerView = (RecyclerView) rootView.findViewById(R.id.main_recycleView);
        filter = (LinearLayout) rootView.findViewById(R.id.filter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        mainRecyclerView.setLayoutManager(linearLayoutManager);
        FragmentManager fragmentManager = getParentFragmentManager();
        categoryAdapter = new CategoryAdapter(getContext(),fragmentManager,false);
        mainRecyclerView.setAdapter(categoryAdapter);
        handleFilter();
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
                        if(b.getIsActive() == 1 && b.getInStock() > 0)
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
    private void handleFilter(){
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FilterCustomDialog filterCustomDialog = FilterCustomDialog.newInstance();
                filterCustomDialog.setCategoryAdapter(categoryAdapter);
                filterCustomDialog.show(fragmentManager,"test dialog");
            }
        });
    }
    private void handleSearch(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //queryDB(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                queryDB(s);
                return false;
            }
        });
    }


}