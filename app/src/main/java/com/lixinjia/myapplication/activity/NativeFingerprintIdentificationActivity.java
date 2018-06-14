package com.lixinjia.myapplication.activity;

import android.hardware.fingerprint.FingerprintManager;
import android.view.View;
import android.widget.TextView;

import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.core.FingerprintCore;
import com.lixinjia.myapplication.utils.FingerprintUtil;

/**
 * 作者：李忻佳
 * 时间：2017/5/4
 * 说明：Android原生指纹识别Activity
 */

public class NativeFingerprintIdentificationActivity extends BaseActivity {
    private TextView startVerify;
    private TextView stopVerify;
    private FingerprintCore mFingerprintCore;

    @Override
    public int bindLayout() {
        return R.layout.act_fingerprint_indentfication;
    }

    @Override
    public void initView(View view) {
        startVerify = $(R.id.act_fingerprint_indentfication_startVerify);
        stopVerify = $(R.id.act_fingerprint_indentfication_stopVerify);
    }

    @Override
    public void doView() {
        mTitle.setCenterText("原生指纹识别");
    }

    @Override
    public void addListener() {
        startVerify.setOnClickListener(this);
        stopVerify.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        if(v == startVerify){
            startFingerprintRecognition();
        }
        if(v == stopVerify){
            cancelFingerprintRecognition();
        }
    }

    @Override
    public void doBusiness() {
        initFingerprintCore();
    }

    /**
     * 开始指纹识别
     */
    private void startFingerprintRecognition() {
        if (mFingerprintCore.isSupport()) {
            if (!mFingerprintCore.isHasEnrolledFingerprints()) {
                showToast("您还没有录制指纹，请录入！");
                FingerprintUtil.openFingerPrintSettingPage(this);
                return;
            }
            showToast("请进行指纹识别，长按指纹解锁键");
            if (mFingerprintCore.isAuthenticating()) {
                showToast("指纹识别已经开启，长按指纹解锁键");
            } else {
                mFingerprintCore.startAuthenticate();
            }
        } else {
            showToast("此设备不支持指纹解锁");
        }
    }

    /**
     * 取消指纹识别
     */
    private void cancelFingerprintRecognition() {
        if (mFingerprintCore.isAuthenticating()) {
            mFingerprintCore.cancelAuthenticate();
        }
    }

    /**
     * 初始指纹识别
     */
    private void initFingerprintCore() {
        mFingerprintCore = new FingerprintCore(this);
        mFingerprintCore.setFingerprintManager(mResultListener);
    }

    /**
     * 识别回调
     */
    private FingerprintCore.IFingerprintResultListener mResultListener = new FingerprintCore.IFingerprintResultListener() {
        @Override
        public void onAuthenticateSuccess(FingerprintManager.AuthenticationResult result) {
            showToast("指纹识别成功");
        }

        @Override
        public void onAuthenticateFailed(int helpId) {
            showToast("指纹识别失败，请重试！");
        }

        @Override
        public void onAuthenticateError(int errMsgId) {
            showToast("指纹识别错误，等待几秒之后再重试");
        }

        @Override
        public void onStartAuthenticateResult(boolean isSuccess) {

        }

    };

    @Override
    protected void onDestroy() {
        if (mFingerprintCore != null) {
            mFingerprintCore.onDestroy();
            mFingerprintCore = null;
        }
        mResultListener = null;
        super.onDestroy();
    }
}
