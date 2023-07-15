package com.example.myapplication;

import static com.example.myapplication.JDBC.deleteProduct;
import static com.example.myapplication.JDBC.getProducts;
import static com.example.myapplication.JDBC.type;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    private LinearLayout buttonContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ArrayList<Product> products = getProducts();
        buttonContainer = findViewById(R.id.buttonContainer_edit);
//        HashMap<Product, Integer> orders = new HashMap<>();
//        AtomicInteger num_of_orders = new AtomicInteger();

        Button button = new Button(this);
        button.setId(View.generateViewId());
        button.setText(R.string.dodaj);
        // Set an onClickListener or perform any desired action
        button.setOnClickListener(v -> {
            Intent secondActivityIntent = new Intent(this, NewProductActivity.class);
            startActivity(secondActivityIntent);
        });

        for (Product p : products) {
            ConstraintLayout rowLayout = new ConstraintLayout(this);

            TextView textView = new TextView(this);
            textView.setId(View.generateViewId());
            textView.setText(p.getName());
            if (type.equals("kurir")) {
                textView.setTextSize(20);
                textView.setPadding(0, 40, 0, 40);
            }
            rowLayout.addView(textView);


            Button button1 = new Button(this);
            button1.setId(View.generateViewId());
            button1.setText(R.string.izmeni);
            if (!type.equals("kurir")) {
                rowLayout.addView(button1);
            }

//            TextView textView1 = new TextView(this);
//            textView1.setId(View.generateViewId());
//            textView1.setText("0");
//            rowLayout.addView(textView1);
//
            Button button2 = new Button(this);
            button2.setId(View.generateViewId());
            button2.setText(R.string.brisi);
            if (!type.equals("kurir")) {
                rowLayout.addView(button2);
            }

            // Set an onClickListener or perform any desired action
            button1.setOnClickListener(v -> {
                Intent secondActivityIntent = new Intent(this, ProductActivity.class);
                secondActivityIntent.putExtra("name", p.getName());
                secondActivityIntent.putExtra("price", p.getPrice());
                secondActivityIntent.putExtra("id", p.getId());
                Log.d("Product", p.getName() + ": cena: " + p.getPrice());
                startActivity(secondActivityIntent);
            });
            button2.setOnClickListener(v -> {
                // Perform action when button is clicked
                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            // switch to order activity
                            if (deleteProduct(textView.getText().toString())) {
                                Intent secondActivityIntent = new Intent(this, EditActivity.class);
                                startActivity(secondActivityIntent);
                            }
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            // stay on same activity
                            break;
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                builder.setMessage("Da li ste sigurni da zelite da izbrisete " + textView.getText().toString() + "?").setPositiveButton("Da", dialogClickListener).setNegativeButton("Ne", dialogClickListener).show();
            });
            if (products.indexOf(p) % 2 == 0) {
                rowLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan));
            } else {
                rowLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan2));
            }

            // Add the button to the layout container
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(rowLayout);

//            // position of add button
            constraintSet.connect(button1.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
            constraintSet.connect(button1.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
//
//            // position of product amount
//            constraintSet.connect(textView1.getId(), ConstraintSet.START, button2.getId(), ConstraintSet.END, 16);
//            constraintSet.connect(textView1.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
//            constraintSet.connect(textView1.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
//            constraintSet.connect(textView1.getId(), ConstraintSet.END, button1.getId(), ConstraintSet.START, 16);
//
            // position of sub button
            constraintSet.connect(button2.getId(), ConstraintSet.END, button1.getId(), ConstraintSet.START); // Adjust the margin as needed
            constraintSet.connect(button2.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);

            // position of product name
            constraintSet.connect(textView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16);
            constraintSet.connect(textView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
            constraintSet.connect(textView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
//            constraintSet.connect(textView.getId(), ConstraintSet.END, button2.getId(), ConstraintSet.START);
            constraintSet.applyTo(rowLayout);

            buttonContainer.addView(rowLayout);
        }
        if (!type.equals("kurir"))
            buttonContainer.addView(button);
    }

    public void openHome(View v){
        if (((TextView)v).getText().equals("Home")) {
            Log.d("Text", "Home babyyyy");
            Intent secondActivityIntent = new Intent(this, MerchantActivity.class);
            startActivity(secondActivityIntent);
        }
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