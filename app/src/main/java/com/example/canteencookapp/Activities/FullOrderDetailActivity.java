package com.example.canteencookapp.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    ArrayList<String> orderItemName, orderItemQuantity, orderItemStatus;
    FullOrderDisplayAdapter fullOrderDisplayAdapter;
    DatabaseReference orderRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_order_detail);

        orderItemName = new ArrayList<>();
        orderItemQuantity = new ArrayList<>();
        orderItemStatus = new ArrayList<>();
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
                orderItemName.clear();
                orderItemQuantity.clear();
                orderItemStatus.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()){
                    if(!orderItemName.contains(dsp.getKey())){
                        orderItemName.add(dsp.getKey());
                        orderItemQuantity.add(dsp.child("Quantity").getValue().toString());
                        orderItemStatus.add(dsp.child("Status").getValue().toString());
                    }
                }
                fullOrderDisplayAdapter = new FullOrderDisplayAdapter(orderItemName, orderItemQuantity, orderItemStatus, getApplicationContext());
                fullOrderListView.setAdapter(fullOrderDisplayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fullOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FullOrderDetailActivity.this);
                builder.setTitle("Select Status of Item");
                builder.setMessage("Select the Status to display to the Customer");
//                todo: find better solution to neutral button if possible
                builder.setNeutralButton("Cooking", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        orderRoot.child(orderItemName.get(position)).child("Status").setValue("Cooking");
                    }
                });


                builder.setPositiveButton("Ready", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        orderRoot.child(orderItemName.get(position)).child("Status").setValue("Ready");
                    }
                });


                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.show();
            }
        });

    }
}
