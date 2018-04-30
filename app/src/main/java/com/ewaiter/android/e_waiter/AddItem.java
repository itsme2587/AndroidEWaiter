package com.ewaiter.android.e_waiter;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.ewaiter.android.e_waiter.data.MenuItemsContract.MenuItemsEntry;
import com.ewaiter.android.e_waiter.data.MenuItemsDbHelper;

import java.net.URI;


public class AddItem extends AppCompatActivity {

    EditText mNameEditText;
    EditText mPriceEditText;
    Spinner mCategoriesSpinner;
    String mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mNameEditText = findViewById(R.id.edit_food_name);
        mPriceEditText = findViewById(R.id.edit_food_price);
        mCategoriesSpinner = findViewById(R.id.spinner_categories);
        setupSpinner();
    }

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter foodSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_food_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        foodSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mCategoriesSpinner.setAdapter(foodSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mCategoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.food_beverages))) {
                        mCategory = "Beverages";
                    } else if (selection.equals(getString(R.string.food_breads))) {
                        mCategory = "Breads";
                    } else if (selection.equals(getString(R.string.food_desserts))) {
                        mCategory = "Desserts";
                    } else if (selection.equals(getString(R.string.food_main_course))) {
                        mCategory = "Main course";
                    } else if (selection.equals(getString(R.string.food_salads))) {
                        mCategory = "Salads";
                    } else if (selection.equals(getString(R.string.food_south_indian))) {
                        mCategory = "South Indian";
                    } else if (selection.equals(getString(R.string.food_starters))) {
                        mCategory = "Starters";
                    } else if (selection.equals(getString(R.string.food_chinese))) {
                        mCategory = "Chinese";
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCategory = "Starters";
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet to database
                insertMenuItem();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertMenuItem() {

        if(mNameEditText.getText().toString().equals("")|| mPriceEditText.getText().toString().equals("")) {
            Toast.makeText(this,"Fill all details to proceed",Toast.LENGTH_SHORT).show();
        }
        else {
            String nameString = mNameEditText.getText().toString().trim();
            String priceString = mPriceEditText.getText().toString().trim();
            int price = Integer.parseInt(priceString);
            // Create a ContentValues object where column names are the keys,
            // and pet attributes from the editor are the values.
            ContentValues values = new ContentValues();
            values.put(MenuItemsEntry.COLUMN_ITEM_NAME, nameString);
            values.put(MenuItemsEntry.COLUMN_ITEM_PRICE, price);
            values.put(MenuItemsEntry.COLUMN_ITEM_CATEGORY, mCategory);

            Uri newUri = getContentResolver().insert(MenuItemsEntry.CONTENT_URI,values);

            if(newUri == null) {
                Toast.makeText(this,"Error with saving item",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this,"Item saved",Toast.LENGTH_SHORT).show();
            }
            finish();

        }



    }
}
