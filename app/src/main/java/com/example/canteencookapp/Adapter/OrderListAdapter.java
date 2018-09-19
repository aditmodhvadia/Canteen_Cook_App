package com.example.canteencookapp.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.canteencookapp.R;

import java.util.ArrayList;

public class OrderListAdapter extends BaseAdapter {

    ArrayList<String> orderID, orderTime, orderRollNo;
    Context context;
    LayoutInflater inflater;

    TextView orderTimeTextView, orderIDTextView, orderRollNoTextView;

    public OrderListAdapter(ArrayList<String> orderID, ArrayList<String> orderTime, ArrayList<String> orderRollNo, Context context) {
        this.orderID = orderID;
        this.orderTime = orderTime;
        this.orderRollNo = orderRollNo;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return orderID.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = inflater.inflate(R.layout.order_list_display_custom_layout,null);

        orderTimeTextView = v.findViewById(R.id.orderTimeTextView);
        orderIDTextView = v.findViewById(R.id.orderIDTextView);
        orderRollNoTextView = v.findViewById(R.id.orderRollNoTextView);

        orderIDTextView.setText(orderID.get(position));
        orderRollNoTextView.setText(orderRollNo.get(position));
        orderTimeTextView.setText(orderTime.get(position));

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in);
        animation.setStartOffset(position * 10);
        v.startAnimation(animation);

        return v;
    }
}
