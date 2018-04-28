package com.ewaiter.android.e_waiter;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ewaiter.android.e_waiter.data.MenuItemsContract;
import com.ewaiter.android.e_waiter.data.MenuItemsDbHelper;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Cart extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mOrderDatabaseReference;
    private DatabaseReference mItemDatabaseReference;

    private ChildEventListener mChildEventListener;

    private static final int CART_ITEMS_LOADER = 100;
    CartItemsCursorAdapter mAdapter;
    TextView totalSum;
    ArrayList<FirebaseCursorPojo> cursorPojoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getItems().getCount() == 0) {
            setContentView(R.layout.empty_view);
        }

        else {
            setContentView(R.layout.activity_cart);
            //To get access to database
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mOrderDatabaseReference = mFirebaseDatabase.getReference().child("orders");

            RecyclerView recyclerView = findViewById(R.id.recyclerView_cart);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new CartItemsCursorAdapter(this, getItems());
            recyclerView.setAdapter(mAdapter);

            getLoaderManager().initLoader(CART_ITEMS_LOADER, null, this);

            totalSum = findViewById(R.id.cart_total);

            MenuItemsDbHelper dbHelper = new MenuItemsDbHelper(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursorSum = db.rawQuery("SELECT SUM(" + MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_PRICE + "*" + MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_QUANTITY + ") as Total FROM " + MenuItemsContract.MenuItemsEntry.TABLE_NAME, null);
            if (cursorSum.moveToFirst()) {
                int total = cursorSum.getInt(cursorSum.getColumnIndex("Total"));
                totalSum.setText("Rs." + String.valueOf(total));
            }

            Cursor cursor = getItems();

            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_NAME));
                String category = cursor.getString(cursor.getColumnIndex(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_CATEGORY));
                int quantity = cursor.getInt(cursor.getColumnIndex(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_QUANTITY));
                int unitPrice = cursor.getInt(cursor.getColumnIndex(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_PRICE));
                cursorPojoList.add(new FirebaseCursorPojo(name,category,unitPrice,quantity));
            }

        }
    }


    private Cursor getItems() {
        String[] projection = {MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_NAME, MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_QUANTITY, MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_PRICE, MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_CATEGORY};
        String selection = MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_QUANTITY + ">?";
        String[] selectionArgs = new String[] {"0"};

        Cursor cursor = getContentResolver().query(MenuItemsContract.MenuItemsEntry.CONTENT_URI,projection,selection,selectionArgs,null,null);
        return cursor;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_NAME, MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_QUANTITY, MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_PRICE, MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_CATEGORY};
        String selection = MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_QUANTITY + ">?";
        String[] selectionArgs = new String[] {"0"};
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_order_proceed, menu);
        if(getItems().getCount() == 0) {
            menu.findItem(R.id.action_proceed).setEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_proceed:
                // Save cart list to database
                mItemDatabaseReference = mFirebaseDatabase.getReference().child("orders").push();
                ArrayList<FirebaseCursorPojo> items = getArrayList();
                for(FirebaseCursorPojo tempItem : items) {
                    mItemDatabaseReference.push().setValue(tempItem);
                }
                Toast.makeText(this,"Order details sent to Chef",Toast.LENGTH_SHORT).show();
                resetQuantity();
                Intent br1 = new Intent("finish TableSelectActivity");
                sendBroadcast(br1);
                Intent br2 = new Intent("finish MenuCategorySelectActivity");
                sendBroadcast(br2);
                Intent intent = new Intent(this,TablesSelectActivity.class);
                startActivity(intent);
                finish();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<FirebaseCursorPojo> getArrayList() {
        return cursorPojoList;
    }

    public void resetQuantity() {
        ContentValues values = new ContentValues();
        values.put(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_QUANTITY, 0);
        this.getContentResolver().update(MenuItemsContract.MenuItemsEntry.CONTENT_URI,values,null,null);
    }


}
