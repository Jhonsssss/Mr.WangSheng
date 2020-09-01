package com.statistical.time.tool;

import android.content.Context;

/**
 * Created by Administrator on 2017/4/17 0017.
 */


public class LocaleLanguageUtil {
    public static LocaleLanguageUtil  languageUtil;

    public  static LocaleLanguageUtil getStance(){
        if (languageUtil==null){
            languageUtil =new LocaleLanguageUtil();
        }

        return languageUtil;
    }
    String appLanguage="en";

    public  void changeLanguage(Context context) {

//        String language =   PreferencesUtil.getStringPreferences(context, PreferenceNames.APP_LANGUAGE);
//        String country =context.getResources().getConfiguration().locale.getCountry();
//        if (!TextUtils.isEmpty(country)&&DataBase.IS_FIRST_SET_LANGUAGE){
//            DataBase.IS_FIRST_SET_LANGUAGE=false;
//            DataBase.sysetemLanguageCountry=country;
//        }
//        if (TextUtils.isEmpty(language)||"allow_system".equals(language.trim())){//跟随系统语言
//            if ("CN".equals( DataBase.sysetemLanguageCountry)||"TW".equals( DataBase.sysetemLanguageCountry)||"HK".equals( DataBase.sysetemLanguageCountry)){
//                language="zh";
//            }else{
//                language="en";
//            }
//        }
//        Resources resources = context.getResources();
//        Configuration configuration = resources.getConfiguration(); // 获取资源配置
//        DisplayMetrics metrics = resources.getDisplayMetrics();
//
//        Log.e("language","language="+language);
//        if ("zh".equals(language)){
//            configuration.locale = Locale.SIMPLIFIED_CHINESE; // 设置当前语言配置为繁体
//            appLanguage="zh";
//        }else{
//            configuration.locale = Locale.ENGLISH; // 设置当前语言配置为繁体
//            appLanguage="en";
//        }
//        resources.updateConfiguration(configuration, metrics); // 更新配置文件

    }

    public String getAppLanguage(){
        return appLanguage;
    }
}
