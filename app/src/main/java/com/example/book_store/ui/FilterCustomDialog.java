package com.example.book_store.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.book_store.R;
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

public class FilterCustomDialog extends DialogFragment {
    private EditText txtStartYear;
    private EditText txtEndYear;
    private EditText txtMinPrice;
    private EditText txtMaxPrice;
    private Button btnFilter;
    private Button btnClose;
    private CategoryAdapter categoryAdapter;
    public FilterCustomDialog() {
    }

    public void setCategoryAdapter(CategoryAdapter adapter){
        this.categoryAdapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.filter_dialog_layout,container);
    }

    public static FilterCustomDialog newInstance() {
        Bundle args = new Bundle();
        FilterCustomDialog fragment = new FilterCustomDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(view != null){
            txtStartYear = view.findViewById(R.id.dialog_txt_startYear);
            txtEndYear = view.findViewById(R.id.dialog_txt_endYear);
            txtMinPrice = view.findViewById(R.id.dialog_txt_minPrice);
            txtMaxPrice = view.findViewById(R.id.dialog_txt_maxPrice);
            btnClose = view.findViewById(R.id.dialog_btn_close);
            btnFilter = view.findViewById(R.id.dialog_btn_filter);
        }
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String y1 = txtStartYear.getText().toString();
                String y2 = txtEndYear.getText().toString();
                String p1 = txtMinPrice.getText().toString();
                String p2 = txtMaxPrice.getText().toString();
                filterSearch(y1,y2,p1,p2);
            }
        });
        getDialog().getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }
    private void filterSearch(String y1,String y2,String p1,String p2){
        int startYear,endYear,minPrice,maxPrice;
        int checkType = 0;
        //1 thoa dk nam xuat ban
        //2 thoa dk gia
        //3 thoa hai dieu kien
        if(!(y1.isEmpty() || y2.isEmpty())){
            startYear = Integer.parseInt(y1);
            endYear = Integer.parseInt(y2);
            checkType = checkType == 0 ? 1 : 3;
        }
        if(!(p1.isEmpty() || p2.isEmpty())){
            minPrice = Integer.parseInt(p1);
            maxPrice = Integer.parseInt(p2);
            checkType = checkType == 0 ? 2 : 3;
        }
        if(checkType == 0){
            Toast.makeText(getContext(), "Không hợp lệ. Vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(checkType == 1){
            queryDB("mot");
        }
        else if(checkType == 2){
            queryDB("hai");
        }
        else queryDB("ba");
        Toast.makeText(getContext(), Integer.toString(checkType), Toast.LENGTH_SHORT).show();

    }
    private void queryDB(String search){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        List<Category> mListCategorys = new ArrayList<>();
        List<Book> listBooks = new ArrayList<>();
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
}
