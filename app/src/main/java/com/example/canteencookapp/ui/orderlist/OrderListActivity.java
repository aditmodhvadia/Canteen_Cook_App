package com.example.canteencookapp.ui.orderlist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteencookapp.R;
import com.example.canteencookapp.ui.base.BaseActivity;
import com.fazemeright.canteen_app_models.models.FullOrder;

import java.util.ArrayList;

public class OrderListActivity extends BaseActivity implements OrderListMvpView {
    //    public static Intent service;
    public static boolean flag = false;
    private TextView ordersHeadingTextView;
    private RecyclerView ordersRecyclerView;
    private OrderListAdapter orderListAdapter;
    private String CATEGORY;
    private OrderListPresenter<OrderListActivity> presenter;
    private OrderListRecyclerViewDisplayAdapter adapter;
    private ArrayList<FullOrder> orderList;


    @Override
    public void initViews() {
        ordersHeadingTextView = findViewById(R.id.ordersHeadingTextView);
        ordersRecyclerView = findViewById(R.id.ordersRecyclerView);
        orderList = new ArrayList<>();
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        ordersRecyclerView.addItemDecoration(new DividerItemDecoration(ordersRecyclerView.getContext(), LinearLayoutManager.VERTICAL));
        adapter = new OrderListRecyclerViewDisplayAdapter(orderList, mContext);
        ordersRecyclerView.setAdapter(adapter);

        presenter = new OrderListPresenter<>();
        presenter.onAttach(this);

        Intent data = getIntent();
        CATEGORY = data.getStringExtra("Category");

//        service = new Intent(this, MyNotificationService.class);

        ordersHeadingTextView.setText(String.format("%s%s", ordersHeadingTextView.getText().toString(), CATEGORY));


        showLoading();
        presenter.fetchOrderList(CATEGORY);
    }

    @Override
    public void onFetchingOrderListSuccessful(ArrayList<FullOrder> orderList) {
        hideLoading();
        adapter.updateOrderList(orderList);
    }

    @Override
    public void onFetchingOrderListFailed(String errMsg) {
        Toast.makeText(OrderListActivity.this, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setListeners() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_order_list;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Stop service when back pressed to open login activity
        flag = false;
//        stopService(service);
        String myPREFERENCES = "Login Data";
        SharedPreferences.Editor editor = getSharedPreferences(myPREFERENCES, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Start Notification Service when activity is paused
//        service.putExtra("Category", CATEGORY);
        flag = true;
//        startService(service);
    }

    @Override
    protected void onResume() {
        super.onResume();
        flag = false;
//        stopService(service);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
//        Stop notification service when Activity is loaded again
        flag = false;
//        stopService(service);
    }
}
