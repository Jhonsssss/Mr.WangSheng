package com.statistical.time.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.statistical.time.receiver.LockScreenReceiver;


/**
 * @author Administrator
 */
public class LockScreenService extends Service {
    private LockScreenReceiver mReceiver;
    private IntentFilter mIntentFilter = new IntentFilter();
    private boolean isNotiShow = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //动态注册
        mIntentFilter.addAction(Intent.ACTION_BOOT_COMPLETED);
        mIntentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        mIntentFilter.addAction(Intent.ACTION_SCREEN_ON);
        mIntentFilter.addAction(Intent.ACTION_TIME_TICK);

        mIntentFilter.setPriority(Integer.MAX_VALUE);
        if (null == mReceiver) {
            mReceiver = new LockScreenReceiver();
            mIntentFilter.setPriority(Integer.MAX_VALUE);
            registerReceiver(mReceiver, mIntentFilter);

            buildNotification();
            Toast.makeText(getApplicationContext(), "开启成功", Toast.LENGTH_LONG).show();
        }

        return START_STICKY;
    }

    /**
     * 通知栏显示
     */
    private void buildNotification() {
//        if (!isNotiShow){ //避免多次显示
//            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            Intent intent = new Intent(this, DetailActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            Notification notification = new NotificationCompat.Builder(this)
//                    .setTicker("APP正在运行")
//                    .setAutoCancel(false)
//                    .setContentTitle("APP正在运行")
//                    .setContentText("运行中")
//                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setContentIntent(pendingIntent)
//                    .build();
//            manager.notify(1, notification);
//
//            startForeground(0x11, notification);
//
//            isNotiShow = true;
//        }
    }

    @Override
    public void onDestroy() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
        super.onDestroy();
    }
}