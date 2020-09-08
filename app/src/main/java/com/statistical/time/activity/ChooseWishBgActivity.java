package com.statistical.time.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.statistical.time.R;
import com.statistical.time.adapter.BackgroundPicAdapter;
import com.statistical.time.base.BaseActivity;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.bean.BackgroundBean;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.common.Constants;
import com.statistical.time.common.EventBusCode;
import com.statistical.time.tool.SystemUtil;
import com.tonicartos.superslim.LayoutManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ChooseWishBgActivity  extends BaseActivity{
    private LayoutManager mLayoutManager;
    private static int lastPosition = 0;
    int style =1;
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


    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    Unbinder unbinder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_wish_bg);
        style =getIntent().getIntExtra("style",1);
      unbinder  = ButterKnife.bind(this);
      initRecyclerview();
        initStatusHeight();
    }
    private void initStatusHeight() {
        View status_bar_height =findViewById(R.id.status_bar_height);
        ViewGroup.LayoutParams layoutParams =  status_bar_height.getLayoutParams();
        layoutParams.height  = SystemUtil.getStatusBarHeight(this);
        status_bar_height.setLayoutParams(layoutParams);

    }

    private void initRecyclerview() {
        mLayoutManager = new LayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);//设置布局管理器
        recyclerView.setHasFixedSize(true);//保持固定的大小，如果可以确定每个Item的高度是固定的，设置这个选项可以提高性能
//        recyclerView.addItemDecoration(new MyRecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL, UiUtil.Dp2Px(10f), ContextCompat.getColor(this, R.color.white)));
        mLayoutManager.setSmoothScrollEnabled(true);
        recyclerView.addOnScrollListener(new RecyclerViewListener(mLayoutManager));
        getAssetsData();

    }


    private void getAssetsData() {



        BackgroundPicAdapter mAdapter ;
                if(style==1){
                    mAdapter = new BackgroundPicAdapter(style, Constants.starRes,Constants.scapeRes,Constants.catongRes);
                }else if (style==2){
                    mAdapter = new BackgroundPicAdapter(style, Constants.type2Res,null,null);
                }else if (style==3){
                    mAdapter = new BackgroundPicAdapter(style, Constants.type3Res,null,null);
                }else{
                    mAdapter = new BackgroundPicAdapter(style, Constants.type4Res1,null,null);
                }

        mAdapter.setOnItemClickLitener(new BackgroundPicAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(BackgroundPicAdapter.CountItemViewHolder holder, int position) {
                Log.e("onItemClick","onItemClick=="+position);

                BackgroundBean bean =new BackgroundBean(Constants.getThemeByPosition(position+1,style),position+1);
                EventBus.getDefault().post(new EventCenter(EventBusCode.SELECT_BG_FINISH,bean));
              finish();
            }

            @Override
            public void onItemLongClick(BackgroundPicAdapter.CountItemViewHolder holder, int position) {
//                Log.e("onItemClick","onItemLongClick=="+position);
            }
        });
        recyclerView.setAdapter(mAdapter);

        recyclerView.scrollToPosition(lastPosition);

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


    static class RecyclerViewListener extends RecyclerView.OnScrollListener {

        private LayoutManager mLayoutManager;

        public RecyclerViewListener(LayoutManager layoutManager) {
            mLayoutManager = layoutManager;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //在这里进行第二次滚动（最后的100米！）
            View topView = recyclerView.getChildAt(0);          //获取可视的第一个view
            lastPosition = mLayoutManager.getPosition(topView);  //得到该View的数组位置
        }
    }
}
