package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Date;
@Entity(tableName = "Orders", foreignKeys = @ForeignKey(entity = Staff.class, parentColumns = "staffID", childColumns = "staff", onUpdate = ForeignKey.CASCADE))
public class Order {

    @PrimaryKey(autoGenerate = true)
    private int orderID;

    @ColumnInfo(name = "customerName")
    private String customerName;

    @ColumnInfo(name = "dateOf")
    private String dateOf;

    @ColumnInfo(name = "finished")
    private int finished;

    @ColumnInfo(name = "price")
    private int price;

    @ColumnInfo(name = "staff")
    private Integer staff;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "phone")
    private String phone;

    public Order() {

    }

    public Integer getStaff() {
        return staff;
    }

    public void setStaff(Integer staff) {
        this.staff = staff;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Order(String date, int finished, int price) {
        this.dateOf = date;
        this.finished = finished;
        this.price = price;
    }
    public Order(int id, String date, int finished, int price) {
        this.orderID = id;
        this.dateOf = date;
        this.finished = finished;
        this.price = price;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDateOf() {
        return dateOf;
    }

    public void setDateOf(String dateOf) {
        this.dateOf = dateOf;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
