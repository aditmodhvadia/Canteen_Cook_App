package com.example.canteencookapp.ui.orderlist;

import com.example.canteencookapp.ui.base.BasePresenter;
import com.fazemeright.canteen_app_models.models.FullOrder;
import com.fazemeright.firebase_api__library.listeners.DBValueEventListener;

import java.util.ArrayList;

public class OrderListPresenter<V extends OrderListMvpView> extends BasePresenter<V> implements OrderListMvpPresenter<V> {


    public OrderListPresenter() {
    }

    @Override
    public void fetchOrderList(String category) {
        apiManager.cookOrderListListener(category, new DBValueEventListener<ArrayList<FullOrder>>() {
            @Override
            public void onDataChange(ArrayList<FullOrder> data) {
                getMvpView().onFetchingOrderListSuccessful(data);
            }

            @Override
            public void onCancelled(Error error) {
                getMvpView().onFetchingOrderListFailed(error.getMessage());
            }
        });

    }
}
