package com.ewaiter.android.e_waiter.Models;

/**
 * Created by Ayush on 25-04-2018.
 */

public class FirebaseCursorPojo {

    private String itemName;
    private String itemCategory;
    private int itemUnitPrice;
    private int quantity;
    private boolean orderStatus;
    private String table;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public FirebaseCursorPojo(String itemName, String itemCategory, int itemUnitPrice, int quantity, boolean orderStatus, String table) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemUnitPrice = itemUnitPrice;
        this.quantity = quantity;
        this.orderStatus = orderStatus;
        this.table = table;
    }

    public FirebaseCursorPojo() {
    }

    public FirebaseCursorPojo(String itemName, String itemCategory, int itemUnitPrice, int quantity) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemUnitPrice = itemUnitPrice;
        this.quantity = quantity;
        orderStatus = true;
    }

    public FirebaseCursorPojo(String itemName, String itemCategory, int itemUnitPrice, int quantity, String table) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemUnitPrice = itemUnitPrice;
        this.quantity = quantity;
        orderStatus = true;
        this.table = table;
    }

    public boolean isOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public int getItemUnitPrice() {
        return itemUnitPrice;
    }

    public void setItemUnitPrice(int itemUnitPrice) {
        this.itemUnitPrice = itemUnitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
