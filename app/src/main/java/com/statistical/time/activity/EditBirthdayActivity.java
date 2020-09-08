package com.statistical.time.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.statistical.time.R;
import com.statistical.time.application.MyApplication;
import com.statistical.time.base.BaseActivity;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.bean.BirdayInfo;
import com.statistical.time.bean.CardBean;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.common.Constants;
import com.statistical.time.common.EventBusCode;
import com.statistical.time.tool.SystemUtil;
import com.statistical.time.tool.UiUtil;
import com.statistical.time.tool.UserUtils;
import com.statistical.time.widget.Watch;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EditBirthdayActivity extends BaseActivity {
    private TimePickerView pvCustomLunar;
    private OptionsPickerView<CardBean> pvCustomOptions;

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @BindView(R.id.watch)
    Watch watch;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;


    @OnClick(R.id.tv_start_time)
    public void showStartTime() {
        pvCustomLunar.show();
    }

    BirdayInfo birdayInfo ;
    boolean isAdd;
    /**
     * 农历时间已扩展至 ： 1900 - 2100年
     */
    private void initLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
      List<BirdayInfo> list = MyApplication.getInstance().getmDaoSession().getBirdayInfoDao().queryBuilder().list();
        if (list!=null&&list.size()>0){
            birdayInfo =list.get(0);
            selectedDate.set(birdayInfo.year,birdayInfo.month,birdayInfo.day,birdayInfo.hour,birdayInfo.minute,birdayInfo.secend);
            tv_end_time.setText(String.valueOf(birdayInfo.max_age));
            isAdd=false;
        }else{
            isAdd=true;
            selectedDate.set(2015,5,1,8,0,0);
            birdayInfo =new BirdayInfo();
            birdayInfo.day=selectedDate.get(Calendar.DATE);
            birdayInfo.year=selectedDate.get(Calendar.YEAR);
            birdayInfo.month=selectedDate.get(Calendar.MONTH);
            birdayInfo.hour=selectedDate.get(Calendar.HOUR);
            birdayInfo.minute=selectedDate.get(Calendar.MINUTE);
            birdayInfo.secend=selectedDate.get(Calendar.SECOND);
            birdayInfo.max_age=80;
        }

        Calendar startDate = Calendar.getInstance();
        startDate.set(1949, 10, 1);
        Calendar endDate = Calendar.getInstance();
//        endDate.set(2099, 2, 28);
        tv_start_time.setText(getTime(selectedDate.getTime()));
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调

                Calendar calendar =Calendar.getInstance();
                calendar.setTime(date);
                birdayInfo.day=calendar.get(Calendar.DATE);
                birdayInfo.year=calendar.get(Calendar.YEAR);
                birdayInfo.month=calendar.get(Calendar.MONTH);
                birdayInfo.hour=calendar.get(Calendar.HOUR);
                birdayInfo.minute=calendar.get(Calendar.MINUTE);
                birdayInfo.secend=calendar.get(Calendar.SECOND);
                String time = getTime(date);
                    tv_start_time.setText(time);

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
                                birdayInfo.setIsLunar(pvCustomLunar.isLunarCalendar());
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
    @OnClick(R.id.tv_end_time)
    public void showEndTime() {//选年龄
        getCardData();
        pvCustomOptions.setPicker(cardItem);
        pvCustomOptions.show();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_birday);
         unbinder = ButterKnife.bind(this);
         watch.start(Constants.LIGHT_MODE);
        watch.setWatchBgRes(R.mipmap.clock_view2_bg,0,0,0);
        initLunarPicker();
        initCustomOptionPicker();
        initStatusHeight();
    }
    private void initStatusHeight() {
        View status_bar_height =findViewById(R.id.status_bar_height);
        ViewGroup.LayoutParams layoutParams =  status_bar_height.getLayoutParams();
        layoutParams.height  = SystemUtil.getStatusBarHeight(this);
        status_bar_height.setLayoutParams(layoutParams);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        watch.stop();
        unbinder.unbind();
    }

    private void initCustomOptionPicker() {//条件选择器初始化，自定义布局
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = cardItem.get(options1).getPickerViewText();
                birdayInfo.max_age =Integer.parseInt(tx);
                tv_end_time.setText(tx);
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
//                        final TextView tvAdd = (TextView) v.findViewById(R.id.tv_add);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });

                    }
                })
                .setSelectOptions(birdayInfo.max_age-20)
                .isDialog(true)
                .setOutSideCancelable(false)
                .build();

        pvCustomOptions.setPicker(cardItem);//添加数据


    }

    private ArrayList<CardBean> cardItem = new ArrayList<>();
    private void getCardData() {
        cardItem.clear();
        for (int i = 0; i < 130; i++) {
            cardItem.add(new CardBean(i, String.valueOf(20+i)));
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    @OnClick(R.id.tv_jisuan_time)
    public void jisuan() {//完成

           if (isAdd){
               MyApplication.getInstance().getmDaoSession().insert(birdayInfo);
               readyGo(MainActivity.class);
           }else{
               MyApplication.getInstance().getmDaoSession().update(birdayInfo);
               EventBus.getDefault().post(new EventCenter(EventBusCode.EDIT_BIRTHDAY_FINISH));

           }
           finish();

    }
}
