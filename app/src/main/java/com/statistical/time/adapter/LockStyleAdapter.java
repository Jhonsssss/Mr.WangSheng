package com.statistical.time.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.statistical.time.R;
import com.statistical.time.tool.UiUtil;
import com.statistical.time.widget.NetworkImageView;

public class LockStyleAdapter extends RecyclerView.Adapter<LockStyleAdapter.LockStyle> {
    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    OnItemClickListener onItemClickListener;
    int  [] res  ={R.mipmap.display1,R.mipmap.display2,R.mipmap.display3,R.mipmap.display4};
    @Override
    public LockStyle onCreateViewHolder(ViewGroup parent, int viewType) {
      View  view =   LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lock_style,null);
        return new LockStyle(view);
    }

    @Override
    public void onBindViewHolder(LockStyle holder, final int position) {
        holder.item_iv_style.displayImage(res[position]);

        holder.item_iv_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                 onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return res.length;
    }


    class  LockStyle extends  RecyclerView.ViewHolder{

           NetworkImageView item_iv_style;
        public LockStyle(View itemView) {
            super(itemView);
            item_iv_style= itemView.findViewById(R.id.item_iv_style);
           ViewGroup.LayoutParams layoutParams =  item_iv_style.getLayoutParams();
            layoutParams.width= (UiUtil.getScreenWidth()-UiUtil.Dp2Px(15))/2;
            layoutParams.height=layoutParams.width*1280/720;
            item_iv_style.setLayoutParams(layoutParams);
        }
    }

    public   interface  OnItemClickListener{
        void  onItemClick(int  position);

    }
}
