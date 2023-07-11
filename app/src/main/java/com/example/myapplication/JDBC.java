package com.example.myapplication;

import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

public class JDBC {

    public static ArrayList<Product> getProducts(){
        ArrayList<Product> products = new ArrayList<>();
        Connection connection = DB.getInstance().getConnection();

        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("select * from Product");
        ) {
            ResultSetMetaData metaData = rs.getMetaData();
            while(rs.next()){
                int id = 0;
                String name = "";
                int price = 0;
                String image = "";
                for(int i = 1; i < metaData.getColumnCount() + 1; i++){
                    if (metaData.getColumnName(i).equals("productID")) {
                        id = rs.getInt(i);
                    } else if (metaData.getColumnName(i).equals("productName")) {
                        name = rs.getString(i);
                    } else if (metaData.getColumnName(i).equals("productPrice")) {
                        price = rs.getInt(i);
                    } else {
                        image = rs.getString(i);
                    }
                }
                Product product = new Product(id, name, price, image);
                products.add(product);
            }
        }
        catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return products;
    }
}
