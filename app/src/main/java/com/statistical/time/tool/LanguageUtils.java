package com.statistical.time.tool;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by HPComputer on 2016/9/28.
 */
public class LanguageUtils {

    public static final int MODE_FOLLOW_SYSTEM = 0;
    public static final int MODE_CHINES_LANGUAGE = 1;
    public static final int MODE_ENGLISH_LANGUAGE = 2;
    public static final int MODE_JAPANESE_LANGUAGE = 3;
    public static final int MODE_KOREAN_LANGUAGE = 4;
    public static final int MODE_TAIWAN_LANGUAGE = 5; //fix by yc 2016/11/17:台湾繁体和香港繁体顺序颠倒，与服务端同步
    public static final int MODE_HONGKONG_LANGUAGE = 6;
    public static final int MODE_FRANCE_LANGUAGE = 7;

    public static String  sysetemLanguageCountry;

    //更新语言配置信息，每个Activity必须在生命周期初始化完成后调用
    public static void getCurrentLanguage(Context ctx) {
        if(ctx == null) {
            return;
        }
        String _id = SystemSetUtil.getInstance().getCurrentLanguageCode();
        if (_id == "") return;
        //应用内配置语言
        Resources resources = ctx.getResources();//获得res资源对象
        Configuration config = resources.getConfiguration();//获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();//获得屏幕参数：主要是分辨率，像素等。
        String country = config.locale.getCountry();
        if(!TextUtils.isEmpty(country)){
            LanguageUtils.sysetemLanguageCountry = country;
        }

        if(_id.equals("follow_system")){
            if(!TextUtils.isEmpty(LanguageUtils.sysetemLanguageCountry)){
                if ("CN".equals(LanguageUtils.sysetemLanguageCountry)){
                    config.locale = Locale.SIMPLIFIED_CHINESE;
                }else{
                    config.locale = Locale.US;
                }
            }
        } else if (_id.equals("en")) {
            config.locale = Locale.US;
        } else if (_id.equals("jp")) {
            config.locale = Locale.JAPAN;
        } else if (_id.equals("ko")) {
            config.locale = Locale.KOREAN;
        } else if (_id.equals("hk")) {
            config.locale = Locale.TRADITIONAL_CHINESE;//当前不支持香港繁体，因为只有少量生活用于不同对APP没影响
        } else if (_id.equals("tw")) {
            config.locale = Locale.TRADITIONAL_CHINESE;
        } else if (_id.equals("fr")) {
            config.locale = Locale.FRANCE;
        } else {
            config.locale = Locale.SIMPLIFIED_CHINESE;
        }

        resources.updateConfiguration(config, dm);
    }


}
