package com.ewaiter.android.e_waiter.Models;

import java.util.List;

/**
 * Created by ABHIRAJ on 29-04-2018.
 */

public class Order {
    private List<OrderItem> orderItems;

    private boolean orderSatus;
    private String orderKey;
    private String table;

    public Order(List<OrderItem> orderItems, boolean orderSatus, String orderKey, String table) {
        this.orderItems = orderItems;
        this.orderSatus = orderSatus;
        this.orderKey = orderKey;
        this.table = table;
    }

    public Order(boolean orderStatus) {
        this.orderSatus = orderStatus;
    }

    public Order(boolean orderStatus, String orderKey, String table) {
        this.orderSatus = orderStatus;
        this.orderKey = orderKey;
        this.table = table;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public boolean isOrderSatus() {
        return orderSatus;
    }

    public String getOrderKey() {
        return orderKey;
    }
}
