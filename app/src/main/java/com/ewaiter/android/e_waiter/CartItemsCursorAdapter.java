package com.ewaiter.android.e_waiter;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ewaiter.android.e_waiter.data.MenuItemsContract;

/**
 * Created by Ayush on 27-04-2018.
 */

public class CartItemsCursorAdapter extends RecyclerView.Adapter<CartItemsCursorAdapter.CartItemsViewHolder> {

    private Context context;
    private Cursor cursor;

    public CartItemsCursorAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public CartItemsCursorAdapter.CartItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_item_individual,parent,false);
        return new CartItemsCursorAdapter.CartItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartItemsCursorAdapter.CartItemsViewHolder holder, int position) {
        if(!cursor.moveToPosition(position)) {
            return;
        }

        String name = cursor.getString(cursor.getColumnIndex(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_NAME));
        String category = cursor.getString(cursor.getColumnIndex(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_CATEGORY));
        int quantity = cursor.getInt(cursor.getColumnIndex(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_QUANTITY));
        int price = cursor.getInt(cursor.getColumnIndex(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_PRICE));

        holder.nameText.setText(name);
        holder.categoryText.setText("(" + category + ")");
        holder.quantityText.setText(String.valueOf(quantity));
        holder.priceText.setText("(" + String.valueOf(price));
        holder.totalText.setText(String.valueOf(price * quantity));

    }


    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class CartItemsViewHolder extends RecyclerView.ViewHolder {

        public TextView nameText;
        public TextView quantityText;
        public TextView priceText;
        public TextView categoryText;
        public TextView totalText;


        public CartItemsViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.cart_item_name);
            quantityText = itemView.findViewById(R.id.cart_item_quantity);
            priceText = itemView.findViewById(R.id.cart_item_price);
            categoryText = itemView.findViewById(R.id.cart_item_category);
            totalText = itemView.findViewById(R.id.cart_item_total);
        }
    }

    public void swapCursor(Cursor newCursor) {
        if(cursor != null) {
            cursor.close();
        }

        cursor = newCursor;

        if(newCursor != null) {
            notifyDataSetChanged();
        }
    }

}
