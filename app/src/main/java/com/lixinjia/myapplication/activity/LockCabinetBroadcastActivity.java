package com.lixinjia.myapplication.activity;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.ScanRecord;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.encryption.AESUtils;
import com.lixinjia.myapplication.http.OkHttpCallback;
import com.lixinjia.myapplication.http.OkHttpManager;
import com.lixinjia.myapplication.model.ParsedAdEntity;
import com.lixinjia.myapplication.utils.SDBase64;
import com.lixinjia.myapplication.utils.SDTimerUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 作者：李忻佳
 * 日期：2018/10/23
 * 说明：
 */

public class LockCabinetBroadcastActivity extends BaseActivity {
    private String UUIDService = "0000FFF0-0000-1000-8000-00805F9B34FB";
    private String UUIDServiceW = "0000FFF6-0000-1000-8000-00805F9B34FB";
    private String UUIDServiceN = "0000FFF6-0000-1000-8000-00805F9B34FB";
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mDevice;
    private Button sousuo;
    private Button start;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private BluetoothGatt mBluetoothGattW;
    private BluetoothAdapter.LeScanCallback mLeScanCallback;
    private BluetoothGattService serviceW;
    private BluetoothGattCharacteristic characteristicW;
    private boolean isStrat = false;
    private byte[] sendKeyByte;

    @Override
    public int bindLayout() {
        return R.layout.act_lockcabinet;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("柜锁");
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            showToast("设备不支持蓝牙");
            return;
        }
        if(!isEnabled())
            openBluetooth();
    }

    @Override
    public void addListener() {
        sousuo = $(R.id.act_lockcabinet_sousuo);
        start = $(R.id.act_lockcabinet_start);
        tv1 = $(R.id.act_lockcabinet_tv1);
        tv2 = $(R.id.act_lockcabinet_tv2);
        tv3 = $(R.id.act_lockcabinet_tv3);
        tv4 = $(R.id.act_lockcabinet_tv4);
        tv5 = $(R.id.act_lockcabinet_tv5);
        tv6 = $(R.id.act_lockcabinet_tv6);
        tv7 = $(R.id.act_lockcabinet_tv7);
        start.setOnClickListener(this);
        sousuo.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void widgetClick(View v) {
        if(v == sousuo){
            sousuo();
        }
        if(v == start){
        }
    }
    @Override
    public void doBusiness() {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void sousuo(){
        if(!isStrat) {
            mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    // 解析广播数据
                    ParsedAdEntity entity = parseData(scanRecord);
                    entity.setDevice(device);
                    String str = "";
                    for (int i = 31; i < 44; i++) {
                        str += scanRecord[i];
                    }
                    if (stringToAscii("1810002000147").equals(str)) {
                        startBlue(entity);
                    }
                }
            };
            // 开始扫描设备
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        }else{
            if(sendKeyByte!=null) {
                write(sendKeyByte);
            }
        }
    }
    public static String stringToAscii(String value)
    {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            sbu.append((int)chars[i]);
        }
        return sbu.toString();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void startBlue(final ParsedAdEntity entity){
        // 停止扫描设备
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void run() {
                Log.e(TAG, "连接的mac地址："+entity.getDevice().getAddress());
                mDevice = mBluetoothAdapter.getRemoteDevice(entity.getDevice().getAddress());
                mBluetoothGattW = mDevice.connectGatt(LockCabinetBroadcastActivity.this, false, new BluetoothGattCallback() {
                    //连接状态改变的回调
                    @Override
                    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                        if (newState == BluetoothProfile.STATE_CONNECTED) {
                            // 连接成功后启动服务发现
                            Log.e(TAG, "W启动服务发现:" + mBluetoothGattW.discoverServices());
                            isStrat = true;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    start.setText("start(连接)");
                                }
                            });
                        }else{
                            Log.e(TAG, "W服务断开");
                            isStrat = false;
                            mBluetoothGattW.connect();mBluetoothGattW.close();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    start.setText("start(断开)");
                                }
                            });
                        }
                    }
                    //发现服务的回调
                    @Override
                    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                        //成功发现服务后可以调用相应方法得到该BLE设备的所有服务，并且打印每一个服务的UUID和每个服务下各个特征的UUID
                        if (status == BluetoothGatt.GATT_SUCCESS) {
                            Log.e(TAG, "W成功发现服务");
                            List<BluetoothGattService> supportedGattServices =mBluetoothGattW.getServices();
                            for (BluetoothGattService supportedGattService : supportedGattServices) {
                                Log.e("W服务的UUID","BluetoothGattService UUID=:"+supportedGattService.getUuid());
                                for (BluetoothGattCharacteristic bluetoothGattCharacteristic : supportedGattService.getCharacteristics()) {
                                    Log.e("W特征UUID","BluetoothGattCharacteristic UUID=:"+bluetoothGattCharacteristic.getUuid());
                                    for (BluetoothGattDescriptor bluetoothGattDescriptor : bluetoothGattCharacteristic.getDescriptors()) {
                                        Log.e("W特征Descriptors","bluetoothGattDescriptor UUID=:"+bluetoothGattDescriptor.getUuid());
                                    }
                                }
                            }
                        serviceW = mBluetoothGattW.getService(UUID.fromString(UUIDService));
                        if(serviceW!=null) {
                            characteristicW = serviceW.getCharacteristic(UUID.fromString(UUIDServiceW));
                            setCharacteristicNotification(characteristicW, true);
                        }else{
                            Log.e(TAG, "WBluetoothGattService 为null");
                        }
                        }else{
                            Log.e(TAG, "W服务发现失败，错误码为:" + status);
                        }
                    }
                    //写操作的回调
                    @Override
                    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                        if (status == BluetoothGatt.GATT_SUCCESS) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv4.setText("写入成功:");
                                }
                            });
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv4.setText("写入失败");
                                }
                            });
                        }
                    }
                    //读操作的回调
                    @Override
                    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                        if (status == BluetoothGatt.GATT_SUCCESS) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv5.setText("读取成功:" );
                                }
                            });
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv5.setText("读取失败");
                                }
                            });
                        }
                    }
                    //数据返回的回调（此处接收BLE设备返回数据）
                    @Override
                    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                        byte[] value = characteristic.getValue();
                        Log.e(TAG, "接收设备返回数据byt  "+str(value));
                        if(value!=null){
                            String strValue = characteristic.getStringValue(0);
                            Log.e(TAG, "返回数据转str  "+strValue);
                            String[] strStr = strValue.split("=");
                            if(strStr.length>1){
                                if(strStr[0].equals("HAL")){
                                    if(strStr[1].equals("1")){
                                        Log.e(TAG, "设备已关好");
                                    }
                                    if(strStr[1].equals("0")){
                                        Log.e(TAG, "设备未关好");
                                    }
                                }else if(strStr[0].equals("BA")){
                                    Log.e(TAG, "设备当前电量："+strStr[1]);
                                }
                            }else{
                                if(strValue.substring(0,3).equals("KEY")){
                                    String b64v = SDBase64.encodeToString(value);
                                    Log.e("b64v",b64v);
                                    OkHttpManager.getInstance().requestInterface("http://192.188.88.2:8093/api/demo/1/"+b64v, new OkHttpCallback.Callback() {
                                        @Override
                                        public void onFailure(Exception e) {

                                        }

                                        @Override
                                        public void onSuccess(String result) {
                                            Log.e(TAG,"请求接口返回数据   "+result);
                                            write(SDBase64.decodeToByte(result));
                                        }
                                    });
                                }
                            }
                        }
                    }
                });
            }
        }).start();
    }
    public static ParsedAdEntity parseData(byte[] adv_data) {
        ParsedAdEntity parsedAd = new ParsedAdEntity();
        ByteBuffer buffer = ByteBuffer.wrap(adv_data).order(ByteOrder.LITTLE_ENDIAN);
        while (buffer.remaining() > 2) {
            byte length = buffer.get();
            if (length == 0)
                break;

            byte type = buffer.get();
            length -= 1;
            switch (type) {
                case 0x01: // Flags
                    parsedAd.setFlags(buffer.get());
                    length--;
                    break;
                case 0x02: // Partial list of 16-bit UUIDs
                case 0x03: // Complete list of 16-bit UUIDs
                case 0x14: // List of 16-bit Service Solicitation UUIDs
                    while (length >= 2) {
                        parsedAd.setUuids(UUID.fromString(String.format(
                                "%08x-0000-1000-8000-00805f9b34fb", buffer.getShort())));
                        length -= 2;
                    }
                    break;
                case 0x04: // Partial list of 32 bit service UUIDs
                case 0x05: // Complete list of 32 bit service UUIDs
                    while (length >= 4) {
                        parsedAd.setUuids(UUID.fromString(String.format(
                                "%08x-0000-1000-8000-00805f9b34fb", buffer.getInt())));
                        length -= 4;
                    }
                    break;
                case 0x06: // Partial list of 128-bit UUIDs
                case 0x07: // Complete list of 128-bit UUIDs
                case 0x15: // List of 128-bit Service Solicitation UUIDs
                    while (length >= 16) {
                        long lsb = buffer.getLong();
                        long msb = buffer.getLong();
                        parsedAd.setUuids(new UUID(msb, lsb));
                        length -= 16;
                    }
                    break;
                case 0x08: // Short local device name
                case 0x09: // Complete local device name
                    byte sb[] = new byte[length];
                    buffer.get(sb, 0, length);
                    length = 0;
                    parsedAd.setLocalName(new String(sb).trim());
                    break;
                case (byte) 0xFF: // Manufacturer Specific Data
                    parsedAd.setManufacturer(buffer.getShort());
                    length -= 2;
                    break;
                default: // skip
                    break;
            }
            if (length > 0) {
                buffer.position(buffer.position() + length);
            }
        }
        return parsedAd;
    }
    private void sss(int i){

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void write(byte[] bytes){
        Log.e(TAG, "发送加密数据  "+str(bytes));
        //将指令放置进特征中
        characteristicW.setValue(bytes);
        //开始写数据
        mBluetoothGattW.writeCharacteristic(characteristicW);
    }
    /**
     * 是否启动蓝牙
     * @return
     */
    private boolean isEnabled(){
        if (mBluetoothAdapter == null)
            return mBluetoothAdapter.isEnabled();
        else
            return false;
    }
    /**
     * 开启蓝牙
     */
    private void openBluetooth(){
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGattW == null) {
            Log.w(TAG, "NBluetoothAdapter not initialized");
            return;
        }
        boolean isEnableNotification =  mBluetoothGattW.setCharacteristicNotification(characteristic, enabled);
        Log.e(TAG, "NisEnableNotification:"+isEnableNotification);
        if(isEnableNotification) {
            List<BluetoothGattDescriptor> descriptorList = characteristic.getDescriptors();
            if(descriptorList != null && descriptorList.size() > 0) {
                for(BluetoothGattDescriptor descriptor : descriptorList) {
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    mBluetoothGattW.writeDescriptor(descriptor);
                    Log.e(TAG, "N写入通知UUID:"+descriptor.getUuid());
                }
            }
        }
    }

    public static String str(byte[] bytes){
        String str = "";
        for (byte aByte : bytes) {
            str += Integer.toHexString(aByte)+" ";
        }
        return str;
    }
    // /** 加密(结果为16进制字符串) **/
//    public static byte[] encrypt(String content, byte[] password, String iv) {
//        byte[] data = null;
//        try {
//            data = content.getBytes("UTF-8");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        data = encrypt(data, password, iv);
//        return data;
//    }
//    // /** 解密字节数组 **/
//    public static byte[] decrypt(byte[] content, byte[] password, String iv) {
//        try {
//            SecretKeySpec key = new SecretKeySpec(password, "AES");
//            Cipher cipher = Cipher.getInstance(CipherMode);
//            cipher.init(Cipher.DECRYPT_MODE, key, createIV(iv));
//            byte[] result = cipher.doFinal(content);
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
