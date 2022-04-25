package com.example.book_store.admin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.book_store.R;
import com.example.book_store.model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CRUDFragment extends Fragment {
    private static final int REQUEST_CODE = 2;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    //Data biding
    EditText txtTitle, txtAuthor,txtYear,txtPrice,txtNum,txtDes;
    Switch isActive;
    Spinner snCategory;
    Button btnAdd,btnAddImg;
    ImageView img;
    Book book = new Book();
    ArrayList<String> categorys;
    //
    ActivityResultLauncher<String> getImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_c_r_u_d, container, false);
        //Firebase
        database = FirebaseDatabase.getInstance();
        //biding
        txtTitle = (EditText) view.findViewById(R.id.crud_title);
        txtAuthor = (EditText) view.findViewById(R.id.crud_author);
        txtYear = (EditText) view.findViewById(R.id.crud_year);
        txtPrice = (EditText) view.findViewById(R.id.crud_price);
        txtNum = (EditText) view.findViewById(R.id.crud_in_stock);
        txtDes = (EditText) view.findViewById(R.id.crud_des);
        isActive = (Switch) view.findViewById(R.id.crud_sw_active);
        snCategory = (Spinner) view.findViewById(R.id.crud_categorys);
        img = (ImageView) view.findViewById(R.id.crud_img);
        //Button add image
        btnAddImg = (Button) view.findViewById(R.id.crud_btn_img) ;
        //Button add book to DB
        btnAdd = (Button) view.findViewById(R.id.crud_btn_add);
        //
        getImage = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        img.setImageURI(result);
                    }
                }
        );
        //Fill data category
        categorys = new ArrayList<>();
        getCategory();
        //Handle event

        getImage();
        return view;
    }
    private void getImage(){
        btnAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage.launch("image/*");

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getCategory(){
        myRef = database.getReference("Categorys");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    String cate = data.getValue(String.class);
                    categorys.add(cate);
                }
                ArrayAdapter adapter;
                adapter = new ArrayAdapter(getContext(),R.layout.style_spinner,categorys);
                //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                snCategory.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        snCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Adapter adapter = adapterView.getAdapter();
                String selectedCate = (String) adapter.getItem(i);
                book.setCategory(selectedCate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}