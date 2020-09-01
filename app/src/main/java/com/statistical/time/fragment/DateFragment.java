package com.statistical.time.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.statistical.time.R;
import com.statistical.time.base.BaseFragment;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.tool.UiUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DateFragment extends BaseFragment {
    private TimePickerView pvCustomLunar;

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
        View rootView = inflater.inflate(R.layout.fragment_date, null);
        unbinder = ButterKnife.bind(this, rootView);

        //#

//        ShadowConfig.Builder config = new ShadowConfig.Builder()
//                .setColor(0xffFA3773)//View颜色
//                .setShadowColor(0x50FA3773)//阴影颜色
//              //  .setGradientColorArray(new int[]{0xff000000,0xffff0000})//如果View是渐变色，则设置color数组
//                .setRadius(UiUtil.Dp2Px(20f))//圆角
//               // .setOffsetX(UiUtil.Dp2Px(0))//横向偏移
//                .setOffsetY(UiUtil.Dp2Px(8));//纵向偏移
//
//        ShadowHelper.setShadowBgForView(tv_jisuan,config);
        initLunarPicker();
        return rootView;

    }

    @BindView(R.id.tv_year)
    TextView tv_year;
    @BindView(R.id.tv_month)
    TextView tv_month;
    @BindView(R.id.tv_week)
    TextView tv_week;
    @BindView(R.id.tv_day)
    TextView tv_day;
    @BindView(R.id.tv_hour)
    TextView tv_hour;
    @BindView(R.id.tv_min)
    TextView tv_min;
    @BindView(R.id.tv_second)
    TextView tv_second;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.tv_jisuan_time)
    TextView tv_jisuan;


    @OnClick(R.id.tv_start_time)
    public void showStartTime() {
        type = 1;
        pvCustomLunar.show();
    }

    @OnClick(R.id.tv_end_time)
    public void showEndTime() {
        type = 2;
        pvCustomLunar.show();
    }


    @OnClick(R.id.tv_jisuan_time)
    public void jisuan() {
        if (startTime > endTime) {
            UiUtil.showToast("结束时间早于开始时间");
            return;
        }

        long time = endTime - startTime;
        tv_year.setText(String.format("%.2f", 1.0f * time / 1000 / 60 / 60 / 24 / 365) + "年");
        tv_month.setText(String.format("%.2f", 1.0f * time / 1000 / 60 / 60 / 24 / 30) + "月");
        tv_week.setText(String.format("%.2f", 1.0f * time / 1000 / 60 / 60 / 24 / 7) + "周");
        tv_day.setText(time / 1000 / 60 / 60 / 24 + "天");
        tv_hour.setText(time / 1000 / 60 / 60 + "小时");
        tv_min.setText(time / 1000 / 60 + "分钟");
        tv_second.setText(time / 1000 + "秒");


    }

    int type = 1;
    long startTime = System.currentTimeMillis(), endTime = System.currentTimeMillis();


    /**
     * 农历时间已扩展至 ： 1900 - 2100年
     */
    private void initLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1949, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2099, 2, 28);
        tv_start_time.setText(getTime(new Date()));
        tv_end_time.setText(getTime(new Date()));
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String time = getTime(date);
                if (type == 1) {
                    tv_start_time.setText(time);
                    startTime = date.getTime();
                } else if (type == 2) {
                    tv_end_time.setText(time);
                    endTime = date.getTime();
                }

            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_lunar, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.returnData();
                                pvCustomLunar.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.dismiss();
                            }
                        });
                        //公农历切换
                        CheckBox cb_lunar = (CheckBox) v.findViewById(R.id.cb_lunar);
                        cb_lunar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                pvCustomLunar.setLunarCalendar(!pvCustomLunar.isLunarCalendar());
                                //自适应宽
                                setTimePickerChildWeight(v, isChecked ? 0.8f : 1f, isChecked ? 1f : 1.1f);
                            }
                        });

                    }

                    /**
                     * 公农历切换后调整宽
                     * @param v
                     * @param yearWeight
                     * @param weight
                     */
                    private void setTimePickerChildWeight(View v, float yearWeight, float weight) {
                        ViewGroup timePicker = (ViewGroup) v.findViewById(R.id.timepicker);
                        View year = timePicker.getChildAt(0);
                        LinearLayout.LayoutParams lp = ((LinearLayout.LayoutParams) year.getLayoutParams());
                        lp.weight = yearWeight;
                        year.setLayoutParams(lp);
                        for (int i = 1; i < timePicker.getChildCount(); i++) {
                            View childAt = timePicker.getChildAt(i);
                            LinearLayout.LayoutParams childLp = ((LinearLayout.LayoutParams) childAt.getLayoutParams());
                            childLp.weight = weight;
                            childAt.setLayoutParams(childLp);
                        }
                    }
                })
                .setType(new boolean[]{true, true, true, true, true, true})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
