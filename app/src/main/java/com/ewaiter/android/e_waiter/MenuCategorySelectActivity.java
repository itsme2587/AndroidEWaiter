package com.ewaiter.android.e_waiter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuCategorySelectActivity extends AppCompatActivity {

    ImageView cart;
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_category_select);

        ArrayList<MenuCategory> menuCategories = new ArrayList<>();

        menuCategories.add(new MenuCategory(R.drawable.chinese,"Chinese","Chinese"));
        menuCategories.add(new MenuCategory(R.drawable.starter,"Starters","Starters"));
        menuCategories.add(new MenuCategory(R.drawable.beverages,"Beverages","Beverages"));
        menuCategories.add(new MenuCategory(R.drawable.south_indian,"South Indian","SouthIndian"));
        menuCategories.add(new MenuCategory(R.drawable.salad,"Salads","Salads"));
        menuCategories.add(new MenuCategory(R.drawable.main_course,"Main Course","MainCourse"));
        menuCategories.add(new MenuCategory(R.drawable.breads,"Breads","Breads"));
        menuCategories.add(new MenuCategory(R.drawable.dessert,"Desserts","Desserts"));

        RecyclerView rv = findViewById(R.id.recycler_view_menu_category);
        rv.setLayoutManager(new GridLayoutManager(this,2));
        rv.setAdapter(new MenuAdapter(this,menuCategories,getTableNumber()));

        TextView tableNumberIntent = findViewById(R.id.table_number_intent);
        tableNumberIntent.setText(getTableNumber());

        cart = findViewById(R.id.cartIcon);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Cart.class);
                intent.putExtra("tableNumber",getTableNumber());
                startActivity(intent);
            }
        });

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(action.equals("finish MenuCategorySelectActivity")) {
                    finish();
                }
            }
        };
        registerReceiver(broadcastReceiver,new IntentFilter("finish MenuCategorySelectActivity"));
    }

    public String getTableNumber() {
        Bundle b = getIntent().getExtras();
        String tableNumber = b.getString("tableNumber");
        return tableNumber;
    }

}
