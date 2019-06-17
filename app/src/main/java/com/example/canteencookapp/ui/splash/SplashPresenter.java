package com.example.canteencookapp.ui.splash;

import com.example.canteencookapp.data.prefs.CanteenPreferenceManager;
import com.example.canteencookapp.data.prefs.PreferenceKeys;
import com.example.canteencookapp.ui.base.BasePresenter;

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {


    public SplashPresenter() {
    }

    @Override
    public void determineIfLoggedIn() {
        if (CanteenPreferenceManager.getInstance().getBoolean(getMvpView().getContext(), PreferenceKeys.IS_LOGIN, false)) {
            getMvpView().userLoggedIn(CanteenPreferenceManager.getInstance().getString(getMvpView().getContext(),
                    PreferenceKeys.CATEGORY, "Chinese"));
//            TODO: dataManager.setCategory();
        } else {
            getMvpView().userNotLoggedIn();
        }
    }
}
