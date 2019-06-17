package com.example.canteencookapp.ui.login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.canteencookapp.R;
import com.example.canteencookapp.data.prefs.CanteenPreferenceManager;
import com.example.canteencookapp.data.prefs.PreferenceKeys;
import com.example.canteencookapp.ui.base.BaseActivity;
import com.example.canteencookapp.ui.orderlist.OrderListActivity;
import com.example.canteencookapp.utils.AlertUtils;
import com.example.canteencookapp.utils.DialogSimple;

public class LoginActivity extends BaseActivity implements LoginMvpView, View.OnClickListener {

    private EditText idEditText;
    private Button loginButton;
    //    Variables
    private String CATEGORY = "Category";
    private LoginPresenter<LoginActivity> presenter;

    @Override
    public void initViews() {

        idEditText = findViewById(R.id.idEditText);
        loginButton = findViewById(R.id.loginButton);

        presenter = new LoginPresenter<>();
        presenter.onAttach(this);

    }

    @Override
    public void setListeners() {
        loginButton.setOnClickListener(this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (OrderListActivity.flag) {
//            stopService(OrderListActivity.service);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                ConnectivityManager cm =
                        (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if (isConnected) {
                    showLoading();
                    presenter.performLogin(idEditText.getText().toString().trim());
                } else
                    Toast.makeText(LoginActivity.this, "No Internet Connectivity", Toast.LENGTH_SHORT).show();

                break;
        }
    }

    @Override
    public void onValueEntryError(String errMsg) {
        hideLoading();
        AlertUtils.showAlertBox(mContext, "", errMsg, getString(R.string.ok), new DialogSimple.AlertDialogListener() {
            @Override
            public void onButtonClicked() {
                idEditText.requestFocus();
            }
        });
    }

    @Override
    public void onSuccessFullVerification(String category) {
        hideLoading();

        idEditText.setText("");
//        Save value of code in Shared preferences
        CanteenPreferenceManager.getInstance().setBoolean(mContext, PreferenceKeys.IS_LOGIN, true);
        CanteenPreferenceManager.getInstance().setString(mContext, PreferenceKeys.CATEGORY, category);
        Intent i = new Intent(LoginActivity.this, OrderListActivity.class);
        i.putExtra(CATEGORY, category);
        startActivity(i);
        finish();
    }
}
