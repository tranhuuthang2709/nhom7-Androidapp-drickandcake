package com.example.drinkandcake.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "DrinkAndCake";
    public static final int DB_VERSION = 14;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE product(id integer primary key,name text not null, price real not null,image text ,category text not null);";
        String sqlAcc = "CREATE TABLE account(id integer primary key,email text not null,password text not null, role text not null,phone text not null,name text not null,address text not null) ";
        String sqlCart = "CREATE TABLE cart(id integer primary key,idP integer not null,idUser integer not null, quantity integer not null) ";
        String sqlData = "INSERT INTO product(name,price,image,category) VALUES('Cafe đen',15000,'cafeden','cafe')," +
                "('Cafe sữa',20000,'cafesua','cafe')," +
                "('Bạc xỉu',20000,'bacxiu','cafe')," +
                "('Cafe Sài Gòn',20000,'cafesg','cafe')," +
                "('Sting',10000,'sting','Nước ngọt')," +
                "('Pepsi',10000,'pepsi','Nước ngọt')," +
                "('Coca',10000,'coca','Nước ngọt')," +
                "('Seven up',10000,'sevenup','Nước ngọt')," +
                "('Bánh mì ngọt',10000,'mingot','Bánh')," +
                "('Bánh mì chả',15000,'micha','Bánh')," +
                "('Bánh mì Hamburger',15000,'hamburger','Bánh')," +
                "('Bánh Pizza',50000,'pizza','Bánh')," +
                "('Trà sữa truyền thống',25000,'tstruyenthong','Trà sữa')," +
                "('Trà sữa thái xanh',25000,'tsthaixanh','Trà sữa')," +
                "('Trà sữa matcha',25000,'tsmatcha','Trà sữa')," +
                "('Trà sữa trân châu',25000,'tstranchau','Trà sữa')," +
                "('Trà sữa khoai môn',25000,'tskhoaimon','Trà sữa')";
        String sqlAdmin = "INSERT INTO account(email,password,role,phone,name,address) VALUES ('admin','admin','admin','admin','admin','admin')";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sqlCart);
        sqLiteDatabase.execSQL(sqlAcc);
        sqLiteDatabase.execSQL(sqlData);
        sqLiteDatabase.execSQL(sqlAdmin);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql ="DROP TABLE IF EXISTS product;";
        String sqlAccount ="DROP TABLE IF EXISTS account;";
        String sqlCart ="DROP TABLE IF EXISTS cart;";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sqlAccount);
        sqLiteDatabase.execSQL(sqlCart);
        onCreate(sqLiteDatabase);
    }


}
