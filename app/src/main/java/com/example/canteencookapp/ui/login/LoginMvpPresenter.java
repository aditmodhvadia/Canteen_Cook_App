package com.example.canteencookapp.ui.login;

import com.example.canteencookapp.ui.base.BaseMvpPresenter;

public interface LoginMvpPresenter<V extends LoginMvpView> extends BaseMvpPresenter<V> {

    void performLogin(String userId);
}
