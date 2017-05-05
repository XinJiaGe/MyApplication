package com.adaptation.lixinjia.myapplication.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * 作者：李忻佳
 * 时间：2017/5/2
 * 说明：BaseFragment
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    protected final String TAG = this.getClass().getSimpleName();
    private View mContextView = null;
    protected FragmentActivity mActivity;
    private Toast mToast;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContextView = inflater.inflate(bindLayout(), container, false);
        mActivity = getActivity();
        initView(mContextView);
        doView();
        addListener();
        doBusiness();
        return mContextView;
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
     * View操作
     *
     */
    public void doView(){};

    /**
     * 设置监听
     */
    public abstract void addListener();

    /** View点击 **/
    public abstract void widgetClick(View v);
    /**
     * 业务操作
     *
     */
    public abstract void doBusiness();
    @Override
    public void onClick(View v) {
        if (fastClick())
            widgetClick(v);
    }
    /**
     * 绑定控件
     *
     * @param resId
     *
     * @return
     */
    public <T extends View> T $(int resId) {
        return (T) mContextView.findViewById(resId);
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
     * 打印LOG D
     * @param msg
     */
    protected void LogD(String msg){
        Log.d(TAG,msg);
    }

    /**
     * 简化Toast
     */
    protected void showToast(String messageId){
        if (mToast == null) {
            mToast = Toast.makeText(mActivity, messageId, Toast.LENGTH_SHORT);
        }
        mToast.setText(messageId);
        mToast.cancel();
        mHandler.removeCallbacks(mShowToastRunnable);
        mHandler.postDelayed(mShowToastRunnable, 0);
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
}
