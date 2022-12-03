package com.example.drinkandcake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinkandcake.model.Cart;
import com.example.drinkandcake.model.Product;
import com.example.drinkandcake.my_interface.IClickItemProductListener;
import com.example.drinkandcake.sqlite.CartDao;
import com.example.drinkandcake.sqlite.ProductDao;

import java.util.ArrayList;
import java.util.List;

public class XacNhanDonHang extends AppCompatActivity {
    private TextView name,price,total;
    private Button confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_don_hang);

        name = findViewById(R.id.nameProduct);
        price = findViewById(R.id.priceProduct);
        total = findViewById(R.id.totalPrice);
        confirm = findViewById(R.id.btnConfirm);
        CartDao cartDao = new CartDao(this);

        SharedPreferences myR = getSharedPreferences("AccountActivity",MODE_PRIVATE);
        int idU = myR.getInt("idUser",0);
        //String nameU = myR.getString("nameUser","");

        ProductDao productDao = new ProductDao(this);
        List<Product> mListP = new ArrayList<>();
        List<Cart> mListCart = new ArrayList<>();
        mListCart = cartDao.getByIdUser(idU+"");
        float totalPrice = 0;
        String StrName = "",StrPrice="";
        for(Cart a : mListCart){
            Product product = new Product();
            product = productDao.getById(a.getIdP() +"");
            product.setQuantity(a.getQuantity());
            mListP.add(product);
            StrName += product.getName()+" (SL:"+product.getQuantity()+")\n";
            StrPrice += product.getPrice()+"đ\n";
            totalPrice += product.getPrice()*product.getQuantity();
        }
        name.setText(StrName);
        price.setText(StrPrice);
        total.setText(totalPrice+20000+"đ");

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(XacNhanDonHang.this,ThanhCongActivity.class);
                startActivity(intent);
            }
        });
    }

}