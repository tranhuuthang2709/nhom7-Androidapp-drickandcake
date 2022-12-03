package com.example.drinkandcake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinkandcake.model.Account;
import com.example.drinkandcake.sqlite.AccountDao;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    private TextView name,email,address,phone;
    private Button signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.nameProfile);
        phone = findViewById(R.id.phoneProfile);
        email = findViewById(R.id.emailProfile);
        address = findViewById(R.id.addressProfile);
        signout = findViewById(R.id.buttonlogout);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        Toast.makeText(ProfileActivity.this, "home ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProfileActivity.this, Home.class);
                        startActivity(intent);
                        break;
                    case R.id.action_cart:
                        Toast.makeText(ProfileActivity.this, "cart ", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(ProfileActivity.this, OderActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.action_history:
                        Toast.makeText(ProfileActivity.this, "history ", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(ProfileActivity.this, HistoryActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.action_profile:
                        Toast.makeText(ProfileActivity.this, "profile ", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        SharedPreferences myR = getSharedPreferences("AccountActivity",MODE_PRIVATE);
        int idU = myR.getInt("idUser",0);

        AccountDao accountDao = new AccountDao(this);
        Account account = new Account();
        account = accountDao.getById(idU+"");

        name.setText(account.getName());
        phone.setText(account.getPhone());
        email.setText(account.getEmail());
        address.setText(account.getAddress());

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}