package com.example.myapplication;

import static com.example.myapplication.JDBC.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        EditText et = findViewById(R.id.imeEdit);
        EditText en = findViewById(R.id.editTextNumber);

        Database database = Room.databaseBuilder(getApplicationContext(), Database.class, "baza").allowMainThreadQueries().build();
        Dao dao = database.getDao();

        Button button = findViewById(R.id.button_new);

        button.setOnClickListener(v -> {
            if (et.getText().toString().equals("") && en.getText().toString().equals("")) {
                Toast toast = Toast.makeText(this, "Ime i cena ne smeju biti prazni", Toast.LENGTH_LONG);
                toast.show();
            } else if (et.getText().toString().equals("")) {
                Toast toast = Toast.makeText(this, "Ime ne sme biti prazno", Toast.LENGTH_LONG);
                toast.show();
            } else if (en.getText().toString().equals("")) {
                Toast toast = Toast.makeText(this, "Cena ne sme biti prazna", Toast.LENGTH_LONG);
                toast.show();
            } else {
                if (addProduct(et.getText().toString(), Integer.parseInt(en.getText().toString()), dao)) {
                    Toast toast = Toast.makeText(this, "Proizvod: " + et.getText().toString() + ", sa cenom: " + en.getText().toString() + " je dodat.", Toast.LENGTH_LONG);
                    toast.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent1 = new Intent(NewProductActivity.this, EditActivity.class);
                            finish();
                            startActivity(intent1);
                        }
                    }, 2000);
                } else {
                    Toast toast = Toast.makeText(this, "Proizvod: " + et.getText().toString() + " vec postoji", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });
    }
}