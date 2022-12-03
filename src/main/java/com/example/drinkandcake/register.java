package com.example.drinkandcake;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.drinkandcake.model.Account;
import com.example.drinkandcake.sqlite.AccountDao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {
    Button registerBtn;
    EditText email,password,phone,name,address;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.emailRegister);
        password = findViewById(R.id.passwordRegister);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        name = findViewById(R.id.name);
        registerBtn = findViewById(R.id.register);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AccountDao accountDao = new AccountDao(register.this);
                    String emailR = email.getText().toString().trim();
                    String passwordR = password.getText().toString().trim();
                    String phoneR = phone.getText().toString().trim();
                    String addressR = address.getText().toString().trim();
                    String nameR = name.getText().toString().trim();

                    accountDao.insert(new Account(emailR,passwordR,"user",phoneR,nameR,addressR));
                    Intent intent = new Intent(register.this,MainActivity.class);
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(register.this,"Register fail",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}