package com.example.myapplication;

import static com.example.myapplication.JDBC.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Random;

public class PayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Database database = Room.databaseBuilder(getApplicationContext(), Database.class, "baza").allowMainThreadQueries().build();
        Dao dao = database.getDao();
        Intent intent = getIntent();
        int orderID = CurrentOrderActivity.orderID;
        Gson gson = new Gson();
        String resp = intent.getStringExtra("ResponseResult");
        if (resp != null) {
            jsonResponse jres = gson.fromJson(resp, jsonResponse.class);
            if (jres.response.financial.result.code.equals("Approved")) {
                TextView tv = findViewById(R.id.kartica);
                tv.setText("Transaction approved");
                tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                dao.finishOrder(orderID);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent1 = new Intent(PayActivity.this, MerchantActivity.class);
                        finish();
                        startActivity(intent1);
                    }
                }, 3000);
            } else {
                TextView tv = findViewById(R.id.kartica);
                tv.setText("Transaction declined");
                tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent1 = new Intent(PayActivity.this, CurrentOrderActivity.class);
                        intent1.putExtra("ID", orderID);
                        finish();
                        startActivity(intent1);
                    }
                }, 3000);
            }
        }

    }


    private void changeIntent(String from) {
        if (from.equals("order")) {
            Intent secondActivityIntent = new Intent(this, OrdersActivity.class);
            finish();
            startActivity(secondActivityIntent);
        } else {
            Intent secondActivityIntent = new Intent(this, MerchantActivity.class);
            finish();
            startActivity(secondActivityIntent);
        }
    }


}