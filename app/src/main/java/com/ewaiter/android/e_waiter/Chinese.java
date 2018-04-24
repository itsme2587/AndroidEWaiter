package com.ewaiter.android.e_waiter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Chinese extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinese);

        Bundle b = getIntent().getExtras();
        String categoryNameIntent = b.getString("categoryName");
        String tableNumberIntent = b.getString("tableNumber");

        TextView categoryName = findViewById(R.id.CategoryNameTv);
        categoryName.setText(categoryNameIntent);

        TextView tableNumber = findViewById(R.id.TableNumberTv);
        tableNumber.setText(tableNumberIntent);
    }
}
