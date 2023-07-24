package com.example.myapplication.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.tables.Order;
import com.example.myapplication.tables.OrderProduct;
import com.example.myapplication.tables.OrderProductInfo;
import com.example.myapplication.tables.Product;
import com.example.myapplication.tables.Staff;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Query("select * from Product order by productName")
    List<Product> getProducts();

    @Query("select * from Product where productName = :name")
    Product findProduct(String name);

    @Query("select * from Product where productID = :id")
    Product getProduct(int id);

    @Query("select * from Order_product where productID = :pid and orderID = :oid")
    OrderProduct getOrderProduct(int pid, int oid);

    @Delete
    int deleteProduct(Product p);

    @Delete
    void deleteOrderProduct(OrderProduct op);

    @Query("UPDATE Product SET productPrice = :price WHERE productID = :id")
    void changePrice(int id, int price);

    @Query("insert into Orders (dateOf, finished, price) values (:date, :finished, :price)")
    void addOrder(String date, int finished, int price);

    @Insert
    long addOrd(Order order);

    @Query("insert into Order_product (orderID, productID, quantity, price) values (:orderID, :productID, :quantity, :price)")
    void addOrderProduct(int orderID, int productID, int quantity, int price);

    @Insert
    long addOP(OrderProduct orderProduct);

    @Query("UPDATE Orders SET finished = 1 WHERE orderID = :id")
    void finishOrder(int id);

    @Query("UPDATE Orders SET finished = 2 WHERE orderID = :id")
    void payOrder(int id);

    @Query("UPDATE Orders SET finished = 3 WHERE orderID = :id")
    void cancelOrder(int id);

    @Query("update orders set customerName = :name, staff = :staff, address = :address, phone = :phone where orderID = :orderID")
    void updateOrder(int orderID, String name, String address, String phone, Integer staff);

    @Query("update orders set invoice = :invoice where orderID = :orderID")
    void updateOrderInvoice(int orderID, String invoice);
    @Update
    void updateOrder(Order order);

    @Update
    void updateOrderProduct(OrderProduct op);

    @Insert
    long addProduct(Product product);

    @Insert
    long addStaff(Staff staff);

    @Query("select * from Orders order by orderID desc")
    List<Order> getOrders();

    @Query("select * from Orders where staff = :courier")
    List<Order> getOrdersForCourier(int courier);

    @Query("select * from Order_product where orderID = :id")
    List<OrderProduct> getProductForOrder(int id);

    @Query("SELECT pr.productID, pr.productName, pr.productPrice, p.quantity FROM orders o, Order_product p, Product pr where o.orderID = p.orderID and o.orderID = :orderID and p.productID = pr.productID")
    List<OrderProductInfo> getOrderInfo(int orderID);

    @Query("SELECT * FROM orders where orderID = :orderID")
    Order getOnlyOrder(int orderID);

    @Query("SELECT * FROM Staff where username = :username and password = :password")
    Staff getStaff(String username, String password);

    @Query("SELECT staff.* FROM staff, orders o where o.staff is not null and staffID=o.staff and o.finished!=3 order by orderID desc LIMIT 1")
    Staff getLastCourier();

    @Query("SELECT * FROM staff where type = 'kurir'")
    List<Staff> getCourier();

    @Query("SELECT * FROM staff where username = :username")
    Staff getUsername(String username);

    @Query("SELECT * FROM staff where staffID = :id")
    Staff getCourierInfo(int id);

    @Query("DELETE FROM Product")
    void clearProducts();

    @Query("DELETE FROM Orders")
    void clearOrders();

    @Insert
    void insertStaff(Staff staff);

    @Query("DELETE FROM Order_product")
    void clearOP();

    @Query("DELETE FROM Staff")
    void clearStaff();

    @Delete
    int deleteStaff(Staff staff);

    @Delete
    int deleteOrder(Order order);

    @Insert
    void initStaff(Staff ... staff);

    @Insert
    void initProducts(Product ... product);

    @Query("select * from Staff")
    List<Staff> getAllStaff();

    @Query("UPDATE SQLITE_SEQUENCE SET seq = 0 WHERE name = 'Product'")
    void clearIDProduct();

    @Query("UPDATE SQLITE_SEQUENCE SET seq = 0 WHERE name = 'Orders'")
    void clearIDOrder();

    @Query("UPDATE SQLITE_SEQUENCE SET seq = 0 WHERE name = 'Staff'")
    void clearIDStaff();

    @Query("UPDATE SQLITE_SEQUENCE SET seq = 0 WHERE name = 'Order_product'")
    void clearIDOP();
}
