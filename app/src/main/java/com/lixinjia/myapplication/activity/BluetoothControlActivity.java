package com.lixinjia.myapplication.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lixinjia.myapplication.R;

import java.util.Set;

/**
 * 作者：李忻佳
 * 时间：2018/1/25/025
 * 说明：
 */

public class BluetoothControlActivity extends BaseActivity {
    private Button bt1;
    private Button bt2;
    private Button bt3;
    private Button bt4;
    private Button bt5;
    private Button bt6;
    private Button bt7;
    private BluetoothAdapter mBluetoothAdapter;
    private int REQUEST_ENABLE = 1;
    private String BLUETOOTH_NAME = "Galaxy Nexus";

    @Override
    public int bindLayout() {
        return R.layout.act_bluetooth;
    }

    @Override
    public void initView(View view) {
        bt1 = $(R.id.act_bluetooth_bt1);
        bt2 = $(R.id.act_bluetooth_bt2);
        bt3 = $(R.id.act_bluetooth_bt3);
        bt4 = $(R.id.act_bluetooth_bt4);
        bt5 = $(R.id.act_bluetooth_bt5);
        bt6 = $(R.id.act_bluetooth_bt6);
        bt7 = $(R.id.act_bluetooth_bt7);
    }

    @Override
    public void addListener() {
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
        bt7.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        if(v == bt1){
            showToast(mBluetoothAdapter.isEnabled()+"");
        }
        if(v == bt2){
            if(!mBluetoothAdapter.isEnabled()){
                //弹出对话框提示用户是后打开
                Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enabler, REQUEST_ENABLE);
                //不做提示，直接打开，不建议用下面的方法，有的手机会有问题。
                // mBluetoothAdapter.enable();
            }
        }
        if(v == bt6){
            getLocalBluetoothInformation();
        }
        if(v == bt3){

        }
        if(v == bt4){
            mBluetoothAdapter.startDiscovery();
            searchBluetoothInformation();
        }
        if(v == bt5){
            mBluetoothAdapter.cancelDiscovery();
        }
        if(v == bt7){
            unregisterReceiver();
        }
    }

    @Override
    public void doBusiness() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // 1、判断设备是否支持蓝牙功能
        if (mBluetoothAdapter == null) {
            //设备不支持蓝牙功能
            Toast.makeText(this, "当前设备不支持蓝牙功能！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取本地蓝牙信息
     */
    private void getLocalBluetoothInformation(){
        //获取本机蓝牙名称
        String name = mBluetoothAdapter.getName();
        //获取本机蓝牙地址
        String address = mBluetoothAdapter.getAddress();
        Log.d(TAG,"bluetooth name ="+name+" address ="+address);
        //获取已配对蓝牙设备
        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
        Log.d(TAG, "bonded device size ="+devices.size());
        for(BluetoothDevice bonddevice:devices){
            Log.d(TAG, "bonded device name ="+bonddevice.getName()+" address"+bonddevice.getAddress());
        }
    }

    /**
     * 搜索蓝牙注册广播
     */
    private void searchBluetoothInformation(){
        IntentFilter filter = new IntentFilter();
        //发现设备
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        //设备连接状态改变
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        //行动扫描模式改变了
        filter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        //蓝牙设备状态改变
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mBluetoothReceiver, filter);
    }

    /**
     * 蓝牙广播接收者
     */
    private BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG,"mBluetoothReceiver action ="+action);
            if(BluetoothDevice.ACTION_FOUND.equals(action)){//每扫描到一个设备，系统都会发送此广播。
                //获取蓝牙设备
                BluetoothDevice scanDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(scanDevice == null || scanDevice.getName() == null) return;
                Log.d(TAG, "name="+scanDevice.getName()+"address="+scanDevice.getAddress());
                //蓝牙设备名称
                String name = scanDevice.getName();
                if(name != null && name.equals(BLUETOOTH_NAME)){
                    mBluetoothAdapter.cancelDiscovery();
                    //取消扫描
//                    mProgressDialog.setTitle(getResources().getString(R.string.progress_connecting));                   //连接到设备。
//                    mBlthChatUtil.connect(scanDevice);
                }
            }else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){

            }
        }

    };

    /**
     * 反注册广播取消蓝牙的配对
     */
    public void unregisterReceiver() {
        mActivity.unregisterReceiver(mBluetoothReceiver);
        if (mBluetoothAdapter != null)
            mBluetoothAdapter.cancelDiscovery();
    }
}
