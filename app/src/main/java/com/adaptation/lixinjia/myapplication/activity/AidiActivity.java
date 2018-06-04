package com.adaptation.lixinjia.myapplication.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import com.adaptation.lixinjia.myapplication.R;
import com.gl.rabbit.IMyAidlInterface;

/**
 * 作者：李忻佳
 * 时间：2018/5/29/029.
 * 说明：
 */

public class AidiActivity extends BaseActivity {
    private Button start;
    private Button stop;

    private IMyAidlInterface myTestAIDL;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myTestAIDL = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myTestAIDL = null;
        }
    };

    @Override
    public int bindLayout() {
        return R.layout.act_aidi;
    }

    @Override
    public void initView(View view) {
        start = $(R.id.act_aidi_start);
        stop = $(R.id.act_aidi_stop);

        mTitle.setCenterText("aidi");

        bind();
    }

    @Override
    public void addListener() {
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        if (v == start) {
            try {
                if (myTestAIDL == null) showToast("myTestAIDL == null");
                else myTestAIDL.rob();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (v == stop) {

        }
    }

    @Override
    public void doBusiness() {
    }

    private void bind() {
        Intent intent = new Intent();
        intent.setAction("com.gl.rabbit.IMyAidlInterface");
//        intent.setPackage("com.gl.rabbit");
        intent.setComponent(new ComponentName("com.gl.rabbit", "com.gl.rabbit.service.MyService"));
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connection != null) {
            unbindService(connection);
        }
    }
}
