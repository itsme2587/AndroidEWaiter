package com.ewaiter.android.e_waiter;

import android.app.Activity;

/**
 * Created by Amam on 04-04-2018.
 */

public class MenuCategory {

    private int categoryImage;
    private String categoryName;
    private String categoryOnClick;

    public MenuCategory(int categoryImage, String categoryName, String categoryOnClick) {
        this.categoryImage = categoryImage;
        this.categoryName = categoryName;
        this.categoryOnClick = categoryOnClick;
    }

    public int getCategoryImage() {
        return categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryOnClick() {
        return categoryOnClick;
    }
}
