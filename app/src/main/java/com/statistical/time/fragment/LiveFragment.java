package com.statistical.time.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.utils.ChinaDate;
import com.statistical.time.R;
import com.statistical.time.activity.MainActivity;
import com.statistical.time.adapter.RestCountAdapter;
import com.statistical.time.application.MyApplication;
import com.statistical.time.base.BaseFragment;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.bean.BirdayInfo;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.common.Constants;
import com.statistical.time.common.EventBusCode;
import com.statistical.time.tool.LogUtil;
import com.statistical.time.tool.UiUtil;
import com.statistical.time.widget.MyRecyclerViewDivider;
import com.statistical.time.widget.Watch;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LiveFragment extends BaseFragment {
    private Unbinder unbinder;

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {
        if (eventCenter != null) {
            switch (eventCenter.getEventCode()) {
                case EventBusCode.EDIT_BIRTHDAY_FINISH:
                    getBirdayMill();
//                    setBirthDayRest();
                    break;
                default:

                    break;

            }


        }


    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }


    @BindView(R.id.watch)
    Watch watch;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.tv_birthday_tip)
    TextView tv_birthday_tip;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_live, null);
        unbinder = ButterKnife.bind(this, rootView);
        getBirdayMill();
        setBirthDayRest();
        initRecyclerView();
        watch.start(Constants.LIGHT_MODE);
        watch.setWatchBgRes(R.mipmap.clock_view2_bg,0,0,0);
        watch.setOnTimeChangeListener(new Watch.OnTimeChangeListener() {
            @Override
            public void onchange() {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventCenter(EventBusCode.CODE_CLOCK_SENCENDS));
                        setTimeUi();
                    }
                });

            }
        });


        return rootView;
    }


    public  void  getBirdayMill(){
        List<BirdayInfo> list = MyApplication.getInstance().getmDaoSession().getBirdayInfoDao().queryBuilder().list();
        if (list != null && list.size() > 0) {
            BirdayInfo birdayInfo = list.get(0);
            max_age = birdayInfo.max_age;
            Calendar birday_calendar = Calendar.getInstance();
            birday_calendar.set(birdayInfo.year, birdayInfo.month, birdayInfo.day, birdayInfo.hour, birdayInfo.minute, birdayInfo.secend);
            birdayTime = birday_calendar.getTimeInMillis();
        }
    }

    private void setBirthDayRest() {

        List<BirdayInfo> list = MyApplication.getInstance().getmDaoSession().getBirdayInfoDao().queryBuilder().list();
        int birth_year;
        if (list != null && list.size() > 0) {

            Calendar calendar = Calendar.getInstance();
            if (calendar.get(Calendar.MONTH) > list.get(0).month || (calendar.get(Calendar.MONTH) == list.get(0).month && calendar.get(Calendar.DATE) > list.get(0).day)) {//生日已经过了 明年了

                calendar.set(calendar.get(Calendar.YEAR) + 1, list.get(0).month, list.get(0).getDay());
            } else {

                calendar.set(calendar.get(Calendar.YEAR), list.get(0).month, list.get(0).getDay());
            }

            birth_year = calendar.get(Calendar.YEAR) - list.get(0).year;
//            if (birdayInfo.getIsLunar()){
//
//                String  oneDay = ChinaDate.oneDay(birdayInfo.year, birdayInfo.month, birdayInfo.day);
//                Log.e("oneDay","oneDay==="+oneDay);
//
//                long[] c =ChinaDate.calElement(2019,11,3);
//                String  td =ChinaDate.getCurrentLunarDate();
//
//                Log.e("td","td==="+td);
//            }else{

                int rest_date = (int) ((calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000 / 60 / 60 / 24);
                if (rest_date==0){
                    tv_birthday_tip.setText("您今天生日了！！！");
                }else if (rest_date<30){
                    tv_birthday_tip.setText(String.format("距离你%1d岁生日还有%2d天！", birth_year,rest_date));
                }else{
                    double total_year = 1.0*(System.currentTimeMillis()-birdayTime)/1000/60/60/24/365;

                 //   LogUtil.ws(total_year+"    "+System.currentTimeMillis()+"  "+birdayTime);
                    tv_birthday_tip.setText(String.format("%.8f岁",total_year));


                }


//            }



        }
    }


    long birdayTime;
    long max_age = 100;

    private void setTimeUi() {
        mdata.clear();
        setBirthDayRest();
        long time = System.currentTimeMillis() - birdayTime;
        mdata.add("约" + (time / 1000 / 60 / 60 / 24 / 365) + "年");
        mdata.add(time / 1000 / 60 / 60 / 24 / 30 + "月");
        mdata.add(time / 1000 / 60 / 60 / 24 / 7 + "周");
        mdata.add(time / 1000 / 60 / 60 / 24 + "天");
        mdata.add(time / 1000 / 60 / 60 + "小时");
        mdata.add(time / 1000 / 60 + "分钟");
        mdata.add(time / 1000 + "秒");
        int progress = (int) (1.0f * time / (max_age * 365 * 24 * 60 * 60 * 1000) * 100);
        if (progress<0){
            progress=0;
        }else if (progress>100){
            progress=100;
        }
        adapter.setProgress(progress);
        adapter.notifyDataSetChanged();

    }

    RestCountAdapter adapter;
    List<String> mdata = new ArrayList<>();

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.addItemDecoration(new MyRecyclerViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, UiUtil.Dp2Px(5), ContextCompat.getColor(getActivity(), R.color.white)));
        adapter = new RestCountAdapter(20, Constants.LIGHT_MODE, mdata);
        recyclerView.setAdapter(adapter);

        setTimeUi();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        watch.stop();
        unbinder.unbind();
    }


    @OnClick(R.id.iv_setting)
    public void showSettingDrawable() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.showDrawer();

    }


}
