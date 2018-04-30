package com.ewaiter.android.e_waiter.Models;

/**
 * Created by ABHIRAJ on 29-04-2018.
 */

public class OrderItem {
    private String itemName;
    private String itemQuantity;

    public OrderItem() {
    }

    public OrderItem(String itemName, String itemQuantity) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
