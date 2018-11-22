package com.lixinjia.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.view.TitleView;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * 作者：李忻佳
 * 时间：2017/5/2
 * 说明：BaseActivity
 */

public abstract class BaseActivity extends AutoLayoutActivity implements View.OnClickListener {
    protected final String TAG = this.getClass().getSimpleName();
    protected Activity mActivity;
    /** 是否沉浸状态栏 **/
    private boolean isSetStatusBar = false;
    /** 是否无标题 **/
    private boolean isNotTitle = false;
    /** 是否无状态栏 **/
    private boolean isNotStatusBar = false;
    /** 当前Activity渲染的视图View **/
    private View mContextView = null;
    private Toast mToast;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    protected TitleView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBar();
        Bundle bundle = getIntent().getExtras();
        initParms(bundle);
        View mView = bindView();
        if (null == mView) {
            mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        } else
            mContextView = mView;
        if (isSetStatusBar) {
            steepStatusBar();
        }
        mActivity = this;
        setContentView(mContextView);
        initView(mContextView);
        doView();
        addListener();
        doBusiness();
    }

    @Override
    public void setContentView(View view) {
        if(isNotStatusBar) {//是否显示状态栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        View VaseView = getLayoutInflater().inflate(R.layout.act_base, null);
        //设置填充act_base布局
        super.setContentView(VaseView);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            view.setFitsSystemWindows(true);
        }
        //加载子类Activity的布局
        if(!isNotTitle){//是否显示title
            //添加title
            FrameLayout titleFrame = (FrameLayout) findViewById(R.id.act_base_title);
            mTitle = new TitleView(mActivity);
            mTitle.getBack().setOnClickListener(this);
            titleFrame.addView(mTitle);
        }
        //添加主布局
        FrameLayout contentView = (FrameLayout) findViewById(R.id.act_base_content);
        contentView.addView(view);
    }
    @Override
    public void onClick(View v) {
        if (fastClick()) {
            if(v == mTitle.getBack()){
                if(mTitle.getmOnBackClickListener()==null){
                    finish();
                }else{
                    mTitle.getmOnBackClickListener().onBackListener();
                }
            }else{
                widgetClick(v);
            }
        }
    }
    /**
     * 沉浸状态栏
     */
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 横竖屏切换
     */
    public void screenSwitch(){
        if(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    /**
     * 初始化头
     *
     */
    public void initBar(){}
    /**
     * 初始化参数
     *
     * @param parms
     */
    public void initParms(Bundle parms){}

    /**
     * 绑定视图
     *
     * @return
     */
    public View bindView() {
        return null;
    }

    /**
     * 绑定布局
     *
     * @return
     */
    public abstract int bindLayout();

    /**
     * 初始化控件
     *
     * @param view
     */
    public abstract void initView(final View view);

    /**
     * 绑定控件
     *
     * @param resId
     *
     * @return
     */
    protected <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    /**
     * 设置监听
     */
    public void addListener(){};

    /**
     * View点击
     * @param v
     */
    public void widgetClick(View v){}
    /**
     * 业务操作
     *
     */
    public void doBusiness(){}
    /**
     * View操作
     *
     */
    public void doView(){}

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 页面跳转
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this,clz));
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 简化Toast
     */
    protected void showToast(String messageId){
        if (mToast == null) {
            mToast = Toast.makeText(this, messageId, Toast.LENGTH_SHORT);
        }
        mToast.setText(messageId);
        mToast.cancel();
        mHandler.removeCallbacks(mShowToastRunnable);
        mHandler.postDelayed(mShowToastRunnable, 0);
    }

    /**
     * 是否设置沉浸状态栏
     *
     * @param isSetStatusBar
     */
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }

    /**
     * 防止快速点击
     *
     * @return
     */
    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }
    /**
     * show Toast
     */
    private Runnable mShowToastRunnable = new Runnable() {
        @Override
        public void run() {
            mToast.show();
        }
    };

    public void setNotTitle(boolean notTitle) {
        isNotTitle = notTitle;
    }

    public void setNotStatusBar(boolean notStatusBar) {
        isNotStatusBar = notStatusBar;
    }
}
