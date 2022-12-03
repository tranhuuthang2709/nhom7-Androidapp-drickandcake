package com.example.drinkandcake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.drinkandcake.model.Cart;
import com.example.drinkandcake.model.Product;
import com.example.drinkandcake.my_interface.IClickItemProductListener;
import com.example.drinkandcake.sqlite.CartDao;
import com.example.drinkandcake.sqlite.DBHelper;
import com.example.drinkandcake.sqlite.ProductDao;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    private RecyclerView recyclerView;
    CardView cafe,ts,nuoc,banh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cafe= findViewById(R.id.cafeCategory);
        ts= findViewById(R.id.ts);
        nuoc= findViewById(R.id.nuoc);
        banh= findViewById(R.id.banh);
        onClickCafe();
        onClickts();
        onClickNuoc();
        onClickBanh();

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        Toast.makeText(Home.this, "home ", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_cart:
                        Toast.makeText(Home.this, "cart ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Home.this, OderActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_history:
                        Toast.makeText(Home.this, "history ", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(Home.this, HistoryActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.action_profile:
                        Toast.makeText(Home.this, "profile ", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(Home.this, ProfileActivity.class);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });

        recyclerView = findViewById(R.id.rcv_food);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        FoodAdapter adapter = new FoodAdapter(this,getListFood(), new IClickItemProductListener() {
            @Override
            public void onClickItemProduct(Product product) {
                onClickGoToDetail(product);
            }
            @Override
            public void onClickBuy(Product product) {
            }
            @Override
            public void onClickCart(Product product, int quantity) {
                clickOrder(product,quantity);
            }
        });
        recyclerView.setAdapter(adapter);
        sqLiteDatabase.close();
    }

    private void onClickCafe() {
        cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("myCate",MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("category","cafe");
                myEdit.commit();
                Intent intent = new Intent(Home.this,CategoryActivity.class);
                startActivity(intent);
            }
        });
    }
    private void onClickts() {
        ts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("myCate",MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("category","Trà sữa");
                myEdit.commit();
                Intent intent = new Intent(Home.this,CategoryActivity.class);
                startActivity(intent);
            }
        });
    }
    private void onClickNuoc() {
        nuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("myCate",MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("category","Nước ngọt");
                myEdit.commit();
                Intent intent = new Intent(Home.this,CategoryActivity.class);
                startActivity(intent);
            }
        });
    }
    private void onClickBanh() {
        banh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("myCate",MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("category","Bánh");
                myEdit.commit();
                Intent intent = new Intent(Home.this,CategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<Product> getListFood() {
        ProductDao productDao = new ProductDao(this);
        return productDao.getALL();
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        SharedPreferences sharedPreferences = getSharedPreferences("mySave",MODE_PRIVATE);
//        SharedPreferences.Editor myEdit = sharedPreferences.edit();
//        myEdit.putString("idProduct","id");
//        myEdit.commit();
//
//    }
    private void onClickGoToDetail(Product product){
        Intent intent = new Intent(this,BuyProductActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("product",product);
        intent.putExtra("XNProduct",bundle);
        startActivity(intent);
    }
    private void clickOrder(Product product,int quantity) {
        SharedPreferences myR = getSharedPreferences("AccountActivity",MODE_PRIVATE);
        int idU = myR.getInt("idUser",0);
        String nameU = myR.getString("nameUser","");

        CartDao cartDao = new CartDao(this);

        List<Cart> listC = new ArrayList<>();
        listC = cartDao.getByIdUser(idU+"");
        Cart cart = new Cart();
        boolean a = false;
        for(Cart c :listC){
            if((c.getIdP()+"").equals(product.getId())){
                cart = c;
                cart.setQuantity(c.getQuantity()+product.getQuantity());
                a = true;
                break;
            }
        }
        if(a){
            cartDao.update(cart);
        }else{
            Cart cart1 = new Cart(Integer.parseInt(product.getId()),idU,quantity);
            cartDao.insert(cart1);
        }
        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
    }
}