package com.example.drinkandcake.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.drinkandcake.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartDao {
    private SQLiteDatabase sqLiteDatabase;

    public CartDao(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getReadableDatabase();
    }


    @SuppressLint("Range")
    public List<Cart> get(String sql, String ...selectArgs){
        List<Cart> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectArgs);
        while (cursor.moveToNext()){
            Cart cart = new Cart();
            cart.setId(cursor.getInt(cursor.getColumnIndex("id")));
            cart.setIdP(cursor.getInt(cursor.getColumnIndex("idP")));
            cart.setIdUser(cursor.getInt(cursor.getColumnIndex("idUser")));
            cart.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));
            list.add(cart);
        }

        return list;
    }

    public Cart getById(String id){
        String sql = "SELECT * FROM cart WHERE id = ?";
        List<Cart> list = get(sql, id);
        return list.get(0);
    }
    public List<Cart> getByIdUser(String id){
        String sql = "SELECT * FROM cart WHERE idUser = ?";
        List<Cart> list = get(sql, id);
        return list;
    }

    public long insert(Cart cart){
        ContentValues contentValues = new ContentValues();
        contentValues.put("idP",cart.getIdP());
        contentValues.put("idUser",cart.getIdUser());
        contentValues.put("quantity",cart.getQuantity());
        return sqLiteDatabase.insert("cart",null,contentValues);
    }

    public long update(Cart cart){
        ContentValues contentValues = new ContentValues();
        contentValues.put("idP",cart.getIdP());
        contentValues.put("quantity",cart.getQuantity());

        return sqLiteDatabase.update("cart",contentValues,"id = ?",new String[]{cart.getId()+""});
    }

    public int deleteId(int id){
        return sqLiteDatabase.delete("cart","id = ?",new String[]{id+""});
    }
    public int deleteIdP(int id){
        return sqLiteDatabase.delete("cart","idP = ?",new String[]{id+""});
    }
}
