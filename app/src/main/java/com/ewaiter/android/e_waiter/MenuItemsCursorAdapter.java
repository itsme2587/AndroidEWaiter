package com.ewaiter.android.e_waiter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public void onBindViewHolder(MenuItemsViewHolder holder, int position) {
        if(!cursor.moveToPosition(position)) {
            return;
        }

        String name = cursor.getString(cursor.getColumnIndex(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_NAME));
        int quantity = cursor.getInt(cursor.getColumnIndex(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_QUANTITY));

        holder.nameText.setText(name);
        holder.quantityText.setText(String.valueOf(quantity));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class MenuItemsViewHolder extends RecyclerView.ViewHolder {

        public TextView nameText;
        public TextView quantityText;

        public MenuItemsViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.item_nameTv);
            quantityText = itemView.findViewById(R.id.quantityTv);
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
