package com.example.canteencookapp.ui.orderdetail;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.canteencookapp.R;
import com.example.canteencookapp.ui.base.BaseActivity;
import com.example.canteencookapp.utils.AlertUtils;
import com.example.canteencookapp.utils.DialogSimple;
import com.fazemeright.canteen_app_models.models.FullOrder;

public class OrderDetailActivity extends BaseActivity implements OrderDetailMvpView {

    private RecyclerView orderDetailRecyclerView;
    private TextView orderIDTextView;
    private OrderDetailRecyclerViewDisplayAdapter adapter;
    private FullOrder order;
    private OrderDetailPresenter<OrderDetailActivity> presenter;

    @Override
    public void initViews() {
        orderDetailRecyclerView = findViewById(R.id.fullOrderListView);
        orderIDTextView = findViewById(R.id.orderIDTextView);
        presenter = new OrderDetailPresenter<>();
        presenter.onAttach(this);

        Intent data = getIntent();
        order = (FullOrder) data.getSerializableExtra("OrderData");

        orderIDTextView.setText(String.format("Order ID: %s", order.getOrderId()));

        orderDetailRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        orderDetailRecyclerView.addItemDecoration(new DividerItemDecoration(orderDetailRecyclerView.getContext(), LinearLayoutManager.VERTICAL));
        adapter = new OrderDetailRecyclerViewDisplayAdapter(order, mContext);
        orderDetailRecyclerView.setAdapter(adapter);

        presenter.fetchOrderDetail(order);
    }

    @Override
    public void onOrderFetchedSuccessfully(FullOrder updatedOrder) {
        order = updatedOrder;
        adapter.updateOrderList(order);
    }

    @Override
    public void onOrderFetchingFailed(String errMsg) {
        AlertUtils.showAlertBox(mContext, "Try again later!", errMsg, getString(R.string.ok), new DialogSimple.AlertDialogListener() {
            @Override
            public void onButtonClicked() {
                finish();
            }
        });
    }

    @Override
    public void setListeners() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_full_order_detail;
    }
}
