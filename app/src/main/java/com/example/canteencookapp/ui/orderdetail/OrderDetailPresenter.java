package com.example.canteencookapp.ui.orderdetail;

import com.example.canteencookapp.ui.base.BasePresenter;
import com.fazemeright.canteen_app_models.models.FullOrder;
import com.fazemeright.firebase_api__library.listeners.DBValueEventListener;

public class OrderDetailPresenter<V extends OrderDetailMvpView> extends BasePresenter<V> implements OrderDetailMvpPresenter<V> {


    public OrderDetailPresenter() {
    }

    @Override
    public void fetchOrderDetail(FullOrder order) {

        apiManager.cookOrderDetailListener(order, new DBValueEventListener<FullOrder>() {
            @Override
            public void onDataChange(FullOrder data) {
                getMvpView().onOrderFetchedSuccessfully(data);
            }

            @Override
            public void onCancelled(Error error) {
                getMvpView().onOrderFetchingFailed(error.getMessage());
            }
        });
    }
}
