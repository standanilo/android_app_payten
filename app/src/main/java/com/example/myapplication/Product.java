package com.example.myapplication;

import java.io.Serializable;

public class Product implements Serializable {

    private int id;
    private String name;
    private int price;
    private String image;

    public Product(int id, String name, int price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product:" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image=" + image;
    }
}
