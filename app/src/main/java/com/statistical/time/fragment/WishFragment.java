package com.statistical.time.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.statistical.time.R;
import com.statistical.time.activity.AddWishActivity;
import com.statistical.time.base.BaseFragment;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.tool.SystemUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WishFragment extends BaseFragment {
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
    @BindView(R.id.tv_wish_has)
    TextView tv_wish_has;
    @BindView(R.id.tv_wish_no)
    TextView tv_wish_no;
    @BindView(R.id.v_wish_has)
    View v_wish_has;
    @BindView(R.id.v_wish_no)
    View v_wish_no;

    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wish, null);
        unbinder = ButterKnife.bind(this, view);
        fragmentManager = getActivity().getSupportFragmentManager();
        setTab(0);
        initStatusHeight(view);
        return view;
    }
    private void initStatusHeight(View rootView) {
        View status_bar_height =rootView.findViewById(R.id.status_bar_height);
        ViewGroup.LayoutParams layoutParams =  status_bar_height.getLayoutParams();
        layoutParams.height  = SystemUtil.getStatusBarHeight(getActivity());
        status_bar_height.setLayoutParams(layoutParams);

    }
    WishListFragment noWishFragment;
    WishListFragment hasWishFragment;

    int mTab = 0;

    private void setTab(int tab) {
        mTab = tab;
        transaction = fragmentManager.beginTransaction();

        if (noWishFragment != null) {
            transaction.hide(noWishFragment);
        }

        if (hasWishFragment != null) {
            transaction.hide(hasWishFragment);
        }

        if (tab == 0) {
            tv_wish_no.setTextColor(Color.BLACK);
            tv_wish_has.setTextColor(Color.parseColor("#6E6E6E"));

            v_wish_no.setVisibility(View.VISIBLE);
            v_wish_has.setVisibility(View.INVISIBLE);

            if (noWishFragment == null) {
                noWishFragment = new WishListFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                noWishFragment.setArguments(bundle);
                transaction.add(R.id.fl_content_child, noWishFragment);
            } else {
                transaction.show(noWishFragment);
            }

        } else {
            tv_wish_has.setTextColor(Color.BLACK);
            tv_wish_no.setTextColor(Color.parseColor("#6E6E6E"));
            v_wish_no.setVisibility(View.INVISIBLE);
            v_wish_has.setVisibility(View.VISIBLE);

            if (hasWishFragment == null) {
                hasWishFragment = new WishListFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("type", 2);
                hasWishFragment.setArguments(bundle);
                transaction.add(R.id.fl_content_child, hasWishFragment);
            } else {
                transaction.show(hasWishFragment);
            }
        }
        transaction.commit();
    }


    @OnClick(R.id.ll_wish_no)
    void selectNoWish() {

        setTab(0);
    }

    @OnClick(R.id.ll_wish_has)
    void selectHasWish() {

        setTab(1);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @OnClick(R.id.iv_add_wish)
    public void addWish() {
        startActivity(new Intent(getActivity(), AddWishActivity.class));

    }

}
