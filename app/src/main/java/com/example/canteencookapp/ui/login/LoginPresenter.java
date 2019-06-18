package com.example.canteencookapp.ui.login;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.canteencookapp.ui.base.BasePresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V> implements LoginMvpPresenter<V> {

    private DatabaseReference cookRoot;
    private String CATEGORY = "Category";

    public LoginPresenter() {
    }

    @Override
    public void performLogin(final String userId) {
        if (TextUtils.isEmpty(userId)) {
            getMvpView().onValueEntryError("Enter ID first");
            return;
        }

        if (userId.length() < 5) {
            getMvpView().onValueEntryError("ID should be more than 5 characters");
            return;
        }

        cookRoot = FirebaseDatabase.getInstance().getReference().child("CookData");
        cookRoot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(userId).exists()) {
//                    send category through intent
                    final String category = dataSnapshot.child(userId).child(CATEGORY).getValue().toString();
                    FirebaseMessaging.getInstance().subscribeToTopic(dataSnapshot.child(userId).child("Topic").getValue().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        getMvpView().onSuccessFullVerification(category);
                                    } else {
                                        getMvpView().onValueEntryError(task.getException().getMessage());
                                    }
                                }
                            });
                } else {
                    getMvpView().onValueEntryError("Invalid ID");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                getMvpView().onValueEntryError(databaseError.getMessage());
            }
        });

    }
}
