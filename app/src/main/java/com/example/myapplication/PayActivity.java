package com.example.myapplication;

import static com.example.myapplication.JDBC.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class PayActivity extends AppCompatActivity {

    public static boolean delivery;

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

        ImageButton back = findViewById(R.id.back);
        ImageButton print = findViewById(R.id.print);
        CheckBox cb = findViewById(R.id.delivery);
        if (type.equals("kurir")) {
            cb.setVisibility(View.INVISIBLE);
        }

        if (resp != null) {
            JSONSaleResponse jres = gson.fromJson(resp, JSONSaleResponse.class);
            if (jres.response.financial.result.code.equals("Approved")) {
                TextView tv = findViewById(R.id.kartica);
                tv.setText("Transaction approved");
                tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
            } else {
                TextView tv = findViewById(R.id.kartica);
                tv.setText("Transaction declined");
                tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                print.setVisibility(View.INVISIBLE);
            }
        }

        CheckBox deliver = findViewById(R.id.delivery);

        back.setOnClickListener(v -> {
            Intent secondActivityIntent;
            if (deliver.isChecked()) {
                delivery = true;
                secondActivityIntent = new Intent(this, CustomerActivity.class);
                secondActivityIntent.putExtra("ID", orderID);
                payOrder(orderID, dao);
            } else {
                delivery = false;
                finishOrder(orderID, dao);
                secondActivityIntent = new Intent(this, OrdersActivity.class);
            }
            finish();
            startActivity(secondActivityIntent);
        });

        print.setOnClickListener(v -> {
            ArrayList<JSONPrintRequest.PrintLine> lines = new ArrayList<>();

            if (deliver.isChecked()) delivery = true;
            else delivery = false;
            String t = "text";
            String st = "NORMAL";

            AtomicInteger price = new AtomicInteger();

            addLine(lines, t, st, "Prodavnica");
            addLine(lines, t, st, " ");
            addLine(lines, t, st, " ");

            addLine(lines, t, st, "================================");
            addLine(lines, t, st, "Naziv   Cena   Kolicina   Ukupno");
            addLine(lines, t, st, "================================");

            CurrentOrderActivity.order.forEach((key, value) -> {
                price.addAndGet(key.getProductPrice() * value);
                addLine(lines, t, st, key.getProductName() + "                                ".substring(0, 31 - key.getProductName().length()));
                addLine(lines, t, st, String.format("%-6s %-10s %-3s %10s", "      ", key.getProductPrice() + ".00", value, (key.getProductPrice() * value) + ".00"));
            });

            addLine(lines, t, st, "________________________________");
            addLine(lines, t, st, "UKUPAN IZNOS                    ".substring(0, 25 - String.valueOf(price.get()).length()) + price.get() + ".00 RSD");
            addLine(lines, t, st, " ");
            addLine(lines, t, st, " ");
            addLine(lines, t, st, "================================");
            addLine(lines, t, st, "PAID BY CARD");
            addLine(lines, t, st, "================================");
            addLine(lines, t, st, "INVOICE: " + MyReceiver.invoice);
            addLine(lines, t, st, "PAN:     " + MyReceiver.pan);
            addLine(lines, t, st, "AMOUNT:  " + price.get() + ".00 RSD");
            addLine(lines, t, st, "AUTH #:  " + MyReceiver.auth);
            addLine(lines, t, st, "________________________________");
            addLine(lines, t, st, " ");
            if (type.equals("kurir")) {
                addLine(lines, t, st, " ");
                addLine(lines, t, st, "POTPIS__________________________");
            }
            JSONPrintRequest preq = new JSONPrintRequest(lines);

            String printRequest = new GsonBuilder().disableHtmlEscaping().create().toJson(preq);
            Log.d("PRINT", printRequest);

            Intent intent1 = preparePrint(printRequest);
            finish();
            getApplicationContext().sendBroadcast(intent1);
        });
    }

    public static void addLine(ArrayList<JSONPrintRequest.PrintLine> lines, String type, String style, String content){
        JSONPrintRequest.PrintLine line = new JSONPrintRequest.PrintLine();
        line.type = type;
        line.style = style;
        line.content = content;
        lines.add(line);
    }

    public static Intent preparePrint(String printRequest) {
        Intent intent = new Intent("com.payten.ecr.action");
        intent.setPackage("com.payten.paytenapos");
        intent.putExtra("ecrJson", printRequest);
        intent.putExtra("senderIntentFilter", "com.example.myapplication.senderIntentFilterPrint");
        intent.putExtra("senderPackage", "com.example.myapplication");
        intent.putExtra("senderClass", "com.payten.ecrdemo.PayActivity");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        return intent;
    }
}