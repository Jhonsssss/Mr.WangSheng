package com.statistical.time.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.statistical.time.R;
import com.statistical.time.activity.ChooseWishBgActivity;
import com.statistical.time.bean.BackgroundBean;
import com.statistical.time.tool.UiUtil;
import com.statistical.time.widget.NetworkImageView;
import com.tonicartos.superslim.GridSLM;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BackgroundPicAdapter extends RecyclerView.Adapter<BackgroundPicAdapter.CountItemViewHolder>{

    int  j= 0;
    private static final int                  VIEW_TYPE_HEADER  = 0x01;
    private static final int                  VIEW_TYPE_CONTENT = 0x00;

    private OnItemClickLitener mOnItemClickLitener;
    int headerCount = 0;
    int sectionFirstPosition = 0;


    int   style=1;
    public BackgroundPicAdapter(int   style, int[][] starRes, int[][] scapeRes, int[][] catongRes) {
        this.style=style;
        if (style==1){
            addItem("明星",starRes);
            addItem("卡通",catongRes);
            addItem("风景",scapeRes);

        }else{
            addItem("默认背景",starRes);
        }



    }

    public void  addItem(String theme, int[][] res){
        String key = theme;
        sectionFirstPosition = j + headerCount;
        headerCount += 1;
        mItems.add(new LineItem(key, 0, 0, sectionFirstPosition,true));

        for (int[] r :res ){
            j++;
            mItems.add(new LineItem(key, r[0],  r[1], sectionFirstPosition,false));

        }

    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    int  margin =0;
    public List<LineItem>    mItems = new ArrayList<>();  //列表数据集合
    @Override
    public int getItemViewType(int position) {
        if (mItems == null || mItems.size() <= 0) //fixed by yangchao 2016/10/9 :防止数组越界异常
            return 0;
        return mItems.get(position).isHeader ? VIEW_TYPE_HEADER : VIEW_TYPE_CONTENT;
    }

    @Override
    public CountItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.small_pic_header_view, parent, false);
        } else {
            view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.small_pic_gallery_item, null);
        }
        return new CountItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CountItemViewHolder holder, int position) {
        if (mItems == null || mItems.size() <= 0) //fixed by yangchao 2016/10/8 :防止数组越界异常
            return;
        final LineItem item = mItems.get(position);
        final String timeKey = item.timeKey;
        final View itemView = holder.itemView;
        int itemViewType = getItemViewType(position);
        initLayout(item, itemView);


        //标题
        if (itemViewType == VIEW_TYPE_HEADER) {
            holder.mTextView.setText(timeKey);
        }else {
            final int imagePath = item.thumbPath;//图片路径
            final int bigimagePath = item.bigPicPath;//图片路径
            displayImage( bigimagePath, holder.ivImage);
            setItemClickEvent(holder);
        }


    }

    private void setItemClickEvent(final CountItemViewHolder holder) {

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick(holder);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder, pos);
                    return false;
                }
            });
        }
    }

int  itemViewType;
int num;


    private void itemClick(CountItemViewHolder holder) {
        num = 0;
        int pos = holder.getLayoutPosition();
        for (int i = 0; i <= pos; i++) {//遍历一下点击itme之前有多少个header
            itemViewType = getItemViewType(i);
            if (itemViewType == VIEW_TYPE_HEADER) {
                num++;
            }
        }

        if (mOnItemClickLitener!=null)
        mOnItemClickLitener.onItemClick(holder, pos - num);
    }

    /**
     * 显示图片
     */
    public void displayImage(int  imagePath, ImageView imageView) {
        if (imagePath!=0){
            imageView.setImageResource(imagePath);
        }
    }
    /**
     * 初始化网格布局
     */
    public void initLayout(LineItem item, View itemView) {
        final GridSLM.LayoutParams lp = GridSLM.LayoutParams.from(itemView.getLayoutParams());
        lp.setSlm(GridSLM.ID);
        lp.setMargins(margin, margin, margin, margin);
        lp.setNumColumns(2);
        lp.setFirstPosition(item.sectionFirstPosition);
        itemView.setLayoutParams(lp);
    }


    @Override
    public int getItemCount() {
        return mItems==null?0:mItems.size();
    }

    /**
     * 媒体库 holder
     * ...
     */
    public class CountItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivImage;//图片
        public TextView mTextView;
        /**
         * download id
         */
        public int id;
        public String url;
        public String key;


        public CountItemViewHolder(View itemView) {
            super(itemView);

            ivImage =  itemView.findViewById(R.id.gallery_image);
            if (ivImage != null) {
                ivImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

                ViewGroup.LayoutParams lp =    ivImage.getLayoutParams();

               lp.width=(UiUtil.getScreenWidth()-UiUtil.Dp2Px(20))/2;
               lp.height =lp.width*645/930;
               ivImage.setLayoutParams(lp);
            }

            mTextView = itemView.findViewById(R.id.title);
        }


       
    }

    private  class LineItem {

        private boolean isHeader;
        private int thumbPath;
        private int  bigPicPath;
        private String localPath;
        public String duration;
//        private int     sectionManager;
        private int     sectionFirstPosition;
        public  String  key;
        private String  timeKey;
        private LineItem(String timeKey,int  thumbPath, int bigPicPath,int sectionFirstPosition,boolean isHeader) {
            this.isHeader = isHeader;
            this.timeKey = timeKey;
            this.thumbPath = thumbPath;
            this.bigPicPath = bigPicPath;
//            this.sectionManager = sectionManager;
            this.sectionFirstPosition = sectionFirstPosition;

        }

//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//
//            return thumbPath ==((LineItem) o).thumbPath;
//
//        }

//        @Override
//        public int hashCode() {
//            return 0;
//        }
    }


    public interface OnItemClickLitener {
        void onItemClick(CountItemViewHolder holder, int position);

        void onItemLongClick(CountItemViewHolder holder, int position);
    }
}
