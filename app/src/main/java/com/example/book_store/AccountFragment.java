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
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_store.model.User;
import com.example.book_store.sharedpreferences.Constants;
import com.example.book_store.sharedpreferences.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AccountFragment extends Fragment {
    TextView txtName;
    TextView txtPhone;
    Button btnLogout;
    Button btnInfor;
    Button btnChangePassword;
    Fragment inforFragment = new InforFragment();
    Fragment changePassFragment = new ChangePasswordFragment();
    private DatabaseReference reference;
    private String phone;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        txtName = view.findViewById(R.id.account_txtUserName);
        txtPhone = view.findViewById(R.id.account_txtUserPhone);
        btnLogout = view.findViewById(R.id.account_btnLogOut);
        btnInfor = view.findViewById(R.id.account_btnInformation);
        btnChangePassword = view.findViewById(R.id.account_btnChangePass);
        handleInfor();
        handleLogout();
        handleChangePass();
        PreferenceManager preferenceManager = new PreferenceManager(getContext(), Constants.LOGIN_KEY_PREFERENCE_NAME);
        phone = preferenceManager.getString(Constants.LOGIN_PHONE);
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User profile = snapshot.getValue(User.class);

                if (profile != null){
                    String name = profile.getFullName();

                    txtName.setText(name);
                    txtPhone.setText(phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
    private void handleLogout(){
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceManager preferenceManager = new PreferenceManager(getContext(), Constants.LOGIN_KEY_PREFERENCE_NAME);
                preferenceManager.clear();
                Intent intent = new Intent(getContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void handleInfor(){
        btnInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.container,inforFragment).commit();
            }
        });
    }
    private void handleChangePass(){
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.container,changePassFragment).commit();
            }
        });
    }
}