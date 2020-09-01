package com.statistical.time.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.statistical.time.R;
import com.statistical.time.common.Constants;
import com.statistical.time.tool.BitmapUtil;
import com.statistical.time.tool.LogUtil;
import com.statistical.time.tool.UiUtil;

import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Watch extends View {
    private Paint mPaint;
    private Context context;


    public OnTimeChangeListener getOnTimeChangeListener() {
        return onTimeChangeListener;
    }

    public void setOnTimeChangeListener(OnTimeChangeListener onTimeChangeListener) {
        this.onTimeChangeListener = onTimeChangeListener;
    }

    OnTimeChangeListener onTimeChangeListener;

    public Watch(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public Watch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }



    private void init() {
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        //分三种，STROKE之绘制轮廓，不绘制内容；FILL，只绘制内容；FILL_AND_STROKE，内容和轮廓都绘制
        mPaint.setStyle(Paint.Style.STROKE);
    }


    Bitmap bgBitmap,hourBitmap,minBitmap,secondBitmap;
  public  void  setWatchBgRes(int bgId,int hourId,int minId,int secondId){
          if (bgId>0){
              isSmall=bgId== R.mipmap.clock_view3_bg;
              bgBitmap = BitmapFactory.decodeResource(context.getResources(), bgId);
          }
          if (hourId>0){
              hourBitmap = BitmapFactory.decodeResource(context.getResources(), hourId);
          }
          if (minId>0){
              minBitmap = BitmapFactory.decodeResource(context.getResources(), minId);
          }
          if (secondId>0){
              secondBitmap = BitmapFactory.decodeResource(context.getResources(), secondId);
          }

      ViewTreeObserver vto = getViewTreeObserver();
          vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
              @Override
              public void onGlobalLayout() {
                  getViewTreeObserver().removeOnGlobalLayoutListener(this);
                 int   viewH = getLayoutParams().height;
                  int   viewW = viewH;



                  if (null != bgBitmap) {

                     int  circleWidth = bgBitmap.getWidth();
                   float     scaling = (float) (viewW-UiUtil.Dp2Px(2))/ circleWidth;

                      LogUtil.e("scaling","scaling==="+scaling);
                      bgBitmap = BitmapUtil.resizeBitmap(bgBitmap, scaling);

                      if (secondBitmap!=null){
                          int secondWidth =secondBitmap.getHeight();

                          float     scaling1 = (float) (viewH-UiUtil.Dp2Px(10))/ secondWidth;
                          secondBitmap = BitmapUtil.resizeBitmap(secondBitmap, scaling1);

                      }
                      requestLayout();
                  }
              }
          });


  } boolean isSmall;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(mMode == Constants.LIGHT_MODE ? Color.BLACK : Color.WHITE);
        if (bgBitmap==null) {
            //设置线宽，线宽默认是1
            mPaint.setStrokeWidth(UiUtil.Dp2Px(1.5f));
            mPaint.setStyle(Paint.Style.STROKE);
            //在屏幕中心画圆，半径为屏幕的1/3
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - UiUtil.Dp2Px(3), mPaint);

        }else{
            canvas.drawBitmap(bgBitmap,UiUtil.Dp2Px(1),UiUtil.Dp2Px(1),mPaint);
        }

        //整个屏幕中心为圆心点
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, UiUtil.Dp2Px(3f), mPaint);

        //安卓坐标系默认实在左上角的，现在我们需要将坐标轴移动到圆心位置，这样利于我们绘制线
//        mPaint.setStrokeWidth(1);
        //坐标原点平移到圆心的位置



        canvas.translate(getWidth() / 2, getHeight() / 2);
        if (secondBitmap!=null){

            //秒针
            canvas.save();  //save方法作用是将画布先保存下来，为了不影响其他的元素，例如绘制两张图片，绘制完第一张接着绘制第二张，第二张可能就会受到第一张的影响，变形啊或者压缩了
            mPaint.setColor(Color.RED);
            mPaint.setStyle(Paint.Style.STROKE);//绘制边框
            mPaint.setStrokeWidth(UiUtil.Dp2Px(1.5f));//边框宽度
            canvas.rotate(secondDegree);//这三个变量在下面代码中
            canvas.drawBitmap(secondBitmap,-secondBitmap.getWidth()/2,-secondBitmap.getHeight()/2, mPaint);//竖直的，只在Y轴上，所以X轴都为0，100其实是指针的长度，因为在上方，所以为负数
            canvas.restore();
            return;
        }

        float secondF= isSmall?0.6f:0.8f;
        float minF= isSmall?0.45f:0.7f;
        float houF= isSmall?0.35f:0.5f;

        //秒针
        canvas.save();  //save方法作用是将画布先保存下来，为了不影响其他的元素，例如绘制两张图片，绘制完第一张接着绘制第二张，第二张可能就会受到第一张的影响，变形啊或者压缩了
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);//绘制边框
        mPaint.setStrokeWidth(UiUtil.Dp2Px(1.5f));//边框宽度
        canvas.rotate(secondDegree);//这三个变量在下面代码中
        canvas.drawLine(0, 0, 0, (float) (-getWidth() / 2 * secondF), mPaint);//竖直的，只在Y轴上，所以X轴都为0，100其实是指针的长度，因为在上方，所以为负数
        canvas.restore();

        //分针
        canvas.save();
        mPaint.setColor(mMode==Constants.LIGHT_MODE?Color.BLACK:Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);//绘制边框
        mPaint.setStrokeWidth(UiUtil.Dp2Px(1.5f));//边框宽度  比指针粗点
        canvas.rotate(minuteDegree);
        canvas.drawLine(0, 0, 0, (float) (-getWidth() / 2 * minF), mPaint);
        canvas.restore();

        //时针
        canvas.save();
        //mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);//绘制边框
        mPaint.setStrokeWidth(UiUtil.Dp2Px(1.5f));//边框宽度  比指分针粗点
        canvas.rotate(hourDegree);
        canvas.drawLine(0, 0, 0, (float) (-getWidth() / 2 * houF), mPaint);
        canvas.restore();
    }


    //如果直接绘制数字的画，文字也跟着旋转了，数字有的就会倒着，所以执行下面这一系列操作，再去绘制数字就正常了
    public void trans(Canvas canvas, String text, int degree, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        //先将原来的坐标轴旋转30度
        canvas.rotate(degree);
        //将旋转完成的坐标轴平移到上方   它只是在y轴进行的平移，所以x轴为0，y轴也就是圆心的位置减去35，35是自己固定的位置，可适当自己修改；但是为负值，因为在y轴的上方，Android坐标系往下为正数
        canvas.translate(0, -(getWidth() / 3 - 35));
        //这时在将原来旋转的30都转回去，此时的坐标轴与开始的坐标轴都是直立的，只不过现在的位置处于原来坐标轴的    右上方
        canvas.rotate(-degree);
        //开始写文字  1，2，3，。。。。。12   因为文字写
        canvas.drawText(text, -rect.width() / 2, rect.height() / 2, paint);
        //写完文字后开始将坐标轴复原   先是顺时针旋转30都，
        canvas.rotate(degree);
        //再平移到圆心的位置
        canvas.translate(0, getWidth() / 3 - 35);
        //在逆时针平移30都
        canvas.rotate(-degree);
    }


    private float secondDegree;
    private float minuteDegree;
    private float hourDegree;
    private Timer timer = new Timer();
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (secondDegree == 360) {
                secondDegree = 0;
            }
            if (minuteDegree == 360) {
                minuteDegree = 0;
            }
            if (hourDegree == 360) {
                hourDegree = 0;
            }

            //这三个变量的换算方式，变量名起分针和秒针起反了，也无所谓了
            //第一个360/60=6，也就是一秒钟走六度
            //第二个6/60 分针一秒针走0.1度
            //时针，一秒钟走1/120度
            secondDegree = secondDegree + 6;
            minuteDegree = minuteDegree + 0.1f;
            hourDegree = hourDegree + 1 / 120f;

            if (onTimeChangeListener!=null){
                onTimeChangeListener.onchange();
            }
            /**
             * 自定义View 刷新界面有三种
             * 1：Invalidate() 如果只是内容变动，可使用此方法
             * 2：postInvalidate() 涉及到线程切换的
             * 3：requestLayout() view位置变动，需要调用此方法 涉及到RadioGroup
             */
            postInvalidate();//涉及到线程，界面刷新需要使用此方法
        }
    };

    int  mMode= Constants.LIGHT_MODE;
    public void start(int mode) {
        mMode =mode;
        Calendar calendar = Calendar.getInstance();

       // LogUtil.e("startTime","     "+calendar.get(Calendar.MINUTE)+"   "+calendar.get(Calendar.SECOND)+"   "+calendar.get(Calendar.HOUR));
        int  second =calendar.get(Calendar.SECOND);
        int  minute =calendar.get(Calendar.MINUTE);
        int  hour = calendar.get(Calendar.HOUR);
        minuteDegree =(minute*6+  second* 0.1f)% 360;
        secondDegree =second* 6 % 360;
        hourDegree =(hour*30+second/120f+minute*0.5f) % 360;
        timer.schedule(timerTask, 0, 1000);
    }

    public void stop() {
        if (timer != null && timerTask != null) {
            timer.cancel();
            timerTask.cancel();
        }

    }


   public    interface OnTimeChangeListener{
        void onchange();
   }
}
