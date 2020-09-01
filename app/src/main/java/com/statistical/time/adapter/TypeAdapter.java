package com.statistical.time.adapter;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class TypeAdapter extends PagerAdapter {

    List<View> mViews;

    public TypeAdapter(List<View> itemsView) {
        this.mViews = itemsView;
    }

    @Override
    public int getCount() {
        return mViews == null ? 0 : mViews.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        if (position < getCount()) {
//            container.removeView(mViews.get(position));
//        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
         View view  =mViews.get(position);
            container.addView(view);
        return view;
    }


    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}





