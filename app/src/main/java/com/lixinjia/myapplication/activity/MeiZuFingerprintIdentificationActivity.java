package com.lixinjia.myapplication.activity;

import android.view.View;
import android.widget.TextView;

import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.utils.FingerprintUtil;
import com.blankj.utilcode.util.LogUtils;
import com.fingerprints.service.FingerprintManager;


/**
 * 作者：李忻佳
 * 时间：2017/5/4
 * 说明：魅族指纹识别Activity
 *          支持魅族MX5, POR5, MA01, MA01C, 魅蓝3S等android L机型,对于魅族android M机型来说, 指纹接口与android原生接口保持一致
 */

public class MeiZuFingerprintIdentificationActivity extends BaseActivity {

    private FingerprintManager mFM;
    private FingerprintManager.IdentifyCallback mIdentifyCallback;
    private TextView startVerify;
    private TextView stopVerify;

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
        mTitle.setCenterText("魅族指纹识别");
    }

    @Override
    public void addListener() {
        startVerify.setOnClickListener(this);
        stopVerify.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        if(v == startVerify){
            startVerify();
        }
        if(v == stopVerify){
            if(mFM!=null){
                mFM.release();
            }
        }
    }

    @Override
    public void doBusiness() {
    }

    public void startVerify() {
        LogUtils.d("开始识别");
        initFingPrintManager(); //得到FingerprintManager实例
        if (mFM == null){
            showToast("此设备不支持指纹识别");
            return;
        }
        if (mFM.getIds() == null) { //得到系统中已经录入的指纹个数
            showToast("您还没有录制指纹，请录入！");
            FingerprintUtil.openFingerPrintSettingPage(this);
            return;
        }
        if (mIdentifyCallback == null) {
            showToast("指纹识别已经开启，长按指纹解锁键");
            mIdentifyCallback = createIdentifyCallback(); //创建指纹认证回调函数
        }
        mFM.startIdentify(mIdentifyCallback, mFM.getIds()); //调用指纹认证接口
    }
    private void initFingPrintManager() {
        if (mFM == null) {
            mFM = FingerprintManager.open(); //调用open方法得到FingerprintManager
        }
    }
    private FingerprintManager.IdentifyCallback createIdentifyCallback() {
        return new FingerprintManager.IdentifyCallback() {
            @Override
            public void onIdentified(int id, boolean updated) { //认证成功
                showToast("认证成功!, 指纹Id:" + id);
                mFM.release(); //认证成功后release, 需要注意的是在不使用指纹功能后必须要调用release, 也就是说open和release严格配对
                //否则会造成mBack不能使用, 因为只有调用release之后才能从指纹模式切换到back模式
            }
            @Override
            public void onNoMatch() { //认证失败
                showToast("认证失败! ");
                startVerify(); //一次认证失败后重新再次发起认证
            }
        };
    }

    @Override
    protected void onDestroy() {
        if(mFM!=null){
            mFM.release();
            mFM = null;
        }
        super.onDestroy();
    }
}
