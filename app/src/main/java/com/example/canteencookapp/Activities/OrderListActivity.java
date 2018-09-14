package com.example.canteencookapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteencookapp.Adapter.OrderListAdapter;
import com.example.canteencookapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {

    TextView ordersHeadingTextView;
    ListView ordersListView;
    OrderListAdapter orderListAdapter;

    DatabaseReference root;

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

        ordersHeadingTextView.setText(ordersHeadingTextView.getText().toString() + data.getStringExtra("Category"));

        root = FirebaseDatabase.getInstance().getReference().child("Order");
        root.keepSynced(true);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderTime.clear();
                orderID.clear();
                orderRollNo.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    if (dsp.child("Items").child(CATEGORY).exists()) {
                        orderID.add(dsp.getKey());
                        orderRollNo.add(dsp.child("Roll No").getValue().toString());
                        orderTime.add(dsp.child("Time to deliver").getValue().toString());
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
                Intent fullOrder = new Intent(OrderListActivity.this, FullOrderDetailActivity.class);
                fullOrder.putExtra("OrderID", orderID.get(position));
                fullOrder.putExtra("Category",CATEGORY);
                startActivity(fullOrder);
            }
        });


    }
}
