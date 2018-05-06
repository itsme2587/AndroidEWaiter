package com.ewaiter.android.e_waiter.Models;

/**
 * Created by ABHIRAJ on 05-05-2018.
 */

public class Notifications {
    private String msg;
    private int table;
    private int waiter_id;

    public Notifications() {
        msg = "Order Served at table " + "0";
    }

    public Notifications(String msg) {
        this.msg = msg;
    }

    public String getMsg() {

        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public int getWaiter_id() {
        return waiter_id;
    }

    public void setWaiter_id(int waiter_id) {
        this.waiter_id = waiter_id;
    }

    public Notifications(String msg, int table, int waiter_id) {

        this.msg = msg;
        this.table = table;
        this.waiter_id = waiter_id;
    }
}
