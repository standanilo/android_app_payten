package com.example.myapplication;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Order.class, Product.class, Staff.class, OrderProduct.class}, version = 2)
public abstract class Database extends RoomDatabase {
    public abstract Dao getDao();
}
