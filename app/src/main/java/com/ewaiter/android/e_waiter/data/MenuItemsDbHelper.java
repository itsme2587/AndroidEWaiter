package com.ewaiter.android.e_waiter.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.ewaiter.android.e_waiter.data.MenuItemsContract.MenuItemsEntry;

/**
 * Created by Ayush on 24-04-2018.
 */

public class MenuItemsDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ewaiter.db";
    private static final int DATABASE_VERSION = 1;

    public MenuItemsDbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    //when database is created for the first time.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_MENU_ITEMS_TABLE = "CREATE TABLE " + MenuItemsEntry.TABLE_NAME + "("
                + MenuItemsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MenuItemsEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                + MenuItemsEntry.COLUMN_ITEM_CATEGORY + " TEXT NOT NULL, "
                + MenuItemsEntry.COLUMN_ITEM_PRICE + " INTEGER NOT NULL, "
                + MenuItemsEntry.COLUMN_ITEM_QUANTITY + " INTEGER NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_MENU_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
