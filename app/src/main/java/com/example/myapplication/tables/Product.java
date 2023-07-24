package com.example.myapplication.tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Product")
public class Product implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int productID;
    @NonNull
    @ColumnInfo(name = "productName")
    private String productName;
    @ColumnInfo(name = "productPrice")
    private int productPrice;

    public Product() {

    }

    public Product(String name, int price) {
        this.productName = name;
        this.productPrice = price;
    }

    public Product(int id, String name, int price) {
        this.productID = id;
        this.productName = name;
        this.productPrice = price;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setProductName(@NonNull String productName) {
        this.productName = productName;
    }

    public int getProductID() {
        return productID;
    }
    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }


    @Override
    public String toString() {
        return "Product:" +
                "id=" + productID +
                ", name='" + productName + '\'' +
                ", price=" + productPrice;
    }
}
