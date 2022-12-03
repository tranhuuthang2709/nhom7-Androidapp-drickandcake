package com.example.drinkandcake.my_interface;

import com.example.drinkandcake.model.Product;

public interface IClickItemProductListener {
    void onClickItemProduct(Product product);
    void onClickBuy(Product product);
    void onClickCart(Product product, int quantity);
}
