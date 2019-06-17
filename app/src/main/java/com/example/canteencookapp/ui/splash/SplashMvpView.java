package com.example.canteencookapp.ui.splash;

import android.content.Context;

import com.example.canteencookapp.ui.base.BaseView;

public interface SplashMvpView extends BaseView {

    Context getContext();

    void userLoggedIn(String category);

    void userNotLoggedIn();
}
