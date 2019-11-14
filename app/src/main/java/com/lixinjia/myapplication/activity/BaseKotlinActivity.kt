package com.lixinjia.myapplication.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.lixinjia.myapplication.R
import com.lixinjia.myapplication.view.TitleView
import com.sunday.eventbus.SDBaseEvent
import com.sunday.eventbus.SDEventManager
import com.sunday.eventbus.SDEventObserver
import com.zhy.autolayout.AutoLayoutActivity
import kotlinx.android.synthetic.main.act_base.*

abstract class BaseKotlinActivity : AutoLayoutActivity(), View.OnClickListener, SDEventObserver {
    val mHandler = Handler(Looper.getMainLooper())
    var mContextView: View? = null
    /** 是否沉浸状态栏  */
    val isSetStatusBar = false
    /** 是否无标题  */
    val isNotTitle = false
    /** 是否无状态栏  */
    val isNotStatusBar = false
    open var mActivity: BaseKotlinActivity? = null
    open var mTitle: TitleView? = null
    var mToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        initParms(bundle)
        val mView = bindView()
        if (null == mView) {
            mContextView = LayoutInflater.from(this).inflate(bindLayout(), null)
        } else
            mContextView = mView
        if (isSetStatusBar) {
            steepStatusBar()
        }
        mActivity = this;
        setContentView(mContextView)
        init();
        doView()
        setClick(addListener())
        doBusiness()
    }

    override fun setContentView(view: View?) {
        if (isNotStatusBar) {//是否显示状态栏
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        super.setContentView(R.layout.act_base)
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) view?.fitsSystemWindows = true
        //加载子类Activity的布局
        if (!isNotTitle) {//是否显示title
            //添加title
            mTitle = TitleView(mActivity)
            mTitle?.getBack()?.setOnClickListener(this)
            act_base_title.addView(mTitle)
        }
        //添加主布局
        act_base_content.addView(view)
    }

    /**
     * 初始化
     */
    private fun init() {
        SDEventManager.register(this)
    }

    /**
     * 循环设置点击事件
     */
    fun setClick(addListener: List<View?>) {
        for (view in addListener){
            view?.setOnClickListener(this)
        }
    }

    /**
     * 事件响应
     */
    override fun onClick(v: View?) {
        if (fastClick()) {
            if (v === mTitle?.back) {
                if (mTitle?.getmOnBackClickListener() == null) {
                    finish()
                } else {
                    mTitle!!.getmOnBackClickListener().onBackListener()
                }
            } else {
                widgetClick(v)
            }
        }
    }

    /**
     * 绑定视图
     * @return
     */
    open fun bindView(): View? {
        return null
    }
    /**
     * 初始化参数
     * @param parms
     */
    open fun initParms(parms: Bundle?) {}
    /**
     * 绑定布局
     * @return
     */
    abstract fun bindLayout(): Int

    /**
     * View操作
     */
    open fun doView() {}

    /**
     * 设置监听
     */
    abstract fun addListener(): List<View?>

    /**
     * View点击
     * @param view
     */
    abstract fun widgetClick(view: View?)

    /**
     * 业务操作

     */
    abstract fun doBusiness()
    /**
     * 防止快速点击

     * @return
     */
    private fun fastClick(): Boolean {
        var lastClick: Long = 0
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false
        }
        lastClick = System.currentTimeMillis()
        return true
    }
    /**
     * 沉浸状态栏
     */
    private fun steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // 透明导航栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
    }

    /**
     * 横竖屏切换
     */
    fun screenSwitch() {
        if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    /**
     * 页面跳转
     * @param clz
     */
    fun startActivity(clz: Class<*>) {
        startActivity(Intent(this@BaseKotlinActivity, clz))
    }

    /**
     * 携带数据的页面跳转
     * @param clz
     * *
     * @param bundle
     */
    fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val intent = Intent()
        intent.setClass(this, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     * @param cls
     * *
     * @param bundle
     * *
     * @param requestCode
     */
    fun startActivityForResult(cls: Class<*>, bundle: Bundle?, requestCode: Int) {
        val intent = Intent()
        intent.setClass(this, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    /**
     * 简化Toast
     */
    protected fun showToast(messageId: String) {
        mToast = Toast.makeText(this, messageId, Toast.LENGTH_SHORT)
        mToast?.setText(messageId)
        mToast?.cancel()
        mHandler.removeCallbacks(mShowToastRunnable)
        mHandler.postDelayed(mShowToastRunnable, 0)
    }

    /**
     * show Toast
     */
    private val mShowToastRunnable = Runnable { mToast?.show() }


    override fun onEvent(event: SDBaseEvent) {}

    override fun onEventMainThread(event: SDBaseEvent) {}

    override fun onEventBackgroundThread(event: SDBaseEvent) {}

    override fun onEventAsync(event: SDBaseEvent) {}
}
