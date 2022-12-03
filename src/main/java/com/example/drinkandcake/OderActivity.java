package com.example.drinkandcake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinkandcake.model.Cart;
import com.example.drinkandcake.model.Product;
import com.example.drinkandcake.my_interface.IClickItemProductListener;
import com.example.drinkandcake.sqlite.CartDao;
import com.example.drinkandcake.sqlite.ProductDao;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class OderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button btnDathang;
    private List<Product> mListP;
    private TextView totalPr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder);

        btnDathang = findViewById(R.id.btnDathang);
        totalPr = findViewById(R.id.totalPr);

        CartDao cartDao = new CartDao(this);

        SharedPreferences myR = getSharedPreferences("AccountActivity",MODE_PRIVATE);
        int idU = myR.getInt("idUser",0);

        ProductDao productDao = new ProductDao(this);
        mListP = new ArrayList<>();
        List<Cart> mListCart = new ArrayList<>();
        mListCart = cartDao.getByIdUser(idU+"");
        float totalPrice = 0;
        for(Cart a : mListCart){
            Product product = new Product();
            product = productDao.getById(a.getIdP() +"");
            product.setQuantity(a.getQuantity());
            mListP.add(product);
            totalPrice += product.getPrice()*product.getQuantity();
        }
        totalPr.setText(totalPrice+"đ");
        recyclerView = findViewById(R.id.RcvCategory2);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        FoodCartAdapter adapter = new FoodCartAdapter(this,mListP, new IClickItemProductListener() {
            @Override
            public void onClickItemProduct(Product product) {
                onClickGoToDetail(product);
            }
            @Override
            public void onClickBuy(Product product) {
                onClickRemove(product);
            }
            @Override
            public void onClickCart(Product product, int quantity) {
            }
        });
        recyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        Toast.makeText(OderActivity.this, "home ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(OderActivity.this, Home.class);
                        startActivity(intent);
                        break;
                    case R.id.action_cart:
                        Toast.makeText(OderActivity.this, "cart ", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_history:
                        Toast.makeText(OderActivity.this, "history ", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(OderActivity.this, HistoryActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.action_profile:
                        Toast.makeText(OderActivity.this, "profile ", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(OderActivity.this, ProfileActivity.class);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });

        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OderActivity.this,XacNhanDonHang.class);
                startActivity(intent);
            }
        });
    }

    private void onClickRemove(Product product) {
        CartDao cartDao = new CartDao(this);
        cartDao.deleteIdP(Integer.parseInt(product.getId()));
        finish();
        startActivity(getIntent());
    }

    private void onClickGoToDetail(Product product){
        Intent intent = new Intent(this,BuyProductActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("product",product);
        intent.putExtra("XNProduct",bundle);
        startActivity(intent);
    }

}