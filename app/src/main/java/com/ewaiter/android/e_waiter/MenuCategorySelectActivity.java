package com.ewaiter.android.e_waiter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuCategorySelectActivity extends AppCompatActivity {

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
    }

    public String getTableNumber() {
        Bundle b = getIntent().getExtras();
        String tableNumber = b.getString("tableNumber");
        return tableNumber;
    }
}
