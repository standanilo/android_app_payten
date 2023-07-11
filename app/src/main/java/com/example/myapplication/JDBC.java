package com.example.myapplication;

import android.util.Log;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

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

    public static boolean deleteProduct(String name) {
        Connection connection = DB.getInstance().getConnection();
        String q = "select * from Product where productName = ?";
        try (PreparedStatement ps = connection.prepareStatement(q, PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Log.d("Key here", name + " id:" + rs.getInt(1));
            } else {
                Log.e("Key here", name + " id: 0" );
                return false;
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }

        String query = "delete from Product where productName = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, name);

            ps.executeUpdate();
            Log.d("Delete", "Deleted " + name);
            return true;

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return false;
    }

    public static void addOrder(HashMap<Product, Integer> orders, String customer) {
        Date date = new Date(System.currentTimeMillis());
        int finished = 0;
        AtomicInteger orderID = new AtomicInteger();
        Connection connection = DB.getInstance().getConnection();
        String query = "insert into Orders (customerName, dateOf, finished, price) values (?, ?, ?, ?) ";
        AtomicInteger price = new AtomicInteger();
        orders.forEach((key, value) -> {
            price.addAndGet(value * key.getPrice());
        });
        try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setString(1, customer);
            ps.setDate(2, date);
            ps.setInt(3, finished);
            ps.setInt(4, price.get());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                orderID.set(rs.getInt(1));
                Log.d("Key added", "id: " + rs.getInt(1));
            }

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }

        if (orderID.get() != 0) {

            orders.forEach((key, value) -> {
                int productID = key.getId();
                int quantity = value;
                int amount = quantity * key.getPrice();
                String query1 = "insert into Order_product (orderID, productID, quantity, price) values (?, ?, ?, ?) ";

                try (PreparedStatement ps = connection.prepareStatement(query1, PreparedStatement.RETURN_GENERATED_KEYS)){
                    ps.setInt(1, orderID.get());
                    ps.setInt(2, productID);
                    ps.setInt(3, quantity);
                    ps.setInt(4, amount);

                    ps.executeUpdate();

                    ResultSet rs = ps.getGeneratedKeys();
                    if(rs.next()){
                        Log.d("Key added", "id: " + rs.getInt(1));
                    }

                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            });
        }

    }
    public static boolean addProduct(String name, int price) {
        Connection connection = DB.getInstance().getConnection();
        String q = "select * from Product where productName = ?";
        try (PreparedStatement ps = connection.prepareStatement(q, PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Log.e("Key added", name + " id:" + rs.getInt(1));
                return false;
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }


        String query = "insert into Product (productName, productPrice, productImage) values (?, ?, ?) ";

        try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setString(1, name);
            ps.setInt(2, price);
            ps.setString(3, "");

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                Log.d("Key added", name + " id:" + rs.getInt(1));
            }
            return true;

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return false;
    }
}
