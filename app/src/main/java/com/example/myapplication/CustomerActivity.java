package com.example.myapplication;

import static com.example.myapplication.JDBC.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class CustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        Database database = Room.databaseBuilder(getApplicationContext(), Database.class, "baza").allowMainThreadQueries().build();
        Dao dao = database.getDao();

        EditText name = findViewById(R.id.name);
        EditText address = findViewById(R.id.address);
        EditText phone = findViewById(R.id.phone);

        Intent intent = getIntent();
        int orderID = intent.getIntExtra("ID", 0);

        Button button = findViewById(R.id.add);

        button.setOnClickListener(v -> {
            updateOrder(orderID, name.getText().toString(), address.getText().toString(), phone.getText().toString(), dao);
            Toast toast = Toast.makeText(this, "Porudzbina dodata", Toast.LENGTH_LONG);
            toast.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent1 = new Intent(CustomerActivity.this, MerchantActivity.class);
                    finish();
                    startActivity(intent1);
                }
            }, 2000);
        });

    }
}