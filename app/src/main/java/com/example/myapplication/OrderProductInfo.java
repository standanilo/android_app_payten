package com.example.myapplication;

import androidx.room.ColumnInfo;

public class OrderProductInfo {
    @ColumnInfo(name = "productID")
    public int productID;

    @ColumnInfo(name = "productName")
    public String productName;

    @ColumnInfo(name = "productPrice")
    public String productPrice;

    @ColumnInfo(name = "quantity")
    public int quantity;
}

