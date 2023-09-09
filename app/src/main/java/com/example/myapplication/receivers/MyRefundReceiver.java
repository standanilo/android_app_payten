package com.example.myapplication.receivers;

import static com.example.myapplication.CurrentOrderActivity.orderID;
import static com.example.myapplication.database.JDBC.deleteOrderProduct;
import static com.example.myapplication.database.JDBC.getOnlyOrder;
import static com.example.myapplication.database.JDBC.getOrderProduct;
import static com.example.myapplication.database.JDBC.updateOrder;
import static com.example.myapplication.database.JDBC.updateOrderProduct;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.room.Room;

import com.example.myapplication.CurrentOrderActivity;
import com.example.myapplication.OrdersActivity;
import com.example.myapplication.RefundActivity;
import com.example.myapplication.database.Dao;
import com.example.myapplication.database.Database;
import com.example.myapplication.requestsandresponses.JSONSaleResponse;
import com.example.myapplication.tables.Order;
import com.example.myapplication.tables.OrderProduct;
import com.google.gson.Gson;

import java.util.concurrent.atomic.AtomicInteger;

public class MyRefundReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String res = intent.getStringExtra("ResponseResult");
        Intent i = new Intent(context, OrdersActivity.class);

        Database database = Room.databaseBuilder(context, Database.class, "baza").allowMainThreadQueries().build();
        Dao dao = database.getDao();

        Gson gson = new Gson();

        JSONSaleResponse jres = gson.fromJson(res, JSONSaleResponse.class);

        if (jres.response.financial.result.code.equals("Approved")) {
            // update order price and amount
            AtomicInteger price = new AtomicInteger();
            RefundActivity.order.forEach((key, value) -> {
                price.addAndGet(key.getProductPrice() * value);
            });
            Order order = getOnlyOrder(CurrentOrderActivity.orderID, dao);
            order.setPrice(order.getPrice() - price.get());
            updateOrder(order, dao);
            RefundActivity.order.forEach((key, value) -> {
                OrderProduct op = getOrderProduct(key.getProductID(), orderID, dao);
                if (RefundActivity.initial.get(key) - value == 0) {
                    deleteOrderProduct(op, dao);
                } else {
                    op.setQuantity(RefundActivity.initial.get(key) - value);
                    op.setPrice(key.getProductPrice() * (RefundActivity.initial.get(key) - value));
                    updateOrderProduct(op, dao);
                }
            });
        }
        Order o = dao.getOnlyOrder(orderID);
        AtomicInteger j = new AtomicInteger();
        RefundActivity.order.forEach((key, value) -> {
            OrderProduct op = getOrderProduct(key.getProductID(), orderID, dao);
            if (op != null) {
                j.getAndIncrement();
                Log.d("op", op.toString());
            }
        });
        if (j.get()==0) {
            Order or = dao.getOnlyOrder(orderID);
            dao.deleteOrder(or);
        }
        i.putExtra("ResponseResult", res);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(i);
    }
}