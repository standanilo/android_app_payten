package com.example.myapplication;

import static com.example.myapplication.JDBC.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Random;

public class PayActivity extends AppCompatActivity {

    private boolean finished = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Intent intent = getIntent();
        int price = intent.getIntExtra("Price", 0);
        int orderID = intent.getIntExtra("ID", 0);
        String from = intent.getStringExtra("From");

        TextView tv = findViewById(R.id.price);
        tv.setText(String.valueOf(price));

        intent = new Intent("com.payten.ecr.action");
        intent.setPackage("com.payten.paytenapos");
        jsonRequest jreq = new jsonRequest();
        jsonRequest.Amounts amounts = jreq.new Amounts();
        jsonRequest.Financial financial = jreq.new Financial();
        jsonRequest.Header header = jreq.new Header();
        jsonRequest.Id id = jreq.new Id();
        jsonRequest.Options options = jreq.new Options();
        jsonRequest.Request request = jreq.new Request();
        jsonRequest.Root root = jreq.new Root();

        options.language = "sr";
        options.print = "true";

        amounts.base = price + ".00";
        amounts.currencyCode = "RSD";

        id.ecr = "000001";

        financial.transaction = "sale";
        financial.id = id;
        financial.options = options;
        financial.amounts = amounts;

        header.length = "282";
        header.version = "01";
        header.hash = "123456";

        request.financial = financial;

        root.header = header;
        root.request = request;

        Gson gson = new Gson();

        String req = gson.toJson(root);
        Log.d("JSON", req);

        intent.putExtra("ecrJson", req);
//        intent.putExtra("senderIntentFilter", "com.example.myapplication.senderIntentFilter");
//        intent.putExtra("senderPackage", "com.example.myapplication");
//        intent.putExtra("senderClass", MainActivity.class);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);

        jsonResponse jres = new jsonResponse();
        jsonResponse.Card card = jres.new Card();
        jsonResponse.Cryptogram1 cryptogram1 = jres.new Cryptogram1();
        jsonResponse.Cryptogram2 cryptogram2 = jres.new Cryptogram2();
        jsonResponse.Cvm cvm = jres.new Cvm();
        jsonResponse.Emv emv = jres.new Emv();
        jsonResponse.Financial financialRes = jres.new Financial();
        jsonResponse.Flags flags = jres.new Flags();
        jsonResponse.Header headerRes = jres.new Header();
        jsonResponse.Id idRes = jres.new Id();
        jsonResponse.Response response = jres.new Response();
        jsonResponse.Result result = jres.new Result();
        jsonResponse.Root rootRes = jres.new Root();

        Intent intent1 = getIntent();
        String res = intent1.getStringExtra("ResponseResult");

        jres = gson.fromJson(res, jsonResponse.class);
        Log.e("JSON", "nesto");
//        String cardNumber = getCardNumber();
//
//        Log.d("card", cardNumber);
//
//        if (checkPayment()) {
//
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            switch (which){
//                                case DialogInterface.BUTTON_POSITIVE:
//                                    finishOrder(orderID);
//                                    changeIntent(from);
//                                    break;
//
//                                case DialogInterface.BUTTON_NEGATIVE:
//                                    // stay on same activity
//                                    break;
//                            }
//                        }
//                    };
//                    AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
//                    builder.setMessage("Broj kartice: " + cardNumber + ", odobreno").setPositiveButton("OK", dialogClickListener).show();
//                }
//            }, 3000);
//        } else {
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            switch (which){
//                                case DialogInterface.BUTTON_POSITIVE:
//                                    finish();
//                                    startActivity(getIntent());
//                                    break;
//
//                                case DialogInterface.BUTTON_NEGATIVE:
//                                    // stay on same activity
//                                    break;
//                            }
//                        }
//                    };
//                    AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
//                    builder.setMessage("Broj kartice: " + cardNumber + ", odbijeno").setPositiveButton("OK", dialogClickListener).show();
//                }
//            }, 3000);
//        }
//        if (finished) {
//        }
    }

    private void changeIntent(String from) {
        if (from.equals("order")) {
            Intent secondActivityIntent = new Intent(this, OrdersActivity.class);
            finish();
            startActivity(secondActivityIntent);
        } else {
            Intent secondActivityIntent = new Intent(this, MerchantActivity.class);
            finish();
            startActivity(secondActivityIntent);
        }
    }

//    public boolean checkPayment() {
//        int val = (int) ((Math.random() * (10 - 1)) + 1);
//        return val > 4;
//    }


//    public String getCardNumber() {
//
//        Random random = new Random(System.currentTimeMillis());
//
//        // The number of random digits that we need to generate is equal to the
//        // total length of the card number minus the start digits given by the
//        // user, minus the check digit at the end.
//
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < 16; i++) {
//            if (i % 4 == 0) {
//                builder.append(" ");
//            }
//            int digit = random.nextInt(10);
//            builder.append(digit);
//        }
//
//        String cardNumber = builder.toString();
//
//        return cardNumber.replaceAll("\\s+","");
//    }


}