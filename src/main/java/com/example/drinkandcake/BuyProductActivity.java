package com.example.drinkandcake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinkandcake.model.Product;

public class BuyProductActivity extends AppCompatActivity {
    private TextView name,price,total,quantity;
    private Button btnDatHang,cong,tru;
    private int def;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_product);

        name = findViewById(R.id.nameP);
        price = findViewById(R.id.priceP);
        total = findViewById(R.id.total);
        btnDatHang = findViewById(R.id.btnDatHang);
        quantity = findViewById(R.id.quantity);
        cong = findViewById(R.id.cong);
        tru = findViewById(R.id.tru);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("XNProduct");
        Product product = (Product) bundle.getSerializable("product");

        name.setText(product.getName());
        price.setText(product.getPrice()+"");
        total.setText(product.getPrice()+20000+"đ");
        quantity.setText(product.getQuantity()+"");
        def = product.getQuantity();
        total.setText(product.getPrice()*def+20000+"đ");
        cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                def += 1;
                quantity.setText(def+"");
                total.setText(product.getPrice()*def+20000+"đ");
            }
        });
        tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(def >= 0){
                    def -= 1;
                    quantity.setText(def+"");
                    total.setText(product.getPrice()*def+20000+"đ");
                }
            }
        });

        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(BuyProductActivity.this,ThanhCongActivity.class);
                startActivity(intent1);
            }
        });
    }
}