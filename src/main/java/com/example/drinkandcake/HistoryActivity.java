package com.example.drinkandcake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        Toast.makeText(HistoryActivity.this, "home ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HistoryActivity.this, Home.class);
                        startActivity(intent);
                        break;
                    case R.id.action_cart:
                        Toast.makeText(HistoryActivity.this, "cart ", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(HistoryActivity.this, OderActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.action_history:
                        Toast.makeText(HistoryActivity.this, "history ", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.action_profile:
                        Toast.makeText(HistoryActivity.this, "profile ", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(HistoryActivity.this, ProfileActivity.class);
                        startActivity(intent1);
                        break;
                }
                return true;
            }
        });
    }
}