package com.ewaiter.android.e_waiter;

/**
 * Created by Ayush on 01-05-2018.
 */

public class BillItem {

    private String itemName;
    private String itemPrice;
    private String itemQuantity;
    private String itemCategory;

    public BillItem(String itemName, String itemPrice, String itemQuantity, String itemCategory) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.itemCategory = itemCategory;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public String getItemCategory() {
        return itemCategory;
    }
}
