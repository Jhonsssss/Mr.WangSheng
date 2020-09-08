package com.statistical.time.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.statistical.time.R;
import com.statistical.time.application.MyApplication;
import com.statistical.time.base.BaseActivity;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.bean.WishInfo;
import com.statistical.time.common.Constants;
import com.statistical.time.common.EventBusCode;
import com.statistical.time.tool.SystemUtil;
import com.statistical.time.tool.TimeUtils;
import com.statistical.time.widget.NetworkImageView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WishDetailActivity extends BaseActivity {
    @BindView(R.id.tv_hope_bg)
    SelectableRoundedImageView tv_hope_bg;

    @BindView(R.id.tv_wish_name)
    TextView tv_wish_name;
    @BindView(R.id.tv_wish_current_date)
    TextView tv_wish_current_date;
    @BindView(R.id.tv_wish_user)
    TextView tv_wish_user;
    @BindView(R.id.tv_wish_time_finished)
    TextView tv_wish_time_finished;
    @BindView(R.id.tv_tip)
    TextView tv_tip;
    @BindView(R.id.tv_wish_time)
    TextView tv_wish_time;
    @BindView(R.id.iv_wish_finish)
    ImageView iv_wish_finish;
    @BindView(R.id.checkbox)
    CheckBox checkbox;



    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

        if (eventCenter!=null){

            if (eventCenter.getEventCode()==EventBusCode.CODE_ADD_FINISH){
                finish();
            }
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    WishInfo wishInfo;
    Unbinder unbinder;

    int style;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wishInfo = (WishInfo) getIntent().getSerializableExtra("data");
       style =  wishInfo.style;
       if (style==1){
           setContentView(R.layout.activity_wish_detail_1);
       }else if (style==2){
           setContentView(R.layout.activity_wish_detail_2);
       }else if (style==3){
           setContentView(R.layout.activity_wish_detail_3);
       }else{
           setContentView(R.layout.activity_wish_detail_4);
       }
        unbinder=  ButterKnife.bind(this);

       initData();
        initStatusHeight();
    }
    private void initStatusHeight() {
        View status_bar_height =findViewById(R.id.status_bar_height);
        ViewGroup.LayoutParams layoutParams =  status_bar_height.getLayoutParams();
        layoutParams.height  = SystemUtil.getStatusBarHeight(this);
        status_bar_height.setLayoutParams(layoutParams);

    }

    private void initData() {
        if (wishInfo!=null){
            tv_hope_bg .setImageResource(Constants.getRes(wishInfo.index,wishInfo.style)[1]);
            tv_wish_name.setText(wishInfo.wishName);

            tv_wish_current_date.setText(getTime(new Date()));

            tv_wish_user.setText(wishInfo.name);

            iv_wish_finish.setVisibility(wishInfo.isFinish? View.VISIBLE:View.GONE);
            tv_tip.setText(wishInfo.isFinish?"重新如愿":"点击已如愿");

            Calendar calendar=Calendar.getInstance();
            calendar.set(wishInfo.wishYear,wishInfo.wishMonth,wishInfo.wishDay);

            if (wishInfo.isFinish){
                tv_wish_time_finished.setText("于"+wishInfo.finishTime+"如愿");
            }else {

              long   time =     calendar.getTime().getTime()-System.currentTimeMillis();
                int day = (int) (time/1000/60/60/24);
                tv_wish_time_finished.setText("还剩下"+day+"天");
            }


            tv_wish_time.setText("心愿期限:"+getTime(calendar.getTime()));


            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                     wishInfo.isFinish=  !wishInfo.isFinish;
                      if (wishInfo.isFinish){
                          wishInfo.finishTime=getTime(new Date());
                          wishInfo.createTime= TimeUtils.getTimeSecend(new Date());
                      }
                    MyApplication.getInstance().getmDaoSession().getWishInfoDao().update(wishInfo);
                    finish();
                    EventBus.getDefault().post(new EventCenter(EventBusCode.CODE_ADD_FINISH));
                }
            });
        }


    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());

        SimpleDateFormat format;
//        if (style==2){
//            format = new SimpleDateFormat("MMM.dd  yyyy", Locale.ENGLISH);
//        }else {
            format = new SimpleDateFormat("yyyy-MM-dd");
//        }

        return format.format(date);
    }

    @Override
    @OnClick(R.id.iv_close)
    public void finish() {
        super.finish();
    }



    @OnClick(R.id.iv_edit)
    public  void  editPage(){
      Intent intent =    new Intent(this, AddWishActivity.class);
      intent.putExtra("data",wishInfo);
      startActivity(intent);

    }
}
