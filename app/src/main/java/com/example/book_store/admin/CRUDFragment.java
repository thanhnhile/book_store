package com.example.book_store.admin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
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
import android.webkit.MimeTypeMap;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CRUDFragment extends Fragment {
    private static final int REQUEST_CODE = 2;
    //Realtime Database
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    //Firebase Storage
    FirebaseStorage storage;
    StorageReference storageReference;
    //Data biding
    EditText txtTitle, txtAuthor,txtYear,txtPrice,txtNum,txtDes;
    Switch swActive;
    Spinner snCategory;
    Button btnAdd,btnAddImg;
    ImageView img;
    Uri imgUri;
    Book book;
    ArrayList<String> categorys;
    //
    ActivityResultLauncher<String> getImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_c_r_u_d, container, false);
        //get Realtime
        database = FirebaseDatabase.getInstance();
        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        //biding
        book = new Book();
        txtTitle = (EditText) view.findViewById(R.id.crud_title);
        txtAuthor = (EditText) view.findViewById(R.id.crud_author);
        txtYear = (EditText) view.findViewById(R.id.crud_year);
        txtPrice = (EditText) view.findViewById(R.id.crud_price);
        txtNum = (EditText) view.findViewById(R.id.crud_in_stock);
        txtDes = (EditText) view.findViewById(R.id.crud_des);
        swActive = (Switch) view.findViewById(R.id.crud_sw_active);
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
                        imgUri = result;
                    }
                }
        );
        //Fill data category
        categorys = new ArrayList<>();
        getCategory();
        //Handle event
        onGetImageClick();
        onAddClick();
        return view;
    }
    private void onGetImageClick(){
        btnAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage.launch("image/*");

            }
        });
    }
    private void onAddClick(){
        myRef = database.getReference("Books");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check data valid
                String title = txtTitle.getText().toString();
                String author = txtAuthor.getText().toString();
                String category = String.valueOf(snCategory.getSelectedItem());
                String year = txtYear.getText().toString();
                String price = txtPrice.getText().toString();
                String inStock = txtNum.getText().toString();
                //int year = Integer.parseInt(txtYear.getText().toString());
                //int price = Integer.parseInt(txtPrice.getText().toString());
                //int inStock = Integer.parseInt(txtNum.getText().toString());
                String desc = txtDes.getText().toString();
                int isActive = 1;
                if(!swActive.isChecked()){
                    isActive = 0;
                }
                if(isValid(title,author,category,year,price,inStock,desc)){
                    //Upload image and get link
                    if(imgUri != null){
                        uploadImage(imgUri);
                        if(book.getImgURL() != null){
                            //Create book object
                           book.setTitle(title);
                           book.setAuthor(author);
                           book.setCategory(category);
                           book.setYear(Integer.parseInt(year));
                           book.setPrice(Integer.parseInt(price));
                           book.setInStock(Integer.parseInt(inStock));
                           book.setDescription(desc);
                           book.setIsActive(isActive);
                            //Add to DB
                            String id = myRef.push().getKey();
                            book.setId(id);
                            myRef.push().setValue(book).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                        Toast.makeText(getContext(), "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                                    else Toast.makeText(getContext(), "Thêm sách thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "Ảnh bìa không được để trống", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
    private void uploadImage(Uri uri){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imgURL = uri.toString();
                        book.setImgURL(imgURL);
                        Toast.makeText(getContext(), "Tải hình ảnh thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Tải hình ảnh thất bại", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        });
    }
    private boolean isValid(String title,String category,String author,String year,String price,String inStock,String desc){
        if(title.trim().isEmpty() || author.trim().isEmpty() || year.trim().isEmpty() ||
        category.trim().isEmpty() || price.trim().isEmpty() || inStock.trim().isEmpty() || desc.trim().isEmpty()){
            Toast.makeText(getContext(), "Các trường không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
//        snCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Adapter adapter = adapterView.getAdapter();
//                String selectedCate = (String) adapter.getItem(i);
//                book.setCategory(selectedCate);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
    }
}