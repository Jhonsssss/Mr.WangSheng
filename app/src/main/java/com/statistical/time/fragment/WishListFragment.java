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
import android.widget.TextView;

import com.statistical.greendao.WishInfoDao;
import com.statistical.time.R;
import com.statistical.time.activity.AddWishActivity;
import com.statistical.time.activity.WishDetailActivity;
import com.statistical.time.adapter.WishListAdapter;
import com.statistical.time.application.MyApplication;
import com.statistical.time.base.BaseFragment;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.bean.WishInfo;
import com.statistical.time.common.EventBusCode;
import com.statistical.time.tool.UiUtil;
import com.statistical.time.widget.MyRecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WishListFragment extends BaseFragment {
    int  type =1;//1 未如愿   2 已如愿
    private WishListAdapter adapter;

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {
           if (eventCenter!=null){
               switch (eventCenter.getEventCode()){
                   case EventBusCode.CODE_ADD_FINISH:

                       List<WishInfo>  list = MyApplication.getInstance().getmDaoSession().getWishInfoDao().queryBuilder().where(WishInfoDao.Properties.IsFinish.eq(type==2)).orderDesc(WishInfoDao.Properties.CreateTime).list();
                       mlist.clear();
                       mlist.addAll(list);
                       adapter.notifyDataSetChanged();
                       empty_view.setVisibility(mlist.size()==0?View.VISIBLE:View.GONE);
                       recyclerView.setVisibility(mlist.size()>0?View.VISIBLE:View.GONE);
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


    @BindView(R.id.wish_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.empty_view)
    TextView empty_view;
    Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_wish_list,null);

         unbinder = ButterKnife.bind(this,rootView);
         type= (int) getArguments().get("type");

       initData();
        return rootView;
    }
    List<WishInfo> mlist=new ArrayList<>();
    private void initData() {
      List<WishInfo>  list = MyApplication.getInstance().getmDaoSession().getWishInfoDao().queryBuilder().where(WishInfoDao.Properties.IsFinish.eq(type==2)).orderDesc(WishInfoDao.Properties.CreateTime).list();
      mlist.clear();
      mlist.addAll(list);

        Log.e("ssssss","mlist===   "+mlist.size());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.addItemDecoration(new MyRecyclerViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, UiUtil.Dp2Px(10f), ContextCompat.getColor(getActivity(), R.color.white)));
        adapter = new WishListAdapter(mlist);
        adapter.setOnItemClickListener(new WishListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent =new Intent(getActivity(), WishDetailActivity.class);
                intent.putExtra("data",mlist.get(position));
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(final int position) {
                UiUtil.showDefaultDialog(getActivity(), "是否要删除该心愿？删除后无法再恢复.", R.string.cancel, R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyApplication.getInstance().getmDaoSession().getWishInfoDao().delete(mlist.get(position));
                        mlist.remove(position);
                        adapter.notifyDataSetChanged();
                        empty_view.setVisibility(mlist.size()==0?View.VISIBLE:View.GONE);
                        recyclerView.setVisibility(mlist.size()>0?View.VISIBLE:View.GONE);
                    }
                });
            }
        });
        recyclerView.setAdapter(adapter);
        empty_view.setVisibility(mlist.size()==0?View.VISIBLE:View.GONE);
        recyclerView.setVisibility(mlist.size()>0?View.VISIBLE:View.GONE);
    }

@OnClick(R.id.empty_view)
  void gotoAdd(){
        startActivity(new Intent(getActivity(), AddWishActivity.class));
}

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
