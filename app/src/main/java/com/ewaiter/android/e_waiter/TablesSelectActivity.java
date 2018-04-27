package com.ewaiter.android.e_waiter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class TablesSelectActivity extends AppCompatActivity {

    BroadcastReceiver broadcastReceiver;
    private Boolean exit = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TablesAdapter.flag = true;
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_select_activity);

        if(getIntent().getBooleanExtra("EXIT",false)) {
            finish();
        }

        ArrayList<String> tableNumbers = new ArrayList<>();

        for(int i = 1 ; i <= 12 ; i++) {
            tableNumbers.add("Table " + i);
        }

        RecyclerView rv = findViewById(R.id.recycler_view_table);
        rv.setLayoutManager(new GridLayoutManager(this,2));
        rv.setAdapter(new TablesAdapter(this,tableNumbers));

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(action.equals("finish TableSelectActivity")) {
                    finish();
                }
            }
        };
        registerReceiver(broadcastReceiver,new IntentFilter("finish TableSelectActivity"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_table_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this, AddItem.class);
                startActivity(intent);
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case R.id.action_refresh:
                Intent intentRestart = getIntent();
                finish();
                startActivity(intentRestart);
                TablesAdapter.flag = true;
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        if(exit) {
            finish();
        }
        else {
            Toast.makeText(this,"Press Back again to Exit.",Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }
}
