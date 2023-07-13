package com.example.myapplication;

import static com.example.myapplication.JDBC.addOrder;
import static com.example.myapplication.JDBC.getProducts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    private LinearLayout buttonContainer;
//    Button button;
    FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Product> products = getProducts();
        buttonContainer = findViewById(R.id.buttonContainer);
        HashMap<Product, Integer> orders = new HashMap<>();

        button = findViewById(R.id.floatingActionButton2);
        // Set an onClickListener or perform any desired action
        button.setOnClickListener(v -> {
            order(orders, this);
        });

//        button = new Button(this);
//        button.setId(View.generateViewId());
//        button.setText(R.string.porudzbina);
//        button.setEnabled(false);
//        // Set an onClickListener or perform any desired action
//        button.setOnClickListener(v -> order(orders));


        AtomicInteger num_of_orders = new AtomicInteger();
        for (Product p : products) {
            orders.put(p, 0);
        }

        for (Product p : products) {
            ConstraintLayout rowLayout = new ConstraintLayout(this);

            ImageView imageView = new ImageView(this);
            imageView.setId(View.generateViewId()); // Generate a unique ID for the ImageView
            // Set the image resource or use an image loader library to load the image

            String imageString = p.getImage();

            int imageResource = getResources().getIdentifier(imageString, "drawable", getPackageName());

            imageView.setImageResource(imageResource); // Replace with your image resource
            rowLayout.addView(imageView);

            TextView textView = new TextView(this);
            textView.setId(View.generateViewId());
            textView.setText(p.getName());
            rowLayout.addView(textView);

            Button button1 = new Button(this);
            button1.setId(View.generateViewId());
            button1.setText("+");
            rowLayout.addView(button1);

            TextView textView1 = new TextView(this);
            textView1.setId(View.generateViewId());
            textView1.setText("0");
            rowLayout.addView(textView1);

            Button button2 = new Button(this);
            button2.setId(View.generateViewId());
            button2.setText("-");
            button2.setEnabled(false);
            rowLayout.addView(button2);

            // Set an onClickListener or perform any desired action
            button1.setOnClickListener(v -> {
                int amount = orders.get(p) + 1;
                num_of_orders.getAndIncrement();
                orders.put(p, amount);
                button2.setEnabled(true);
                button.setEnabled(true);
                textView1.setText(String.valueOf(amount));
            });
            button2.setOnClickListener(v -> {
                // Perform action when button is clicked
                int amount = orders.get(p) - 1;
                orders.put(p, amount);
                num_of_orders.getAndDecrement();
                textView1.setText(String.valueOf(amount));
                if (amount == 0) {
                    button2.setEnabled(false);
                }
                if (num_of_orders.get() == 0) {
                    button.setEnabled(false);
                }
            });
            if (products.indexOf(p) % 2 == 0) {
                rowLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan));
            } else {
                rowLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan2));
            }

            // Add the button to the layout container
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(rowLayout);

            // position of image
            constraintSet.connect(imageView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
            constraintSet.connect(imageView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);

            // position of add button
            constraintSet.connect(button1.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
            constraintSet.connect(button1.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);

            // position of product amount
            constraintSet.connect(textView1.getId(), ConstraintSet.START, button2.getId(), ConstraintSet.END, 16);
            constraintSet.connect(textView1.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
            constraintSet.connect(textView1.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
            constraintSet.connect(textView1.getId(), ConstraintSet.END, button1.getId(), ConstraintSet.START, 16);

            // position of sub button
            constraintSet.connect(button2.getId(), ConstraintSet.END, button1.getId(), ConstraintSet.START, 45); // Adjust the margin as needed
            constraintSet.connect(button2.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);

            // position of product name
            constraintSet.connect(textView.getId(), ConstraintSet.START, imageView.getId(), ConstraintSet.END, 16);
            constraintSet.connect(textView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
            constraintSet.connect(textView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
            constraintSet.connect(textView.getId(), ConstraintSet.END, button2.getId(), ConstraintSet.START);
            constraintSet.applyTo(rowLayout);

            buttonContainer.addView(rowLayout);
        }
//        buttonContainer.addView(button);

    }
    public void order(HashMap<Product, Integer> orders, Activity mainActivity) {
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
                        addOrder(current, "Danilo");
                        Intent secondActivityIntent = new Intent(mainActivity, CurrentOrder.class);
                        secondActivityIntent.putExtra("order", current);
                        startActivity(secondActivityIntent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Log.e("Decline", "NO");
                        // stay on same activity
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Da li ste sigurni?").setPositiveButton("Da", dialogClickListener)
                .setNegativeButton("Ne", dialogClickListener).show();
    }

    public void openEdit(View v){
        if (((TextView)v).getText().equals("Products")) {
            Log.d("Text", "Products babyyyy");
            Intent secondActivityIntent = new Intent(this, EditActivity.class);
            startActivity(secondActivityIntent);
        }
        // change to according activity
    }
    public void openOrders(View v){
        if (((TextView)v).getText().equals("Orders")) {
            Log.d("Text", "Orders babyyyy");
            Intent secondActivityIntent = new Intent(this, OrdersActivity.class);
            startActivity(secondActivityIntent);
        }
        // change to according activity
    }
}