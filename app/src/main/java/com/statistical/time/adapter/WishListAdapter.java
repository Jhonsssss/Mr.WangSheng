package com.statistical.time.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.statistical.time.R;
import com.statistical.time.bean.WishInfo;
import com.statistical.time.common.Constants;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishHolder> {

    private final List<WishInfo> mList;

    public WishListAdapter(List<WishInfo> mlist) {
        this.mList =mlist;
    }

    @Override
    public WishHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View rootView =   LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wish,null);
        return new WishHolder(rootView);
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    @Override
    public void onBindViewHolder(WishHolder holder, final int position) {
        WishInfo wishInfo =mList.get(position);

        holder.iv_wish_bg.setImageResource(Constants.getRes(wishInfo.index,wishInfo.style)[0]);
        holder.tv_hope.setText(wishInfo.wishName);
        holder.iv_wish_finish.setVisibility(wishInfo.isFinish?View.VISIBLE:View.GONE);
        holder.tv_time_finish.setVisibility(wishInfo.isFinish?View.VISIBLE:View.GONE);
        holder.tv_wish_will.setText(wishInfo.createTime+"");
        if (wishInfo.isFinish){
            holder.tv_time_finish.setText("于"+wishInfo.finishTime+"如愿");
        }

        holder.iv_wish_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(position);
                }
            }
        });
        holder.iv_wish_bg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemLongClick(position);
                }

                return false;
            }
        });

    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    OnItemClickListener onItemClickListener;

    public class  WishHolder extends  RecyclerView.ViewHolder{
          ImageView iv_wish_bg;
          ImageView iv_wish_finish;
          TextView tv_time_finish;
          TextView tv_hope;
          TextView tv_wish_will;

        public WishHolder(View itemView) {
            super(itemView);
            iv_wish_bg =itemView.findViewById(R.id.iv_wish_bg);
            iv_wish_finish =itemView.findViewById(R.id.iv_wish_finish);
            tv_time_finish =itemView.findViewById(R.id.tv_time_finish);
            tv_hope =itemView.findViewById(R.id.tv_hope);
            tv_wish_will =itemView.findViewById(R.id.tv_wish_will);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemLongClick(int position);
    }
}
