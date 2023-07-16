package com.example.myapplication;

import static com.example.myapplication.JDBC.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {

    private LinearLayout buttonContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Database database = Room.databaseBuilder(getApplicationContext(), Database.class, "baza").allowMainThreadQueries().build();
        Dao dao = database.getDao();
        ArrayList<Order> orders = new ArrayList<>();
        if (type.equals("trgovac")) {
            orders = getOrders(dao);
        } else {
            orders = getOrdersForCourier(currentCourierID, dao);
        }
        buttonContainer = findViewById(R.id.buttonContainer_edit);

        for (Order o : orders) {
//            ConstraintLayout rowLayout = new ConstraintLayout(this);
            LinearLayout linearLayoutMain = new LinearLayout(this);
            linearLayoutMain.setOrientation(LinearLayout.VERTICAL);

            LinearLayout linearLayout1 = new LinearLayout(this);
            LinearLayout linearLayout2 = new LinearLayout(this);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    3
            );
            LinearLayout.LayoutParams text1Params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    2
            );
            LinearLayout.LayoutParams text2Params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    2
            );
            LinearLayout.LayoutParams text3Params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    5
            );
            LinearLayout.LayoutParams text4Params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    2
            );
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    5
            );
            LinearLayout.LayoutParams text5Params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    5
            );

            TextView textView = new TextView(this);
            textView.setId(View.generateViewId());
            textView.setText("ID: " + String.valueOf(o.getOrderID()));
//            rowLayout.addView(textView);
            textView.setTextSize(20);
            textView.setLayoutParams(textParams);
            linearLayout1.addView(textView);

            TextView textView3 = new TextView(this);
            textView3.setId(View.generateViewId());
            textView3.setText("cena: " + o.getPrice());
            textView3.setTextSize(18);
            textView3.setGravity(Gravity.CENTER);
            textView3.setLayoutParams(text3Params);
            linearLayout1.addView(textView3);

            TextView textView5 = new TextView(this);
            textView5.setId(View.generateViewId());
            textView5.setText(o.getDateOf());
            textView5.setTextSize(18);
            textView5.setGravity(Gravity.CENTER);
            textView5.setLayoutParams(text5Params);
            linearLayout1.addView(textView5);

            TextView textView1 = new TextView(this);
            textView1.setId(View.generateViewId());
            if (o.getCustomerName() != null) {
                textView1.setText(o.getCustomerName());
            }
            textView1.setTextSize(18);
//            rowLayout.addView(textView1);
            textView1.setLayoutParams(text1Params);
            linearLayout2.addView(textView1);

            TextView textView4 = new TextView(this);
            textView4.setId(View.generateViewId());
            if (o.getPhone() != null) {
                textView4.setText("br tel: " + o.getPhone());
            }
            textView4.setTextSize(18);
            textView4.setGravity(Gravity.CENTER);
//            rowLayout.addView(textView4);
            textView4.setLayoutParams(text4Params);
            linearLayout2.addView(textView4);

            TextView textView2 = new TextView(this);
            textView2.setId(View.generateViewId());
            if (o.getAddress() != null) {
                textView2.setText("adr: " + o.getAddress());
            }
            textView2.setTextSize(18);
            textView2.setGravity(Gravity.CENTER);
//            rowLayout.addView(textView2);
            textView2.setLayoutParams(text2Params);
            linearLayout2.addView(textView2);

            Button button = new Button(this);
            button.setId(View.generateViewId());
            button.setText(R.string.plati);
            button.setLayoutParams(buttonParams);
            linearLayout1.addView(button);

            if (JDBC.type.equals("trgovac")) {
                if (o.getStaff() != null){
                    Staff staff = dao.getCourierInfo(o.getStaff());
                    if (staff != null) {
                        TextView textView6 = new TextView(this);
                        textView6.setId(View.generateViewId());
                        textView6.setText("kurir: " + staff.getName());
                        textView6.setTextSize(18);
                        textView6.setGravity(Gravity.CENTER);
                        textView6.setLayoutParams(buttonParams);
                        linearLayout1.addView(textView6);
                    }
                }
            } else {
                button.setText(R.string.info);
            }

            if (o.getFinished() == 1 || JDBC.type.equals("trgovac")) {
                button.setVisibility(View.GONE);
            }
//            rowLayout.addView(button);

            button.setOnClickListener(v -> {
                Intent secondActivityIntent = new Intent(this, CurrentOrderActivity.class);
                secondActivityIntent.putExtra("order", getOrder(o.getOrderID(), dao));
                secondActivityIntent.putExtra("ID", o.getOrderID());
                secondActivityIntent.putExtra("From", "orders");
                startActivity(secondActivityIntent);
            });


            if (o.getFinished() == 1) {
                if (orders.indexOf(o) % 2 == 0) {
//                    rowLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                    linearLayout1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                    linearLayout2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                } else {
//                    rowLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green1));
                    linearLayout1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green1));
                    linearLayout2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green1));
                }
            } else {
                if (orders.indexOf(o) % 2 == 0) {
//                    rowLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    linearLayout1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    linearLayout2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                } else {
//                    rowLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red1));
                    linearLayout1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red1));
                    linearLayout2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red1));
                }
            }

//            ConstraintSet constraintSet = new ConstraintSet();
//            constraintSet.clone(rowLayout);

//            // position of add button
//            constraintSet.connect(button.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
//            constraintSet.connect(button.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
//
//            constraintSet.connect(textView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16);
//            constraintSet.connect(textView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
//            constraintSet.connect(textView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
//
//            constraintSet.connect(textView1.getId(), ConstraintSet.START, textView.getId(), ConstraintSet.END, 50);
//            constraintSet.connect(textView1.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
//            constraintSet.connect(textView1.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
//
//            constraintSet.connect(textView2.getId(), ConstraintSet.START, textView1.getId(), ConstraintSet.END);
//            constraintSet.connect(textView2.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
//            constraintSet.connect(textView2.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
//
//            constraintSet.connect(textView3.getId(), ConstraintSet.START, textView2.getId(), ConstraintSet.END);
//            constraintSet.connect(textView3.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
//            constraintSet.connect(textView3.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
//
//            constraintSet.applyTo(rowLayout);

//            buttonContainer.addView(rowLayout);
            linearLayoutMain.addView(linearLayout1);
            linearLayoutMain.addView(linearLayout2);

            buttonContainer.addView(linearLayoutMain);
        }
    }

    public void openEdit(View v){
        if (((TextView)v).getText().equals("Products")) {
            Log.d("Text", "Products babyyyy");
            Intent secondActivityIntent = new Intent(this, EditActivity.class);
            finish();
            startActivity(secondActivityIntent);
        }
        // change to according activity
    }
    public void openHome(View v){
        if (((TextView)v).getText().equals("Home")) {
            Log.d("Text", "Home babyyyy");
            Intent secondActivityIntent = new Intent(this, MerchantActivity.class);
            finish();
            startActivity(secondActivityIntent);
        }
    }
}