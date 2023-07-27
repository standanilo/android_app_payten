package com.example.myapplication;

import static com.example.myapplication.database.JDBC.*;
import static com.example.myapplication.PayActivity.addLine;
import static com.example.myapplication.PayActivity.preparePrint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.database.Dao;
import com.example.myapplication.database.Database;
import com.example.myapplication.requestsandresponses.JSONPrintRequest;
import com.example.myapplication.requestsandresponses.JSONVoidRequest;
import com.example.myapplication.tables.Order;
import com.example.myapplication.tables.Product;
import com.example.myapplication.tables.Staff;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class OrdersActivity extends AppCompatActivity {

    private LinearLayout buttonContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Database database = Room.databaseBuilder(getApplicationContext(), Database.class, "baza").allowMainThreadQueries().build();
        Dao dao = database.getDao();
        ArrayList<Order> orders;
        if (type.equals("trgovac")) {
            orders = getOrders(dao);
        } else {
            orders = getOrdersForCourier(currentCourierID, dao);
        }
        buttonContainer = findViewById(R.id.buttonContainer_edit);

        LinearLayout.LayoutParams t1Params = new LinearLayout.LayoutParams(
                0,
                80,
                1
        );
        LinearLayout.LayoutParams t2Params = new LinearLayout.LayoutParams(
                0,
                80,
                1
        );
        LinearLayout.LayoutParams t3Params = new LinearLayout.LayoutParams(
                0,
                80,
                1
        );
        LinearLayout.LayoutParams t4Params = new LinearLayout.LayoutParams(
                0,
                80,
                1
        );

        LinearLayout.LayoutParams l1Params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                90
        );

        LinearLayout linearLayout = new LinearLayout(this);
        TextView t1 = new TextView(this);
        t1.setId(View.generateViewId());
        t1.setText("placeno + dostavljeno +");
        t1.setTextSize(15);
        t1.setGravity(Gravity.CENTER_HORIZONTAL);
        t1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan1));
        t1.setLayoutParams(t1Params);
        linearLayout.addView(t1);

        TextView t2 = new TextView(this);
        t2.setId(View.generateViewId());
        t2.setText("placeno + dostavljeno -");
        t2.setTextSize(15);
        t2.setGravity(Gravity.CENTER_HORIZONTAL);
        t2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        t2.setLayoutParams(t2Params);
        linearLayout.addView(t2);

        TextView t3 = new TextView(this);
        t3.setId(View.generateViewId());
        t3.setText("placeno - dostavljeno -");
        t3.setTextSize(15);
        t3.setGravity(Gravity.CENTER_HORIZONTAL);
        t3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
        t3.setLayoutParams(t3Params);
        linearLayout.addView(t3);

        TextView t4 = new TextView(this);
        t4.setId(View.generateViewId());
        t4.setText("otkazano");
        t4.setTextSize(15);
        t4.setGravity(Gravity.CENTER_HORIZONTAL);
        t4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
        t4.setLayoutParams(t4Params);
        linearLayout.addView(t4);

        linearLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        linearLayout.setLayoutParams(l1Params);
        buttonContainer.addView(linearLayout);

        for (Order o : orders) {
            LinearLayout linearLayoutMain = new LinearLayout(this);
            linearLayoutMain.setOrientation(LinearLayout.VERTICAL);

            LinearLayout linearLayout1 = new LinearLayout(this);
            LinearLayout linearLayout2 = new LinearLayout(this);
            LinearLayout linearLayout3 = new LinearLayout(this);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            );
            LinearLayout.LayoutParams text1Params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            );
            LinearLayout.LayoutParams text2Params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            );
            LinearLayout.LayoutParams text3Params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    2
            );
            LinearLayout.LayoutParams text4Params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            );
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            );
            LinearLayout.LayoutParams button1Params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            );
            LinearLayout.LayoutParams button2Params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            );
            LinearLayout.LayoutParams button3Params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            );
            LinearLayout.LayoutParams text5Params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    2
            );
            LinearLayout.LayoutParams text6Params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    2
            );

            TextView textView = new TextView(this);
            textView.setId(View.generateViewId());
            textView.setText("ID: " + o.getOrderID());
            textView.setTextSize(20);
            textView.setLayoutParams(textParams);
            linearLayout1.addView(textView);

            TextView textView3 = new TextView(this);
            textView3.setId(View.generateViewId());
            textView3.setText("cena: " + o.getPrice());
            textView3.setTextSize(18);
            textView3.setGravity(Gravity.CENTER);
            textView3.setLayoutParams(text3Params);
            linearLayout1.addView(textView3);

            TextView textView5 = new TextView(this);
            textView5.setId(View.generateViewId());
            textView5.setText(o.getDateOf());
            textView5.setTextSize(18);
            textView5.setGravity(Gravity.CENTER);
            textView5.setLayoutParams(text5Params);
            linearLayout1.addView(textView5);

            TextView textView1 = new TextView(this);
            textView1.setId(View.generateViewId());
            if (o.getCustomerName() != null) {
                textView1.setText(o.getCustomerName());
            }
            textView1.setTextSize(18);
            textView1.setLayoutParams(text1Params);
            linearLayout2.addView(textView1);

            TextView textView4 = new TextView(this);
            textView4.setId(View.generateViewId());
            if (o.getPhone() != null) {
                textView4.setText("br tel: " + o.getPhone());
            }
            textView4.setTextSize(18);
            textView4.setGravity(Gravity.CENTER);
            textView4.setLayoutParams(text4Params);
            linearLayout2.addView(textView4);

            TextView textView2 = new TextView(this);
            textView2.setId(View.generateViewId());
            if (o.getAddress() != null) {
                textView2.setText("adr: " + o.getAddress());
            }
            textView2.setTextSize(18);
            textView2.setGravity(Gravity.CENTER);
            textView2.setLayoutParams(text2Params);
            linearLayout2.addView(textView2);

            buttonParams.leftMargin = 32;
            Button button = new Button(this);
            button.setId(View.generateViewId());
            button.setText(R.string.plati);
            button.setLayoutParams(buttonParams);
            button.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.merch_button, null));
            linearLayout3.addView(button);

            Button button1 = new Button(this);
            button1.setId(View.generateViewId());
            button1.setText("Print");
            button1.setLayoutParams(button1Params);
            button1.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.merch_button, null));
            linearLayout3.addView(button1);

            Button button2 = new Button(this);
            button2.setId(View.generateViewId());
            button2.setText("Otkazi");
            button2.setLayoutParams(button2Params);
            button2.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.merch_button, null));
            linearLayout3.addView(button2);

            button3Params.rightMargin = 32;
            Button button3 = new Button(this);
            button3.setId(View.generateViewId());
            button3.setText("Vrati");
            button3.setLayoutParams(button3Params);
            button3.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.merch_button, null));
            linearLayout3.addView(button3);
            button3.setVisibility(View.INVISIBLE);

            if (type.equals("trgovac")) {
                if (o.getStaff() != null){
                    Staff staff = getCourierInfo(o.getStaff(), dao);
                    if (staff != null) {
                        TextView textView6 = new TextView(this);
                        textView6.setId(View.generateViewId());
                        textView6.setText("kurir: " + staff.getName());
                        textView6.setTextSize(18);
                        textView6.setGravity(Gravity.CENTER);
                        textView6.setLayoutParams(text6Params);
                        linearLayout1.addView(textView6);
                    }
                }
            } else {
                button.setText(R.string.info);
            }

            if (o.getStaff() == null) {
                button.setVisibility(View.VISIBLE);
            } else if (type.equals("trgovac")) {
                button.setVisibility(View.INVISIBLE);
            } else if (type.equals("kurir")) {
                button2.setVisibility(View.INVISIBLE);
            }

            if (o.getFinished() == 1 || o.getFinished() == 3) {
                button.setVisibility(View.INVISIBLE);
            }
            if (type.equals("kurir") && o.getFinished() == 3) {
                button1.setVisibility(View.INVISIBLE);
            }
            if (o.getFinished() == 3) {
                button2.setVisibility(View.INVISIBLE);
            }
            if (type.equals("trgovac") && o.getFinished() == 1) {
                button3.setVisibility(View.VISIBLE);
            }

            button.setOnClickListener(v -> {
                Intent secondActivityIntent = new Intent(this, CurrentOrderActivity.class);
                secondActivityIntent.putExtra("order", getOrder(o.getOrderID(), dao));
                secondActivityIntent.putExtra("ID", o.getOrderID());
                secondActivityIntent.putExtra("From", "orders");
                finish();
                startActivity(secondActivityIntent);
            });

            if (o.getFinished() == 1) {
                if (orders.indexOf(o) % 2 == 0) {
                    linearLayout1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan1));
                    linearLayout2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan1));
                    linearLayout3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan1));
                } else {
                    linearLayout1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan2));
                    linearLayout2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan2));
                    linearLayout3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan2));
                }
            } else if (o.getFinished() == 3){
                if (orders.indexOf(o) % 2 == 0) {
                    linearLayout1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    linearLayout2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    linearLayout3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                } else {
                    linearLayout1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red1));
                    linearLayout2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red1));
                    linearLayout3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red1));
                }
            } else if (o.getFinished() == 2){
                if (orders.indexOf(o) % 2 == 0) {
                    linearLayout1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                    linearLayout2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                    linearLayout3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                } else {
                    linearLayout1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green1));
                    linearLayout2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green1));
                    linearLayout3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green1));
                }
            } else {
                if (orders.indexOf(o) % 2 == 0) {
                    linearLayout1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                    linearLayout2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                    linearLayout3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                } else {
                    linearLayout1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow1));
                    linearLayout2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow1));
                    linearLayout3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow1));
                }
            }

            button1.setOnClickListener(v -> {
                ArrayList<JSONPrintRequest.PrintLine> lines = new ArrayList<>();
                HashMap<Product, Integer> order = getOrder(o.getOrderID(), dao);

                String t = "text";
                String st = "NORMAL";

                AtomicInteger price = new AtomicInteger();

                addLine(lines, t, st, "Prodavnica");
                addLine(lines, t, st, " ");
                addLine(lines, t, st, " ");

                addLine(lines, t, st, "================================");
                addLine(lines, t, st, "Naziv   Cena   Kolicina   Ukupno");
                addLine(lines, t, st, "================================");

                order.forEach((key, value) -> {
                    price.addAndGet(key.getProductPrice() * value);
                    addLine(lines, t, st, key.getProductName() + "                                ".substring(0, 31 - key.getProductName().length()));
                    addLine(lines, t, st, String.format("%-6s %-10s %-3s %10s", "      ", key.getProductPrice() + ".00", value, (key.getProductPrice() * value) + ".00"));
                });

                addLine(lines, t, st, "________________________________");
                addLine(lines, t, st, "UKUPAN IZNOS                    ".substring(0, 25 - String.valueOf(price.get()).length()) + price.get() + ".00 RSD");
                addLine(lines, t, st, " ");
                if (type.equals("kurir")) {
                    addLine(lines, t, st, " ");
                    addLine(lines, t, st, "POTPIS__________________________");
                    addLine(lines, t, st, " ");
                } else {
                    addLine(lines, t, st, "________________________________");
                    addLine(lines, t, st, " ");
                }

                JSONPrintRequest preq = new JSONPrintRequest(lines);

                String printRequest = new GsonBuilder().disableHtmlEscaping().create().toJson(preq);
                Log.d("PRINT", printRequest);

                Intent intent1 = preparePrint(printRequest);
                finish();
                getApplicationContext().sendBroadcast(intent1);
            });

            button2.setOnClickListener(v -> {
                if (o.getFinished() == 2 || o.getFinished() == 1) {
                    Intent intent1 = new Intent("com.payten.ecr.action");
                    intent1.setPackage("com.payten.paytenapos");

                    JSONVoidRequest jreq = new JSONVoidRequest(o);

                    String req = new Gson().toJson(jreq);

                    CurrentOrderActivity.orderID = o.getOrderID();
                    prepareTransaction(intent1, req);
                    finish();
                    sendBroadcast(intent1);
                } else {
                    cancelOrder(o.getOrderID(), dao);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            });

            button3.setOnClickListener(v -> {
                Intent secondActivityIntent = new Intent(this, RefundActivity.class);
                secondActivityIntent.putExtra("ID", o.getOrderID());
                finish();
                startActivity(secondActivityIntent);
                CurrentOrderActivity.orderID = o.getOrderID();
            });

            linearLayoutMain.addView(linearLayout1);
            if (o.getStaff() != null) {
                linearLayoutMain.addView(linearLayout2);
            }
            linearLayoutMain.addView(linearLayout3);

            buttonContainer.addView(linearLayoutMain);
        }
    }

    public void openEdit(View v){
        Intent secondActivityIntent = new Intent(this, EditActivity.class);
        secondActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        overridePendingTransition(0, 0);
        startActivity(secondActivityIntent);
        overridePendingTransition(0, 0);
    }
    public void openHome(View v){
        Intent secondActivityIntent = new Intent(this, MerchantActivity.class);
        secondActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        overridePendingTransition(0, 0);
        startActivity(secondActivityIntent);
        overridePendingTransition(0, 0);
    }

    private static void prepareTransaction(Intent intent, String req) {
        intent.putExtra("ecrJson", req);
        intent.putExtra("senderIntentFilter", "com.example.myapplication.senderIntentFilterVoid");
        intent.putExtra("senderPackage", "com.example.myapplication");
        intent.putExtra("senderClass", "com.example.myapplication.OrdersActivity");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
    }
}