package com.statistical.time.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.statistical.time.R;

public class HopeListAdapter extends RecyclerView.Adapter<HopeListAdapter.HopeListHolder> {


    private final String[] hopes;

    public HopeListAdapter(String [] hopes) {
        this.hopes = hopes;
    }


    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    OnItemClickListener onItemClickListener;
    @Override
    public HopeListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hop_list,null);
        return new HopeListHolder(rootView);
    }

    @Override
    public void onBindViewHolder(HopeListHolder holder, final int position) {
           String s  =hopes[position];
           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   if (onItemClickListener!=null){
                       onItemClickListener.onItemClick(position);
                   }
               }
           });
           holder.item_tv_hope.setText(s);

    }

    @Override
    public int getItemCount() {
        return hopes==null?0:hopes.length;
    }

    class HopeListHolder extends  RecyclerView.ViewHolder{


        TextView item_tv_hope;
        View itemView;
        public HopeListHolder(View itemView) {
            super(itemView);
            item_tv_hope=itemView.findViewById(R.id.item_tv_hope);
            this.itemView =itemView;

        }
    }

    public   interface  OnItemClickListener{
        void  onItemClick(int  position);

    }
}
