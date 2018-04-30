package com.ewaiter.android.e_waiter.Models;

import java.util.List;

/**
 * Created by ABHIRAJ on 29-04-2018.
 */

public class Order {
    private List<OrderItem> orderItems;

    private boolean orderSatus;
    private String orderKey;

    public Order(boolean orderStatus) {
        this.orderSatus = orderStatus;
    }

    public Order(boolean orderStatus, String orderKey) {
        this.orderSatus = orderStatus;
        this.orderKey = orderKey;
    }

    public boolean isOrderSatus() {
        return orderSatus;
    }

    public String getOrderKey() {
        return orderKey;
    }
}
