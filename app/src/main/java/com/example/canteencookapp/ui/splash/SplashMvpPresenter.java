package com.example.canteencookapp.ui.splash;

import com.example.canteencookapp.ui.base.BaseMvpPresenter;

public interface SplashMvpPresenter<V extends SplashMvpView> extends BaseMvpPresenter<V> {

    void determineIfLoggedIn();
}
