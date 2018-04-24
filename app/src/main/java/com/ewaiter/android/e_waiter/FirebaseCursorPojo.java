package com.ewaiter.android.e_waiter;

/**
 * Created by Ayush on 25-04-2018.
 */

public class FirebaseCursorPojo {

    private String itemName;
    private String itemCategory;
    private int itemUnitPrice;
    private int quantity;


    public FirebaseCursorPojo(String itemName, String itemCategory, int itemUnitPrice, int quantity) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemUnitPrice = itemUnitPrice;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public int getItemUnitPrice() {
        return itemUnitPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}
