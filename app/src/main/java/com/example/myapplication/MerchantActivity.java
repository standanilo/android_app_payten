package com.example.myapplication;

import static com.example.myapplication.database.JDBC.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Room;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
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
import com.example.myapplication.tables.Order;
import com.example.myapplication.tables.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MerchantActivity extends AppCompatActivity {
    private LinearLayout buttonContainer;
    FloatingActionButton button;
    int price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        Database database = Room.databaseBuilder(getApplicationContext(), Database.class, "baza").allowMainThreadQueries().build();
        Dao dao = database.getDao();
        if (type.equals("trgovac")) {

            ArrayList<Product> products = getProducts(dao);
            buttonContainer = findViewById(R.id.buttonContainer);
            HashMap<Product, Integer> orders = new HashMap<>();

            button = findViewById(R.id.floatingActionButton2);

            button.setOnClickListener(v -> {
                order(orders, this, dao);
            });

            TextView prices = findViewById(R.id.price1);
            prices.setText("Cena: ");

            AtomicInteger num_of_orders = new AtomicInteger();
            for (Product p : products) {
                orders.put(p, 0);
            }

            for (Product p : products) {
                LinearLayout linearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        10
                );
                LinearLayout.LayoutParams text1Params = new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.MATCH_PARENT,
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

                textParams.leftMargin = 32;
                TextView textView = new TextView(this);
                textView.setId(View.generateViewId());
                textView.setText(p.getProductName());
                textView.setTextSize(18);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(16, 0,0,0);
                textView.setLayoutParams(textParams);
                linearLayout.addView(textView);

                button1Params.bottomMargin = 24;
                button1Params.topMargin = 24;
                Button button1 = new Button(this);
                button1.setId(View.generateViewId());
                button1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                button1.setText("+");
                button1.setLayoutParams(button1Params);
                button1.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.merch_add_button, null));
                linearLayout.addView(button1);

                TextView textView1 = new TextView(this);
                textView1.setId(View.generateViewId());
                textView1.setText("0");
                textView1.setGravity(Gravity.CENTER);
                textView1.setLayoutParams(text1Params);
                linearLayout.addView(textView1);

                button2Params.bottomMargin = 24;
                button2Params.topMargin = 24;
                button2Params.rightMargin = 32;
                Button button2 = new Button(this);
                button2.setId(View.generateViewId());
                button2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                button2.setText("-");
                button2.setEnabled(false);
                button2.setLayoutParams(button2Params);
                button2.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.merch_remove_button, null));
                linearLayout.addView(button2);

                button1.setOnClickListener(v -> {
                    int amount = orders.get(p) + 1;
                    num_of_orders.getAndIncrement();
                    orders.put(p, amount);
                    button2.setEnabled(true);
                    button.setEnabled(true);
                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.navy_blue)));
                    textView1.setText(String.valueOf(amount));
                    incPrice(p.getProductPrice());
                    prices.setText("Cena: " + price);
                });
                button2.setOnClickListener(v -> {
                    int amount = orders.get(p) - 1;
                    orders.put(p, amount);
                    num_of_orders.getAndDecrement();
                    textView1.setText(String.valueOf(amount));
                    if (amount == 0) {
                        button2.setEnabled(false);
                    }
                    if (num_of_orders.get() == 0) {
                        button.setEnabled(false);
                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.blue_grotto)));
                    }
                    decPrice(p.getProductPrice());
                    prices.setText("Cena: " + price);
                });
                linearLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.linear_layout, null));
//                if (products.indexOf(p) % 2 == 0) {
//                    linearLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan1));
//                } else {
//                    linearLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan2));
//                }
                buttonContainer = findViewById(R.id.buttonContainer);
                buttonContainer.addView(linearLayout);
            }

        } else if (type.equals("kurir")){
            button = findViewById(R.id.floatingActionButton2);
            button.setEnabled(true);

            button.setOnClickListener(v -> {
                Intent secondActivityIntent = new Intent(this, OrdersActivity.class);
                finish();
                startActivity(secondActivityIntent);
            });

            TextView text = findViewById(R.id.textView9);
            text.setText("Zdravo " + currentCourierName + ", broj poruzbina " + numberOfOrders(getOrdersForCourier(currentCourierID, dao)) + ".");
            text.setTextSize(25);

        }

    }
    public void order(HashMap<Product, Integer> orders, Activity activity, Dao dao) {
        HashMap<Product, Integer> current = new HashMap<>();
        orders.forEach((key, value) -> {
            if (value != 0) {
                Log.d("Order", "key: " + key + " value: " + value);
                current.put(key, value);
            }

        });
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Log.d("Confirm", "YES");
                        int orderID = addOrder(current, dao);
                        Intent secondActivityIntent = new Intent(activity, CurrentOrderActivity.class);
                        secondActivityIntent.putExtra("order", current);
                        secondActivityIntent.putExtra("ID", orderID);
                        secondActivityIntent.putExtra("From", "main");
                        startActivity(secondActivityIntent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Log.e("Decline", "NO");
                        // stay on same activity
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(MerchantActivity.this);
        builder.setMessage("Da li ste sigurni?").setPositiveButton("Da", dialogClickListener)
                .setNegativeButton("Ne", dialogClickListener).show();
    }

    private void incPrice(int amount) {
        price += amount;
    }

    private void decPrice(int amount) {
        price -= amount;
    }
    public void openEdit(View v){
        Intent secondActivityIntent = new Intent(this, EditActivity.class);
        secondActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        overridePendingTransition(0, 0);
        startActivity(secondActivityIntent);
        overridePendingTransition(0, 0);
    }
    public void openOrders(View v){
        Intent secondActivityIntent = new Intent(this, OrdersActivity.class);
        secondActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        overridePendingTransition(0, 0);
        startActivity(secondActivityIntent);
        overridePendingTransition(0, 0);
    }

    public int numberOfOrders(ArrayList<Order> orders) {
        int count = 0;
        for(Order o: orders) {
            if (o.getFinished() == 0 || o.getFinished() == 2) {
                count++;
            }
        }
        return count;
    }
}