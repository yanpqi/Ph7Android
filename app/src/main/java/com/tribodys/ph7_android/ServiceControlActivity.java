package com.tribodys.ph7_android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tribodys.ph7_android.R;
import com.tribodys.ph7_android.components_usage.MyService;
import com.tribodys.ph7_android.components_usage.MyServiceAIDL;

/**
 * Created by qiyanpeng on 18-6-14.
 */

public class ServiceControlActivity extends Activity implements View.OnClickListener {
    private Button mStartService;
    private Button mStopService;
    private Button mBindService;
    private Button mUnbindService;
    private MyService.MyBinder mBinder;
    private MyServiceAIDL mNewBinder;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e("ph7_android", "Service Connected.");
            // mBinder = (MyService.MyBinder)iBinder;
            // mBinder.startDownload("http://www.baidu.com");
            mNewBinder = MyServiceAIDL.Stub.asInterface(iBinder);
            try {
                Log.e("ph7_android", "call plus " + mNewBinder.plus(10, 5));
                Log.e("ph7_android", "call upper " + mNewBinder.toUpperCase("aidl is good"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("ph7_android", "Service Disconnected.");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("ph7_android", "main activity runs in " + Process.myPid() + ", " + Thread.currentThread().getId());
        setContentView(R.layout.service_control_layout);
        mStartService = (Button) findViewById(R.id.start_service);
        mStopService = (Button) findViewById(R.id.stop_service);
        mStartService.setOnClickListener(this);
        mStopService.setOnClickListener(this);
        mBindService = (Button) findViewById(R.id.bind_service);
        mUnbindService = (Button) findViewById(R.id.unbind_service);
        mBindService.setOnClickListener(this);
        mUnbindService.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_service:
                Intent start = new Intent(this, MyService.class);
                startService(start);
                break;
            case R.id.stop_service:
                Intent stop = new Intent(this, MyService.class);
                stopService(stop);
                break;
            case R.id.bind_service:
                Intent bind = new Intent(this, MyService.class);
                bindService(bind, mConnection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                unbindService(mConnection);
                break;
            default:
        }
    }
}
