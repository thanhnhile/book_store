package com.example.book_store;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.book_store.model.User;
import com.example.book_store.sharedpreferences.Constants;
import com.example.book_store.sharedpreferences.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Format;
import com.example.book_store.ui.FormatCurrency;

public class CheckOutFragment extends Fragment {
    EditText txtPhone;
    EditText txtAddress;
    EditText txtName;
    TextView txtValue;
    private DatabaseReference reference;
    Button btnBack;
    Button btnConfirm;
    private String phone;
    public CheckOutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_check_out, container, false);
        txtName = (EditText) view.findViewById(R.id.checkout_txtname);
        txtPhone = (EditText) view.findViewById(R.id.checkout_txtphone);
        txtAddress = (EditText) view.findViewById(R.id.checkout_txtaddress);
        btnBack = (Button) view.findViewById(R.id.changePass_btnBack);
        btnConfirm = (Button) view.findViewById(R.id.checkout_btnConfirm);
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
        getParentFragmentManager().setFragmentResultListener("data", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                int data = result.getInt("value");
                txtValue = (TextView) view.findViewById(R.id.txtTongHoaDon);
                int value = data + 20000;
                txtValue.setText(FormatCurrency.formatVND(value));
            }
        });
        return view;
    }
}