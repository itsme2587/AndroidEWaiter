package com.ewaiter.android.e_waiter;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.nfc.Tag;
import android.provider.Settings;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ewaiter.android.e_waiter.Models.FirebaseCursorPojo;
import com.ewaiter.android.e_waiter.Models.OrderSummary;
import com.ewaiter.android.e_waiter.data.MenuItemsContract;
import com.google.android.gms.tasks.OnSuccessListener;
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
    ArrayList<FirebaseCursorPojo> cursorPojoList = new ArrayList<>();

    String TABLE_NUMBER;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        TABLE_NUMBER = b.getString("tableNumber");

        mProgressBar = findViewById(R.id.progressBar);

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

//            MenuItemsDbHelper dbHelper = new MenuItemsDbHelper(this);
//            SQLiteDatabase db = dbHelper.getReadableDatabase();
//            Cursor cursorSum = db.rawQuery("SELECT SUM(" + MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_PRICE + "*" + MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_QUANTITY + ") as Total FROM " + MenuItemsContract.MenuItemsEntry.TABLE_NAME, null);
//            if (cursorSum.moveToFirst()) {
//                int total = cursorSum.getInt(cursorSum.getColumnIndex("Total"));
//                totalSum.setText("Rs." + String.valueOf(total));
//            }

            Cursor cursor = getItems();

            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_NAME));
                String category = cursor.getString(cursor.getColumnIndex(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_CATEGORY));
                int quantity = cursor.getInt(cursor.getColumnIndex(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_QUANTITY));
                int unitPrice = cursor.getInt(cursor.getColumnIndex(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_PRICE));
                cursorPojoList.add(new FirebaseCursorPojo(name,category,unitPrice,quantity,TABLE_NUMBER));
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
//                mProgressBar.setVisibility(View.VISIBLE);
                orderSaveToSharedPreferance();
                // Save cart list to database
                mItemDatabaseReference = mFirebaseDatabase.getReference().child("orders").push();
                ArrayList<FirebaseCursorPojo> items = getArrayList();
                for(FirebaseCursorPojo tempItem : items) {
                    mItemDatabaseReference.push().setValue(tempItem);
                }
                Toast.makeText(this,"Order details sent to Chef",Toast.LENGTH_SHORT).show();
//                mProgressBar.setVisibility(View.INVISIBLE);
                resetQuantity();
              //  Intent br1 = new Intent("finish TableSelectActivity");
              //  sendBroadcast(br1);
                Intent br1 = new Intent("finish MenuCategorySelectActivity");
                sendBroadcast(br1);
              //  Intent intent = new Intent(this,TablesSelectActivity.class);
              //  startActivity(intent);
                finish();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case R.id.action_generate_bill:
                Intent intentBill = new Intent(Cart.this, OrderTotalCheckOut.class);
                intentBill.putExtra("tableNumber",TABLE_NUMBER);
                startActivity(intentBill);
                finish();
                return true;

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

    public void orderSaveToSharedPreferance() {

        SharedPreferences sharedPreference = getSharedPreferences(TABLE_NUMBER, Context.MODE_PRIVATE);
        String completeOrder = sharedPreference.getString("Order_Details","");
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString("Order_Details",completeOrder + proceedOrderString());
        editor.commit();
    }

    public String proceedOrderString() {
        String orderString = "";
        Cursor cursor = getItems();
        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_NAME));
            String quantity = String.valueOf(cursor.getInt(cursor.getColumnIndex(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_QUANTITY)));
            String category = cursor.getString(cursor.getColumnIndex(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_CATEGORY));
            String price = String.valueOf(cursor.getInt(cursor.getColumnIndex(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_PRICE)));
            orderString = (orderString + name + "," + quantity + "," +  category + "," + price + "/");
        }
        return orderString;
    }
}
