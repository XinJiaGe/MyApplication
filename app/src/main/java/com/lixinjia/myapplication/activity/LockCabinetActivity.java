package com.lixinjia.myapplication.activity;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
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

import com.blankj.utilcode.util.StringUtils;
import com.hyphenate.util.Utils;
import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.utils.SDHandlerUtil;
import com.lixinjia.myapplication.utils.SDTimerUtil;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 作者：李忻佳
 * 日期：2018/10/23
 * 说明：
 */

public class LockCabinetActivity extends BaseActivity {
    private BluetoothAdapter mBluetoothAdapter;
    private String address = "EF:6F:6A:A3:71:D2";
    private String UUIDService = "6e400001-e6ac-a7e7-b1b3-e699bae80060";
    private String UUIDServiceW = "6e400002-e6ac-a7e7-b1b3-e699bae80060";
    private String UUIDServiceN = "6e400003-e6ac-a7e7-b1b3-e699bae80060";
    private BluetoothGatt mBluetoothGattW;
    private BluetoothGatt mBluetoothGattN;
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
    private BluetoothGattService serviceW;
    private BluetoothGattService serviceN;
    private BluetoothGattCharacteristic characteristicW;
    private BluetoothGattCharacteristic characteristicN;
    private byte key;
    private boolean wServiceIsOk = false;
    private boolean nServiceIsOk = false;

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
            // 设置进度条
            setProgressBarIndeterminateVisibility(true);
            setTitle("正在搜索...");
            // 判断是否在搜索,如果在搜索，就取消搜索
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
            // 开始搜索
            mBluetoothAdapter.startDiscovery();
        }
        if(v == start){
            startBlue();
        }
    }
    @Override
    public void doBusiness() {
        tv1.setText("服务的UUID:"+UUIDService);
        tv2.setText("写入的UUID:"+UUIDServiceW);
        tv3.setText("通知的UUID:"+UUIDServiceN);
        /**
         * 异步搜索蓝牙设备——广播接收
         */
        // 找到设备的广播
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        // 注册广播
        registerReceiver(receiver, filter);
        // 搜索完成的广播
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        // 注册广播
        registerReceiver(receiver, filter);
    }
    // 广播接收器
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 收到的广播类型
            String action = intent.getAction();
            // 发现设备的广播
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 从intent中获取设备
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 判断是否配对过
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    // 添加到列表
                    Log.e("BroadcastReceiver",device.getName() + ":" + device.getAddress() + "\n");
                }
                // 搜索完成
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                    .equals(action)) {
                //关闭进度条
                setProgressBarIndeterminateVisibility(true);
                setTitle("搜索完成！");
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void startBlue() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDevice = mBluetoothAdapter.getRemoteDevice(address);
                mBluetoothGattW = mDevice.connectGatt(LockCabinetActivity.this, false, new BluetoothGattCallback() {
                    //连接状态改变的回调
                    @Override
                    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                        if (newState == BluetoothProfile.STATE_CONNECTED) {
                            // 连接成功后启动服务发现
                            Log.e(TAG, "W启动服务发现:" + mBluetoothGattW.discoverServices());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    start.setText("start(连接)");
                                }
                            });
                        }else{
                            wServiceIsOk = false;
                            Log.e(TAG, "W服务断开");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    start.setText("start(断开)");
                                }
                            });
                        }
                    }

                    //发现服务的回调
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
                                wServiceIsOk = true;
                                getKey();
                            }else{
                                Log.e(TAG, "WBluetoothGattService 为null");
                            }
                        }else{
                            Log.e(TAG, "W服务发现失败，错误码为:" + status);
                        }
                    }

                    //写操作的回调
                    public void onCharacteristicWrite(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic, int status) {
                        if (status == BluetoothGatt.GATT_SUCCESS) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv4.setText("写入成功:" +toHexString(characteristic.getValue()));
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
                });
                mBluetoothGattN = mDevice.connectGatt(LockCabinetActivity.this, false, new BluetoothGattCallback() {
                    //连接状态改变的回调
                    @Override
                    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                        if (newState == BluetoothProfile.STATE_CONNECTED) {
                            // 连接成功后启动服务发现
                            Log.e(TAG, "N启动服务发现:" + mBluetoothGattN.discoverServices());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    start.setText("start(连接)");
                                }
                            });
                        }else{
                            nServiceIsOk = false;
                            Log.e(TAG, "N服务断开");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    start.setText("start(断开)");
                                }
                            });
                        }
                    }

                    //发现服务的回调
                    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                        //成功发现服务后可以调用相应方法得到该BLE设备的所有服务，并且打印每一个服务的UUID和每个服务下各个特征的UUID
                        if (status == BluetoothGatt.GATT_SUCCESS) {
                            Log.e(TAG, "N成功发现服务");
                            serviceN = mBluetoothGattN.getService(UUID.fromString(UUIDService));
                            if(serviceN!=null) {
                                characteristicN = serviceN.getCharacteristic(UUID.fromString(UUIDServiceN));
                                setCharacteristicNotification(characteristicN, true);
                                nServiceIsOk = true;
                                getKey();
                            }else{
                                Log.e(TAG, "NBluetoothGattService 为null");
                            }
                        }else{
                            Log.e(TAG, "N服务发现失败，错误码为:" + status);
                        }
                    }

                    //读操作的回调
                    public void onCharacteristicRead(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic, int status) {
                        if (status == BluetoothGatt.GATT_SUCCESS) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv5.setText("读取成功:" +toHexString(characteristic.getValue()));
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
                    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                        final byte[] value = characteristic.getValue();
                        Log.e(TAG, "接收设备返回数据  "+str(value));
                        if(value[0] == (byte)0xA3&&value[1] == (byte)0xA4){
                            int len = Integer.parseInt(Integer.toHexString(value[2]));
                            Log.e(TAG, "len长度："+len);
                            byte[] bytes = new byte[6 + len];
                            for (int i = 0; i < bytes.length; i++) {
                                bytes[i] = value[i];
                            }
                            Log.e(TAG, "取出的数据  "+str(value));
                            byte crc = calcCrc8(bytes);
                            String str = Integer.toHexString(0x00ff & crc);
                            Log.e(TAG, "校验结果:"+str);
                            if(crc == value[6 + len]){
                                Log.e(TAG, "校验正确");
                            }else{
                                Log.e(TAG, "校验错误");
                            }
                            if((bytes[6]^(bytes[3]-0x32))==1){
                                Log.e(TAG, "校验标识正确："+(bytes[6]^(bytes[3]-0x32)));
                                switch ((bytes[6] ^ (bytes[3] - 0x32))) {
                                    case 1://获取key
                                        key = (byte) (bytes[7]^(bytes[3]-0x32));
                                        Log.e(TAG, "key："+key);
                                        Log.e(TAG, "------------获取key End-----------");
                                        open();
                                        break;
                                    case 5://开锁
                                        Log.e(TAG, "------------开锁 End-----------");
                                        break;
                                }
                            }else{
                                Log.e(TAG, "校验标识错误："+(bytes[6]^(bytes[3]-0x32)));
                                switch ((bytes[6] ^ (bytes[3] - 0x32))) {
                                    case 1:
                                        Log.e(TAG, "CRC 认证错误");
                                        break;
                                    case 2:
                                        Log.e(TAG, "未获取通信key");
                                        break;
                                    case 3:
                                        Log.e(TAG, "已获取通信key，但key错误");
                                        break;
                                }
                            }
                        }else{
                            System.out.println("返回值验证错误");
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String str = "";
                                for (byte b : value) {
                                    str += b +" ";
                                }
                                key = value[4];
                                tv7.setText(str);
                                tv6.setText("接收BLE设备返回数据  "+toHexString(value));
                            }
                        });
                    }
                });
            }
        }).start();
    }

    /**
     * 开锁
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void open(){
        Log.e(TAG, "------------开锁 Strat-----------");
        int rand = 10;
        byte[] bytes = new byte[]{(byte)0xA3,(byte)0xA4,0x0A,0x01,key,0x05,0x01,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
        Log.e(TAG, "原始数据  "+str(bytes));
        byte[] bytes2 = new byte[bytes.length+1];
        Log.e(TAG, "随机数  "+rand);
        bytes[3] = (byte) (0x32+rand);
        Log.e(TAG, "随机数+0x32  "+str(bytes));
        byte[] timeBytes = longToBytes(System.currentTimeMillis());
        for (int i = 0; i < 4; i++) {
            bytes[bytes.length-(i+2)] = timeBytes[timeBytes.length-(i+1)];
        }
        Log.e(TAG, "插入时间戳后的数据  "+str(bytes));
        for (int i = 0; i < bytes.length; i++) {
            if(i > 3) {
                int a = bytes[i]^rand;
                bytes[i] = (byte)a;
            }
        }
        Log.e(TAG, "亦或处理  "+str(bytes));
        byte crc = calcCrc8(bytes);
        for (int i = 0; i < bytes.length; i++) {
            bytes2[i] = bytes[i];
        }
        String str = Integer.toHexString(0x00ff & crc);
        Log.e(TAG, "校验结果:"+str);
        bytes2[bytes2.length-1] = crc;
        write(bytes2);
    }

    /**
     * 获取信息
     */
    private void getInformation(){

    }

    /**
     * 获取key
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void getKey(){
        if(wServiceIsOk&&nServiceIsOk){
            SDTimerUtil timerUtil = new SDTimerUtil();
            timerUtil.startWork(1000, new SDTimerUtil.SDTimerListener() {
                @Override
                public void onWork() {

                }

                @Override
                public void onWorkMain() {Log.e(TAG, "------------获取key Strat-----------");
                    int rand = 10;
                    byte[] bytes = new byte[]{(byte)0xA3,(byte)0xA4,0x08,0x00,0x00,0x01,0x4F,0x6D,0x6E,0x69,0x57,0x34,0x47,0x58};
                    Log.e(TAG, "原始数据  "+str(bytes));
                    byte[] bytes2 = new byte[bytes.length+1];
                    Log.e(TAG, "随机数  "+rand);
                    bytes[3] = (byte) (0x32+rand);
                    Log.e(TAG, "随机数+0x32  "+str(bytes));
                    for (int i = 0; i < bytes.length; i++) {
                        if(i > 3) {
                            int a = bytes[i]^rand;
                            bytes[i] = (byte)a;
                        }
                    }
                    Log.e(TAG, "亦或处理  "+str(bytes));
                    byte crc = calcCrc8(bytes);
                    for (int i = 0; i < bytes.length; i++) {
                        bytes2[i] = bytes[i];
                    }
                    String str = Integer.toHexString(0x00ff & crc);
                    Log.e(TAG, "校验结果:"+str);
                    bytes2[bytes2.length-1] = crc;
                    write(bytes2);
                }
            });
        }
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGattN == null) {
            Log.w(TAG, "NBluetoothAdapter not initialized");
            return;
        }
        boolean isEnableNotification =  mBluetoothGattN.setCharacteristicNotification(characteristic, enabled);
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
    /**
     * 开启蓝牙
     */
    private void openBluetooth(){
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
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

    @Override
    public void onDestroy() {
        if (mBluetoothAdapter != null)
            mBluetoothAdapter.cancelDiscovery();
        super.onDestroy();
    }


    public static String toHexString(byte[] byteArray) {
        final StringBuilder hexString = new StringBuilder("");
        if (byteArray == null || byteArray.length <= 0)
            return null;
        for (int i = 0; i < byteArray.length; i++) {
            int v = byteArray[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                hexString.append(0).append(" ");
            }
            hexString.append(hv).append(" ");
        }
        return hexString.toString().toLowerCase();
    }
    public static byte[] toByteArray(String hexString) {
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() >> 1];
        int index = 0;
        for (int i = 0; i < hexString.length(); i++) {
            if (index  > hexString.length() - 1)
                return byteArray;
            byte highDit = (byte) (Character.digit(hexString.charAt(index), 16) & 0xFF);
            byte lowDit = (byte) (Character.digit(hexString.charAt(index + 1), 16) & 0xFF);
            byteArray[i] = (byte) (highDit << 4 | lowDit);
            index += 2;
        }
        return byteArray;
    }public static String str(byte[] bytes){
        String str = "";
        for (byte aByte : bytes) {
            str += Integer.toHexString(aByte)+" ";
        }
        return str;
    }
    /**
     * 获取随机数   start - end
     * @param start
     * @param end
     * @return
     */
    public static int getRandom(int start ,int end){
        Random random = new Random();
        return random.nextInt(end)%(end-start+1) + start;
    }
    /**
     * 计算数组的CRC8校验值
     *
     * @param data
     *            需要计算的数组
     * @return CRC8校验值
     */
    public static byte calcCrc8(byte[] data) {
        return calcCrc8(data, 0, data.length, (byte) 0);
    }
    /**
     * 计算CRC8校验值
     *
     * @param data
     *            数据
     * @param offset
     *            起始位置
     * @param len
     *            长度
     * @return 校验值
     */
    public static byte calcCrc8(byte[] data, int offset, int len) {
        return calcCrc8(data, offset, len, (byte) 0);
    }

    /**
     * 计算CRC8校验值
     *
     * @param data
     *            数据
     * @param offset
     *            起始位置
     * @param len
     *            长度
     * @param preval
     *            之前的校验值
     * @return 校验值
     */
    public static byte calcCrc8(byte[] data, int offset, int len, byte preval) {
        byte ret = preval;
        for (int i = offset; i < (offset + len); ++i) {
            ret = crc8_tab[(0x00ff & (ret ^ data[i]))];
        }
        return ret;
    }
    static byte[] crc8_tab = { (byte) 0, (byte) 94, (byte) 188, (byte) 226, (byte) 97, (byte) 63, (byte) 221, (byte) 131, (byte) 194, (byte) 156, (byte) 126, (byte) 32, (byte) 163, (byte) 253, (byte) 31, (byte) 65, (byte) 157, (byte) 195, (byte) 33, (byte) 127, (byte) 252, (byte) 162, (byte) 64, (byte) 30, (byte) 95, (byte) 1, (byte) 227, (byte) 189, (byte) 62, (byte) 96, (byte) 130, (byte) 220, (byte) 35, (byte) 125, (byte) 159, (byte) 193, (byte) 66, (byte) 28, (byte) 254, (byte) 160, (byte) 225, (byte) 191, (byte) 93, (byte) 3, (byte) 128, (byte) 222, (byte) 60, (byte) 98, (byte) 190, (byte) 224, (byte) 2, (byte) 92, (byte) 223, (byte) 129, (byte) 99, (byte) 61, (byte) 124, (byte) 34, (byte) 192, (byte) 158, (byte) 29, (byte) 67, (byte) 161, (byte) 255, (byte) 70, (byte) 24,
            (byte) 250, (byte) 164, (byte) 39, (byte) 121, (byte) 155, (byte) 197, (byte) 132, (byte) 218, (byte) 56, (byte) 102, (byte) 229, (byte) 187, (byte) 89, (byte) 7, (byte) 219, (byte) 133, (byte) 103, (byte) 57, (byte) 186, (byte) 228, (byte) 6, (byte) 88, (byte) 25, (byte) 71, (byte) 165, (byte) 251, (byte) 120, (byte) 38, (byte) 196, (byte) 154, (byte) 101, (byte) 59, (byte) 217, (byte) 135, (byte) 4, (byte) 90, (byte) 184, (byte) 230, (byte) 167, (byte) 249, (byte) 27, (byte) 69, (byte) 198, (byte) 152, (byte) 122, (byte) 36, (byte) 248, (byte) 166, (byte) 68, (byte) 26, (byte) 153, (byte) 199, (byte) 37, (byte) 123, (byte) 58, (byte) 100, (byte) 134, (byte) 216, (byte) 91, (byte) 5, (byte) 231, (byte) 185, (byte) 140, (byte) 210, (byte) 48, (byte) 110, (byte) 237,
            (byte) 179, (byte) 81, (byte) 15, (byte) 78, (byte) 16, (byte) 242, (byte) 172, (byte) 47, (byte) 113, (byte) 147, (byte) 205, (byte) 17, (byte) 79, (byte) 173, (byte) 243, (byte) 112, (byte) 46, (byte) 204, (byte) 146, (byte) 211, (byte) 141, (byte) 111, (byte) 49, (byte) 178, (byte) 236, (byte) 14, (byte) 80, (byte) 175, (byte) 241, (byte) 19, (byte) 77, (byte) 206, (byte) 144, (byte) 114, (byte) 44, (byte) 109, (byte) 51, (byte) 209, (byte) 143, (byte) 12, (byte) 82, (byte) 176, (byte) 238, (byte) 50, (byte) 108, (byte) 142, (byte) 208, (byte) 83, (byte) 13, (byte) 239, (byte) 177, (byte) 240, (byte) 174, (byte) 76, (byte) 18, (byte) 145, (byte) 207, (byte) 45, (byte) 115, (byte) 202, (byte) 148, (byte) 118, (byte) 40, (byte) 171, (byte) 245, (byte) 23, (byte) 73, (byte) 8,
            (byte) 86, (byte) 180, (byte) 234, (byte) 105, (byte) 55, (byte) 213, (byte) 139, (byte) 87, (byte) 9, (byte) 235, (byte) 181, (byte) 54, (byte) 104, (byte) 138, (byte) 212, (byte) 149, (byte) 203, (byte) 41, (byte) 119, (byte) 244, (byte) 170, (byte) 72, (byte) 22, (byte) 233, (byte) 183, (byte) 85, (byte) 11, (byte) 136, (byte) 214, (byte) 52, (byte) 106, (byte) 43, (byte) 117, (byte) 151, (byte) 201, (byte) 74, (byte) 20, (byte) 246, (byte) 168, (byte) 116, (byte) 42, (byte) 200, (byte) 150, (byte) 21, (byte) 75, (byte) 169, (byte) 247, (byte) 182, (byte) 232, (byte) 10, (byte) 84, (byte) 215, (byte) 137, (byte) 107, 53 };
    private static ByteBuffer buffer = ByteBuffer.allocate(8);
    private static byte[] longToBytes(long x) {
        buffer.putLong(0, x);
        return buffer.array();
    }
}
