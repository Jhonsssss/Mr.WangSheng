package com.statistical.time.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.statistical.time.R;
import com.statistical.time.base.BaseActivity;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.tool.CommonUtils;
import com.statistical.time.tool.SystemUtil;
import com.statistical.time.tool.UiUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AboutActivity extends BaseActivity {
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
    @BindView(R.id.tv_version_name)
    TextView tv_version_name;
    Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
         unbinder = ButterKnife.bind(this);

        initStatusHeight();
         initView();
    }
    private void initStatusHeight() {
        View status_bar_height =findViewById(R.id.status_bar_height);
        ViewGroup.LayoutParams layoutParams =  status_bar_height.getLayoutParams();
        layoutParams.height  = SystemUtil.getStatusBarHeight(this);
        status_bar_height.setLayoutParams(layoutParams);

    }
    private void initView() {
        String appCurrentVersionName = CommonUtils.getVersionName(AboutActivity.this);
        tv_version_name.setText(getString(R.string.app_name)+"   V"+appCurrentVersionName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_text_update)
    public  void  appUpdate(){
        if (UiUtil.isFastClick()) return;
        UiUtil.showToast("已是最新版本");
    }

    @OnClick(R.id.iv_close)
    @Override
    public void finish() {
        super.finish();
    }
}
