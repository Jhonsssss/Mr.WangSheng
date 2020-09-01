package com.statistical.time.tool;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.statistical.time.R;
import com.statistical.time.application.ActivityManager;
import com.statistical.time.application.MyApplication;
import com.statistical.time.bean.BirdayInfo;
import com.statistical.time.common.InterfaceFinals;
import com.statistical.time.common.PreferenceNames;
import com.statistical.time.dialog.MaterialDialogBuilderL;


/**
 * Created by Administrator on 2017/7/27 0027.
 */

public class UserUtils {

    private static long lastClickTime;
    private static long lastClickTimes;
    private static String USER_ID = null;
    public static final String KEY_TEL = "edit_data_tel";
    public static final String KEY_REALNAME = "edit_data_name";
    public static final String KEY_NICKNAME = "edit_data_nickname";
    public static final String KEY_ADDRESS = "edit_data_address";
    public static final String KEY_SEX = "edit_data_sex";
    public static final String KEY_AVATAR = "edit_data_avatar";
    public static final String KEY_BGURL = "edit_data_bgurl";

    public static String U_STATUS        = "uStatus";


    public static void setUserID(String uid) {
        if (!TextUtils.isEmpty(uid))
            USER_ID = uid;
        SharedPreferencesUtils.setParam(MyApplication.getInstance().getContext(), "user_ID", uid);
    }

    public static String getUserID() {
        if (TextUtils.isEmpty(USER_ID))
            USER_ID = (String) SharedPreferencesUtils.getParam(MyApplication.getInstance().getContext(), "user_ID", "0");
        return USER_ID;
    }


    public static void setUserSettingLanguage(String key, int idx) {
        SharedPreferencesUtils.setParam(MyApplication.getInstance().getContext(), key, idx);
    }

    public static boolean isLogin() {
        return UserUtils.getUserStatus() != 0;
    }

//    public static void getNewInfo(final Context context){
//        final MemberDTO  dto=   MyDBUtil.getDbInstance(context).getUser();
//        if (dto==null) return;
//        Map<String,Object> params = new HashMap<>();
//        params.put("account",dto.getAccount());
//        params.put("token",dto.getToken());
//        params.put("method","get");
//        LogUtil.i("modifyinfoii",dto.getToken());
//        XUtil.Post(InterfaceFinals.GET_NEW_INFO, params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                judeStatus(result,dto,context);
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//            }
//
//            @Override
//            public void onFinished() {
//            }
//        });
//    }

//    private static void judeStatus(String result, MemberDTO memberDTO, Context context) {
//        try{
//            LogUtil.i("modifyinfoii",result);
//            JSONObject jsonObject  = new JSONObject(result);
//            String status  = jsonObject.optString("status");
//                if ("success".equals(status)) {
//                    memberDTO.setPic(jsonObject.optString("pic"));
//                    memberDTO.setUsername(jsonObject.optString("username"));
//                    memberDTO.setCountry(jsonObject.getJSONObject("country_name").optString("number"));
//                    memberDTO.setSex(jsonObject.optString("sex"));
//                    memberDTO.setContact(jsonObject.optString("contact"));
//                    memberDTO.setToken(jsonObject.optString("token"));
//                    MyDBUtil.getDbInstance(context).insertMember(memberDTO);
//                }else{
//                    ToastUtil.showToast(context,context.getString(R.string.error_login_infomation));
//                    MyDBUtil.getDbInstance(context).deleteMember();
//                    PreferencesUtil.setPreferences(context, PreferenceNames.IS_LOGIN,false);
//                    ActivityManager.getInstance().ExitApplication();
//                    Intent intent = new Intent(context,LoginActivity.class);
//                    intent.putExtra("account",memberDTO.getAccount());
//                    context.startActivity(intent);
//                }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000 && time - lastClickTime > 0) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public synchronized static boolean isRepeatClick(long times) {
        long time = System.currentTimeMillis();
        if (time - lastClickTimes < times) {
            return true;
        }
        lastClickTimes = time;
        return false;
    }

    /**
     * 判断是不是需要特殊处理的服务端code码
     *
     * @param status
     * @return
     */
    public static boolean isErrorCode(int status) {
        return (status == 110 || status == 107 || status == 106 || status == 100000011);
    }

    //是否登录
    public static void setUserStatus(int userLevel) {
        SharedPreferencesUtils.setParam(MyApplication.getInstance().getContext(), U_STATUS, userLevel);
    }

    public static int getUserStatus() {
        return  (int) SharedPreferencesUtils.getParam(MyApplication.getInstance().getContext(), U_STATUS, 0);
    }

    public static int getUserSettingLanguage(String key) {
        int languageMode;
        if (SharedPreferencesUtils.isHasParam(MyApplication.getInstance(), key)) { //使用用户设置语言
            languageMode = (int) SharedPreferencesUtils.getParam(MyApplication.getInstance().getContext(), key, 0);
        } else { //使用系统设置语言
            String _id = getSystemSettingLanguage(MyApplication.getInstance().getContext());

            if ("zh".equals(_id)) {
                languageMode = LanguageUtils.MODE_CHINES_LANGUAGE;
            } else if ("jp".equals(_id)) {
                languageMode = LanguageUtils.MODE_JAPANESE_LANGUAGE;
            } else if ("ko".equals(_id)) {
                languageMode = LanguageUtils.MODE_KOREAN_LANGUAGE;
            } else if ("hk".equals(_id)) {
                languageMode = LanguageUtils.MODE_HONGKONG_LANGUAGE;
            } else if ("tw".equals(_id)) {
                languageMode = LanguageUtils.MODE_TAIWAN_LANGUAGE;
            } else if ("en".equals(_id)) {
                languageMode = LanguageUtils.MODE_ENGLISH_LANGUAGE;
            } else if ("fr".equals(_id)) {
                languageMode = LanguageUtils.MODE_FRANCE_LANGUAGE;
            } else { //当前手机系统为非中文，加载英文
                languageMode = LanguageUtils.MODE_ENGLISH_LANGUAGE;
            }
        }

        return languageMode;
    }


    public static String getSystemSettingLanguage(Context context) {
        String systemLanguage = "";
        if (context.getResources().getConfiguration().locale.getCountry().equalsIgnoreCase("CN")) {
            systemLanguage = "zh";
        } else if (context.getResources().getConfiguration().locale.getCountry().equalsIgnoreCase("JP")) {
            systemLanguage = "jp";
        } else if (context.getResources().getConfiguration().locale.getCountry().equalsIgnoreCase("KO")) {
            systemLanguage = "ko";
        } else if (context.getResources().getConfiguration().locale.getCountry().equalsIgnoreCase("HK")) {
            systemLanguage = "hk";
        } else if (context.getResources().getConfiguration().locale.getCountry().equalsIgnoreCase("TW")) {
            systemLanguage = "tw";
        } else if (context.getResources().getConfiguration().locale.getCountry().equalsIgnoreCase("EN")) {
            systemLanguage = "en";
        } else if (context.getResources().getConfiguration().locale.getCountry().equalsIgnoreCase("FR")) {
            systemLanguage = "fr";
        } else { //当前手机系统为非中文，加载英文网页
            systemLanguage = "en";
        }

        return systemLanguage;
    }

    //获取当前系统语言
    public static int getCurrentLanguage(){
        String language = SystemUtil.getSystemLanguage();
        if(language.endsWith("zh")){//0是代表中文
            return 0;
        }else{
            return 1;
        }
    }

    public static String getUserName() {
        return (String) SharedPreferencesUtils.getParam(MyApplication.getInstance().getContext(), "user_name", UiUtil.getString(R.string.mine));
    }

    public static void setUserName(String userName) {
        if (!TextUtils.isEmpty(userName))
            SharedPreferencesUtils.setParam(MyApplication.getInstance().getContext(), "user_name", userName);
    }

    //sessionId
    public static void setSessionId(String sessionId) {
        SESSIONID_ID = sessionId;
        SPUtil.put(MyApplication.getInstance().getContext(), InterfaceFinals.SP_SESSIONID, sessionId);
    }

    private static String SESSIONID_ID = null;

    public static String getSessionId() {
        if (TextUtils.isEmpty(SESSIONID_ID)) {
            SESSIONID_ID = (String) SPUtil.get(MyApplication.getInstance().getContext(), InterfaceFinals.SP_SESSIONID,
                    "");
        }
        return SESSIONID_ID;
    }

    public static void showTipsDialog(Activity activity, String tips) {
        new MaterialDialogBuilderL(activity).content(tips)
                .cancelable(false)
                .positiveText(R.string.agree)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        ActivityManager.getInstance().finishActivity();
                    }
                })
                .negativeText(R.string.disagree)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    }
                })
                .show();
    }

    /**
     * 用户退出登录操作
     */
    public static void hanleLogout() {
    /*-----------------友盟账号统计-start--------------------------*/
//        LogEvent.onProfileSignOff();
    /*-----------------友盟账号统计-end--------------------------*/
//        MyOKHttpUtils.getIntance().oKHttpGet(URLUtil.LOG_OUT, "UserUtis", new MyCallback() {
//            @Override
//            public void onError(okhttp3.Call call, Exception e, int id) {
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//            }
//        });
        ActivityManager.getInstance().finishAllActivity();
        SharedPreferencesUtils.setPreferences(MyApplication.getInstance().getContext(), PreferenceNames.IS_LOGIN,false);
        clearUserData(MyApplication.getInstance().getContext());
        setUserID("0");//fix by dingchenghao 退出登录应该清空用户id，由于该函数内部做了空判断
//        UserUtils.setUserStatus(0);
        setSessionId("");
        MyApplication.isAgreeUserClause = true;
//        UserUtils.setUserHeadPath("");
//        UserUtils.setUserIntroduce("");
//        UserUtils.setUserNickname(UiUtil.getString(R.string.un_login));
//        UserUtils.setUserLevel(1);
//        SPUtil.remove(MyApplication.getAppContext(), ServerConstants.U_LEVEL);
//        UserUtils.setUserGender(1);
//        EventBus.getDefault().post(new EventCenter(UavConstants.CODE_LOG_OUT, true));
    }

    private static void clearUserData(Context context) {
//        PreferencesUtil.removeParam(context, UavConstants.USER_ID);
        SharedPreferencesUtils.removeParam(context, KEY_NICKNAME);
        SharedPreferencesUtils.removeParam(context, KEY_AVATAR);
        SharedPreferencesUtils.removeParam(context, KEY_TEL);
        SharedPreferencesUtils.removeParam(context, KEY_ADDRESS);
        SharedPreferencesUtils.removeParam(context, KEY_SEX);
    }

    public static final String KEY_FIRST_OPEN= "key_first_open";
    public static final String KEY_LOCK_STYLE= "key_lock_style";
    public static final String KEY_CURRENT_LOCK_STYLE= "key_current_lock_style";

    public  static  boolean   isFirstOpen(Context context){
      return (boolean) SharedPreferencesUtils.getParam(context,KEY_FIRST_OPEN,true);
    }

    public  void  setFirstOpne(Context context,boolean isFirst){
        SharedPreferencesUtils.setParam(context,KEY_FIRST_OPEN,isFirst);
    }

    public static void setLockStyle(Context context,int position) {
        SharedPreferencesUtils.setParam(context,KEY_LOCK_STYLE,position);

    }
    public static int getLockStyle(Context context) {
     return (int) SharedPreferencesUtils.getParam(context,KEY_LOCK_STYLE,0);
    }
    public static void setCurrentLockStyle(Context context,int position) {
        SharedPreferencesUtils.setParam(context,KEY_CURRENT_LOCK_STYLE,position);

    }
    public static int getCurrentLockStyle(Context context) {
     return (int) SharedPreferencesUtils.getParam(context,KEY_CURRENT_LOCK_STYLE,0);
    }
}
