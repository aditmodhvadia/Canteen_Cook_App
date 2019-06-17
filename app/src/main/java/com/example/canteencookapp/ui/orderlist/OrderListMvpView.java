package com.example.canteencookapp.ui.orderlist;

import android.content.Context;

import com.example.canteencookapp.ui.base.BaseView;
import com.fazemeright.canteen_app_models.models.FullOrder;

import java.util.ArrayList;

public interface OrderListMvpView extends BaseView {

    Context getContext();

    void onFetchingOrderListFailed(String errMsg);

    void onFetchingOrderListSuccessful(ArrayList<FullOrder> orderList);
}
