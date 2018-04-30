package com.ewaiter.android.e_waiter.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ewaiter.android.e_waiter.Models.Order;
import com.ewaiter.android.e_waiter.OrderDetails;
import com.ewaiter.android.e_waiter.R;
import java.util.List;


public class OrderAdapter extends ArrayAdapter<Order> {
    private Context context;

    public OrderAdapter(Context context, int resource, List<Order> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.order_list_item, parent, false);
        }

        TextView orderStatusTextView = (TextView) convertView.findViewById(R.id.order_status);
        TextView orderIdTextView = (TextView) convertView.findViewById(R.id.order_id);
        LinearLayout orderListItemView = (LinearLayout) convertView.findViewById(R.id.order_list_item_view);

        final Order order = getItem(position);

        String orderStatus = null;
        if(order.isOrderSatus())
            orderStatus = "open";
        else
            orderStatus = "closed";

        orderStatusTextView.setText(orderStatus);
        orderIdTextView.setText(order.getOrderKey());

        orderListItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, OrderDetails.class);
                i.putExtra("position",position);
                i.putExtra("key",order.getOrderKey());
                context.startActivity(i);
            }
        });

        return convertView;
    }
}
