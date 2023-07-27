package com.example.myapplication;

import static com.example.myapplication.database.JDBC.getOnlyOrder;
import static com.example.myapplication.database.JDBC.getProduct;
import static com.example.myapplication.database.JDBC.getProducts;
import static com.example.myapplication.database.JDBC.getProductsForOrder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Room;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.database.Dao;
import com.example.myapplication.database.Database;
import com.example.myapplication.requestsandresponses.JSONRefundRequest;
import com.example.myapplication.tables.Order;
import com.example.myapplication.tables.OrderProduct;
import com.example.myapplication.tables.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RefundActivity extends AppCompatActivity {

    private LinearLayout buttonContainer;
    int price = 0;

    public static HashMap<Product, Integer> order;
    public static HashMap<Product, Integer> initial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);
        FloatingActionButton button;

        Intent i = getIntent();
        int orderID = i.getIntExtra("ID", 0);

        Database database = Room.databaseBuilder(getApplicationContext(), Database.class, "baza").allowMainThreadQueries().build();
        Dao dao = database.getDao();

        ArrayList<OrderProduct> productsForOrder = getProductsForOrder(orderID, dao);

        order = new HashMap<>();
        initial = new HashMap<>();
        ArrayList<Product> products = new ArrayList<>();

        for (OrderProduct op : productsForOrder) {
            Product p = getProduct(op.getProductID(), dao);
            products.add(p);
        }
        buttonContainer = findViewById(R.id.buttonContainer);

        button = findViewById(R.id.floatingActionButton);

        Order o = getOnlyOrder(orderID, dao);

        TextView prices = findViewById(R.id.price);

        price = o.getPrice();
        prices.setText("Povracaj: " + price);

        AtomicInteger num_of_orders = new AtomicInteger();
        for (Product p : products) {
            order.put(p, productsForOrder.get(products.indexOf(p)).getQuantity());
            initial.put(p, productsForOrder.get(products.indexOf(p)).getQuantity());
            num_of_orders.getAndAdd(productsForOrder.get(products.indexOf(p)).getQuantity());
        }

        for (Product p : products) {
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    10
            );
            LinearLayout.LayoutParams text1Params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            );
            LinearLayout.LayoutParams button1Params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    3
            );
            LinearLayout.LayoutParams button2Params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    3
            );

            TextView textView = new TextView(this);
            textView.setId(View.generateViewId());
            textView.setText(p.getProductName());
            textView.setTextSize(15);
            textView.setPadding(10, 0,0,0);
            textView.setLayoutParams(textParams);
            linearLayout.addView(textView);

            Button button1 = new Button(this);
            button1.setId(View.generateViewId());
            button1.setText("+");
            button1.setLayoutParams(button1Params);
            button1.setEnabled(false);
            button1.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.merch_button, null));
            button1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            button1.setGravity(Gravity.CENTER);
            button1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            linearLayout.addView(button1);

            TextView textView1 = new TextView(this);
            textView1.setId(View.generateViewId());
            textView1.setText(String.valueOf(order.get(p)));
            textView1.setGravity(Gravity.CENTER);
            textView1.setLayoutParams(text1Params);
            linearLayout.addView(textView1);

            Button button2 = new Button(this);
            button2.setId(View.generateViewId());
            button2.setText("-");
            button2.setEnabled(true);
            button2.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.merch_button, null));
            button2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            button2.setGravity(Gravity.CENTER);
            button2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            button2.setLayoutParams(button2Params);
            linearLayout.addView(button2);

            button.setEnabled(true);

            button1.setOnClickListener(v -> {
                int amount = order.get(p) + 1;
                num_of_orders.getAndIncrement();
                order.put(p, amount);
                if (amount == initial.get(p)) {
                    button1.setEnabled(false);
                }
                button2.setEnabled(true);
                button.setEnabled(true);
                button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.navy_blue)));
                textView1.setText(String.valueOf(amount));
                incPrice(p.getProductPrice());
                prices.setText("Povracaj: " + price);
            });
            button2.setOnClickListener(v -> {
                int amount = order.get(p) - 1;
                order.put(p, amount);
                num_of_orders.getAndDecrement();
                textView1.setText(String.valueOf(amount));
                button1.setEnabled(true);
                if (amount == 0) {
                    button2.setEnabled(false);
                }
                if (num_of_orders.get() == 0) {
                    button.setEnabled(false);
                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.blue_grotto)));
                }
                decPrice(p.getProductPrice());
                prices.setText("Povracaj: " + price);
            });
            if (productsForOrder.indexOf(p) % 2 == 0) {
                linearLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan1));
            } else {
                linearLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan2));
            }
            buttonContainer = findViewById(R.id.buttonContainer);
            buttonContainer.addView(linearLayout);

            button.setOnClickListener(v -> {
                Intent intent1 = new Intent("com.payten.ecr.action");
                intent1.setPackage("com.payten.paytenapos");

                JSONRefundRequest jreq = new JSONRefundRequest(price, o.getInvoice());

                String req = new Gson().toJson(jreq);

                CurrentOrderActivity.orderID = o.getOrderID();
                prepareTransaction(intent1, req);
                finish();
                sendBroadcast(intent1);
            });

        }
    }
    private void incPrice(int amount) {
        price += amount;
    }

    private void decPrice(int amount) {
        price -= amount;
    }

    private static void prepareTransaction(Intent intent, String req) {
        intent.putExtra("ecrJson", req);
        intent.putExtra("senderIntentFilter", "com.example.myapplication.senderIntentFilterRefund");
        intent.putExtra("senderPackage", "com.example.myapplication");
        intent.putExtra("senderClass", "com.example.myapplication.RefundActivity");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
    }
}