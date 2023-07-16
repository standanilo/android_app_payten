package com.example.myapplication;

import static com.example.myapplication.JDBC.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

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
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            changeIntent();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(CustomerActivity.this);
            builder.setMessage("Porudzbina dodata").setPositiveButton("OK", dialogClickListener).show();
        });

    }

    private void changeIntent() {
            Intent secondActivityIntent = new Intent(this, MerchantActivity.class);
            startActivity(secondActivityIntent);
    }
}