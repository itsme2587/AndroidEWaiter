package com.ewaiter.android.e_waiter;

/**
 * Created by Ayush on 02-05-2018.
 */

public class Table {

    private String tableNumber;
    private boolean status;

    public Table(String tableNumber, boolean status) {
        this.tableNumber = tableNumber;
        this.status = status;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
