package com.statistical.time.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.statistical.time.R;
import com.statistical.time.application.MyApplication;
import com.statistical.time.base.BaseActivity;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.bean.BirdayInfo;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.bean.EventInfo;
import com.statistical.time.common.Constants;
import com.statistical.time.service.LockScreenService;
import com.statistical.time.tool.SystemUtil;
import com.statistical.time.tool.UserUtils;
import com.statistical.time.widget.Watch;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LockScreenStyleDetailActivity extends BaseActivity {
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

    @BindView(R.id.watch)
    Watch watch;
    @BindView(R.id.tv_rest_year)
    TextView tv_rest_year;
    @BindView(R.id.tv_rest_percent)
    TextView tv_rest_percent;
    @BindView(R.id.iv_rest_battery)
    ImageView iv_rest_battery;


    int style;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen_style_detail);
        unbinder = ButterKnife.bind(this);
        setDeadDayRest();

        style = UserUtils.getLockStyle(this);
        switch (style) {
            case 0:
                watch.start(Constants.DRAK_MODE);
                break;
            case 1:
                watch.start(Constants.LIGHT_MODE);
                watch.setWatchBgRes(R.mipmap.clock_view2_bg,0,0,0);
                break;
            case 2:
                watch.start(Constants.DRAK_MODE);
                watch.setWatchBgRes(R.mipmap.clock_view3_bg,0,0,0);
                break;
            case 3:
                watch.start(Constants.DRAK_MODE);
                watch.setWatchBgRes(R.mipmap.clock_view4_bg,0,0,R.mipmap.ren);
                break;
            default:
                break;
        }

        watch.setOnTimeChangeListener(new Watch.OnTimeChangeListener() {
            @Override
            public void onchange() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setTimeUi();
                            }
                        });
                    }
                });

            }
        });
        setTimeUi();
        initStatusHeight();
    }
    private void initStatusHeight() {
        View status_bar_height =findViewById(R.id.status_bar_height);
        ViewGroup.LayoutParams layoutParams =  status_bar_height.getLayoutParams();
        layoutParams.height  = SystemUtil.getStatusBarHeight(this);
        status_bar_height.setLayoutParams(layoutParams);

    }

    private void setTimeUi() {

        double rest_year = (deadTime - System.currentTimeMillis()) * 1.0 / 1000 / 60 / 60 / 24 / 365;
        tv_rest_year.setText(String.format("%.8f", rest_year));

        int percent = (int) (rest_year / max_age * 100);
        tv_rest_percent.setText(percent + "%");
        int progress = 100 - percent;

        if (progress < 0) {
            progress = 0;
        } else if (progress > 100) {
            progress = 100;
        }
        int level = (100 - progress) * 9 / 100;
        // Log.e("iv_rest_battery","level==="+level);
        iv_rest_battery.setImageLevel(level);


    }

    private int max_age;
    private long deadTime;

    private void setDeadDayRest() {

        List<BirdayInfo> list = MyApplication.getInstance().getmDaoSession().getBirdayInfoDao().queryBuilder().list();
        if (list != null && list.size() > 0) {
            BirdayInfo birdayInfo = list.get(0);
            max_age = birdayInfo.max_age;
            Calendar dead_calendar = Calendar.getInstance();
            dead_calendar.set(birdayInfo.year + max_age, birdayInfo.month, birdayInfo.day, birdayInfo.hour, birdayInfo.minute, birdayInfo.secend);
            deadTime = dead_calendar.getTimeInMillis();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        watch.stop();
        unbinder.unbind();
    }


    @OnClick(R.id.iv_close)
    @Override
    public void finish() {
        super.finish();
    }
    @OnClick(R.id.tv_use_lock)
    public void useLock() {
    UserUtils.setCurrentLockStyle(this,style);
    startService(new Intent(this,LockScreenService.class));
    finish();
    }
}
