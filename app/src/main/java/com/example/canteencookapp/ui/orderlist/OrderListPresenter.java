package com.example.canteencookapp.ui.orderlist;

import android.support.annotation.NonNull;

import com.example.canteencookapp.ui.base.BasePresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderListPresenter<V extends OrderListMvpView> extends BasePresenter<V> implements OrderListMvpPresenter<V> {


    public OrderListPresenter() {
    }

    @Override
    public void fetchOrderList(final String category) {
        DatabaseReference root = FirebaseDatabase.getInstance().getReference();


        root.child("Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> orderID, orderTime, orderRollNo;

                orderID = new ArrayList<>();
                orderTime = new ArrayList<>();
                orderRollNo = new ArrayList<>();

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    if (dsp.child("Items").child(category).exists()) {
                        orderID.add(dsp.getKey());
                        orderTime.add(dsp.child("Time to deliver").getValue().toString());
                        orderRollNo.add(dsp.child("Roll No").getValue().toString());
                    }
                }
                getMvpView().onFetchingOrderListSuccessful(orderID, orderRollNo, orderTime);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                getMvpView().onFetchingOrderListFailed(databaseError.getMessage());
            }
        });

    }
}
