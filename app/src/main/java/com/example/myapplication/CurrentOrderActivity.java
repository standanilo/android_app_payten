package com.example.myapplication;

import static com.example.myapplication.JDBC.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CurrentOrderActivity extends AppCompatActivity {
    private LinearLayout buttonContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);
        buttonContainer = findViewById(R.id.buttonContainer);
        Database database = Room.databaseBuilder(getApplicationContext(), Database.class, "baza").allowMainThreadQueries().build();
        Dao dao = database.getDao();
        Intent intent = getIntent();
        HashMap<Product, Integer> order = (HashMap<Product, Integer>) intent.getSerializableExtra("order");
        int orderID = intent.getIntExtra("ID", 0);
        String from = intent.getStringExtra("From");
        AtomicInteger totalCost = new AtomicInteger();

        order.forEach((key, value) -> {
            if (value != 0) {
                ConstraintLayout rowLayout = new ConstraintLayout(this);

                TextView textView1 = new TextView(this);
                textView1.setId(View.generateViewId());
                textView1.setText(key.getProductName() + ": " + String.valueOf(key.getProductPrice()) + " x " + String.valueOf(value) + " = " + String.valueOf(key.getProductPrice() * value));
                textView1.setTextSize(12);

                rowLayout.addView(textView1);

                totalCost.addAndGet((int) (key.getProductPrice() * value));

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

        if (type.equals("kurir")) {
            TextView name = findViewById(R.id.name2);
            TextView phone = findViewById(R.id.phone2);
            TextView address = findViewById(R.id.address2);

            Order o = getOnlyOrder(orderID, dao);
            name.setText(o.getCustomerName());
            phone.setText(o.getPhone());
            address.setText(o.getAddress());

        } else {
            TextView name = findViewById(R.id.name1);
            TextView phone = findViewById(R.id.phone1);
            TextView address = findViewById(R.id.address1);

            name.setVisibility(View.INVISIBLE);
            phone.setVisibility(View.INVISIBLE);
            address.setVisibility(View.INVISIBLE);
        }

        button1.setOnClickListener(v -> {
            Intent secondActivityIntent = new Intent(this, PayActivity.class);
            secondActivityIntent.putExtra("Price", totalCost.get());
            secondActivityIntent.putExtra("ID", orderID);
            if (from.equals("main")) {
                secondActivityIntent.putExtra("From", "main");
            } else {
                secondActivityIntent.putExtra("From", "order");
            }
            startActivity(secondActivityIntent);
        });

        button2.setOnClickListener(v -> {
            if (type.equals("trgovac")) {
                Intent secondActivityIntent = new Intent(this, CustomerActivity.class);
                secondActivityIntent.putExtra("ID", orderID);
                finish();
                startActivity(secondActivityIntent);
            } else {
                Intent secondActivityIntent = new Intent(this, OrdersActivity.class);
                finish();
                startActivity(secondActivityIntent);
            }
        });

    }
}