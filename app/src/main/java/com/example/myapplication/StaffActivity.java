package com.example.myapplication;

import static com.example.myapplication.database.JDBC.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.database.Dao;
import com.example.myapplication.database.Database;
import com.example.myapplication.tables.Staff;

import java.util.ArrayList;

public class StaffActivity extends AppCompatActivity {

    private LinearLayout buttonContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        Database database = Room.databaseBuilder(getApplicationContext(), Database.class, "baza").allowMainThreadQueries().build();
        Dao dao = database.getDao();

        buttonContainer = findViewById(R.id.buttonContainer_edit);

        ArrayList<Staff> staff = (ArrayList<Staff>) getAllStaff(dao);

        Button button = new Button(this);
        button.setId(View.generateViewId());
        button.setText(R.string.dodaj);
        button.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan4));

        button.setOnClickListener(v -> {
            Intent secondActivityIntent = new Intent(this, NewStaffActivity.class);
            startActivity(secondActivityIntent);
        });

        LinearLayout linear = new LinearLayout(this);
        LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
        LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                3
        );
        LinearLayout.LayoutParams Params2 = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                3
        );
        LinearLayout.LayoutParams Params3 = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                2
        );
        LinearLayout.LayoutParams Params4 = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                3
        );


        TextView text = new TextView(this);
        text.setId(View.generateViewId());
        text.setText("ID");
        text.setTextSize(18);
        text.setPadding(20, 0,0,0);
        text.setLayoutParams(Params);
        linear.addView(text);

        TextView text1 = new TextView(this);
        text1.setId(View.generateViewId());
        text1.setText(R.string.ime);
        text1.setTextSize(18);
        text1.setLayoutParams(Params1);
        linear.addView(text1);

        TextView text2 = new TextView(this);
        text2.setId(View.generateViewId());
        text2.setText("Username");
        text2.setTextSize(18);
        text2.setLayoutParams(Params2);
        linear.addView(text2);

        TextView text3 = new TextView(this);
        text3.setId(View.generateViewId());
        text3.setText("Pozicija");
        text3.setTextSize(18);
        text3.setLayoutParams(Params4);
        linear.addView(text3);

        Button button2 = new Button(this);
        button2.setId(View.generateViewId());
        button2.setText(R.string.brisi);
        button2.setLayoutParams(Params3);
        button2.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.merch_button, null));
        button2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        button2.setGravity(Gravity.CENTER);
        button2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        linear.addView(button2);

        buttonContainer.addView(linear);

        for (Staff s : staff) {
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            );
            LinearLayout.LayoutParams text1Params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    3
            );
            LinearLayout.LayoutParams text2Params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    3
            );
            LinearLayout.LayoutParams button1Params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    2
            );
            LinearLayout.LayoutParams text3Params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    3
            );

            TextView textView = new TextView(this);
            textView.setId(View.generateViewId());
            textView.setText(String.valueOf(s.getStaffID()));
            textView.setTextSize(18);
            textView.setPadding(20, 0,0,0);
            textView.setLayoutParams(textParams);
            linearLayout.addView(textView);

            TextView textView1 = new TextView(this);
            textView1.setId(View.generateViewId());
            textView1.setText(s.getName());
            textView1.setTextSize(18);
            textView1.setLayoutParams(text1Params);
            linearLayout.addView(textView1);

            TextView textView2 = new TextView(this);
            textView2.setId(View.generateViewId());
            textView2.setText(s.getUsername());
            textView2.setTextSize(18);
            textView2.setLayoutParams(text2Params);
            linearLayout.addView(textView2);

            TextView textView3 = new TextView(this);
            textView3.setId(View.generateViewId());
            textView3.setText(s.getType());
            textView3.setTextSize(18);
            textView3.setLayoutParams(text3Params);
            linearLayout.addView(textView3);

            Button button1 = new Button(this);
            button1.setId(View.generateViewId());
            button1.setText(R.string.brisi);
            button1.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.merch_button, null));
            button1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            button1.setGravity(Gravity.CENTER);
            button1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            button1.setLayoutParams(button1Params);
            linearLayout.addView(button1);
            button1.setOnClickListener(v -> {
                // Perform action when button is clicked
                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            // switch to order activity
                            if (deleteStaff(s.getStaffID(), dao)) {
                                Intent secondActivityIntent = new Intent(this, StaffActivity.class);
                                startActivity(secondActivityIntent);
                            }
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            // stay on same activity
                            break;
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(StaffActivity.this);
                builder.setMessage("Da li ste sigurni da zelite da izbrisete " + s.getName() + "?").setPositiveButton("Da", dialogClickListener).setNegativeButton("Ne", dialogClickListener).show();
            });

            buttonContainer.addView(linearLayout);
        }
        buttonContainer.addView(button);
    }

    public void goBack(View view) {
        Intent secondActivityIntent = new Intent(this, MainActivity.class);
        finish();
        startActivity(secondActivityIntent);
    }
}