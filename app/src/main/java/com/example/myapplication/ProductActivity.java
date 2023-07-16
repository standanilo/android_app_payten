package com.example.myapplication;

import static com.example.myapplication.JDBC.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Database database = Room.databaseBuilder(getApplicationContext(), Database.class, "baza").allowMainThreadQueries().build();
        Dao dao = database.getDao();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int price = intent.getIntExtra("price", 0);
        int id = intent.getIntExtra("id", 0);

        TextView idField = findViewById(R.id.id_field);
        EditText priceField = findViewById(R.id.price_field);
        TextView nameField = findViewById(R.id.name_field);

        priceField.setHint(String.valueOf(price));
        idField.setText(String.valueOf(id));
        nameField.setText(name);

        Button button = findViewById(R.id.button2);

        button.setOnClickListener(v -> {
            int newPrice = Integer.parseInt(String.valueOf(priceField.getText()));
            if (newPrice == price) {
                Log.d("Price", "Price stayed the same");
            } else {
                changePrice(id, newPrice, dao);
            }
            Intent secondActivityIntent = new Intent(this, EditActivity.class);
            startActivity(secondActivityIntent);
        });
    }
}