package com.example.myapplication;

import static com.example.myapplication.JDBC.changePrice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CurrentOrder extends AppCompatActivity {
    private LinearLayout buttonContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);
        buttonContainer = findViewById(R.id.buttonContainer);
        Intent intent = getIntent();
        HashMap<Product, Integer> order = (HashMap<Product, Integer>) intent.getSerializableExtra("order");

        AtomicInteger totalCost = new AtomicInteger();

        order.forEach((key, value) -> {
            if (value != 0) {
                ConstraintLayout rowLayout = new ConstraintLayout(this);

                TextView textView1 = new TextView(this);
                textView1.setId(View.generateViewId());
                textView1.setText(key.getName() + ": " + String.valueOf(key.getPrice()) + " x " + String.valueOf(value) + " = " + String.valueOf(key.getPrice() * value));
                textView1.setTextSize(20);

                rowLayout.addView(textView1);

                totalCost.addAndGet(key.getPrice() * value);

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(rowLayout);

                constraintSet.connect(textView1.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 100);
                constraintSet.connect(textView1.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 100);

                constraintSet.applyTo(rowLayout);

                buttonContainer.addView(rowLayout);
            }

        });
        TextView tv = findViewById(R.id.prices);
        tv.setText(String.valueOf(totalCost.get()));

        Button button1 = findViewById(R.id.pay);
        Button button2 = findViewById(R.id.pay_later);

        button1.setOnClickListener(v -> {
            Intent secondActivityIntent = new Intent(this, PayActivity.class);
            secondActivityIntent.putExtra("Price", totalCost.get());
            startActivity(secondActivityIntent);
        });

        button2.setOnClickListener(v -> {
            Intent secondActivityIntent = new Intent(this, MainActivity.class);
            startActivity(secondActivityIntent);
        });

    }
}