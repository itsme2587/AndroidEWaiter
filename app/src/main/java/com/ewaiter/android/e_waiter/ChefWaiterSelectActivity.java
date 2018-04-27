package com.ewaiter.android.e_waiter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ChefWaiterSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_waiter_select);

        final TextView chefTv = findViewById(R.id.chefTv);
        final TextView waiterTv = findViewById(R.id.waiterTv);

        chefTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chefTv.setBackgroundResource(R.drawable.chef_waiter_colored);
                Intent intent = new Intent(ChefWaiterSelectActivity.this,ChefActivity.class);
                startActivity(intent);
                finish();
            }
        });

        waiterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waiterTv.setBackgroundResource(R.drawable.chef_waiter_colored);
                Intent intent = new Intent(ChefWaiterSelectActivity.this,TablesSelectActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
