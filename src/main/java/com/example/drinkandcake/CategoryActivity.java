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

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button cong,tru,btnGioHang;
    private TextView quantity,title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        SharedPreferences myR = getSharedPreferences("myCate",MODE_PRIVATE);
        String category = myR.getString("category","");

        ProductDao productDao = new ProductDao(this);
        btnGioHang = findViewById(R.id.btnGioHang);
        title = findViewById(R.id.title);

        title.setText(category);
        List<Product> list = new ArrayList<>();
        list = productDao.getByCategory(category);

        recyclerView = findViewById(R.id.RcvCategory);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        CategoryAdapter adapter = new CategoryAdapter(this,list, new IClickItemProductListener() {
            @Override
            public void onClickItemProduct(Product product) {
            }
            @Override
            public void onClickBuy(Product product) {
                clickBuy(product);
            }
            @Override
            public void onClickCart(Product product,int quantity) {
                clickOrder(product,quantity);
            }
        });
        recyclerView.setAdapter(adapter);

        btnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CategoryActivity.this, OderActivity.class);
                startActivity(intent);
            }
        });
    }

    private void clickBuy(Product product) {
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