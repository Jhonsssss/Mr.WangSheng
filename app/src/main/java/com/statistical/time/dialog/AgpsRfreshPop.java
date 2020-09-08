package com.statistical.time.dialog;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;

import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.statistical.time.R;
import com.statistical.time.tool.UiUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 提示Pop
 */
public class AgpsRfreshPop {

  @BindView(R.id.tv_agpstips) TextView tvAgpsTips;

  private        Context       context;
  private        PopupWindow   mPopupWindow;
  private        int           tips;
  private        ImageView     anchor;
  private static AgpsRfreshPop instance;

  public AgpsRfreshPop(Context context, ImageView ivAgpsCheck) {
    this.context = context;
    this.anchor = ivAgpsCheck;
  }

  public synchronized static AgpsRfreshPop getInstance(Context context, ImageView ivAgpsCheck) {
    if (null == instance) {
      instance = new AgpsRfreshPop(context, ivAgpsCheck);
    }
    return instance;
  }

  public void dismiss() {
    if (mPopupWindow != null) {
      mPopupWindow.dismiss();
      mPopupWindow = null;
    }
  }

  public void show(int tipsId, final AppCompatActivity activity) {
    this.tips = tipsId;
    createPopDialog();
    anchor.post(new Runnable() {
      @Override public void run() {
        if(activity==null||activity.isFinishing()){
          mPopupWindow = null;
          return;
        }
        if (null != mPopupWindow) {
          try {
            int[] location = new int[2];
            anchor.getLocationOnScreen(location);
            mPopupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY,
                location[0] + anchor.getHeight(), location[1] - UiUtil.Dp2Px(8));
          } catch (Exception e) {
            e.getStackTrace();
          }
        }
      }
    });
  }

  private void createPopDialog() {
    dismiss();
    View foot_popunwindwow = null;
    LayoutInflater LayoutInflater =
        (android.view.LayoutInflater) context.getSystemService(AppCompatActivity.LAYOUT_INFLATER_SERVICE);
    foot_popunwindwow = LayoutInflater.inflate(R.layout.pop_agps_tips_center, null);
    ButterKnife.bind(this, foot_popunwindwow);
    mPopupWindow = new PopupWindow(foot_popunwindwow, ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT, true);
    mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
    // 设置点击窗口外边窗口消失
    mPopupWindow.setOutsideTouchable(true);
    mPopupWindow.setFocusable(false);//不抢占按钮事件
    mPopupWindow.setAnimationStyle(R.style.agps_popwin_anim_style);
    tvAgpsTips.setText(UiUtil.getContext().getString(tips));
//    mPopupWindow.update();
  }

  /**
   * 清除之前对象  防止重置引导 add windown token erro
   */
  public void clearInstance() {
    if (instance != null) {
      dismiss();
      instance = null;
    }
  }
}
