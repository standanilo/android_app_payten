package com.example.myapplication;

import static com.example.myapplication.JDBC.addProduct;
import static com.example.myapplication.JDBC.finishOrder;
import static com.example.myapplication.JDBC.getUser;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

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

        RadioButton radioButton = findViewById(R.id.radioButton);

        button.setOnClickListener(v -> {
            if (name.getText().toString().equals("") || username.getText().toString().equals("") || password.getText().toString().equals("")) {
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
                AlertDialog.Builder builder = new AlertDialog.Builder(NewStaffActivity.this);
                builder.setMessage("Polja ne smeju biti prazna").setPositiveButton("OK", dialogClickListener).show();
            } else {
                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            // switch to other activity
                            Intent secondActivityIntent = new Intent(this, StaffActivity.class);
                            finish();
                            startActivity(secondActivityIntent);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                };
                if (getUser(username.getText().toString(), dao)) {
                    String type = "";
                    if (radioButton.isActivated()) type = "kurir";
                    else type = "trgovac";
                    Staff staff = new Staff(name.getText().toString(), type, username.getText().toString(), password.getText().toString());
                    JDBC.insertStaff(staff, dao);
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewStaffActivity.this);
                    builder.setMessage("Ime: " + name.getText().toString() + " je dodat.").setPositiveButton("OK", dialogClickListener).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewStaffActivity.this);
                    builder.setMessage("Ime: " + name.getText().toString() + " vec postoji.").setPositiveButton("OK", dialogClickListener).show();
                }

            }
        });
    }
}