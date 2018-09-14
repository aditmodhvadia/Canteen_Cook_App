package com.example.canteencookapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.canteencookapp.Adapter.FullOrderDisplayAdapter;
import com.example.canteencookapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FullOrderDetailActivity extends AppCompatActivity {

//    Views
    ListView fullOrderListView;

//    Variables
    String CATEGORY, ORDER_ID, ORDER_TIME, ROLL_NO;
    ArrayList<String> orderItemName, orderItemQuantity;
    FullOrderDisplayAdapter fullOrderDisplayAdapter;
    DatabaseReference orderRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_order_detail);

        orderItemName = new ArrayList<>();
        orderItemQuantity = new ArrayList<>();
        fullOrderListView = findViewById(R.id.fullOrderListView);

        Intent data = getIntent();

        CATEGORY = data.getStringExtra("Category");
        ORDER_ID = data.getStringExtra("OrderID");
        ORDER_TIME = data.getStringExtra("Time");
        ROLL_NO = data.getStringExtra("RollNo");

        orderRoot = FirebaseDatabase.getInstance().getReference().child("Order").child(ORDER_ID).child("Items").child(CATEGORY);

        orderRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                orderItemName.clear();
////                orderItemQuantity.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()){
                    if(!orderItemName.contains(dsp.getKey())){
                        orderItemName.add(dsp.getKey());
                        orderItemQuantity.add(dsp.child("Quantity").getValue().toString());
                    }
                }
                fullOrderDisplayAdapter = new FullOrderDisplayAdapter(orderItemName, orderItemQuantity, getApplicationContext());
                fullOrderListView.setAdapter(fullOrderDisplayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        orderItemName.add("Testing 1");
//        orderItemName.add("Testing 2");
//        orderItemName.add("Testing 3");
//        orderItemQuantity.add("1");
//        orderItemQuantity.add("2");
//        orderItemQuantity.add("3");

    }
}
