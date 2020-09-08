package com.statistical.time.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.statistical.time.R;
import com.statistical.time.application.MyApplication;
import com.statistical.time.base.BaseActivity;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.bean.BackgroundBean;
import com.statistical.time.bean.BirdayInfo;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.bean.WishInfo;
import com.statistical.time.common.Constants;
import com.statistical.time.common.EventBusCode;
import com.statistical.time.tool.LogUtil;
import com.statistical.time.tool.StringUtil;
import com.statistical.time.tool.SystemUtil;
import com.statistical.time.tool.TimeUtils;
import com.statistical.time.tool.UiUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddWishActivity extends BaseActivity {
    private TimePickerView pvCustomLunar;
    int styleType = 1;
    BackgroundBean mBackgroundBean;

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

        if (eventCenter != null) {
            if (eventCenter.getEventCode() == EventBusCode.SELECT_BG_FINISH) {
                mBackgroundBean = (BackgroundBean) eventCenter.getData();
                LogUtil.e("onEventComming", "onEventComming===    " + mBackgroundBean.theme + "    " + mBackgroundBean.index);
                tv_choose_bg.setText(mBackgroundBean.theme + "●" + StringUtil.int2chineseNum(Constants.getRealPosition(mBackgroundBean.index,styleType)));

            }else if (eventCenter.getEventCode()==EventBusCode.CODE_STYLE_FINISH){
                int  style = (int) eventCenter.getData();
                if (style!=styleType){
                    styleType=style;
                    mBackgroundBean = new BackgroundBean(Constants.getThemeByPosition(0,styleType), 1);
                    tv_choose_bg.setText(mBackgroundBean.theme + "●" + StringUtil.int2chineseNum(Constants.getRealPosition(mBackgroundBean.index,styleType)));
                    tv_add_type.setText("样式"+StringUtil.int2chineseNum(styleType));
                }

            }

        }

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_wish);
        unbinder = ButterKnife.bind(this);

        currentWish = (WishInfo) getIntent().getSerializableExtra("data");
        if (currentWish == null) {
            mBackgroundBean = new BackgroundBean(Constants.getThemeByPosition(0,styleType), 1);
        } else {
            mBackgroundBean = new BackgroundBean(Constants.getThemeByPosition(currentWish.index,currentWish.style), currentWish.index);
            Calendar calendar = Calendar.getInstance();
            calendar.set(currentWish.wishYear, currentWish.wishMonth, currentWish.wishDay);
            currentCalendar = calendar;
            tv_hope.setText(currentWish.wishName);
            tv_count.setText(currentWish.wishName.length() + "/20");
            et_name.setText(currentWish.name);
            styleType=currentWish.style;
        }


        tv_choose_bg.setText(mBackgroundBean.theme + "●" + StringUtil.int2chineseNum(Constants.getRealPosition(mBackgroundBean.index,styleType)));
        tv_add_type.setText("样式"+StringUtil.int2chineseNum(styleType));
        initLunarPicker();
        initStatusHeight();
    }
    private void initStatusHeight() {
        View status_bar_height =findViewById(R.id.status_bar_height);
        ViewGroup.LayoutParams layoutParams =  status_bar_height.getLayoutParams();
        layoutParams.height  = SystemUtil.getStatusBarHeight(this);
        status_bar_height.setLayoutParams(layoutParams);

    }

    @BindView(R.id.tv_choose_bg)
    TextView tv_choose_bg;
    @BindView(R.id.tv_add_type)
    TextView tv_add_type;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.rl_add_wish)
    public void addWishList() {
//        readyGo(AddWishListActivity.class);
        startActivityForResult(new Intent(this, AddWishListActivity.class), 1);
    }
    @OnClick(R.id.tv_add_type)
    public void addWishType() {

        Intent intent  =new Intent(this,ChooseTypeActivity.class);
        intent.putExtra("style",styleType);
        startActivity(intent);
    }


    @BindView(R.id.tv_hope)
    TextView tv_hope;
    @BindView(R.id.tv_count)
    TextView tv_count;
    @BindView(R.id.tv_hope_time)
    TextView tv_hope_time;
    @BindView(R.id.et_name)
    EditText et_name;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                String et = data.getStringExtra("content");
                // LogUtil.e("onActivityResult","onActivityResult==="+requestCode+"   "+resultCode+"   "+et);
                if (!TextUtils.isEmpty(et)) {
                    tv_hope.setText(et);
                    tv_count.setText(et.length() + "/20");
                } else {
                    tv_hope.setText("");
                    tv_count.setText("0/20");
                }
            }
        }

    }


    @Override
    @OnClick(R.id.iv_close)
    public void finish() {
        super.finish();
    }


    @OnClick(R.id.tv_hope_time)
    public void showHopeDialog() {
        pvCustomLunar.show();

    }

    @OnClick(R.id.tv_choose_bg)
    public void gotoChooseBackground() {
        Intent intent = new Intent(this, ChooseWishBgActivity.class);
        intent.putExtra("style", styleType);
        startActivity(intent);
    }


    Calendar currentCalendar;

    /**
     * 农历时间已扩展至 ： 1900 - 2100年
     */
    private void initLunarPicker() {

        if (currentCalendar == null) {
            Calendar selectedDate = Calendar.getInstance();//系统当前时间
            selectedDate.setTime(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 20));
            currentCalendar = selectedDate;
        }


        Calendar startDate;
        if (currentCalendar.getTime().getTime() < System.currentTimeMillis()) {
            startDate = currentCalendar;
        } else {
            startDate = Calendar.getInstance();
        }


        Calendar endDate = Calendar.getInstance();
        endDate.set(2070, 12, 12);
//        endDate.set(2099, 2, 28);
        tv_hope_time.setText(getTime(currentCalendar.getTime()));
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String time = getTime(date);
                tv_hope_time.setText(time);
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                currentCalendar = c;
            }
        })
                .setDate(currentCalendar)
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
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    WishInfo currentWish;

    @OnClick(R.id.tv_add)
    public void addWish() {
        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            UiUtil.showToast("请输入您的名字");
            return;
        }
        String hope = tv_hope.getText().toString().trim();
        if (TextUtils.isEmpty(hope)) {
            UiUtil.showToast("请选择您的愿望");
            return;
        }

        if (mBackgroundBean == null) {
            UiUtil.showToast(R.string.tv_select_bg);
            return;
        }

        if (currentWish == null) {
            currentWish = new WishInfo();
            currentWish.wishName = hope;
            currentWish.index = mBackgroundBean.index;
            currentWish.style = styleType;
            currentWish.name = name;
            currentWish.wishDay = currentCalendar.get(Calendar.DATE);
            currentWish.wishYear = currentCalendar.get(Calendar.YEAR);
            currentWish.wishMonth = currentCalendar.get(Calendar.MONTH);
            currentWish.isFinish = false;
            currentWish.createTime= TimeUtils.getTimeSecend(new Date());
            MyApplication.getInstance().getmDaoSession().getWishInfoDao().insert(currentWish);
        } else {
            currentWish.wishName = hope;
            currentWish.index = mBackgroundBean.index;
            currentWish.style = styleType;
            currentWish.name = name;
            currentWish.isFinish = false;
            currentWish.createTime=TimeUtils.getTimeSecend(new Date());
            currentWish.wishDay = currentCalendar.get(Calendar.DATE);
            currentWish.wishYear = currentCalendar.get(Calendar.YEAR);
            currentWish.wishMonth = currentCalendar.get(Calendar.MONTH);
            MyApplication.getInstance().getmDaoSession().getWishInfoDao().update(currentWish);
        }

        finish();
        EventBus.getDefault().post(new EventCenter(EventBusCode.CODE_ADD_FINISH));
    }

}
