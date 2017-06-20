package com.xinjiage.stepdetector;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.xinjiage.stepdetector.service.StepCounterService;

/**
 * 作者：李忻佳
 * 时间：2017/6/8
 * 说明：
 */

public class StepDetector {
    private static StepDetector mStepDetector;
    private String stepSensorTypeName = "step_sensor_type_name";
    private String stepTotalName = "step_total_name";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private onStepListener mStepListener;
    private Application mApplication;


    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(mStepListener!=null){
                mStepListener.onListener(msg.arg2,msg.arg1);
            }
        }
    };

    public static StepDetector getInstance() {
        if (mStepDetector == null) {
            mStepDetector = new StepDetector();
        }
        return mStepDetector;
    }

    /**
     * 初始化
     * @param application
     */
    public StepDetector init(Application application){
        this.mApplication = application;
        initType(null);
        return this;
    }

    /**
     * 初始化
     * @param application
     * @param stepSensorType
     *          TYPE_STEP_COUNTER:获取手机计步总步数模式
     *          TYPE_STEP_DETECTOR:获取手机计步每步模式
     */
    public StepDetector init(Application application, stepSensorType stepSensorType){
        this.mApplication = application;
        initType(stepSensorType);
        return this;
    }

    /**
     * 判断支持计步类型
     * @param type
     */
    private void initType(stepSensorType type){
        mSharedPreferences = mApplication.getSharedPreferences(getClass().getName(), Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        if(type == null){
            SensorManager mSensorManager = (SensorManager) mApplication.getSystemService(Activity.SENSOR_SERVICE);
            Sensor mSensorC = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            Sensor mSensorD = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            if(mSensorC != null){
                mEditor.putInt(stepSensorTypeName,1);
            }else if(mSensorD != null){
                mEditor.putInt(stepSensorTypeName,2);
            }else{
                mEditor.putInt(stepSensorTypeName,3);
            }
        }else{
            if(type == stepSensorType.TYPE_STEP_COUNTER){
                mEditor.putInt(stepSensorTypeName,1);
            }else if(type == stepSensorType.TYPE_STEP_DETECTOR){
                mEditor.putInt(stepSensorTypeName,2);
            }else if(type == stepSensorType.TYPE_ACCELEROMETER){
                mEditor.putInt(stepSensorTypeName,3);
            }
        }
        mEditor.commit();
    }

    /**
     * 开启计步服务
     */
    public void startStepService(){
        if(mApplication == null){
            Log.e("StepDetector","请执行StepDetector.getInstance().init()进行初始化");
        }else {
            Intent service = new Intent(mApplication, StepCounterService.class);
            mApplication.startService(service);
        }
    }

    /**
     * 设置回调
     * @param Listener
     */
    public void setmStepListener(onStepListener Listener){
        this.mStepListener = Listener;
    }
    public interface onStepListener{
        public void onListener(int count,int step);
    }

    /**
     * 计步类型
     */
    private enum stepSensorType{
        TYPE_STEP_COUNTER,
        TYPE_STEP_DETECTOR,
        TYPE_ACCELEROMETER
    }

    /**
     * 是否支持谷歌计步
     * @return
     */
    public boolean isStandByGoogleStep(){
        if(mSharedPreferences.getInt(stepSensorTypeName,0) == 3){
            return false;
        }else{
            return true;
        }
    }
    public String getStepSensorTypeName() {
        return stepSensorTypeName;
    }

    public SharedPreferences.Editor getmEditor() {
        return mEditor;
    }

    public SharedPreferences getmSharedPreferences() {
        return mSharedPreferences;
    }

    public String getStepTotalName() {
        return stepTotalName;
    }
}
