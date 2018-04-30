package com.ewaiter.android.e_waiter;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ewaiter.android.e_waiter.data.MenuItemsContract;
import com.ewaiter.android.e_waiter.data.MenuItemsDbHelper;
import com.ewaiter.android.e_waiter.data.MenuItemsContract.MenuItemsEntry;

public class Beverages extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int BEVERAGES_LOADER = 1;
    MenuItemsCursorAdapter mAdapter;

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

        RecyclerView recyclerView = findViewById(R.id.recycler_view_menu_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MenuItemsCursorAdapter(this,getItems());
        recyclerView.setAdapter(mAdapter);

        getLoaderManager().initLoader(BEVERAGES_LOADER,null,this);
    }

    private Cursor getItems() {
        String[] projection = {MenuItemsEntry.COLUMN_ITEM_NAME,MenuItemsEntry.COLUMN_ITEM_QUANTITY,MenuItemsEntry._ID};
        String selection = MenuItemsEntry.COLUMN_ITEM_CATEGORY + "=?";
        String[] selectionArgs = new String[] {"Beverages"};

        Cursor cursor = getContentResolver().query(MenuItemsEntry.CONTENT_URI,projection,selection,selectionArgs,null,null);
        return cursor;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {MenuItemsEntry.COLUMN_ITEM_NAME,MenuItemsEntry.COLUMN_ITEM_QUANTITY, MenuItemsContract.MenuItemsEntry._ID };
        String selection = MenuItemsEntry.COLUMN_ITEM_CATEGORY + "=?";
        String[] selectionArgs = new String[] {"Beverages"};
        return new CursorLoader(this,MenuItemsEntry.CONTENT_URI,projection,selection,selectionArgs,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        //update with new cursor containing updated data
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
