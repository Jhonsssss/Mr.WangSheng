package com.statistical.time.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.statistical.time.R;
import com.statistical.time.adapter.HopeListAdapter;
import com.statistical.time.base.BaseActivity;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.common.Constants;
import com.statistical.time.tool.SystemUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public  class AddWishListActivity extends BaseActivity {

    private HopeListAdapter adapter;

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
    String[] hopes;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wish_list);
         unbinder = ButterKnife.bind(this);

     hopes =     getResources().getStringArray(R.array.hope_list);

        initRecyclerView();

        et_hope.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_length.setText(s.length()+"/20");
            }
        });
        initStatusHeight();
    }
    private void initStatusHeight() {
        View status_bar_height =findViewById(R.id.status_bar_height);
        ViewGroup.LayoutParams layoutParams =  status_bar_height.getLayoutParams();
        layoutParams.height  = SystemUtil.getStatusBarHeight(this);
        status_bar_height.setLayoutParams(layoutParams);

    }

    @BindView(R.id.et_hope)
    EditText et_hope;
    @BindView(R.id.tv_length)
    TextView  tv_length;

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
       // recyclerView.addItemDecoration(new MyRecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL, UiUtil.Dp2Px(0.5f), ContextCompat.getColor(this, R.color.color_F6F6F6)));
        adapter = new HopeListAdapter(hopes);
        adapter.setOnItemClickListener(new HopeListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String  s  =hopes[position];
                et_hope.setText(s);
                et_hope.setSelection(s.length());
                tv_length.setText(s.length()+"/20");
            }
        });
        recyclerView.setAdapter(adapter);
    }


    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;


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
    public  void  commit(){
        Intent intent =new Intent();
        intent.putExtra("content",et_hope.getText().toString());
        setResult(RESULT_OK,intent);
        finish();

    }
}
