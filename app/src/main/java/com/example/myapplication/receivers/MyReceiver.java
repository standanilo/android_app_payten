package com.example.myapplication.receivers;

import static com.example.myapplication.CurrentOrderActivity.orderID;
import static com.example.myapplication.database.JDBC.getOnlyOrder;
import static com.example.myapplication.database.JDBC.updateOrderInvoice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.room.Room;

import com.example.myapplication.PayActivity;
import com.example.myapplication.database.Dao;
import com.example.myapplication.database.Database;
import com.example.myapplication.requestsandresponses.JSONSaleResponse;
import com.google.gson.Gson;

public class MyReceiver extends BroadcastReceiver {

    public static String invoice = "";
    public static String pan = "";
    public static String auth = "";
    public static String base = "";
    public static String curCode = "";
    @Override
    public void onReceive(Context context, Intent intent) {
        String res = intent.getStringExtra("ResponseResult");
        Intent i = new Intent(context, PayActivity.class);

        Gson gson = new Gson();

        JSONSaleResponse jres = gson.fromJson(res, JSONSaleResponse.class);

        Log.d("JSON", jres.response.financial.result.code);

        getDetails(context, jres);

        i.putExtra("ResponseResult", res);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(i);
    }

    private void getDetails(Context context, JSONSaleResponse jres) {

        Database database = Room.databaseBuilder(context, Database.class, "baza").allowMainThreadQueries().build();
        Dao dao = database.getDao();

        invoice = jres.response.financial.id.invoice;
        pan = jres.response.financial.id.card.pan;
        auth = jres.response.financial.id.authorization;
        base = jres.response.financial.amounts.base;
        curCode = jres.response.financial.amounts.currencyCode;
        updateOrderInvoice(orderID, invoice, dao);
    }
}
