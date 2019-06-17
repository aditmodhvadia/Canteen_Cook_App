package com.example.canteencookapp.ui.orderlist;

import com.example.canteencookapp.ui.base.BaseMvpPresenter;

public interface OrderListMvpPresenter<V extends OrderListMvpView> extends BaseMvpPresenter<V> {

    void fetchOrderList(String category);
}
