package com.statistical.time.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.statistical.lib.SwipeBackLayout;
import com.statistical.lib.app.SwipeBackActivity;
import com.statistical.time.R;
import com.statistical.time.application.MyApplication;
import com.statistical.time.bean.BirdayInfo;
import com.statistical.time.common.Constants;
import com.statistical.time.tool.LogUtil;
import com.statistical.time.tool.Parser;
import com.statistical.time.tool.UserUtils;
import com.statistical.time.widget.Watch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Administrator
 */
public class LockScreenActivity extends SwipeBackActivity {
    private KeyGuardReceiver mKeyGuardReceiver;
    private TextView mTimeView;
    private TextView mDateView;

    Unbinder unbinder;
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    private static final String[] DAY_OF_WEEK = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    private int style;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parser.KEY_GUARD_INSTANCES.add(this);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
//                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        );

        registerKeyGuardReceiver();//屏蔽Home

        setContentView(R.layout.activity_lock_screen);

        unbinder=     ButterKnife.bind(this);
        initViews();
        mSwipeBackLayout = getSwipeBackLayout();
        Parser.killBackgroundProcess(this);
        mSwipeBackLayout.setEdgeTrackingEnabled( SwipeBackLayout.EDGE_LEFT);
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {
            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
            }

            @Override
            public void onScrollOverThreshold() {
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @BindView(R.id.watch)
    Watch watch;
    @BindView(R.id.tv_rest_year)
    TextView tv_rest_year;
    @BindView(R.id.tv_rest_percent)
    TextView tv_rest_percent;
    @BindView(R.id.iv_rest_battery)
    ImageView iv_rest_battery;

    private void initViews() {
        mTimeView = (TextView) findViewById(R.id.time);
        mDateView = (TextView) findViewById(R.id.date);


        setDate();


        setDeadDayRest();

        style = UserUtils.getCurrentLockStyle(this);
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
    }

    private void setDate() {

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        mTimeView.setText(TIME_FORMAT.format(date));
        String dateString = (calendar.get(Calendar.MONTH) + 1) + "月"
                + calendar.get(Calendar.DAY_OF_MONTH) + "日 "
                + DAY_OF_WEEK[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        mDateView.setText(dateString);
    }


    private void setTimeUi() {

        if(tv_rest_year==null) return;
        double rest_year = (deadTime - System.currentTimeMillis()) * 1.0 / 1000 / 60 / 60 / 24 / 365;
        tv_rest_year.setText(String.format("%.8f", rest_year));

        int percent = (int) (rest_year / max_age * 100);
        tv_rest_percent.setText(percent + "%");
        int progress = 100 - percent;
        setDate();
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
    public void onBackPressed() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int key = event.getKeyCode();
        switch (key) {
            case KeyEvent.KEYCODE_BACK: {
                return true;
            }
            case KeyEvent.KEYCODE_MENU: {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void registerKeyGuardReceiver() {
        if (null == mKeyGuardReceiver) {
            mKeyGuardReceiver = new KeyGuardReceiver();
            registerReceiver(mKeyGuardReceiver, new IntentFilter());
        }
    }

    private void unregisterKeyGuardReceiver() {
        if (mKeyGuardReceiver != null) {
            unregisterReceiver(mKeyGuardReceiver);
        }
    }

    // 4.0以上无法屏蔽Home键，所以没什么作用
    class KeyGuardReceiver extends BroadcastReceiver {

        static final String SYSTEM_REASON = "reason";
        static final String SYSTEM_HOME_KEY = "homekey";// home key
        static final String SYSTEM_RECENT_APPS = "recentapps";// long home key

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_REASON);
                if (reason != null) {
                    if (reason.equals(SYSTEM_HOME_KEY)) {
                        finish();
                    } else if (reason.equals(SYSTEM_RECENT_APPS)) {
                    }
                }
            } else if (action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED) || action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
                finish();
            }
        }
    }


    private void showToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }



    @Override
    protected void onDestroy() {
        unregisterKeyGuardReceiver();
        super.onDestroy();
        watch.stop();
        unbinder.unbind();
    }

}




