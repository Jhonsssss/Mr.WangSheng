package com.statistical.time.fragment;

import android.content.Intent;
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

import com.statistical.time.R;
import com.statistical.time.activity.EventActivity;
import com.statistical.time.activity.MainActivity;
import com.statistical.time.adapter.RestCountAdapter;
import com.statistical.time.application.MyApplication;
import com.statistical.time.base.BaseFragment;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.bean.BirdayInfo;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.bean.EventInfo;
import com.statistical.time.common.Constants;
import com.statistical.time.common.EventBusCode;
import com.statistical.time.tool.UiUtil;
import com.statistical.time.widget.MyRecyclerViewDivider;
import com.statistical.time.widget.Watch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DeadFragment extends BaseFragment {
    private RestCountAdapter adapter;
    private int max_age;
    private long deadTime;

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {
        if (eventCenter != null) {
            switch (eventCenter.getEventCode()) {
                case EventBusCode.EDIT_BIRTHDAY_FINISH:

                    setDeadDayRest();
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
    @BindView(R.id.tv_rest_year)
    TextView tv_rest_year;
    @BindView(R.id.tv_rest_percent)
    TextView tv_rest_percent;
    @BindView(R.id.empty_view)
    TextView empty_view;
    @BindView(R.id.iv_rest_battery)
    ImageView iv_rest_battery;
    Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_dead,null);
        unbinder =    ButterKnife.bind(this,root);
        setDeadDayRest();
        initRecyclerView();
        watch.start(Constants.DRAK_MODE);
        watch.setWatchBgRes(R.mipmap.clock_view4_bg,0,0,0);
        watch.setOnTimeChangeListener(new Watch.OnTimeChangeListener() {
            @Override
            public void onchange() {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                          getActivity().runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  setTimeUi();
                              }
                          });
                    }
                });

            }
        });

        return root;
    }


    private void setDeadDayRest() {

        List<BirdayInfo> list = MyApplication.getInstance().getmDaoSession().getBirdayInfoDao().queryBuilder().list();
        if (list != null && list.size() > 0) {
            BirdayInfo birdayInfo = list.get(0);
            max_age = birdayInfo.max_age;
            Calendar dead_calendar = Calendar.getInstance();
            dead_calendar.set(birdayInfo.year+max_age, birdayInfo.month, birdayInfo.day, birdayInfo.hour, birdayInfo.minute, birdayInfo.secend);
            deadTime = dead_calendar.getTimeInMillis();
        }
    }


    List<String> mdata= new ArrayList<>();

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.addItemDecoration(new MyRecyclerViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, UiUtil.Dp2Px(5), ContextCompat.getColor(getActivity(), R.color.drak)));
        adapter = new RestCountAdapter(20, Constants.DRAK_MODE, mdata);
        adapter.setOnItemClickListener(new RestCountAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                gotoEvent();
            }
        });
        recyclerView.setAdapter(adapter);

        setTimeUi();
    }

    private void setTimeUi() {

        double rest_year =(deadTime - System.currentTimeMillis())*1.0 / 1000 / 60 / 60 / 24/365;
        tv_rest_year.setText(String.format("%.8f", rest_year));

        int percent = (int) (rest_year/max_age*100);
        tv_rest_percent.setText(percent+"%");
        int progress=100-percent;

        if (progress<0){
            progress=0;
        }else if (progress>100){
            progress=100;
        }
        int level =(100-progress)*9/100;
       // Log.e("iv_rest_battery","level==="+level);
        iv_rest_battery.setImageLevel(level);
        adapter.setProgress(progress);

        long time  = (long) (rest_year*365);
        if (time<0) time=0;
        final List<EventInfo> eventInfos = MyApplication.getInstance().getmDaoSession().getEventInfoDao().queryBuilder().list();
        mdata.clear();
        if (eventInfos!=null)
        for (EventInfo eventInfo:eventInfos){
            if (eventInfo.isSelect){
                int count = (int) (eventInfo.durtion*time);
                mdata.add(eventInfo.name+count+"次");
            }

        }

       adapter.notifyDataSetChanged();

        recyclerView.setVisibility(mdata.size()>0?View.VISIBLE:View.GONE);
        empty_view.setVisibility(mdata.size()==0?View.VISIBLE:View.GONE);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        watch.stop();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_setting)
    public void showSettingDrawable(){
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.showDrawer();

    }

    @OnClick(R.id.empty_view)
    protected  void  gotoEvent(){
        startActivity(new Intent(getActivity(), EventActivity.class));
    }



}
