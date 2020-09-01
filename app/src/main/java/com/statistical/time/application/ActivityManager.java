package com.statistical.time.application;


import android.app.Activity;

import java.util.Stack;


/**
 * ActivityManager app的activity的管理类
 * <p/>
 * Created by tianlai on 16-3-7.
 */
public class ActivityManager {

    //Activity 管理
    private static Stack<Activity> activityStack;

    public ActivityManager() {
        activityStack = new Stack<Activity>();
    }

    private  static ActivityManager inStance=null;
    public static ActivityManager  getInstance(){
              if (inStance==null){
                  inStance=new ActivityManager();
              }
               return  inStance;
    }
    /**
     * 将activity加入列表
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 将activity移除列表
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 退出程序
     */
    public void ExitApplication() {
        try {
            finishAllActivity();
            System.exit(0);
        } catch (Exception e) {
        }
        // 杀死进程
       // android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        try {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    finishActivity(activity);
                    return;
                }
            }
        }catch (Exception e){
            e.getMessage();
        }

    }

    /**
     * 判断一个Activity 是否存在
     *
     * @param clz
     * @return
     */
    public boolean isActivityExist(Class<?> clz) {
        try {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(clz)) {
                    return true;
                }
            }
        }catch (Exception e){
            e.getMessage();
        }

        return  false;
    }

}
