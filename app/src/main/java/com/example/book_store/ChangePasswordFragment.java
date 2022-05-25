package com.example.book_store;

import static com.google.firebase.auth.EmailAuthProvider.getCredential;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.book_store.model.User;
import com.example.book_store.sharedpreferences.Constants;
import com.example.book_store.sharedpreferences.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.okhttp.internal.DiskLruCache;

import java.util.concurrent.CountDownLatch;


public class ChangePasswordFragment extends Fragment {

    EditText txtPass,txtNewPass;
    Button btnSave;
    Button btnBack;
    private String phone,pass;
    DatabaseReference reference;

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
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });
        return view;
    }
    private void updatePassword() {
        readData(new MyCallBack() {
            @Override
            public void onCallback(String value) {
                PreferenceManager preferenceManager = new PreferenceManager(getContext(), Constants.LOGIN_KEY_PREFERENCE_NAME);
                phone = preferenceManager.getString(Constants.LOGIN_PHONE);
                reference = FirebaseDatabase.getInstance().getReference("Users");
                pass = value;
                if (pass.equals(txtPass.getText().toString())) {
                    String newPass = txtNewPass.getText().toString();
                    if(newPass.length() >= 6){
                        reference.child(phone).child("password").setValue(newPass);
                        Toast.makeText(getActivity(), "Thay doi mat khau thanh cong", Toast.LENGTH_SHORT).show();
                    }
                    else  Toast.makeText(getActivity(),"Vui lòng nhập mật khẩu dài hơn 6 ký tự!",Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(getActivity(), "Thay doi mat khau that bai", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void readData(MyCallBack myCallback) {
        PreferenceManager preferenceManager = new PreferenceManager(getContext(), Constants.LOGIN_KEY_PREFERENCE_NAME);
        phone = preferenceManager.getString(Constants.LOGIN_PHONE);
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(phone).child("password").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}