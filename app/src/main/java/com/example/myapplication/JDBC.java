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
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class JDBC {

    public static int currentCourierID = 0;

    public static String currentCourierName = "";

    public static String type = "";

    public static ArrayList<Product> getProducts(Dao dao) {
        return (ArrayList<Product>) dao.getProducts();
    }
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

    public static boolean deleteProduct(String name, Dao dao) {
        Product product = dao.findProduct(name);
        if (product != null) {
            Log.d("ID", "id: " + product.getProductID());
        } else {
            Log.e("ID", " id: 0" );
            return false;
        }
        int deleted = dao.deleteProduct(product);
        if (deleted > 0) {
            return true;
        } else {
            return false;
        }

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

    public static void changePrice(int id, int price, Dao dao) {
        dao.changePrice(id, price);
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

    public static int addOrder(HashMap<Product, Integer> orders, Dao dao) {
        Date date = new Date(System.currentTimeMillis());
        int finished = 0;
        AtomicInteger price = new AtomicInteger();

        orders.forEach((key, value) -> {
            price.addAndGet(value * key.getProductPrice());
        });
        long id = dao.addOrd(new Order(date.toString(), finished, price.get()));

        if (id != 0) {
            orders.forEach((key, value) -> {
                int productID = key.getProductID();
                int quantity = value;
                int amount = quantity * key.getProductPrice();
                int idOP = (int) dao.addOP(new OrderProduct((int) id, productID, quantity, amount));
                if (idOP == 0) {
                    Log.e("Error", "Couldn't add product " + productID);
                }
            });
        }
        return (int) id;
    }
    public static int addOrder(HashMap<Product, Integer> orders) {
        Date date = new Date(System.currentTimeMillis());
        int finished = 0;
        AtomicInteger orderID = new AtomicInteger();
        Connection connection = DB.getInstance().getConnection();
        String query = "insert into Orders (dateOf, finished, price) values (?, ?, ?) ";
        AtomicInteger price = new AtomicInteger();

        orders.forEach((key, value) -> {
            price.addAndGet(value * key.getProductPrice());
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
                int productID = key.getProductID();
                int quantity = value;
                int amount = quantity * key.getProductPrice();
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

    public static void finishOrder(int orderID, Dao dao) {
        dao.finishOrder(orderID);
    }

    public static void payOrder(int orderID, Dao dao) {
        dao.payOrder(orderID);
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

    public static void cancelOrder(int id, Dao dao) {
        dao.cancelOrder(id);
    }

    public static void updateOrder(int orderID, String name, String address, String phone, Dao dao) {
        Staff courier = dao.getLastCourier();
        Order order = dao.getOnlyOrder(orderID);
        if (courier == null) {
            courier = dao.getCourier().get(0);
        }
        List<Staff> couriers = dao.getCourier();

        int last = -1;

        for(Staff staff : couriers) {
            last++;
            if (staff.getStaffID() == courier.getStaffID()) {
                break;
            }
        }

        if (last == couriers.size() - 1) {
            order.setStaff(couriers.get(0).getStaffID());
        } else {
            order.setStaff(couriers.get(last + 1).getStaffID());
        }
        order.setCustomerName(name);
        order.setAddress(address);
        order.setPhone(phone);
        dao.updateOrder(order);
    }
    public static void updateOrder(int orderID, String name, String address, String phone) {
        Connection connection = DB.getInstance().getConnection();
        String q = "update orders set customerName = ?, staff = ?, address = ?, phone = ? where orderID = ?";
        try (PreparedStatement ps = connection.prepareStatement(q)){
            ps.setString(1, name);
            int courier = (getLastCourier() % 3) + 1;
            ps.setInt(2, courier);
            ps.setString(3, address);
            ps.setString(4, phone);
            ps.setInt(5, orderID);

            ps.executeUpdate();

            Log.d("Update", "Updated " + orderID);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    public static boolean addProduct(String name, int price, Dao dao) {
        Product product = dao.findProduct(name);
        if (product != null) return false;
        int id = (int) dao.addProduct(new Product(name, price));
        return id != 0;
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

    public static ArrayList<Order> getOrders(Dao dao){
          return (ArrayList<Order>) dao.getOrders();
    }
    public static ArrayList<Order> getOrders(){
        ArrayList<Order> orders = new ArrayList<>();
        Connection connection = DB.getInstance().getConnection();

        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("select * from Orders order by orderID desc");
        ) {
            ResultSetMetaData metaData = rs.getMetaData();
            while(rs.next()){
                int id = 0;
                String date = null;
                int finished = 0;
                int price = 0;
                for(int i = 1; i < metaData.getColumnCount() + 1; i++){
                    if (metaData.getColumnName(i).equals("orderID")) {
                        id = rs.getInt(i);
                    } else if (metaData.getColumnName(i).equals("dateOf")) {
                        date = rs.getString(i);
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

    public static ArrayList<Order> getOrdersForCourier(int courierID, Dao dao) {
        return (ArrayList<Order>) dao.getOrdersForCourier(courierID);
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
                String date = rs.getString(3);
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

    public static HashMap<Product, Integer> getOrder(int orderID, Dao dao) {
        List<OrderProductInfo> orderedProducts = dao.getOrderInfo(orderID);
        HashMap<Product, Integer> order = new HashMap<>();

        for(OrderProductInfo op : orderedProducts) {
            order.put(new Product(op.productID, op.productName, op.productPrice), op.quantity);
        }

        return order;
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

    public static Order getOnlyOrder(int orderID, Dao dao) {
        return dao.getOnlyOrder(orderID);
    }
    public static Order getOnlyOrder(int orderID){
        Order order = new Order();
        Connection connection = DB.getInstance().getConnection();

        String q = "SELECT * FROM orders where orderID = ?";

        try (PreparedStatement ps = connection.prepareStatement(q)){
            ps.setInt(1, orderID);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                order.setOrderID(rs.getInt(1));
                order.setCustomerName(rs.getString(2));
                order.setDateOf(rs.getString(3));
                order.setFinished(rs.getInt(4));
                order.setPrice(rs.getInt(5));
                order.setStaff(rs.getInt(6));
                order.setAddress(rs.getString(7));
                order.setPhone(rs.getString(8));
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }

        return order;
    }

    public static String getStaff(String username, String password, Dao dao) {
        Staff staff = dao.getStaff(username, password);
        if (staff == null) {
            type = "";
        } else {
            type = staff.getType();
            currentCourierID = staff.getStaffID();
            currentCourierName = staff.getName();
        }
        return type;
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

    public static Staff getLastCourier(Dao dao) {
        return dao.getLastCourier();
    }
    public static int getLastCourier() {
        Connection connection = DB.getInstance().getConnection();
        int lastCourier = 0;
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT TOP 1 staff FROM orders where staff is not null order by orderID desc");
        ) {
            ResultSetMetaData metaData = rs.getMetaData();
            if(rs.next()){
                for(int i = 1; i < metaData.getColumnCount() + 1; i++){
                    if (metaData.getColumnName(i).equals("staff")) {
                        lastCourier = rs.getInt(i);
                    } else {
                        Log.e("Error", "Not right column");
                    }
                }
            }
        }
        catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return lastCourier;
    }

    public static boolean getUser(String username, Dao dao) {
        Staff staff = dao.getUsername(username);
        return staff == null;
    }

    public static void insertStaff(Staff staff, Dao dao) {
        dao.insertStaff(staff);
    }
    public static boolean deleteStaff(int id, Dao dao) {
        Staff staff = dao.getCourierInfo(id);
        return dao.deleteStaff(staff) > 0;
    }

    public static Staff getCourierInfo(int id, Dao dao) {
        return dao.getCourierInfo(id);
    }

    public static void clearAll(Dao dao){
        dao.clearOP();
        dao.clearIDOP();
        dao.clearOrders();
        dao.clearIDOrder();
        dao.clearProducts();
        dao.clearIDProduct();
    }

    public static List<Staff> getAllStaff(Dao dao) {
        return dao.getAllStaff();
    }

}
