package com.ewaiter.android.e_waiter;

/**
 * Created by Ayush on 25-04-2018.
 */

public class FirebaseCursorPojo {

    private String itemName;
    private String itemCategory;
    private int itemUnitPrice;
    private int quantity;
    private boolean orderStatus;

    public FirebaseCursorPojo() {
    }

    public FirebaseCursorPojo(String itemName, String itemCategory, int itemUnitPrice, int quantity) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemUnitPrice = itemUnitPrice;
        this.quantity = quantity;
        orderStatus = true;
    }

    public FirebaseCursorPojo(String itemName, String itemCategory, int itemUnitPrice, int quantity, boolean orderStatus) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemUnitPrice = itemUnitPrice;
        this.quantity = quantity;
        this.orderStatus = orderStatus;
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
