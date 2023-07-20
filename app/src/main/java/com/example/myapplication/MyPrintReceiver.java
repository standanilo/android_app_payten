package com.example.myapplication;

import static com.example.myapplication.CurrentOrderActivity.orderID;
import static com.example.myapplication.JDBC.finishOrder;
import static com.example.myapplication.JDBC.payOrder;
import static com.example.myapplication.PayActivity.delivery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.room.Room;

import com.google.gson.Gson;

public class MyPrintReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String res = intent.getStringExtra("ResponseResult");
        Intent i;
        Database database = Room.databaseBuilder(context.getApplicationContext(), Database.class, "baza").allowMainThreadQueries().build();
        Dao dao = database.getDao();
        if (delivery) {
            i = new Intent(context, CustomerActivity.class);
            i.putExtra("ID", orderID);
            payOrder(orderID, dao);
        } else {
            finishOrder(orderID, dao);
            i = new Intent(context, OrdersActivity.class);
        }

        Gson gson = new Gson();

        JSONSaleResponse jres = gson.fromJson(res, JSONSaleResponse.class);

        i.putExtra("ResponseResult", res);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(i);
    }
}
