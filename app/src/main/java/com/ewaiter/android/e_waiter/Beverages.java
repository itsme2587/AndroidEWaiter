package com.ewaiter.android.e_waiter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ewaiter.android.e_waiter.data.MenuItemsContract;
import com.ewaiter.android.e_waiter.data.MenuItemsDbHelper;
import com.ewaiter.android.e_waiter.data.MenuItemsContract.MenuItemsEntry;

public class Beverages extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beverages);

        Bundle b = getIntent().getExtras();
        String categoryNameIntent = b.getString("categoryName");
        String tableNumberIntent = b.getString("tableNumber");

        TextView categoryName = findViewById(R.id.CategoryNameTv);
        categoryName.setText(categoryNameIntent);

        TextView tableNumber = findViewById(R.id.TableNumberTv);
        tableNumber.setText(tableNumberIntent);
        displayitems();
    }

    public void displayitems() {
        String[] projection = {MenuItemsEntry.COLUMN_ITEM_NAME,MenuItemsEntry.COLUMN_ITEM_PRICE };
        String selection = MenuItemsEntry.COLUMN_ITEM_CATEGORY + "=?";
        String[] selectionArgs = new String[] {"Beverages"};

        Cursor cursor = getContentResolver().query(MenuItemsEntry.CONTENT_URI,projection,selection,selectionArgs,null,null);
        try {
            TextView tableNumber = findViewById(R.id.TableNumberTv);
            tableNumber.setText("Number of rows in menu_items table: " + cursor.getCount());
        } finally {
            cursor.close();
        }
    }
}
