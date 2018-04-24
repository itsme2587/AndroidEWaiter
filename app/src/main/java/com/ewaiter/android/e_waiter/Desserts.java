package com.ewaiter.android.e_waiter;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ewaiter.android.e_waiter.data.MenuItemsContract;

public class Desserts extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int DESSERTS_LOADER = 4;
    MenuItemsCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desserts);

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

        getLoaderManager().initLoader(DESSERTS_LOADER,null,this);

    }

    private Cursor getItems() {
        String[] projection = {MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_NAME, MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_QUANTITY, MenuItemsContract.MenuItemsEntry._ID };
        String selection = MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_CATEGORY + "=?";
        String[] selectionArgs = new String[] {"Desserts"};

        Cursor cursor = getContentResolver().query(MenuItemsContract.MenuItemsEntry.CONTENT_URI,projection,selection,selectionArgs,null,null);
        return cursor;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_NAME, MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_QUANTITY, MenuItemsContract.MenuItemsEntry._ID };
        String selection = MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_CATEGORY + "=?";
        String[] selectionArgs = new String[] {"Desserts"};
        return new CursorLoader(this, MenuItemsContract.MenuItemsEntry.CONTENT_URI,projection,selection,selectionArgs,null);
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
