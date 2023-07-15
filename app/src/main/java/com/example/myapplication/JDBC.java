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

    private static int courier = 1;
    public static int currentCourierID = 0;

    public static String currentCourierName = "";

    public static String type = "";
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
                for(int i = 1; i < metaData.getColumnCount() + 1; i++){
                    if (metaData.getColumnName(i).equals("productID")) {
                        id = rs.getInt(i);
                    } else if (metaData.getColumnName(i).equals("productName")) {
                        name = rs.getString(i);
                    } else if (metaData.getColumnName(i).equals("productPrice")) {
                        price = rs.getInt(i);
                    } else {
                        Log.e("Error", "Not right column");
                    }
                }
                Product product = new Product(id, name, price);
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

    public static void changePrice(int id, int price) {
        Connection connection = DB.getInstance().getConnection();
        String query = "UPDATE Product SET productPrice = ? WHERE productID = ?";

        try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, price);
            ps.setInt(2, id);

            ps.executeUpdate();
            Log.d("Update", "Updated " + id);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }
    public static int addOrder(HashMap<Product, Integer> orders) {
        Date date = new Date(System.currentTimeMillis());
        int finished = 0;
        AtomicInteger orderID = new AtomicInteger();
        Connection connection = DB.getInstance().getConnection();
        String query = "insert into Orders (dateOf, finished, price) values (?, ?, ?) ";
        AtomicInteger price = new AtomicInteger();

        orders.forEach((key, value) -> {
            price.addAndGet(value * key.getPrice());
        });
        try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setDate(1, date);
            ps.setInt(2, finished);
            ps.setInt(3, price.get());

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
        return orderID.get();
    }

    public static void finishOrder(int orderID) {
        Connection connection = DB.getInstance().getConnection();
        String q = "UPDATE Orders SET finished = 1 WHERE orderID = ?";
        try (PreparedStatement ps = connection.prepareStatement(q)){
            ps.setInt(1, orderID);

            ps.executeUpdate();

            Log.d("Update", "Updated " + orderID);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    public static void updateOrder(int orderID, String name, String address, String phone) {
        Connection connection = DB.getInstance().getConnection();
        String q = "update orders set customerName = ?, staff = ?, address = ?, phone = ? where orderID = ?";
        try (PreparedStatement ps = connection.prepareStatement(q)){
            ps.setString(1, name);
            ps.setInt(2, courier);
            courier = (courier % 3) + 1;
            ps.setString(3, address);
            ps.setString(4, phone);
            ps.setInt(5, orderID);

            ps.executeUpdate();

            Log.d("Update", "Updated " + orderID);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
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

        String query = "insert into Product (productName, productPrice) values (?, ?) ";

        try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setString(1, name);
            ps.setInt(2, price);

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

    public static ArrayList<Order> getOrders(){
        ArrayList<Order> orders = new ArrayList<>();
        Connection connection = DB.getInstance().getConnection();

        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("select * from Orders");
        ) {
            ResultSetMetaData metaData = rs.getMetaData();
            while(rs.next()){
                int id = 0;
                String customer = "";
                Date date = null;
                int finished = 0;
                int price = 0;
                for(int i = 1; i < metaData.getColumnCount() + 1; i++){
                    if (metaData.getColumnName(i).equals("orderID")) {
                        id = rs.getInt(i);
                    } else if (metaData.getColumnName(i).equals("customerName")) {
                        customer = rs.getString(i);
                    } else if (metaData.getColumnName(i).equals("dateOf")) {
                        date = rs.getDate(i);
                    } else if (metaData.getColumnName(i).equals("finished")) {
                        finished = rs.getInt(i);
                    } else if (metaData.getColumnName(i).equals("price")) {
                        price = rs.getInt(i);
                    }
                }
                Order order = new Order(id, date, finished, price);
                orders.add(order);
            }
        }
        catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return orders;
    }

    public static ArrayList<Order> getOrdersForCourier(int courierID) {
        ArrayList<Order> orders = new ArrayList<>();
        Connection connection = DB.getInstance().getConnection();
        String q = "select * from Orders where staff = ?";

        try (PreparedStatement ps = connection.prepareStatement(q)){
            ps.setInt(1, courierID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int orderID = rs.getInt(1);
                Date date = rs.getDate(3);
                int finished = rs.getInt(4);
                int price = rs.getInt(5);
                Order order = new Order(orderID, date, finished, price);
                orders.add(order);
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return orders;
    }

    public static HashMap<Product, Integer> getOrder(int orderID){
        HashMap<Product, Integer> order = new HashMap<>();
        Connection connection = DB.getInstance().getConnection();

        String q = "SELECT pr.productID, pr.productName, pr.productPrice, p.quantity FROM orders o, Order_product p, Product pr where o.orderID = p.orderID and o.orderID = ? and p.productID = pr.productID";

        try (PreparedStatement ps = connection.prepareStatement(q)){
            ps.setInt(1, orderID);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int price = rs.getInt(3);
                int quantity = rs.getInt(4);
                Product product = new Product(id, name, price);
                order.put(product, quantity);
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }

        return order;
    }

    public static String getStaff(String username, String password) {
        Connection connection = DB.getInstance().getConnection();
        String q = "SELECT staffID, type, name FROM Staff where username = ? and password = ?";

        try (PreparedStatement ps = connection.prepareStatement(q)){
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                type = rs.getString(2);
                currentCourierID = rs.getInt(1);
                currentCourierName = rs.getString(3);
                return rs.getString(2);
            } else {
                return "";
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return "";
    }
}
