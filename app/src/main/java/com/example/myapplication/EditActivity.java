package com.example.myapplication;

import static com.example.myapplication.database.JDBC.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.database.Dao;
import com.example.myapplication.database.Database;
import com.example.myapplication.tables.Product;

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
        button.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.add_button, null));
        button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        button.setGravity(Gravity.CENTER);

        button.setOnClickListener(v -> {
            Intent secondActivityIntent = new Intent(this, NewProductActivity.class);
            startActivity(secondActivityIntent);
        });

        ArrayList<Product> products = getProducts(dao);

        for (Product p : products) {
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    5
            );
            LinearLayout.LayoutParams button1Params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    2
            );
            LinearLayout.LayoutParams button2Params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    2
            );

            textParams.leftMargin = 32;
            TextView textView = new TextView(this);
            textView.setId(View.generateViewId());
            textView.setText(p.getProductName());
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(20, 0,0,0);
            textView.setLayoutParams(textParams);
            linearLayout.addView(textView);


            button1Params.bottomMargin = 24;
            button1Params.topMargin = 24;
            Button button1 = new Button(this);
            button1.setId(View.generateViewId());
            button1.setText(R.string.izmeni);
            button1.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.merch_button, null));
            button1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            button1.setGravity(Gravity.CENTER);
            button1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            if (!type.equals("kurir")) {
                button1.setLayoutParams(button1Params);
                linearLayout.addView(button1);
            }

            button2Params.bottomMargin = 24;
            button2Params.topMargin = 24;
            button2Params.leftMargin = 32;
            button2Params.rightMargin = 32;
            Button button2 = new Button(this);
            button2.setId(View.generateViewId());
            button2.setText(R.string.brisi);
            button2.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.merch_button, null));
            button2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            button2.setGravity(Gravity.CENTER);
            button2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
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
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);
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
            linearLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.linear_layout_cyan, null));
//            if (products.indexOf(p) % 2 == 0) {
//                linearLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan1));
//            } else {
//                linearLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan2));
//            }
            buttonContainer.addView(linearLayout);
        }
        if (!type.equals("kurir")) buttonContainer.addView(button);
    }

    public void openHome(View v){
        Intent secondActivityIntent = new Intent(this, MerchantActivity.class);
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
}