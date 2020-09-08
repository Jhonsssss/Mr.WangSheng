package com.statistical.time.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.statistical.time.R;
import com.statistical.time.adapter.SelectEventAdapter;
import com.statistical.time.application.MyApplication;
import com.statistical.time.base.BaseActivity;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.bean.EventInfo;
import com.statistical.time.tool.UiUtil;
import com.statistical.time.widget.MyRecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EventActivity extends BaseActivity {
    private SelectEventAdapter adapter;

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

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        unbinder = ButterKnife.bind(this);
        initRecyclerView();

    }

    List<EventInfo> mEventInfos  =new ArrayList<>();


    private void initRecyclerView() {


        mEventInfos.clear();
        final List<EventInfo> eventInfos = MyApplication.getInstance().getmDaoSession().getEventInfoDao().queryBuilder().list();

        if (eventInfos!=null)
        this.mEventInfos.addAll(eventInfos);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.addItemDecoration(new MyRecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL, UiUtil.Dp2Px(10), ContextCompat.getColor(this, R.color.white_slight)));
        adapter = new SelectEventAdapter(mEventInfos);
        adapter.setOnItemClickListener(new SelectEventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

               EventInfo e  =   mEventInfos.get(position);
                e.isSelect =!e.isSelect;
                MyApplication.getInstance().getmDaoSession().getEventInfoDao().update(e);
                adapter.notifyDataSetChanged();
                //EventBus.getDefault().post(new EventCenter(EventBusCode.EDIT_BIRTHDAY_FINISH));

            }
        });
        recyclerView.setAdapter(adapter);

//
//        setTimeUi();
    }


    @Override
    @OnClick(R.id.iv_close)
    public void finish() {
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       unbinder.unbind();

    }

}
