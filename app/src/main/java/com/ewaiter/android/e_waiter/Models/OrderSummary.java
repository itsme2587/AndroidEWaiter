package com.ewaiter.android.e_waiter.Models;

/**
 * Created by ABHIRAJ on 06-05-2018.
 */

public class OrderSummary {
    String date;
    String table;
    String waiterToken;
    String paymentMode;
    float amount;

    public OrderSummary(String date, String table, String waiterToken, String paymentMode, float amount) {
        this.date = date;
        this.table = table;
        this.waiterToken = waiterToken;
        this.paymentMode = paymentMode;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getWaiterToken() {
        return waiterToken;
    }

    public void setWaiterToken(String waiterToken) {
        this.waiterToken = waiterToken;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}

