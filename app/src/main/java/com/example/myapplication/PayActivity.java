package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

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

        ImageButton back = findViewById(R.id.back);
        ImageButton print = findViewById(R.id.print);

        if (resp != null) {
            jsonResponse jres = gson.fromJson(resp, jsonResponse.class);
            if (jres.response.financial.result.code.equals("Approved")) {
                TextView tv = findViewById(R.id.kartica);
                tv.setText("Transaction approved");
                tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                dao.finishOrder(orderID);

            } else {
                TextView tv = findViewById(R.id.kartica);
                tv.setText("Transaction declined");
                tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                print.setVisibility(View.INVISIBLE);
            }
        }

        back.setOnClickListener(v -> {
            Intent secondActivityIntent = new Intent(this, OrdersActivity.class);
            finish();
            startActivity(secondActivityIntent);
        });

        print.setOnClickListener(v -> {
            ArrayList<printReq.PrintLine> lines = new ArrayList<>();

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

            printReq preq = new printReq();
            preq.header = new printReq.Header();
            preq.request = new printReq.Request();
            preq.request.command = new printReq.Command();
            preq.request.command.printer = new printReq.Printer();
            preq.request.command.printer.type = "JSON";
            preq.request.command.printer.printLines = lines;

            String tempRequest = "\"request\":"+new GsonBuilder().disableHtmlEscaping().create().toJson(preq.request);
            String generatedSHA512 = HashUtils.performSHA512(tempRequest);

            preq.header.version = "01";
            preq.header.length = tempRequest.length();
            preq.header.hash = generatedSHA512;

            String printRequest = new GsonBuilder().disableHtmlEscaping().create().toJson(preq);
            Log.d("PRINT", printRequest);

            Intent intent1 = new Intent("com.payten.ecr.action");
            intent1.setPackage("com.payten.paytenapos");
            intent1.putExtra("ecrJson", printRequest);
            intent1.putExtra("senderIntentFilter", "com.example.myapplication.senderIntentFilterPrint");
            intent1.putExtra("senderPackage", "com.example.myapplication");
            intent1.putExtra("senderClass", "com.payten.ecrdemo.PayActivity");
            intent1.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            getApplicationContext().sendBroadcast(intent1);
        });
    }

    public static void addLine(ArrayList<printReq.PrintLine> lines, String type, String style, String content){
        printReq.PrintLine line = new printReq.PrintLine();
        line.type = type;
        line.style = style;
        line.content = content;
        lines.add(line);
    }
}