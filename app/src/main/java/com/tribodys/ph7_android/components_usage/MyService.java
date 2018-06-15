package com.tribodys.ph7_android.components_usage;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import com.tribodys.ph7_android.R;
import com.tribodys.ph7_android.ServiceControlActivity;

/**
 * Created by qiyanpeng on 18-6-14.
 */

public class MyService extends Service {
    private MyBinder mMyBinder = new MyBinder();

    private MyServiceAIDL.Stub mNewBinder = new MyServiceAIDL.Stub() {
        @Override
        public int plus(int a, int b) throws RemoteException {
            return a + b;
        }

        @Override
        public String toUpperCase(String text) throws RemoteException {
            return text.toUpperCase();
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // return mMyBinder;
        return mNewBinder;
    }

    // Let service to be foreground, so it will not be killed when memory is low.
    // it will stin in status bar.
    private void asForeGround() {
        Intent notificationIntent = new Intent(this, ServiceControlActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new Notification.Builder(this)
                .setContentText("通知来了")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
    }

    private void sleeps() {
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("ph7_android", "service onCreate called.");
        Log.e("ph7_android", "service runs in " + Process.myPid() + "," + Thread.currentThread().getId());
        // sleeps();
    }

    @Override
    public void onDestroy() {
        Log.e("ph7_android", "service onDestroy called.");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("ph7_android", "service onStartCommand called.");
        return super.onStartCommand(intent, flags, startId);
    }

    public class MyBinder extends Binder {
        public void startDownload(String url) {
            Log.e("ph7_android", "called startDownload through MyBinder.");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.e("ph7_android", "doing download work.");
                }
            }).start();
        }
    }
}
