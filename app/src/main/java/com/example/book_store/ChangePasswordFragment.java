package com.example.book_store;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.book_store.sharedpreferences.Constants;
import com.example.book_store.sharedpreferences.PreferenceManager;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;


public class ChangePasswordFragment extends Fragment {

    EditText txtPass,txtNewPass;
    Button btnSave;
    Button btnBack;
    private String phone;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        txtPass = view.findViewById(R.id.changePass_pass);
        txtNewPass = view.findViewById(R.id.changePass_newPass);
        btnSave = view.findViewById(R.id.changePass_btnSave);
//
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String oldPass = txtPass.getText().toString().trim();
//                String newPass = txtNewPass.getText().toString().trim();
//                if (TextUtils.isEmpty(oldPass)) {
//                    Toast.makeText(getActivity(), "Nhap mat khau hien tai", Toast.LENGTH_SHORT).show();
//                }
//                updatePassword(oldPass,newPass);
//            }
//        });
        return view;
    }
//    private void updatePassword(String oldPass,String newPass){
//        PreferenceManager preferenceManager = new PreferenceManager(getContext(), Constants.LOGIN_KEY_PREFERENCE_NAME);
//        phone = preferenceManager.getString(Constants.LOGIN_PHONE);
//        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        AuthCredential authCredential = getCredential(phone,oldPass)
//    }
}