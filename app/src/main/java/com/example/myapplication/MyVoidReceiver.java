package com.example.myapplication;

import static com.example.myapplication.CurrentOrderActivity.orderID;
import static com.example.myapplication.JDBC.cancelOrder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.room.Room;

import com.google.gson.Gson;

public class MyVoidReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String res = intent.getStringExtra("ResponseResult");
        Intent i = new Intent(context, OrdersActivity.class);

        Database database = Room.databaseBuilder(context, Database.class, "baza").allowMainThreadQueries().build();
        Dao dao = database.getDao();

        Gson gson = new Gson();

        JSONSaleResponse jres = gson.fromJson(res, JSONSaleResponse.class);

        if (jres.response.financial.result.code.equals("Approved")) {
            cancelOrder(orderID, dao);
        }
        Order o = dao.getOnlyOrder(orderID);

        i.putExtra("ResponseResult", res);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(i);
    }
}