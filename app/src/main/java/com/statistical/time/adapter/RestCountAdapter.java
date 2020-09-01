package com.statistical.time.adapter;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.statistical.time.R;
import com.statistical.time.common.Constants;

import java.util.List;

public class RestCountAdapter extends RecyclerView.Adapter<RestCountAdapter.RestCountHolder> {


    private  int progress;
    int mode= Constants.DRAK_MODE;

     List<String> mList;

    public RestCountAdapter(int  progress ,int mode, List<String> mList) {
        this.progress =progress;
        this.mode = mode;
        this.mList = mList;
    }

    @Override
    public RestCountHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View  view ;

        if (mode== Constants.DRAK_MODE){
            view  =   LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rest_count_,null);
        }else{
            view  =   LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rest_count,null);
        }
        return new RestCountHolder(view);
    }

    @Override
    public void onBindViewHolder(RestCountHolder holder, final int position) {

         String   s  =mList.get(position);
         holder.textView.setText(s);
         holder.progressBar.setProgress(progress);
         holder.progressBar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (onItemClickListener!=null){
                     onItemClickListener.onItemClick(position);
                 }
             }
         });

    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    class RestCountHolder extends  RecyclerView.ViewHolder{

       ProgressBar progressBar;
       TextView textView ;
        public RestCountHolder(View itemView) {
            super(itemView);
            progressBar =itemView.findViewById(R.id.item_progress);
            textView =itemView.findViewById(R.id.item_tv_count);
        }
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    OnItemClickListener onItemClickListener;
    public   interface  OnItemClickListener{
        void  onItemClick(int  position);

    }
}
