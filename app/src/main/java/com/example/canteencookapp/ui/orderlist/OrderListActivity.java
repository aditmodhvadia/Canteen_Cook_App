package com.example.canteencookapp.ui.orderlist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteencookapp.R;
import com.example.canteencookapp.ui.base.BaseActivity;
import com.example.canteencookapp.ui.orderdetail.OrderDetailActivity;

import java.util.ArrayList;

public class OrderListActivity extends BaseActivity implements OrderListMvpView {
    //    public static Intent service;
    public static boolean flag = false;
    private TextView ordersHeadingTextView;
    private ListView ordersListView;
    private OrderListAdapter orderListAdapter;
    private String CATEGORY;
    private OrderListPresenter<OrderListActivity> presenter;


    @Override
    public void initViews() {
        ordersHeadingTextView = findViewById(R.id.ordersHeadingTextView);
        ordersListView = findViewById(R.id.ordersListView);
        presenter = new OrderListPresenter<>();
        presenter.onAttach(this);

        Intent data = getIntent();
        CATEGORY = data.getStringExtra("Category");

//        service = new Intent(this, MyNotificationService.class);

        ordersHeadingTextView.setText(ordersHeadingTextView.getText().toString() + data.getStringExtra("Category"));


        showLoading();
        presenter.fetchOrderList(CATEGORY);
    }

    @Override
    public void onFetchingOrderListSuccessful(final ArrayList<String> orderID, final ArrayList<String> orderRollNo, final ArrayList<String> orderTime) {
        hideLoading();
        orderListAdapter = new OrderListAdapter(orderID, orderTime, orderRollNo, mContext);
        ordersListView.setAdapter(orderListAdapter);

        ordersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Open full order detail activity by passing the order id, category, time and rollno
                Intent fullOrder = new Intent(OrderListActivity.this, OrderDetailActivity.class);
                fullOrder.putExtra("OrderID", orderID.get(position));
                fullOrder.putExtra("Category", CATEGORY);
                fullOrder.putExtra("Time", orderTime.get(position));
                fullOrder.putExtra("RollNo", orderRollNo.get(position));
                startActivity(fullOrder);
            }
        });
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
