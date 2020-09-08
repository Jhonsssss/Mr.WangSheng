package com.statistical.time.activity;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.statistical.time.R;
import com.statistical.time.base.BaseActivity;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.common.EventBusCode;
import com.statistical.time.fragment.CalendarFragment;
import com.statistical.time.fragment.DateFragment;
import com.statistical.time.fragment.DeadFragment;
import com.statistical.time.fragment.LiveFragment;
import com.statistical.time.fragment.WishFragment;
import com.statistical.time.model.MainActivityModel;
import com.statistical.time.present.MainActivityPresent;
import com.statistical.time.tool.LogUtil;
import com.statistical.time.view.MainActivityView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity<MainActivityPresent> implements MainActivityView, MainActivityModel,View.OnClickListener {

    // Used to load the 'native-lib' library on application startup.
    Unbinder unbinder;
    FragmentManager fragmentManager;
    private FragmentTransaction fTransaction;
    @BindView(R.id.drawerlayout)
    FlowingDrawer mDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         unbinder = ButterKnife.bind(this);
         fragmentManager = getSupportFragmentManager();
          setTab(0);
          initTakePhotoInfo();
          initMenu();

    }

    private void initMenu() {
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                if (newState == ElasticDrawer.STATE_CLOSED) {
                    Log.i("MainActivity", "Drawer STATE_CLOSED");
                }

                Log.i("MainActivity", "Drawer state=="+newState);
            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {
               // Log.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
            }
        });
    }

    private SoundPool soundPool;
    @BindView(R.id.iv_sheng)
    ImageView ivSheng;
    @BindView(R.id.iv_si)
    ImageView ivSi;
    @BindView(R.id.iv_riqi)
    ImageView ivRiqi;
    @BindView(R.id.iv_xinyuan)
    ImageView ivXinyuan;
    @BindView(R.id.fl_content)
    FrameLayout flContent;

    int sheng_music;
    int  si_music;

    public void initTakePhotoInfo() {
        //第一个参数为同时播放数据流的最大个数，第二数据流类型(是否受控于手机音量)，第三为声音质量
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
        sheng_music = soundPool.load(this, R.raw.sheng, 1); //第3个为音乐的优先级
        si_music = soundPool.load(this, R.raw.si, 2); //第3个为音乐的优先级
    }


    public  void playLiveMusic(){
        soundPool.stop(sheng_music);
        soundPool.stop(si_music);
        soundPool.play(sheng_music, 1, 1, 0, 0, 1);
    }
    public  void playDeadMusic(){
        soundPool.stop(sheng_music);
        soundPool.stop(si_music);
        soundPool.play(si_music, 1, 1, 0, 0, 1);

    }


    public void destroySoundPool() {
        if (soundPool == null) {
            return;
        }
        soundPool.unload(sheng_music);
        soundPool.unload(si_music);
        soundPool.release();
    }
int  mIndex;

    public  void  setTab(int  index){
        mIndex=index;
        LogUtil.e("tab===","tab==="+index);
        fTransaction = fragmentManager.beginTransaction();
        if (riQiFragment!=null) {
            fTransaction.hide(riQiFragment);
        }
        if (CalendarFragment!=null) {
            fTransaction.hide(CalendarFragment);
        }
        if (shengFragment!=null) {
            fTransaction.hide(shengFragment);
        }
        if (siFragment!=null) {
            fTransaction.hide(siFragment);
        }
        if (xiyuanFragment!=null) {
            fTransaction.hide(xiyuanFragment);
        }
        ivRiqi.setSelected(false);
        ivSheng.setSelected(false);
        ivSi.setSelected(false);
        ivXinyuan.setSelected(false);
        switch (index){
            case 0:
                ivSheng.setSelected(true);
                if (shengFragment==null) {
                    LogUtil.e("初始化","初始化++++++++++++ShengFragment");
                    shengFragment = new LiveFragment();
                    fTransaction.add(R.id.fl_content,shengFragment);
                }else{
                    fTransaction.show(shengFragment);
                }
                fTransaction.commit();
                break;

            case 1:
                ivSi.setSelected(true);

                if (siFragment==null) {
                    LogUtil.e("初始化","初始化++++++++++++ShengFragment");
                    siFragment = new DeadFragment();
                    fTransaction.add(R.id.fl_content,siFragment);
                }else{
                    fTransaction.show(siFragment);
                }
                fTransaction.commit();
                break;
            case 2:
                ivXinyuan.setSelected(true);


                if (xiyuanFragment==null) {
                    LogUtil.e("初始化","初始化++++++++++++ShengFragment");
                    xiyuanFragment = new WishFragment();
                    fTransaction.add(R.id.fl_content,xiyuanFragment);
                }else{
                    fTransaction.show(xiyuanFragment);
                }
                fTransaction.commit();
                break;
            case 3:
                ivRiqi.setSelected(true);
                if (riQiFragment==null) {
                    LogUtil.e("初始化","初始化++++++++++++RiQiFragment111");
                    riQiFragment = new DateFragment();
                    fTransaction.add(R.id.fl_content,riQiFragment);
                }else{
                    fTransaction.show(riQiFragment);
                }
                fTransaction.commit();
                break;
            case 4:
                ivRiqi.setSelected(true);
                if (CalendarFragment==null) {
                    LogUtil.e("初始化","初始化++++++++++++RiQiFragment111");
                    CalendarFragment = new CalendarFragment();
                    fTransaction.add(R.id.fl_content,CalendarFragment);
                }else{
                    fTransaction.show(CalendarFragment);
                }
                fTransaction.commit();
                break;

                default:
                    break;
        }
    }
    DateFragment riQiFragment;
   CalendarFragment CalendarFragment;
    LiveFragment shengFragment;
    DeadFragment siFragment;
    WishFragment xiyuanFragment;


    @OnClick({R.id.ll_sheng,R.id.ll_si,R.id.ll_xinyuan,R.id.ll_riqi})
    public  void  clickTab(View view){
     switch (view.getId()){
         case R.id.ll_sheng:

             setTab(0);
             break;
         case  R.id.ll_si:
             setTab(1);
             break;
         case R.id.ll_xinyuan:
             setTab(2);
             break;
         case  R.id.ll_riqi:
             setTab(4);
             break;
            default:
                break;

     }

    }


    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

        if (eventCenter!=null){
            if (eventCenter.getEventCode()==EventBusCode.CODE_CLOCK_SENCENDS){

                if (System.currentTimeMillis()/1000%2==0){

                    if (mIndex==0){

                        playLiveMusic();
                    }else if (mIndex==1){
                        playDeadMusic();
                    }else {

                        soundPool.stop(sheng_music);
                        soundPool.stop(si_music);
                    }
                }

            }
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return new MainActivityPresent(this,this,this);
    }

    public void showDrawer() {
        if (mDrawer.getDrawerState()==ElasticDrawer.STATE_CLOSED) {
            mDrawer.openMenu(true);
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

    @Override
    public void onClick(View v) {

    }

    @Override
    public void test() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        destroySoundPool();
    }


    @Override
    public void onBackPressed() {
        if (mDrawer.getDrawerState()==ElasticDrawer.STATE_OPEN) {
            mDrawer.closeMenu(true);
            return;
        }
        if (mDrawer.getDrawerState()==ElasticDrawer.STATE_CLOSED) {
            finish();
        }


    }
}
