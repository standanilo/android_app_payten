package com.example.myapplication;

import static com.example.myapplication.JDBC.getOrder;
import static com.example.myapplication.JDBC.getOrders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {

    private LinearLayout buttonContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ArrayList<Order> orders = getOrders();
        buttonContainer = findViewById(R.id.buttonContainer_edit);

        for (Order o : orders) {
            ConstraintLayout rowLayout = new ConstraintLayout(this);

            TextView textView = new TextView(this);
            textView.setId(View.generateViewId());
            textView.setText("ID: " + String.valueOf(o.getId()));
            textView.setTextSize(25);
            textView.setHeight(150);
            rowLayout.addView(textView);

            TextView textView1 = new TextView(this);
            textView1.setId(View.generateViewId());
            textView1.setText(o.getCustomer() + "/");
            textView1.setTextSize(18);
            rowLayout.addView(textView1);

            TextView textView2 = new TextView(this);
            textView2.setId(View.generateViewId());
            textView2.setText(String.valueOf(o.getDate()) + "/");
            textView2.setTextSize(18);
            rowLayout.addView(textView2);

            TextView textView3 = new TextView(this);
            textView3.setId(View.generateViewId());
            textView3.setText(String.valueOf(o.getPrice()) + " din");
            textView3.setTextSize(18);
            rowLayout.addView(textView3);
            Button button = new Button(this);
            button.setId(View.generateViewId());
            button.setText(R.string.plati);
            if (o.getFinished() == 1) {
                button.setVisibility(View.GONE);
            }
            rowLayout.addView(button);
            button.setOnClickListener(v -> {
                Intent secondActivityIntent = new Intent(this, CurrentOrder.class);
                secondActivityIntent.putExtra("order", getOrder(o.getId()));
                secondActivityIntent.putExtra("ID", o.getId());
                secondActivityIntent.putExtra("From", "orders");
                startActivity(secondActivityIntent);
            });


            if (o.getFinished() == 1) {
                if (orders.indexOf(o) % 2 == 0) {
                    rowLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                } else {
                    rowLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green1));
                }
            } else {
                if (orders.indexOf(o) % 2 == 0) {
                    rowLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                } else {
                    rowLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red1));
                }
            }

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(rowLayout);

//            // position of add button
            constraintSet.connect(button.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
            constraintSet.connect(button.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);

            constraintSet.connect(textView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16);
            constraintSet.connect(textView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
            constraintSet.connect(textView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

            constraintSet.connect(textView1.getId(), ConstraintSet.START, textView.getId(), ConstraintSet.END, 50);
            constraintSet.connect(textView1.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
            constraintSet.connect(textView1.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

            constraintSet.connect(textView2.getId(), ConstraintSet.START, textView1.getId(), ConstraintSet.END);
            constraintSet.connect(textView2.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
            constraintSet.connect(textView2.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

            constraintSet.connect(textView3.getId(), ConstraintSet.START, textView2.getId(), ConstraintSet.END);
            constraintSet.connect(textView3.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
            constraintSet.connect(textView3.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

            constraintSet.applyTo(rowLayout);

            buttonContainer.addView(rowLayout);
        }
    }

    public void openEdit(View v){
        if (((TextView)v).getText().equals("Products")) {
            Log.d("Text", "Products babyyyy");
            Intent secondActivityIntent = new Intent(this, EditActivity.class);
            startActivity(secondActivityIntent);
        }
        // change to according activity
    }
    public void openHome(View v){
        if (((TextView)v).getText().equals("Home")) {
            Log.d("Text", "Home babyyyy");
            Intent secondActivityIntent = new Intent(this, MainActivity.class);
            startActivity(secondActivityIntent);
        }
    }
}