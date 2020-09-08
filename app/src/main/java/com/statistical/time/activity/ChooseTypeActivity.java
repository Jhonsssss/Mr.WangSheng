package com.statistical.time.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.statistical.time.R;
import com.statistical.time.adapter.TypeAdapter;
import com.statistical.time.base.BaseActivity;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.common.EventBusCode;
import com.statistical.time.tool.SystemUtil;
import com.statistical.time.tool.UiUtil;
import com.statistical.time.widget.ZoomOutPageTransformer;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ChooseTypeActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager viewPager;

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_type);
        unbinder=  ButterKnife.bind(this);

        style =getIntent().getIntExtra("style",1);
        initViewpager();

        initStatusHeight();
    }
    private void initStatusHeight() {
        View status_bar_height =findViewById(R.id.status_bar_height);
        ViewGroup.LayoutParams layoutParams =  status_bar_height.getLayoutParams();
        layoutParams.height  = SystemUtil.getStatusBarHeight(this);
        status_bar_height.setLayoutParams(layoutParams);

    }

    int  style =1;
    private void initViewpager() {
        List<View> rootView = new ArrayList<>();
        addView(rootView);
        TypeAdapter myAdapter = new TypeAdapter(rootView);
        viewPager.setAdapter(myAdapter);
        viewPager.setOffscreenPageLimit(myAdapter.getCount());
        viewPager.setPageMargin(-UiUtil.Dp2Px(50));
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                style=position+1;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(style-1,false);

    }

    private void addView(List<View> rootView) {

      rootView.clear();
          View item_view1 =LayoutInflater.from(this).inflate(R.layout.item_type_1,null);
          rootView.add(item_view1);
          View item_view2 =LayoutInflater.from(this).inflate(R.layout.item_type_2,null);
          rootView.add(item_view2);
          View item_view3 =LayoutInflater.from(this).inflate(R.layout.item_type_3,null);
          rootView.add(item_view3);
          View item_view4 =LayoutInflater.from(this).inflate(R.layout.item_type_4,null);
          rootView.add(item_view4);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @Override
    @OnClick(R.id.iv_close)
    public void finish() {
        super.finish();
    }


    @OnClick(R.id.tv_finish)
    public  void  setFinish(){
        EventBus.getDefault().post(new EventCenter(EventBusCode.CODE_STYLE_FINISH,style));
        finish();
    }
}
