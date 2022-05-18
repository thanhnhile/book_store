package com.example.book_store;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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


public class CategoryShowAllBooksFragment extends Fragment {

    RecyclerView recyclerView;
    Spinner spinnerSort;
    List<Category> list;
    CategoryAdapter categoryAdapter;
    String category;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_show_all_books, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.catetogory_recyclerview);
        spinnerSort = (Spinner) view.findViewById(R.id.spinner_sort);
        setSpinnerSort();
        Bundle bundle = getArguments();
        if(bundle != null){
           category = bundle.getString("category_name");
        }
        list = new ArrayList<>();
        FragmentManager fragmentManager = getParentFragmentManager();
        categoryAdapter = new CategoryAdapter(getContext(),fragmentManager,false);
        LinearLayoutManager linearLayoutManager;
        linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(categoryAdapter);
        getData();
        handleSortChange();
        return view;
    }
    private  void handleSortChange(){
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sort = (String) adapterView.getAdapter().getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void setSpinnerSort(){
        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("Mới nhất");
        spinnerItems.add("Bán chạy");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(),R.layout.style_spinner,spinnerItems);
        spinnerSort.setAdapter(spinnerAdapter);
    }
    private void getData(){
        List<Book> listBooks = new ArrayList<>();
        Query query = FirebaseDatabase.getInstance().getReference("Books")
                .orderByChild("category")
                .equalTo(category);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(list != null){
                    list.clear();
                }
                if(snapshot.exists()){
                    for(DataSnapshot data:snapshot.getChildren()){
                        Book b = data.getValue(Book.class);
                        listBooks.add(b);
                    }
                    list.add(new Category(category,listBooks));
                    categoryAdapter.setData(list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}