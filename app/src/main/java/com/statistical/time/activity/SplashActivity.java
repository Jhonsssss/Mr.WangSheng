package com.statistical.time.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.statistical.time.R;
import com.statistical.time.application.MyApplication;
import com.statistical.time.base.BaseActivity;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.bean.BirdayInfo;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.bean.EventInfo;
import com.statistical.time.tool.LogUtil;

import java.util.List;


/**
 * Created by chengbin on 2015/12/18.
 * 闪屏页面
 */
public class SplashActivity extends BaseActivity {

    public static final int REQUEST_CODE = 0;
    private static final String TAG = "SplashActivity";
//    private  final int SHOW_LOADING_TIME = 3400;


    final private static int PERMISSIONS_CODE_WRITE_SETTINGS = 30;

    private boolean isRequireCheck; // 是否需要系统权限检测, 防止和系统提示框重叠

    private static final String PACKAGE_URL_SCHEME = "package:"; // 方案

    String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };


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

    //    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
//        Intent intent = getIntent();
//        String action = intent.getAction();
//         imageView = findViewById(R.id.iv_splash_open);

        TextView tv_ming =findViewById(R.id.tv_ming);
//        ImageView iv_slash =findViewById(R.id.iv_slash);
      String []  mings =   getResources().getStringArray(R.array.time_ming);
//        iv_slash.setImageResource(R.mipmap.splash_bg1);
       int i  = (int) (Math.random()*mings.length);
       if (i<0) {
           i=0;
       }else if (i>mings.length){
           i=mings.length-1;
       }
      tv_ming.setText(mings[i]);
        addEvent();
        check();


    }

    String[][] events = new String[][]{
            {"写日记", "0"}, {"做饭", "1"}, {"看电影", "2"}, {"打游戏", "3"}, {"旅行", "4"}, {"上厕所", "5"}, {"洗澡", "6"}, {"享受长假", "7"}, {"度过周末", "8"}, {"睡觉", "9"}, {"做爱", "10"}, {"吃饭", "11"}
    };
    float[] durtions = new float[]{
            1f, 3f, 1f / 7, 2, 1f / 30, 2, 1, 1f / 30, 1f / 7, 1, 2f / 7, 3
    };


    private void addEvent() {

        List<EventInfo> eventInfos = MyApplication.getInstance().getmDaoSession().getEventInfoDao().queryBuilder().list();
        if (eventInfos == null || eventInfos.size() == 0) {
            for (int i = 0; i < events.length; i++) {
                EventInfo eventInfo = new EventInfo();
                eventInfo.index = Integer.parseInt(events[i][1]);
                eventInfo.durtion = durtions[i];
                eventInfo.name = events[i][0];
                MyApplication.getInstance().getmDaoSession().getEventInfoDao().insert(eventInfo);
            }


        }

    }

    private void check() {

        LogUtil.e("   ssssss","检查权限111");
        isCheck = true;
        boolean isAllGranted = checkPermissionAllGranted(perms);
        LogUtil.e("   ssssss","检查权限22222");
        if (isAllGranted) {
            mHandler.sendEmptyMessageDelayed(110, 2000);
            return;
        }

        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                this,
                perms,
                REQUEST_CODE
        );

    }

    boolean isCheck = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (!isCheck) {
            check();
        }
    }


    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        if (Build.VERSION.SDK_INT >= 23) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    // 只要有一个权限没有被授予, 则直接返回 false
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (hasAllPermissionsGranted(grantResults)) {
                // 如果所有的权限都授予了, 则执行备份代码
                setTvJumpAd();
            } else {
                finish();
            }
        }
    }

    // 含有全部的权限
    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }


    // 启动应用的设置
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivityForResult(intent, 0);
        isCheck = false;
    }

    private void askForPermissionsWriteSettings() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, PERMISSIONS_CODE_WRITE_SETTINGS);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 110:
                    setTvJumpAd();
                    break;
            }
        }
    };

    public void setTvJumpAd() {

        List<BirdayInfo> list = MyApplication.getInstance().getmDaoSession().getBirdayInfoDao().queryBuilder().list();
        if (list != null && list.size() > 0) {
            readyGo(MainActivity.class);
        } else {
            readyGo(EditBirthdayActivity.class);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
