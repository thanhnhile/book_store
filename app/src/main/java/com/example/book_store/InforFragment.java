package com.example.book_store;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.book_store.model.User;
import com.example.book_store.sharedpreferences.Constants;
import com.example.book_store.sharedpreferences.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class InforFragment extends Fragment {
    EditText txtPhone;
    EditText txtAddress;
    EditText txtName;
    Button btnBack;
    Button btnUpdateInfor;
    Fragment updateInforFragment = new UpdateInforFragment();
    private DatabaseReference reference;

    private String phone;
//    Fragment accountFragment = new AccountFragment();
    public InforFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_infor, container, false);
        btnBack = (Button) view.findViewById(R.id.infor_btnBack);
        btnUpdateInfor = (Button) view.findViewById(R.id.infor_btnUpdate);
        txtName = (EditText) view.findViewById(R.id.infor_txtname);
        txtPhone = (EditText) view.findViewById(R.id.infor_txtphone);
        txtAddress = (EditText) view.findViewById(R.id.infor_txtaddress);
        handleUpdateInfor();
        PreferenceManager preferenceManager = new PreferenceManager(getContext(), Constants.LOGIN_KEY_PREFERENCE_NAME);
        phone = preferenceManager.getString(Constants.LOGIN_PHONE);
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User profile = snapshot.getValue(User.class);

                if (profile != null){
                    String name = profile.getFullName();
                    String address = profile.getAddress();

                    txtName.setText(name);
                    txtAddress.setText(address);
                    txtPhone.setText(phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        handleBack();


        return view;
    }
//    private void handleBack(){
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getParentFragmentManager().beginTransaction().replace(R.id.container,acc).addToBackStack(null).commit();
//            }
//        });
//    }
    private void handleUpdateInfor(){
        btnUpdateInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.container,updateInforFragment).commit();
            }
        });
    }
}