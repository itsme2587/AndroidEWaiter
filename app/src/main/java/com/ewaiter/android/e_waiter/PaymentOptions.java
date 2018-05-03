package com.ewaiter.android.e_waiter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ewaiter.android.e_waiter.Models.FirebaseCursorPojo;
import com.ewaiter.android.e_waiter.Models.OrderSummary;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PaymentOptions extends AppCompatActivity {

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mOrderSummaryDatabaseReference;

    private Button paytm;
    private Button cash;

    private String key;
    private float amount;
    private String table;

    private TextView tableTextView;
    private TextView amountTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_options);

        key = getIntent().getStringExtra("key");
        amount = getIntent().getFloatExtra("amount",0.0f);
        table = getIntent().getStringExtra("table");

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mOrderSummaryDatabaseReference = mFirebaseDatabase.getReference().child("orderSummary");

        tableTextView = findViewById(R.id.table);
        tableTextView.setText(table);
        amountTextView = findViewById(R.id.amount);
        amountTextView.setText(amount + "");

        paytm = findViewById(R.id.paytm);
        cash = findViewById(R.id.cash);

        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mOrderSummaryDatabaseReference.push().setValue(new OrderSummary(key,table,getDeviceToken(),"paytm",amount));
                Intent paytmQR = new Intent(getApplicationContext(),PaytmQR.class);
                paytmQR.putExtra("key",key);
                paytmQR.putExtra("amount",amount);
                paytmQR.putExtra("table",table);
                getApplicationContext().startActivity(paytmQR);
//                finish();
            }
        });

        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderSummaryDatabaseReference.push().setValue(new OrderSummary(key,table,getDeviceToken(),"cash",amount));
                finish();
            }
        });

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(action.equals("finish PaymentOptions")) {
                    finish();
                }
            }
        };
        registerReceiver(broadcastReceiver,new IntentFilter("finish PaymentOptions"));
    }
    public String getDeviceToken() {
        String deviceToken = Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        return deviceToken;
    }

}
