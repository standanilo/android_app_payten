package com.example.myapplication.database;

import androidx.room.RoomDatabase;

import com.example.myapplication.tables.Order;
import com.example.myapplication.tables.OrderProduct;
import com.example.myapplication.tables.Product;
import com.example.myapplication.tables.Staff;

@androidx.room.Database(entities = {Order.class, Product.class, Staff.class, OrderProduct.class}, version = 4)
public abstract class Database extends RoomDatabase {
    public abstract Dao getDao();
}
