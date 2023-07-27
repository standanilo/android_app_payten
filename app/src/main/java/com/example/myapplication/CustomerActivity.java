package com.example.myapplication;

import static com.example.myapplication.database.JDBC.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.database.Dao;
import com.example.myapplication.database.Database;

import java.util.regex.Pattern;

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
            String regex = "\\+3816\\d{7,8}|06\\d{7,8}";
            boolean isMatch = Pattern.compile("\\+3816\\d{7,8}|06\\d{7,8}")
                    .matcher(phone.getText())
                    .find(); // returns true
            if (isMatch) {
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
            } else {
                Toast toast = Toast.makeText(this, "Pogresan format broja telefona", Toast.LENGTH_LONG);
                toast.show();
            }
        });

    }
}