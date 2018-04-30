package com.ewaiter.android.e_waiter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.ewaiter.android.e_waiter.Adapter.OrderAdapter;
import com.ewaiter.android.e_waiter.Adapter.OrderDetailsAdapter;
import com.ewaiter.android.e_waiter.Models.Order;
import com.ewaiter.android.e_waiter.Models.OrderItem;
import com.ewaiter.android.e_waiter.Models.OrderItemHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderDetails extends AppCompatActivity {

    List<OrderItemHolder> listOfOrders;
    private String key;

    private ListView mOrderDetailsListView;
    private OrderDetailsAdapter mOrderDetailsAdapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mOrderDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details_list);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);
        key = intent.getStringExtra("key");

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mOrderDatabaseReference = mFirebaseDatabase.getReference().child("orders");

        listOfOrders = ChefActivity.listOfOrders;

        mOrderDetailsListView = (ListView) findViewById(R.id.orderDetailsListView);

        // Initialize message ListView and its adapter
        final List<OrderItem> orderItems;
        orderItems = listOfOrders.get(position).getOrderItemsList();

        mOrderDetailsAdapter = new OrderDetailsAdapter(this, R.layout.order_details_list_item, orderItems);
        mOrderDetailsListView.setAdapter(mOrderDetailsAdapter);
    }
    public void closeOrder(View view){
        mOrderDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshotChild: dataSnapshot.getChildren()) {
                    if(dataSnapshotChild.getKey().equals(key)) {
                        Iterable<DataSnapshot> items = dataSnapshotChild.getChildren();
                        for (DataSnapshot order: items) {
                            order.getRef().child("orderStatus").setValue(false);
                        }
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Intent i = new Intent(this,ChefActivity.class);
        finish();
        startActivity(i);
    }
}
