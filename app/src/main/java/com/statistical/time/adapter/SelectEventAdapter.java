package com.statistical.time.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.statistical.time.R;
import com.statistical.time.bean.EventInfo;

import java.util.List;

public class SelectEventAdapter extends RecyclerView.Adapter<SelectEventAdapter.SelectEventHolder> {
    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    OnItemClickListener onItemClickListener;

    private final List<EventInfo> mEventInfos;

    public SelectEventAdapter(List<EventInfo> eventInfos) {
        mEventInfos =eventInfos;
    }

    @Override
    public SelectEventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View rootView  =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_event,null);

        return new SelectEventHolder(rootView);
    }

    @Override
    public void onBindViewHolder(SelectEventHolder holder, final int position) {

        EventInfo info  =mEventInfos.get(position);

        holder.item_tv_event.setText(info.name);
        holder.item_tv_event.setSelected(info.isSelect);

        holder.item_tv_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null) onItemClickListener.onItemClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mEventInfos==null?0:mEventInfos.size();
    }

    class SelectEventHolder extends RecyclerView.ViewHolder{
         TextView item_tv_event;

        public SelectEventHolder(View itemView) {
            super(itemView);
            item_tv_event =itemView.findViewById(R.id.item_tv_event);
        }
    }


 public   interface  OnItemClickListener{
       void  onItemClick(int  position);

    }
}
