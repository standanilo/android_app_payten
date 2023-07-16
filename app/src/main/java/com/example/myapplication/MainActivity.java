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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.login);

        Database database = Room.databaseBuilder(getApplicationContext(), Database.class, "baza").allowMainThreadQueries().build();
        Dao dao = database.getDao();

        ArrayList<Product> products = getProducts(dao);
        ArrayList<Staff> staffs = (ArrayList<Staff>) dao.getAllStaff();

        if (staffs.size() == 0) {
            dao.initStaff(new Staff("Danilo", "trgovac", "danilo", "danilo"),
                    new Staff("Marija", "kurir", "marija", "marija"),
                    new Staff("Dejan", "kurir", "dejan", "dejan"),
                    new Staff("Relja", "kurir", "relja", "relja"));
        }

//        dao.clearProducts();

//        dao.clearIDProduct();

        if (products.size() == 0) {
            dao.initProducts(new Product("Sporet", 15000), new Product("Frizider", 12000),
                    new Product("Ves masina", 10500), new Product("Masina za pranje vesa", 9300),
                    new Product("TV", 7000), new Product("Zamrzivac", 5600),
                    new Product("Mikser", 3500), new Product("Mikrotalasna", 4000),
                    new Product("Klima", 11000), new Product("Sto", 2100),
                    new Product("Stolica", 1200), new Product("Luster", 3250),
                    new Product("Laptop", 40700), new Product("Kompjuter", 65100),
                    new Product("Monitor", 10900), new Product("Daska za peglanje", 2500),
                    new Product("Fen za kosu", 1900), new Product("Pegla za kosu", 2100),
                    new Product("Usisivac", 6700), new Product("Toster", 2300),
                    new Product("Fotelja", 3800), new Product("Dvosed", 13900),
                    new Product("Trosed", 15400), new Product("Krevet", 8200),
                    new Product("Krevet king size", 10100), new Product("Bazen", 4500),
                    new Product("Aparat za kafu", 3900), new Product("Pegla", 2600));
        }

//        dao.clearOP();
//        dao.clearIDOP();
//
//        dao.clearOrders();
//        dao.clearIDOrder();

        button.setOnClickListener(v -> {
            EditText username = findViewById(R.id.username);
            EditText password = findViewById(R.id.password);

            String type = getStaff(username.getText().toString(), password.getText().toString(), dao);

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