package com.example.myapplication;

import static com.example.myapplication.JDBC.addOrder;
import static com.example.myapplication.JDBC.getStaff;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.login);

        button.setOnClickListener(v -> {
            EditText username = findViewById(R.id.username);
            EditText password = findViewById(R.id.password);

            String type = getStaff(username.getText().toString(), password.getText().toString());

            if (type.equals("kurir") || type.equals("trgovac")) {
                Intent secondActivityIntent = new Intent(this, MerchantActivity.class);
                startActivity(secondActivityIntent);
            } else {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Pogresno uneti podaci").setPositiveButton("OK", dialogClickListener).show();
            }
        });
    }
}