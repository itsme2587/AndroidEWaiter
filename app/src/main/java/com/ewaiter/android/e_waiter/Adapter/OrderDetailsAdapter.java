package com.ewaiter.android.e_waiter.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ewaiter.android.e_waiter.ChefActivity;
import com.ewaiter.android.e_waiter.Models.Order;
import com.ewaiter.android.e_waiter.Models.OrderItem;
import com.ewaiter.android.e_waiter.OrderDetails;
import com.ewaiter.android.e_waiter.R;

import java.util.List;

/**
 * Created by ABHIRAJ on 29-04-2018.
 */

public class OrderDetailsAdapter extends ArrayAdapter<OrderItem> {

    private Context context;

    public OrderDetailsAdapter(Context context, int resource, List<OrderItem> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.order_details_list_item, parent, false);
        }

        TextView itemNameTextView = (TextView) convertView.findViewById(R.id.item_name);
        TextView itemQuantityTextView = (TextView) convertView.findViewById(R.id.item_quantity);

        OrderItem orderItem = getItem(position);

        itemNameTextView.setText(orderItem.getItemName());
        itemQuantityTextView.setText(orderItem.getItemQuantity());

        return convertView;
    }
}

