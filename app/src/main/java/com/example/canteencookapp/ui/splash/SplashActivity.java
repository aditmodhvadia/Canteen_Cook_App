package com.example.canteencookapp.ui.splash;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.canteencookapp.R;
import com.example.canteencookapp.ui.base.BaseActivity;
import com.example.canteencookapp.ui.login.LoginActivity;
import com.example.canteencookapp.ui.orderlist.OrderListActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class SplashActivity extends BaseActivity implements SplashMvpView {

    private SplashPresenter<SplashActivity> presenter;
    private String tag = "##Fcm";


    @Override
    public void initViews() {
        presenter = new SplashPresenter<>();
        presenter.onAttach(this);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(tag, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(tag, token);
                        presenter.determineIfLoggedIn();
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
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
