package com.example.book_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.book_store.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {
    EditText txtFullName,txtPhone,txtPass;
    Button btnRegister,btnLogin;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        txtFullName = (EditText) findViewById(R.id.txtFullname);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtPass = (EditText) findViewById(R.id.txPass);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToLoginActivity();
            }
        });
    }
    private void register(){
        String phone = txtPhone.getText().toString();
        String fullName = txtFullName.getText().toString();
        String pass = txtPass.getText().toString();
        if(isValid(phone,fullName,pass)){
            User user = new User(fullName,phone,pass,"None");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //Check phone number is exist
                    if(snapshot.hasChild(phone)){
                        Toast.makeText(SignupActivity.this, "Số điện thoại đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        myRef.child(user.getPhone()).setValue(user);
                        Toast.makeText(SignupActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        //Login
                        changeToLoginActivity();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
    private Boolean isValid(String phone, String fullName, String password){
        String validNumber = "^[+]?[0-9]{8,15}$";
        if(!phone.matches(validNumber)){
            Toast.makeText(SignupActivity.this,"Số điện thoại không hợp lệ!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(fullName.trim().isEmpty() || password.isEmpty()){
            Toast.makeText(SignupActivity.this,"Không được để trống",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.length() < 6){
            Toast.makeText(SignupActivity.this,"Vui lòng nhập mật khẩu dài hơn 6 ký tự!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void changeToLoginActivity(){
        Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(loginIntent);
    }
}