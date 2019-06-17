package com.example.canteencookapp.ui.orderdetail;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.canteencookapp.R;
import com.fazemeright.canteen_app_models.models.FullOrder;

public class OrderDetailRecyclerViewDisplayAdapter extends RecyclerView.Adapter<OrderDetailRecyclerViewDisplayAdapter.ViewHolder> {

    private Context context;
    private FullOrder order;

    public OrderDetailRecyclerViewDisplayAdapter(FullOrder order, Context context) {
        this.order = order;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderDetailRecyclerViewDisplayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.full_order_display_custom_layout, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final OrderDetailRecyclerViewDisplayAdapter.ViewHolder holder, final int position) {

        holder.orderItemNameTextView.setText(order.getOrderItems().get(holder.getAdapterPosition()).getItemName());
        holder.orderItemQuantityTextView.setText(String.valueOf(order.getOrderItems().get(holder.getAdapterPosition()).getItemQuantity()));
        holder.orderStatusTextView.setText(order.getOrderItems().get(position).getItemStatus());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Select Status of Item");
                builder.setMessage("Select the Status to display to the Customer");
//                todo: find better solution to neutral button if possible
                /*builder.setNeutralButton("Cooking", new DialogInterface.OnClickListener() {
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
                });*/

                AlertDialog dialog = builder.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        if (order != null && order.getOrderItems() != null) {
            return order.getOrderItems().size();
        } else {
            return 0;
        }
    }

    public Context getContext() {
        return context;
    }

    public void updateOrderList(FullOrder updatedOrder) {
        this.order = updatedOrder;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView orderItemNameTextView, orderItemQuantityTextView, orderStatusTextView;

        ViewHolder(View itemView) {
            super(itemView);
            orderItemNameTextView = itemView.findViewById(R.id.orderItemNameTextView);
            orderItemQuantityTextView = itemView.findViewById(R.id.orderItemQuantityTextView);
            orderStatusTextView = itemView.findViewById(R.id.orderStatusTextView);
        }
    }
}
