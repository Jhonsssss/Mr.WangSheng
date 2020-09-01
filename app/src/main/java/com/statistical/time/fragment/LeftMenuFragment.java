package com.statistical.time.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.statistical.time.R;
import com.statistical.time.activity.AboutActivity;
import com.statistical.time.activity.ClockStyleActivity;
import com.statistical.time.activity.EditBirthdayActivity;
import com.statistical.time.activity.EventActivity;
import com.statistical.time.activity.LockScreenStyleActivity;
import com.statistical.time.activity.SettingActivity;
import com.statistical.time.activity.WebViewActivity;
import com.statistical.time.base.BaseFragment;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.tool.LocaleLanguageUtil;
import com.statistical.time.tool.UiUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LeftMenuFragment extends BaseFragment {
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View rootView =inflater.inflate(R.layout.fragment_left_menu,null);
        unbinder=   ButterKnife.bind(this,rootView);
        return rootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.ll_edit_birthday)
    public void  editBirthday(){
        startActivity(new Intent(getActivity(), EditBirthdayActivity.class));
    }
    @OnClick(R.id.ll_edit_event)
    public void  editEvent(){
        startActivity(new Intent(getActivity(), EventActivity.class));
    }

    @OnClick(R.id.ll_clock_style)
    public void  clockEvent(){
        startActivity(new Intent(getActivity(), ClockStyleActivity.class));
    }
    @OnClick(R.id.ll_about)
    public void  about(){
        startActivity(new Intent(getActivity(), AboutActivity.class));
    }

    @OnClick(R.id.ll_setting)
    public void  setting(){
        startActivity(new Intent(getActivity(), SettingActivity.class));
    }
    @OnClick(R.id.ll_lock_style)
    public void  lockStyle(){
        startActivity(new Intent(getActivity(), LockScreenStyleActivity.class));
    }
    @OnClick(R.id.ll_weibo)
    public void  weibo(){
        UiUtil.showToast("暂未开启");
    }
    @OnClick(R.id.ll_qq)
    public void  qq(){
        if (isQQClientAvailable(getActivity())){
            String url = "mqqwpa://im/chat?chat_type=wpa&uin=474163161";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }else{
            UiUtil.showToast("QQ未安装");
        }

    }
    @OnClick(R.id.ll_help)
    public void  help(){
        String url = "file:///android_asset/html/test.html";
        WebViewActivity.gotoWebViewActivity(getActivity(), url, true);
    }

    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

}
