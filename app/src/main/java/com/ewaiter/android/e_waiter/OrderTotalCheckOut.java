package com.ewaiter.android.e_waiter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderTotalCheckOut extends AppCompatActivity {

    ArrayList<BillItem> billItems = new ArrayList<>();
    private float subtotal = 0;
    private float taxes;
    BillItemsAdapter mAdapter;

    String key = "5-5-2018";
    float amount = 1243.25f;
    String tableNumber;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_total_check_out);

        mAdapter = new BillItemsAdapter(this, billItems);

        Bundle b = getIntent().getExtras();
        tableNumber = b.getString("tableNumber");

        final SharedPreferences sharedPreference = getSharedPreferences(tableNumber, Context.MODE_PRIVATE);
        String completeOrder = sharedPreference.getString("Order_Details","No String Found");

        if(completeOrder.equals("No String Found")) {
            Toast.makeText(this,"Nothing To Display",Toast.LENGTH_SHORT).show();
            finish();
        }

        else {
            String[] items = completeOrder.split("/");

            for(int i = 0 ; i < items.length ; i++) {
                String[] singleItem = items[i].split(",");
                String name = singleItem[0];
                String quantity = singleItem[1];
                String category = singleItem[2];
                String price = singleItem[3];
                billItems.add(new BillItem(name,price,quantity,category));
                subtotal = subtotal + (Integer.parseInt(quantity)*Integer.parseInt(price));
            }



            RecyclerView recyclerView = findViewById(R.id.recyclerView_checkout);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(mAdapter);

            TextView totalTv = findViewById(R.id.total);
            TextView subtotalTv = findViewById(R.id.subtotal);
            TextView taxesTv = findViewById(R.id.taxes);

            taxes = Math.round((18f/100) * subtotal);
            subtotal = Math.round(subtotal);

            subtotalTv.setText("Rs." + String.valueOf(subtotal));
            taxesTv.setText("Rs." + String.valueOf(taxes));
            totalTv.setText("Rs." + String.valueOf(subtotal + taxes));

            ImageView makePayment = findViewById(R.id.make_payment);
            ImageView sendBill = findViewById(R.id.send_bill);

            makePayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentPayment = new Intent(OrderTotalCheckOut.this,PaymentActivity.class);
                    finish();
                    startActivity(intentPayment);
                    Intent br = new Intent("finish " + tableNumber);
                    sendBroadcast(br);
                    SharedPreferences.Editor editor = sharedPreference.edit();
                    editor.clear();
                    editor.commit();
                    Intent br2 = new Intent("finish MenuCategorySelectActivity");
                    sendBroadcast(br2);

                    //Open PaymentOptions
                    Intent intentPaymentOptions = new Intent(getApplicationContext(), PaymentOptions.class);
                    intentPaymentOptions.putExtra("key",key);
                    intentPaymentOptions.putExtra("amount",amount);
                    intentPaymentOptions.putExtra("table",tableNumber);
                    getApplicationContext().startActivity(intentPaymentOptions);
                    finish();
                }
            });

            sendBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:amanchopra2587@gmail.com"));
                    //intent.putExtra(intent.EXTRA_EMAIL, "amanchopra2588@gmail.com");
                    //no effect of upper line
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Invoice");
                    intent.putExtra(Intent.EXTRA_TEXT, "Order details");
                    if(intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            });
        }
    }

}
