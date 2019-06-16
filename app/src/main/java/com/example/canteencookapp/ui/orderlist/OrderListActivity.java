package com.example.canteencookapp.ui.orderlist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteencookapp.ui.orderdetail.FullOrderDetailActivity;
import com.example.canteencookapp.R;
import com.example.canteencookapp.Service.MyNotificationService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {
    public static Intent service;
    public static boolean flag = false;
    //    Views
    TextView ordersHeadingTextView;
    ListView ordersListView;
    OrderListAdapter orderListAdapter;
    //    Firebase Variables
    DatabaseReference root;
    //    Variables
    ArrayList<String> orderID, orderTime, orderRollNo;
    String CATEGORY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        ordersHeadingTextView = findViewById(R.id.ordersHeadingTextView);
        ordersListView = findViewById(R.id.ordersListView);

        orderID = new ArrayList<>();
        orderTime = new ArrayList<>();
        orderRollNo = new ArrayList<>();

        Intent data = getIntent();
        CATEGORY = data.getStringExtra("Category");

        service = new Intent(this, MyNotificationService.class);

        ordersHeadingTextView.setText(ordersHeadingTextView.getText().toString() + data.getStringExtra("Category"));

        root = FirebaseDatabase.getInstance().getReference();
//        root.keepSynced(true);

        root.child("Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                orderTime.clear();
                orderID.clear();
                orderRollNo.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    if (dsp.child("Items").child(CATEGORY).exists()) {
                        orderID.add(dsp.getKey());
                        orderTime.add(dsp.child("Time to deliver").getValue().toString());
                        orderRollNo.add(dsp.child("Roll No").getValue().toString());
                    }
                }
                orderListAdapter = new OrderListAdapter(orderID, orderTime, orderRollNo, getApplicationContext());
                ordersListView.setAdapter(orderListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(OrderListActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        ordersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Open full order detail activity by passing the order id, category, time and rollno
                Intent fullOrder = new Intent(OrderListActivity.this, FullOrderDetailActivity.class);
                fullOrder.putExtra("OrderID", orderID.get(position));
                fullOrder.putExtra("Category", CATEGORY);
                fullOrder.putExtra("Time", orderTime.get(position));
                fullOrder.putExtra("RollNo", orderRollNo.get(position));
                startActivity(fullOrder);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Stop service when back pressed to open login activity
        flag = false;
        stopService(service);
        String myPREFERENCES = "Login Data";
        SharedPreferences.Editor editor = getSharedPreferences(myPREFERENCES, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Start Notification Service when activity is paused
        service.putExtra("Category", CATEGORY);
        flag = true;
        startService(service);
    }

    @Override
    protected void onResume() {
        super.onResume();
        flag = false;
        stopService(service);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
//        Stop notification service when Activity is loaded again
        flag = false;
        stopService(service);
    }
}
