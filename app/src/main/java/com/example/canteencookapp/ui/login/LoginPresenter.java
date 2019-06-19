package com.example.canteencookapp.ui.login;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.canteencookapp.ui.base.BasePresenter;
import com.fazemeright.firebase_api__library.listeners.DBValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V> implements LoginMvpPresenter<V> {

    public LoginPresenter() {
    }

    @Override
    public void performLogin(String userId) {
        if (TextUtils.isEmpty(userId)) {
            getMvpView().onValueEntryError("Enter ID first");
            return;
        }

        if (userId.length() < 5) {
            getMvpView().onValueEntryError("ID should be more than 5 characters");
            return;
        }

        apiManager.verifyCookCode(userId, new DBValueEventListener<ArrayList<String>>() {
            @Override
            public void onDataChange(final ArrayList<String> data) {
                FirebaseMessaging.getInstance().subscribeToTopic(data.get(1))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    getMvpView().onSuccessFullVerification(data.get(0));
                                } else {
                                    getMvpView().onValueEntryError(task.getException().getMessage());
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(Error error) {
                getMvpView().onValueEntryError(error.getMessage());
            }
        });


    }
}
