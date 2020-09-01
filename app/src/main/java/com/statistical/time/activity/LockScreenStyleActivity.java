package com.statistical.time.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.statistical.time.R;
import com.statistical.time.adapter.HopeListAdapter;
import com.statistical.time.adapter.LockStyleAdapter;
import com.statistical.time.base.BaseActivity;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.tool.UiUtil;
import com.statistical.time.tool.UserUtils;
import com.statistical.time.widget.MyRecyclerViewDivider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
public class LockScreenStyleActivity extends BaseActivity {
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
        setContentView(R.layout.activity_lock_screen_style);
        unbinder = ButterKnife.bind(this);
        initRecyclerView();
    }
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.addItemDecoration(new MyRecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL, UiUtil.Dp2Px(5f), ContextCompat.getColor(this, R.color.color_F8F8F8)));
        LockStyleAdapter   adapter = new LockStyleAdapter();
        adapter.setOnItemClickListener(new LockStyleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                UserUtils.setLockStyle(LockScreenStyleActivity.this,position);

                Intent intent =new Intent(LockScreenStyleActivity.this,LockScreenStyleDetailActivity.class);
                startActivity(intent);

            }
        });
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @OnClick(R.id.iv_close)
    @Override
    public void finish() {
        super.finish();
    }
}
