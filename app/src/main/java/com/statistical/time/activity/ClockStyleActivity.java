package com.statistical.time.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.statistical.time.R;
import com.statistical.time.base.BaseActivity;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.common.Constants;
import com.statistical.time.widget.Watch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ClockStyleActivity extends BaseActivity {
    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_stytle);
     unbinder = ButterKnife.bind(this);
     initWatch();
    }

    private void initWatch() {
        live_watch1.start(Constants.LIGHT_MODE);
        dead_watch1.start(Constants.DRAK_MODE);


        live_watch2.start(Constants.LIGHT_MODE);
        live_watch2.setWatchBgRes(R.mipmap.clock_view2_bg,0,0,0);
        dead_watch2.start(Constants.DRAK_MODE);
        dead_watch2.setWatchBgRes(R.mipmap.clock_view4_bg,0,0,0);
    }



    @BindView(R.id.live_watch1)
    Watch live_watch1;
    @BindView(R.id.live_watch2)
    Watch live_watch2;
    @BindView(R.id.dead_watch1)
    Watch dead_watch1;
    @BindView(R.id.dead_watch2)
    Watch dead_watch2;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
