package com.example.canteencookapp.ui.splash;

import android.content.Intent;

import com.example.canteencookapp.R;
import com.example.canteencookapp.ui.base.BaseActivity;
import com.example.canteencookapp.ui.login.LoginActivity;
import com.example.canteencookapp.ui.orderlist.OrderListActivity;

public class SplashActivity extends BaseActivity implements SplashMvpView {

    SplashPresenter<SplashActivity> presenter;

    @Override
    public void initViews() {
        presenter = new SplashPresenter<>();
        presenter.onAttach(this);

        presenter.determineIfLoggedIn();
    }

    @Override
    public void userLoggedIn(String category) {
        Intent i = new Intent(SplashActivity.this, OrderListActivity.class);
        i.putExtra("Category", category);
        startActivity(i);
        finish();
    }

    @Override
    public void userNotLoggedIn() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void setListeners() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_splash;
    }
}
