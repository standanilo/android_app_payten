package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Order_product", foreignKeys =
        { @ForeignKey(entity = Order.class, parentColumns = "orderID", childColumns = "orderID", onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = Product.class, parentColumns = "productID", childColumns = "productID", onUpdate = ForeignKey.CASCADE)})
public class OrderProduct {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    @ColumnInfo(name = "orderID")
    private int orderID;
    @ColumnInfo(name = "productID")
    private int productID;
    @ColumnInfo(name = "quantity")
    private int quantity;
    @ColumnInfo(name = "price")
    private int price;

    public OrderProduct() {

    }

    public OrderProduct(int orderID, int productID, int quantity, int price) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderProduct(int id, int orderID, int productID, int quantity, int price) {
        this.ID = id;
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
