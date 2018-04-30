package com.ewaiter.android.e_waiter.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABHIRAJ on 29-04-2018.
 */

public class OrderItemHolder {
    private List<OrderItem> orderItems;

    public OrderItemHolder() {
    }

    public OrderItemHolder(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
    }

    public List<OrderItem> getOrderItemsList() {
        return orderItems;
    }
}
