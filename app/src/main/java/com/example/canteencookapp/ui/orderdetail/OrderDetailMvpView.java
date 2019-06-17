package com.example.canteencookapp.ui.orderdetail;

import android.content.Context;

import com.example.canteencookapp.ui.base.BaseView;
import com.fazemeright.canteen_app_models.models.FullOrder;

public interface OrderDetailMvpView extends BaseView {

    Context getContext();

    void onOrderFetchedSuccessfully(FullOrder updatedOrder);

    void onOrderFetchingFailed(String errMsg);
}
