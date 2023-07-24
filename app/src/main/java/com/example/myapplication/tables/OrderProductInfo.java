package com.example.myapplication.tables;

import androidx.room.ColumnInfo;

public class OrderProductInfo {
    @ColumnInfo(name = "productID")
    public int productID;

    @ColumnInfo(name = "productName")
    public String productName;

    @ColumnInfo(name = "productPrice")
    public int productPrice;

    @ColumnInfo(name = "quantity")
    public int quantity;
}

