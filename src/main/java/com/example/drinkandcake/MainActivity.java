package com.example.drinkandcake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.drinkandcake.model.Account;
import com.example.drinkandcake.sqlite.AccountDao;
import com.example.drinkandcake.sqlite.DBHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button login,register;
    private EditText edTextEmail,edTextPassord;
    private Account account = new Account();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();


        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        edTextEmail = findViewById(R.id.edEmail);
        edTextPassord = findViewById(R.id.edPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, Home.class);
//                startActivity(intent);
                onClickSigninSqlite();
               // onClickSignin();
            }

            private void onClickSigninSqlite() {
                String email = edTextEmail.getText().toString().trim();
                String password = edTextPassord.getText().toString().trim();
                AccountDao accountDao = new AccountDao(MainActivity.this);
                List<Account> list = accountDao.getALL();
                int i = 0;
                boolean bol = false;
                for(Account a : list){
                    if(email.equals(a.getEmail()) && password.equals(a.getPassword())){
                        bol = true;
                        account = a;
                        onPause();
                        Intent intent = new Intent(MainActivity.this, Home.class);
                        startActivity(intent);
                        finishAffinity();
                        break;
                    }
                }
                if(bol){
                    Toast.makeText(MainActivity.this, "Authentication success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Authentication fail", Toast.LENGTH_SHORT).show();
                }

            }

            private void onClickSignin() {
                String email = edTextEmail.getText().toString();
                String password = edTextPassord.getText().toString();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(MainActivity.this, Home.class);
                                    startActivity(intent);
                                    finishAffinity();
                                } else {
                                    Toast.makeText(MainActivity.this, "Authentication fail", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, register.class);
                startActivity(intent);
                finishAffinity();
            }
        });
        sqLiteDatabase.close();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("AccountActivity",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("idUser",account.getId());
        myEdit.commit();
    }
}