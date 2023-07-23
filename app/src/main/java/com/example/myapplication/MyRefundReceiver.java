package com.example.myapplication;

import static com.example.myapplication.CurrentOrderActivity.orderID;
import static com.example.myapplication.JDBC.deleteOrderProduct;
import static com.example.myapplication.JDBC.getOnlyOrder;
import static com.example.myapplication.JDBC.getOrderProduct;
import static com.example.myapplication.JDBC.updateOrder;
import static com.example.myapplication.JDBC.updateOrderProduct;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.room.Room;

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
        RefundActivity.order.forEach((key, value) -> {
            OrderProduct op = getOrderProduct(key.getProductID(), orderID, dao);
            if (op != null) Log.d("op", op.toString());
        });
        i.putExtra("ResponseResult", res);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(i);
    }
}