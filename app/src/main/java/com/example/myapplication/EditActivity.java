package com.example.myapplication;

import static com.example.myapplication.JDBC.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

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
        Database database = Room.databaseBuilder(getApplicationContext(), Database.class, "baza").allowMainThreadQueries().build();
        Dao dao = database.getDao();

        buttonContainer = findViewById(R.id.buttonContainer_edit);

        Button button = new Button(this);
        button.setId(View.generateViewId());
        button.setText(R.string.dodaj);
        button.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan4));

        button.setOnClickListener(v -> {
            Intent secondActivityIntent = new Intent(this, NewProductActivity.class);
            startActivity(secondActivityIntent);
        });

        ArrayList<Product> products = getProducts(dao);

        for (Product p : products) {
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    3
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

            TextView textView = new TextView(this);
            textView.setId(View.generateViewId());
            textView.setText(p.getProductName());
            if (type.equals("kurir")) {
                textView.setTextSize(20);
            } else {
                textView.setTextSize(15);
            }
            textView.setPadding(20, 0,0,0);
            textView.setLayoutParams(textParams);
            linearLayout.addView(textView);


            Button button1 = new Button(this);
            button1.setId(View.generateViewId());
            button1.setText(R.string.izmeni);
            if (!type.equals("kurir")) {
                button1.setLayoutParams(button1Params);
                linearLayout.addView(button1);
            }

            Button button2 = new Button(this);
            button2.setId(View.generateViewId());
            button2.setText(R.string.brisi);

            if (!type.equals("kurir")) {
                button2.setLayoutParams(button2Params);
                linearLayout.addView(button2);
            }

            button1.setOnClickListener(v -> {
                Intent secondActivityIntent = new Intent(this, ProductActivity.class);
                secondActivityIntent.putExtra("name", p.getProductName());
                secondActivityIntent.putExtra("price", p.getProductPrice());
                secondActivityIntent.putExtra("id", p.getProductID());
                Log.d("Product", p.getProductName() + ": cena: " + p.getProductPrice());
                startActivity(secondActivityIntent);
            });
            button2.setOnClickListener(v -> {
                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            // switch to order activity
                            if (deleteProduct(textView.getText().toString(), dao)) {
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
                linearLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan));
            } else {
                linearLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan2));
            }
            buttonContainer.addView(linearLayout);
        }
        if (!type.equals("kurir")) buttonContainer.addView(button);
    }

    public void openHome(View v){
        Intent secondActivityIntent = new Intent(this, MerchantActivity.class);
        startActivity(secondActivityIntent);
    }
    public void openOrders(View v){
        Intent secondActivityIntent = new Intent(this, OrdersActivity.class);
        startActivity(secondActivityIntent);
    }
}