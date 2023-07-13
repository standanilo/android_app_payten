package com.example.myapplication;

import java.sql.Date;

public class Order {

    private int id;

    private String customer;

    private Date date;

    private int finished;

    private int price;

    public Order(int id, String customer, Date date, int finished, int price) {
        this.id = id;
        this.date = date;
        this.customer = customer;
        this.finished = finished;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
