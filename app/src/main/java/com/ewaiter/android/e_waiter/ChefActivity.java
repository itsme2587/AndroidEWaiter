package com.ewaiter.android.e_waiter;

import android.content.Context;
import android.nfc.Tag;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import com.ewaiter.android.e_waiter.Adapter.OrderAdapter;
import com.ewaiter.android.e_waiter.Models.Order;
import com.ewaiter.android.e_waiter.Models.OrderItem;
import com.ewaiter.android.e_waiter.Models.OrderItemHolder;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChefActivity extends AppCompatActivity {

    static List<OrderItemHolder> listOfOrders;

    private ListView mOrderListView;
    public OrderAdapter mOrderAdapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mOrderDatabaseReference;
    private ValueEventListener mValueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_list);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mOrderDatabaseReference = mFirebaseDatabase.getReference().child("orders");

        mOrderListView = (ListView) findViewById(R.id.orderListView);

        // Initialize message ListView and its adapter
        final List<Order> orders = new ArrayList<>();
        mOrderAdapter = new OrderAdapter(this, R.layout.order_list_item, orders);
        mOrderListView.setAdapter(mOrderAdapter);

        attachDatabaseReadListener();

    }


    public void attachDatabaseReadListener() {
        if(mValueEventListener == null)
            mValueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    listOfOrders = new ArrayList<>();
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        boolean status = false;
                        Iterable<DataSnapshot> items = postSnapshot.getChildren();
                        List<OrderItem> orderItems = new ArrayList<>();
                        for (DataSnapshot order: items) {
                            FirebaseCursorPojo o = order.getValue(FirebaseCursorPojo.class);
                            status = o.isOrderStatus();
                            if(status) {
                                OrderItem orderItem = new OrderItem(o.getItemName(),o.getQuantity()+"");
                                orderItems.add(orderItem);
                            }
                        }
                        if(status) {
                            listOfOrders.add(new OrderItemHolder(orderItems));
                            mOrderAdapter.add(new Order(status,postSnapshot.getKey()));
                        }
                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
        mOrderDatabaseReference.addValueEventListener(mValueEventListener);
    }


    public void detachDatabaseReadListener() {
        if(mValueEventListener != null) {
            mOrderDatabaseReference.removeEventListener(mValueEventListener);
            mValueEventListener = null;
        }
    }

}
