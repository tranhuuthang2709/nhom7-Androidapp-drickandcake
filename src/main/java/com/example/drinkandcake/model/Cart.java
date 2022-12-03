package com.example.drinkandcake.model;

public class Cart {
    private int id,idP,idUser,quantity;

    public Cart() {
    }

    public Cart(int idP, int idUser, int quantity) {
        this.idP = idP;
        this.idUser = idUser;
        this.quantity = quantity;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
