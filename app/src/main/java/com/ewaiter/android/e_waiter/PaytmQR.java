package com.ewaiter.android.e_waiter;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ewaiter.android.e_waiter.Models.OrderSummary;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaytmQR extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mOrderSummaryDatabaseReference;

    private Button closeButton;

    private String key;
    private float amount;
    private String table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm_qr);

        key = getIntent().getStringExtra("key");
        amount = getIntent().getFloatExtra("amount",0.0f);
        table = getIntent().getStringExtra("table");

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mOrderSummaryDatabaseReference = mFirebaseDatabase.getReference().child("orderSummary");

        closeButton = findViewById(R.id.close);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderSummaryDatabaseReference.push().setValue(new OrderSummary(key,table,getDeviceToken(),"paytm",amount));
                Intent tablesActivity = new Intent(getBaseContext(),TablesSelectActivity.class);
                getBaseContext().startActivity(tablesActivity);
                finish();
            }
        });
    }

    public String getDeviceToken() {
        String deviceToken = Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        return deviceToken;
    }
}
