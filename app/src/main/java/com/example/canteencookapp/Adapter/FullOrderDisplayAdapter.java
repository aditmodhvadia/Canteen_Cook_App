package com.example.canteencookapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.canteencookapp.R;

import java.util.ArrayList;

public class FullOrderDisplayAdapter extends BaseAdapter {

    ArrayList<String> orderItemName, orderItemQuantity, orderItemStatus;
    Context context;
    LayoutInflater inflater;

    TextView orderItemNameTextView, orderItemQuantityTextView, orderStatusTextView;

    public FullOrderDisplayAdapter(ArrayList<String> orderItemName, ArrayList<String> orderItemQuantity, ArrayList<String> orderItemStatus, Context context) {
        this.orderItemName = orderItemName;
        this.orderItemQuantity = orderItemQuantity;
        this.orderItemStatus = orderItemStatus;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return orderItemName.size();
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

        View v = inflater.inflate(R.layout.full_order_display_custom_layout,null);

        orderItemNameTextView = v.findViewById(R.id.orderItemNameTextView);
        orderItemQuantityTextView = v.findViewById(R.id.orderItemQuantityTextView);
        orderStatusTextView = v.findViewById(R.id.orderStatusTextView);

        orderItemNameTextView.setText(orderItemName.get(position));
        orderItemQuantityTextView.setText(orderItemQuantity.get(position));
        orderStatusTextView.setText(orderItemStatus.get(position));

//        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
//        v.startAnimation(animation);

        return v;

    }
}
