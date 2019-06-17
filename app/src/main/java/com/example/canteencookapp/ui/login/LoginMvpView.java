package com.example.canteencookapp.ui.login;

import android.content.Context;

import com.example.canteencookapp.ui.base.BaseView;

public interface LoginMvpView extends BaseView {

    Context getContext();

    void onValueEntryError(String errMsg);

    void onSuccessFullVerification(String category);
}
