package com.statistical.time.application;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.statistical.greendao.DaoMaster;
import com.statistical.greendao.DaoSession;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.common.Constants;
import com.statistical.time.tool.LanguageUtils;
import com.statistical.time.tool.UserUtils;
import com.statistical.time.tool.Wifi4GUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public class MyApplication extends Application {
    private static MyApplication instance;
    public static boolean isAgreeUserClause = true;
    private DaoSession mDaoSession;
    private static final String DB_NAME = "time_app.db";
    public static MyApplication getInstance() {
        return instance;
    }
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();


        initGreenDao();
        initOkHttpClient();
        initOKFresco();

        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_LOCALE_CHANGED);
        intentFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        intentFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        registerReceiver(broadcastReceiver, intentFilter);
    }
    public static Context getAppContext() {
        return context;
    }
    public Context getContext() {
        return context;
    }
    private void initOkHttpClient() {
        try {
            Wifi4GUtils.bringUpCellularNetwork(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(30000, TimeUnit.MILLISECONDS)
                .readTimeout(30000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    private void initOKFresco(){
        ImagePipelineConfig frescoConfig = OkHttpImagePipelineConfigFactory
                .newBuilder(this, new OkHttpClient())
                .build();
        Fresco.initialize(this,frescoConfig);
    }
    /**
     * 横屏后，本地语言错乱
     *
     * @return
     */
    public static Context getLanguageContext() {
        Context context = getAppContext();
        LanguageUtils.getCurrentLanguage(context);
        return context;
    }
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        String SYSTEM_REASON = "reason";
        String SYSTEM_HOME_KEY = "homekey";
        String SYSTEM_HOME_KEY_LONG = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_REASON);
                if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
                    Constants.agpsdownloadflag = false;
                    EventBus.getDefault().post(new EventCenter(Constants.CODE_SYSTEM_HOME_KEY));
                } else if (TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)) {
                    //表示长按home键,显示最近使用的程序列表
                }
            }
            //来电监听
            if (action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
                TelephonyManager telephony =
                        (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                int state = telephony.getCallState();
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        Constants.agpsdownloadflag = false;
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        Constants.agpsdownloadflag = false;
                        break;
                    default:
                        break;
                }
            }

            if(action.equals(Intent.ACTION_LOCALE_CHANGED)){
                Configuration config = getResources().getConfiguration();//获得res资源对象
                String country = config.locale.getCountry();
                if(!TextUtils.isEmpty(country)){
                    if ("CN".equals(country)){
                        UserUtils.setUserSettingLanguage("locallanguage", 1);//中文
                    } else {
                        UserUtils.setUserSettingLanguage("locallanguage", 2);//默认为英文
                    }
                }
            }

        }
    };

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, DB_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getmDaoSession() {
        if(mDaoSession==null){
            initGreenDao();
        }
        return mDaoSession;
    }
}
