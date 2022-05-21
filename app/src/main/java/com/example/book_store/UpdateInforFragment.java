package com.example.book_store;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_store.model.User;
import com.example.book_store.sharedpreferences.Constants;
import com.example.book_store.sharedpreferences.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

public class UpdateInforFragment extends Fragment {
    EditText txtName,txtAddress;
    Button btnSave;
    Button btnBack;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;
    private String phone,name,address;
    public UpdateInforFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_update_infor, container, false);
        txtName = view.findViewById(R.id.updateinfor_txtname);
        txtAddress = view.findViewById(R.id.updateinfor_txtaddress);
        btnSave = view.findViewById(R.id.updateinfor_btnUpdate);

        reference = FirebaseDatabase.getInstance().getReference("Users");
        showProfile();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfor();
            }
        });
        return view;
    }

    private void showProfile() {
        PreferenceManager preferenceManager = new PreferenceManager(getContext(), Constants.LOGIN_KEY_PREFERENCE_NAME);
        phone = preferenceManager.getString(Constants.LOGIN_PHONE);
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User profile = snapshot.getValue(User.class);

                if (profile != null){
                    name = profile.getFullName();
                    address = profile.getAddress();

                    txtName.setText(name);
                    txtAddress.setText(address);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void updateInfor() {
        if (isNameChanged() || isAddressChanged()) {
            Toast.makeText(getActivity(), "Da cap nhat thong tin", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(getActivity(), "Khong the cap nhat thong tin", Toast.LENGTH_SHORT).show();
    }
    private boolean isNameChanged(){
        PreferenceManager preferenceManager = new PreferenceManager(getContext(), Constants.LOGIN_KEY_PREFERENCE_NAME);
        phone = preferenceManager.getString(Constants.LOGIN_PHONE);
        if (! name.equals(txtName.getText().toString())) {
            reference.child(phone).child("fullName").setValue(txtName.getText().toString());
            name = txtName.getText().toString();
            return true;
        }
        else {
            return false;
        }
    }
    private boolean isAddressChanged(){
        PreferenceManager preferenceManager = new PreferenceManager(getContext(), Constants.LOGIN_KEY_PREFERENCE_NAME);
        phone = preferenceManager.getString(Constants.LOGIN_PHONE);
        if (! address.equals(txtAddress.getText().toString())) {
            reference.child(phone).child("address").setValue(txtAddress.getText().toString());
            address = txtAddress.getText().toString();
            return true;
        }
        else {
            return false;
        }
    }
}