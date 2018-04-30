package com.ewaiter.android.e_waiter.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Ayush on 24-04-2018.
 */

public class MenuItemProvider extends ContentProvider {

    private static final  int MENUITEMS = 100;
    private static final  int MENUITEMS_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    MenuItemsDbHelper mDbHelper;

    static {
        sUriMatcher.addURI(MenuItemsContract.CONTENT_AUTHORITY,MenuItemsContract.PATH_MENU_ITEMS,MENUITEMS);
        sUriMatcher.addURI(MenuItemsContract.CONTENT_AUTHORITY,MenuItemsContract.PATH_MENU_ITEMS + "/#",MENUITEMS_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new MenuItemsDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case MENUITEMS:
                cursor = database.query(MenuItemsContract.MenuItemsEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case MENUITEMS_ID:
                selection = MenuItemsContract.MenuItemsEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(MenuItemsContract.MenuItemsEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        //cursor.setNotificationUri(getContext().getContentResolver(),uri);
        //if the data in the uri changes this updates the cursor.
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case MENUITEMS:
                return MenuItemsContract.MenuItemsEntry.CONTENT_LIST_TYPE;
            case MENUITEMS_ID:
                return MenuItemsContract.MenuItemsEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        String name = values.getAsString(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_NAME);
        int price = values.getAsInteger(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_PRICE);
        if(name == null) {
            throw new IllegalArgumentException("Menu Item Requires a Name");
        }
        if(price < 0 ) {
            throw new IllegalArgumentException("Price cannot be negetive");
        }

        int match = sUriMatcher.match(uri);

        switch (match) {
            case MENUITEMS:
                SQLiteDatabase database = mDbHelper.getReadableDatabase();
                long id = database.insert(MenuItemsContract.MenuItemsEntry.TABLE_NAME,null,values);
                if(id == -1) {
                    Log.e(MenuItemProvider.class.getSimpleName(),"Failed to Insert row for uri " + uri);
                    return null;
                }
                //notify all listener that the data has changed for the pet content uri.
                //query wale urinotificationlistener ko bta dega ki data change hua h.
                //getContext().getContentResolver().notifyChange(uri,null);
                return ContentUris.withAppendedId(uri,id);
            default:
                throw new IllegalArgumentException("Cannot insert unknown URI " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        switch (match) {
            case MENUITEMS:
                //notify all listener that the data has changed for the pet content uri.
                //query wale urinotificationlistener ko bta dega ki data change hua h.
             //   getContext().getContentResolver().notifyChange(uri,null);
                return database.delete(MenuItemsContract.MenuItemsEntry.TABLE_NAME,selection,selectionArgs);
            case MENUITEMS_ID:
                selection = MenuItemsContract.MenuItemsEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                //notify all listener that the data has changed for the pet content uri.
                //query wale urinotificationlistener ko bta dega ki data change hua h.
               // getContext().getContentResolver().notifyChange(uri,null);
                return database.delete(MenuItemsContract.MenuItemsEntry.TABLE_NAME,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Cannot delete unknown URI " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
//        if(values.size() == 0) {
//            return 0;
//        }
//        String name = values.getAsString(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_NAME);
//        int price = values.getAsInteger(MenuItemsContract.MenuItemsEntry.COLUMN_ITEM_PRICE);
//        if(name == null) {
//            throw new IllegalArgumentException("Menu Item Requires a Name");
//        }
//        if(price < 0 ) {
//            throw new IllegalArgumentException("Price cannot be negetive");
//        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        switch (match) {
            case MENUITEMS:
               // getContext().getContentResolver().notifyChange(uri,null);
                return database.update(MenuItemsContract.MenuItemsEntry.TABLE_NAME,values,selection,selectionArgs);
            case MENUITEMS_ID:
                selection = MenuItemsContract.MenuItemsEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
               // getContext().getContentResolver().notifyChange(uri,null);
                return database.update(MenuItemsContract.MenuItemsEntry.TABLE_NAME,values,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Cannot update unknown URI " + uri);
        }
    }
}
