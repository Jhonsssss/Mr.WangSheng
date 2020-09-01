package com.statistical.time.tool;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class ToastUtil {

    public static Context mContext;

    public static void register(Context context) {
        mContext = context.getApplicationContext();
    }

    public static void showToast(int resId) {
        Toast.makeText(mContext, mContext.getString(resId), Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(int resId) {
        Toast.makeText(mContext, mContext.getString(resId), Toast.LENGTH_LONG).show();
    }

    public static void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示消息
     *
     * @param message 消息
     */
    private static Toast toast;
    private static long time;
    private static String mMessage;
    public static void showToast(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
            time = System.currentTimeMillis();
            mMessage = message;
            toast.show();
            Log.e("Toast","create_showtoast");
        } else {


//            if (message.equals(mMessage)) {
//                if (isNeedShow( System.currentTimeMillis())) {
            cancleToast();
            toast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
            toast.show();
//                    time = System.currentTimeMillis();
            Log.e("Toast","showtoast");
//                }else{
//                    toast.setText(message);
//                    Log.e("Toast","no_showtoast");
//
//                }
//            } else {
//                toast.setText(message);
//
//                toast.show();
//                time = System.currentTimeMillis();
//                mMessage = message;
//            }
        }
    }
    /**
     * 取消显示的toast
     */
    public static void cancleToast() {

        if (toast!=null){
            toast.cancel();
        }
    }
}
