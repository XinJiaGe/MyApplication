package com.adaptation.lixinjia.myapplication.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.adaptation.lixinjia.myapplication.R;
import com.adaptation.lixinjia.myapplication.utils.ReflectUtils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 作者：李忻佳
 * 时间：2018/5/30/030.
 * 说明：
 */

public class MessengerActivity extends BaseActivity implements Handler.Callback {
    private Button start;
    private Button stop;
    public static final String TAG = "AndroidTest1Activity";

    private Handler mHandler;


    private AtomicBoolean mIsBound = new AtomicBoolean(false);

    private IBinder mBoundService;

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service. Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            mBoundService = service;

            Log.d(TAG, "MainService - service connected...");

            ReflectUtils.invokeMethod(mBoundService, "getView", new Class<?>[] {
                    Handler.class, String.class
            }, new Object[] {
                    mHandler, "getFragment"
            });
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            mBoundService = null;

            Log.d(TAG, "MainService - service disconnected...");
        }

    };

    private void doBindService() {
        // Establish a connection with the service. We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        Intent intent = new Intent();
        intent.setClassName("com.gl.rabbit", "com.gl.rabbit.service.MyService");
        intent.setAction("");

        intent.putExtra("message", new String[]{"123", "234"});

        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        mIsBound.set(true);

        Log.d(TAG, "MainService - doBindService - is bound: " + mIsBound);
    }

    private void doUnbindService() {
        if (mIsBound.get()) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound.set(false);

            Log.d(TAG, "MainService - doUnbindService - is bound: " + mIsBound);
        }
    }

    private IBinder getBoundService() {
        // TODO How to do if service is null?
        return mBoundService;
    }

    @Override
    public boolean handleMessage(Message msg) {
        Log.d(TAG, "-- Handle message - what: " + msg.what + " | obj: " + msg.obj);
        if (msg.obj instanceof View) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.content);
            layout.addView((View) msg.obj);
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.act_aidi;
    }

    @Override
    public void initView(View view) {
        start = $(R.id.act_aidi_start);
        stop = $(R.id.act_aidi_stop);

        mTitle.setCenterText("aidi");

        mHandler = new Handler(this);

        doBindService();
    }

    @Override
    public void addListener() {
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        if (v == start) {

        }
        if (v == stop) {

        }
    }

    @Override
    public void doBusiness() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }
}
