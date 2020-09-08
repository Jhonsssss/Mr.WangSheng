package com.statistical.lib.app;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;


import com.statistical.lib.SwipeBackLayout;
import com.statistical.lib.Utils;

import java.lang.ref.WeakReference;


/**
 * Created by laysionqet on 2018/4/24.
 */
public class SwipeBackListenerActivityAdapter implements SwipeBackLayout.SwipeListenerEx {
    private final WeakReference<AppCompatActivity> mActivity;

    public SwipeBackListenerActivityAdapter(@NonNull AppCompatActivity activity) {
        mActivity = new WeakReference<>(activity);
    }

    @Override
    public void onScrollStateChange(int state, float scrollPercent) {

    }

    @Override
    public void onEdgeTouch(int edgeFlag) {
        AppCompatActivity activity = mActivity.get();
        if (null != activity) {
            Utils.convertActivityToTranslucent(activity);
        }
    }

    @Override
    public void onScrollOverThreshold() {

    }

    @Override
    public void onContentViewSwipedBack() {
        AppCompatActivity activity = mActivity.get();
        if (null != activity && !activity.isFinishing()) {
            activity.finish();
            activity.overridePendingTransition(0, 0);
        }
    }
}
