package com.ewaiter.android.e_waiter;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ewaiter.android.e_waiter.data.MenuItemsContract;

public class Breads extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breads);

        Bundle b = getIntent().getExtras();
        String categoryNameIntent = b.getString("categoryName");
        String tableNumberIntent = b.getString("tableNumber");

        TextView categoryName = findViewById(R.id.CategoryNameTv);
        categoryName.setText(categoryNameIntent);

        TextView tableNumber = findViewById(R.id.TableNumberTv);
        tableNumber.setText(tableNumberIntent);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_menu_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MenuItemsCursorAdapter mAdapter = new MenuItemsCursorAdapter(this,getItems());
        recyclerView.setAdapter(mAdapter);

    }

    private Cursor getItems() {
        String[] projection = {MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_NAME, MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_QUANTITY };
        String selection = MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_CATEGORY + "=?";
        String[] selectionArgs = new String[] {"Breads"};

        Cursor cursor = getContentResolver().query(MenuItemsContract.MenuItemsEntry.CONTENT_URI,projection,selection,selectionArgs,null,null);
        return cursor;
    }
}
