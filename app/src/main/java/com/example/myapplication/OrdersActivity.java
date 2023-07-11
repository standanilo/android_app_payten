package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class OrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
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