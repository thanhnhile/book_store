package com.example.book_store.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.book_store.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;


public class CategoryFragment extends Fragment {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    EditText txtCate;
    Button btnAdd;
    ListView cateListview;
    ArrayList<String> listCate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_category, container, false);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Categorys");
        txtCate = (EditText) view.findViewById(R.id.category_txt);
        btnAdd = (Button) view.findViewById(R.id.category_btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cateName = txtCate.getText().toString();
                if(cateName.trim().isEmpty()){
                    Toast.makeText(getContext(), "Trường không được để trống", Toast.LENGTH_SHORT).show();
                }
                else{
                    myRef.push().setValue(cateName).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(),"Thêm thể loại thành công",Toast.LENGTH_SHORT).show();
                                txtCate.setText("");
                            }
                            else{
                                Toast.makeText(getContext(),"Thêm thể loại thất bại\nVui lòng thử lại!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
        listCate = new ArrayList<>();
        cateListview = (ListView) view.findViewById(R.id.category_lv);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,listCate);
        cateListview.setAdapter(adapter);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(listCate != null){
                    listCate.clear();
                }
                for(DataSnapshot data:snapshot.getChildren()){
                    String category = data.getValue(String.class);
                    listCate.add(category);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}