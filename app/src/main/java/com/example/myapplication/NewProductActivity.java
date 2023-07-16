package com.example.myapplication;

import static com.example.myapplication.JDBC.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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
                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            // switch to order activity
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            // stay on same activity
                            break;
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(NewProductActivity.this);
                builder.setMessage("Ime i cena ne smeju biti prazni").setPositiveButton("OK", dialogClickListener).show();
            } else if (et.getText().toString().equals("")) {
                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            // switch to order activity
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            // stay on same activity
                            break;
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(NewProductActivity.this);
                builder.setMessage("Ime ne sme biti prazno").setPositiveButton("OK", dialogClickListener).show();
            } else if (en.getText().toString().equals("")) {
                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            // switch to order activity
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            // stay on same activity
                            break;
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(NewProductActivity.this);
                builder.setMessage("Cena ne sme biti prazna").setPositiveButton("OK", dialogClickListener).show();
            } else {
                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            // switch to other activity
                            Intent secondActivityIntent = new Intent(this, EditActivity.class);
                            finish();
                            startActivity(secondActivityIntent);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            // stay on same activity
                            break;
                    }
                };
                if (addProduct(et.getText().toString(), Integer.parseInt(en.getText().toString()), dao)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewProductActivity.this);
                    builder.setMessage("Proizvod: " + et.getText().toString() + ", sa cenom: " + en.getText().toString() + " je dodat.").setPositiveButton("OK", dialogClickListener).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewProductActivity.this);
                    builder.setMessage("Proizvod: " + et.getText().toString() + " vec postoji").setNegativeButton("OK", dialogClickListener).show();
                }

            }
        });
    }
}