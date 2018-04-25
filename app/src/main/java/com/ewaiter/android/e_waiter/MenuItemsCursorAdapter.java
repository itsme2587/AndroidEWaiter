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
 * Created by Ayush on 25-04-2018.
 */

public class MenuItemsCursorAdapter extends RecyclerView.Adapter<MenuItemsCursorAdapter.MenuItemsViewHolder> {

    private Context context;
    private Cursor cursor;

    public MenuItemsCursorAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public MenuItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.individual_menu_item,parent,false);
        return new MenuItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MenuItemsViewHolder holder, int position) {
        if(!cursor.moveToPosition(position)) {
            return;
        }

        String name = cursor.getString(cursor.getColumnIndex(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_NAME));

        holder.nameText.setText(name);
        holder.quantityText.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_QUANTITY))));

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class MenuItemsViewHolder extends RecyclerView.ViewHolder {

        public TextView nameText;
        public TextView quantityText;
        public Button addBtn;
        public Button subBtn;
        public int quantity = 0;


        public MenuItemsViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.item_nameTv);
            quantityText = itemView.findViewById(R.id.quantityTv);
            addBtn = itemView.findViewById(R.id.addBtn);
            subBtn = itemView.findViewById(R.id.subBtn);

            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    quantity = quantity + 1;
                    quantityText.setText(String.valueOf(quantity));
                    Uri currentItemUri = ContentUris.withAppendedId(MenuItemsContract.MenuItemsEntry.CONTENT_URI, position + 1);
                    ContentValues values = new ContentValues();
                    values.put(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_QUANTITY,quantity);
                    context.getContentResolver().update(currentItemUri,values,null,null);
                }
            });

            subBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(quantity == 0) {
                        Toast.makeText(context,"Quantity Cannot Be Negetive",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        int position = getAdapterPosition();
                        quantity = quantity - 1;
                        quantityText.setText(String.valueOf(quantity));
                        Uri currentItemUri = ContentUris.withAppendedId(MenuItemsContract.MenuItemsEntry.CONTENT_URI, position + 1);
                        ContentValues values = new ContentValues();
                        values.put(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_QUANTITY,quantity);
                        context.getContentResolver().update(currentItemUri,values,null,null);
                    }
                }
            });
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
