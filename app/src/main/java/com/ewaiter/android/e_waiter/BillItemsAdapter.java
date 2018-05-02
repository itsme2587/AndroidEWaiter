package com.ewaiter.android.e_waiter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ayush on 01-05-2018.
 */

public class BillItemsAdapter extends RecyclerView.Adapter<BillItemsAdapter.BillItemViewHolder> {

    Context c;
    ArrayList<BillItem> billItems;

    public BillItemsAdapter(Context c, ArrayList<BillItem> billItems) {
        this.c = c;
        this.billItems = billItems;
    }

    @Override
    public BillItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.bill_item_individual,parent,false);
        return new BillItemsAdapter.BillItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BillItemViewHolder holder, int position) {
        BillItem data = billItems.get(position);
        holder.nameText.setText(data.getItemName());
        holder.categoryText.setText("(" + data.getItemCategory() + ")");
        holder.quantityText.setText(data.getItemQuantity());
        holder.priceText.setText("(" + data.getItemPrice());
        int total = Integer.parseInt(data.getItemQuantity()) * Integer.parseInt(data.getItemPrice());
        holder.totalText.setText("Rs." + String.valueOf(total));
    }

    @Override
    public int getItemCount() {
        return billItems.size();
    }

    public class BillItemViewHolder extends RecyclerView.ViewHolder {

        public TextView nameText;
        public TextView quantityText;
        public TextView priceText;
        public TextView categoryText;
        public TextView totalText;

        public BillItemViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.bill_item_name);
            quantityText = itemView.findViewById(R.id.bill_item_quantity);
            priceText = itemView.findViewById(R.id.bill_item_price);
            categoryText = itemView.findViewById(R.id.bill_item_category);
            totalText = itemView.findViewById(R.id.bill_item_total);
        }
    }
}
