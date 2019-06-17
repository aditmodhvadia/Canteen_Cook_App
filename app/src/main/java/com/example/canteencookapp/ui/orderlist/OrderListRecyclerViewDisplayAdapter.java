package com.example.canteencookapp.ui.orderlist;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.canteencookapp.R;
import com.example.canteencookapp.ui.orderdetail.OrderDetailActivity;
import com.fazemeright.canteen_app_models.models.FullOrder;

import java.util.ArrayList;

public class OrderListRecyclerViewDisplayAdapter extends RecyclerView.Adapter<OrderListRecyclerViewDisplayAdapter.ViewHolder> {

    private ArrayList<FullOrder> orderListItems;
    private Context context;

    public OrderListRecyclerViewDisplayAdapter(ArrayList<FullOrder> orderListItems, Context context) {
        this.orderListItems = orderListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderListRecyclerViewDisplayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_display_custom_layout, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final OrderListRecyclerViewDisplayAdapter.ViewHolder holder, final int position) {


//        Set text for all the TextViews
        if (orderListItems.get(holder.getAdapterPosition()).getDisplayID() != null) {
            holder.orderIDTextView.setText(String.format("Order ID %s", orderListItems.get(holder.getAdapterPosition()).getDisplayID()));
        } else {
            holder.orderIDTextView.setText(String.format("Order ID %s", orderListItems.get(holder.getAdapterPosition()).getOrderId()));
        }
        holder.orderRollNoTextView.setText(orderListItems.get(holder.getAdapterPosition()).getRollNo());
        holder.orderTimeTextView.setText(orderListItems.get(holder.getAdapterPosition()).getTimeToDeliver());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                Open full order detail activity by passing the order id, category, time and rollno
                Intent fullOrder = new Intent(context, OrderDetailActivity.class);
                fullOrder.putExtra("OrderData", orderListItems.get(holder.getAdapterPosition()));
                context.startActivity(fullOrder);

            }
        });
    }

    @Override
    public int getItemCount() {
        return orderListItems.size();
    }

    public Context getContext() {
        return context;
    }

    public void updateOrderList(ArrayList<FullOrder> orderList) {
        this.orderListItems = orderList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView orderTimeTextView, orderIDTextView, orderRollNoTextView;

        ViewHolder(View itemView) {
            super(itemView);
            orderTimeTextView = itemView.findViewById(R.id.orderTimeTextView);
            orderIDTextView = itemView.findViewById(R.id.orderIDTextView);
            orderRollNoTextView = itemView.findViewById(R.id.orderRollNoTextView);
        }
    }
}
