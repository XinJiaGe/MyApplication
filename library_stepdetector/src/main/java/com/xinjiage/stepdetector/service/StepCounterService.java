package com.xinjiage.stepdetector.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.xinjiage.stepdetector.StepDetector;

/**
 * 作者：李忻佳
 * 时间：2017/6/8
 * 说明：计步服务
 */

public class StepCounterService extends Service implements SensorEventListener {
    private Boolean FLAG = false;// 服务运行标志
    private float SENSITIVITYMINALL = 3;   //灵敏度最小值
    private float SENSITIVITYMAXAll = 50;   //灵敏度最大值
    private SensorManager mSensorManager;
    private int StepSensorType;
    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;
    private int CURRENT_SETP = 0;
    private int CURRENT_SETP_COUNT = 0;
    private float mLastValues[] = new float[3 * 2];
    private float mScale[] = new float[2];
    private float mYOffset;
    private static long end = 0;
    private static long start = 0;
    /** 最后加速度方向 */
    private float mLastDirections[] = new float[3 * 2];
    private float mLastExtremes[][] = { new float[3 * 2], new float[3 * 2] };
    private float mLastDiff[] = new float[3 * 2];
    private int mLastMatch = -1;
    private float mGravity = SensorManager.STANDARD_GRAVITY-0.8f;
    private float diff;

    @Override
    public void onCreate() {
        super.onCreate();
        FLAG = true;// 标记为服务正在运行
        // 获取传感器的服务，初始化传感器
        mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        StepSensorType = StepDetector.getInstance().getmSharedPreferences().getInt(StepDetector.getInstance().getStepSensorTypeName(),3);
        if(StepSensorType == 1){
            // 注册传感器，注册监听器
            mSensorManager.registerListener(StepCounterService.this,mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),SensorManager.SENSOR_DELAY_FASTEST);
        }else if(StepSensorType == 2){
            // 注册传感器，注册监听器
            mSensorManager.registerListener(StepCounterService.this,mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR),SensorManager.SENSOR_DELAY_FASTEST);
        }else{
            // 注册传感器，注册监听器
            mSensorManager.registerListener(StepCounterService.this,mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_FASTEST);
            // 电源管理服务
            mPowerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
            mWakeLock = mPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK|PowerManager.ON_AFTER_RELEASE, "S");
            mWakeLock.acquire();

            int h = 480;
            mYOffset = h * 0.5f;
            mScale[0] = -(h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
            mScale[1] = -(h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FLAG = false;// 服务停止
        mSensorManager.unregisterListener(this);
        if (mWakeLock != null) {
            mWakeLock.release();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(StepSensorType == 1){
            int stepTotal = StepDetector.getInstance().getmSharedPreferences().getInt(StepDetector.getInstance().getStepTotalName(),0);
            float mCount = event.values[0];
            CURRENT_SETP_COUNT = (int) mCount;
            StepDetector.getInstance().getmEditor().putInt(StepDetector.getInstance().getStepTotalName(), (int) mCount);
            if(stepTotal!=0&&mCount>stepTotal){
                CURRENT_SETP += mCount-stepTotal;
            }
            if(mCount == 0){
                CURRENT_SETP++;
            }
            send();
        }else if(StepSensorType == 2){
            if (event.values[0]==1.0f) {
                CURRENT_SETP++;
                send();
            }
        }else{
            Sensor sensor = event.sensor;
            synchronized (this) {
                if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    int j = (sensor.getType() == Sensor.TYPE_ACCELEROMETER) ? 1 : 0;
                    if (j == 1) {
                        float vSum = 0;
                        for (int i = 0; i < 3; i++) {
                            final float v = mYOffset + event.values[i] * mScale[j];
                            vSum += v;
                        }
                        int k = 0;
                        float v = vSum / 3;

                        float direction = (v > mLastValues[k] ? 1 : (v < mLastValues[k] ? -1 : 0));
                        if (direction == -mLastDirections[k]) {
                            // Direction changed
                            int extType = (direction > 0 ? 0 : 1); // minumum or
                            // maximum?
                            mLastExtremes[extType][k] = mLastValues[k];
                            diff = Math.abs(mLastExtremes[extType][k] - mLastExtremes[1 - extType][k]);
//							Log.d("DIFF",diff+"");
                            if (diff > SENSITIVITYMINALL && diff < SENSITIVITYMAXAll) {
                                boolean isAlmostAsLargeAsPrevious = diff > (mLastDiff[k] * 2 / 3);
                                boolean isPreviousLargeEnough = mLastDiff[k] > (diff / 3);
                                boolean isNotContra = (mLastMatch != 1 - extType);

                                if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough && isNotContra) {
                                    end = System.currentTimeMillis();
                                    if (end - start > 500) {// 此时判断为走了一步
                                        mLastMatch = extType;
                                        start = end;
                                        CURRENT_SETP++;
                                        send();
                                    }
                                } else {
                                    mLastMatch = -1;
                                }
                            }
                            mLastDiff[k] = diff;
                        }
                        mLastDirections[k] = direction;
                        mLastValues[k] = v;
                    }
                }
            }
        }
    }
    private void send(){
        Message message = new Message();
        message.arg1 = CURRENT_SETP;
        message.arg2 = CURRENT_SETP_COUNT;
        StepDetector.getInstance().handler.sendMessage(message);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public Boolean getFLAG() {
        return FLAG;
    }
}
