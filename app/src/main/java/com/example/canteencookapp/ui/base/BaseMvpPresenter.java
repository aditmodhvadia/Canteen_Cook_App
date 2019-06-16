package com.example.canteencookapp.ui.base;

public interface BaseMvpPresenter<V extends BaseView> {

    void onAttach(V mvpView);

    void onDetach();
}