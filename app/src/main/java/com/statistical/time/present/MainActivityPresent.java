package com.statistical.time.present;

import com.statistical.time.activity.MainActivity;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.model.MainActivityModel;
import com.statistical.time.tool.LogUtil;
import com.statistical.time.view.MainActivityView;

public class MainActivityPresent extends BasePresenter<MainActivityModel, MainActivityView> {
    private MainActivity mainActivity;

    public MainActivityPresent(MainActivity activity,MainActivityView view,MainActivityModel model){
        this.mainActivity = activity;
        mModel = model;
        mView = view;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mView.test();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
