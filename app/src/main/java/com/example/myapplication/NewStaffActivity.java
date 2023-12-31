package com.example.myapplication;

import static com.example.myapplication.database.JDBC.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.database.Dao;
import com.example.myapplication.database.Database;
import com.example.myapplication.tables.Staff;

public class NewStaffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_staff);

        Database database = Room.databaseBuilder(getApplicationContext(), Database.class, "baza").allowMainThreadQueries().build();
        Dao dao = database.getDao();

        Button button = findViewById(R.id.button_new);
        EditText name = findViewById(R.id.imeEdit);
        EditText username = findViewById(R.id.korisnickoEdit);
        EditText password = findViewById(R.id.lozinkaEdit);

        CheckBox checkBox = findViewById(R.id.checkBox);

        button.setOnClickListener(v -> {
            if (name.getText().toString().equals("") || username.getText().toString().equals("") || password.getText().toString().equals("")) {
                Toast toast = Toast.makeText(this, "Polja ne smeju biti prazna", Toast.LENGTH_LONG);
                toast.show();
            } else {
                if (getUser(username.getText().toString(), dao)) {
                    String type;
                    if (checkBox.isChecked()) type = "kurir";
                    else type = "trgovac";
                    Staff staff = new Staff(name.getText().toString(), type, username.getText().toString(), password.getText().toString());
                    insertStaff(staff, dao);
                    Toast toast = Toast.makeText(this, "Ime: " + username.getText().toString() + " je dodat.", Toast.LENGTH_LONG);
                    toast.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent1 = new Intent(NewStaffActivity.this, StaffActivity.class);
                            finish();
                            startActivity(intent1);
                        }
                    }, 2000);
                } else {
                    Toast toast = Toast.makeText(this, "Ime: " + username.getText().toString() + " vec postoji.", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });
    }
}