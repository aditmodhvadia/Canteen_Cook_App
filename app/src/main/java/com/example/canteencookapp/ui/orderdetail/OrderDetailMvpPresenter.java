package com.example.canteencookapp.ui.orderdetail;

import com.example.canteencookapp.ui.base.BaseMvpPresenter;
import com.fazemeright.canteen_app_models.models.FullOrder;

public interface OrderDetailMvpPresenter<V extends OrderDetailMvpView> extends BaseMvpPresenter<V> {

    void fetchOrderDetail(FullOrder order);
}
