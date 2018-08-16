package com.lixinjia.myapplication.activity.lrecyclerview;

import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.util.WeakHandler;
import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.activity.BaseLRecycerviewActivity;
import com.lixinjia.myapplication.adapter.lrecyclerview.MyLRecyclerviewAdapter;
import com.lixinjia.myapplication.model.LRecyclerViewModel;
import com.lixinjia.myapplication.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：李忻佳
 * 日期：2018/6/21
 * 说明：
 */

public class TextLRecyclerviewActivity extends BaseLRecycerviewActivity {
    private LRecyclerView mRecyclerView;
    private List<LRecyclerViewModel> list = new ArrayList<>();
    private MyLRecyclerviewAdapter mDataAdapter;
    private Button xianBt;
    private Button wangBt;

    //WeakHandler必须是Activity的一个实例变量.原因详见：http://dk-exp.com/2015/11/11/weak-handler/
    private WeakHandler mHandler = new WeakHandler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    int currentSize = mDataAdapter.getItemCount();
                    //模拟组装10个数据
                    List<LRecyclerViewModel> newList = new ArrayList<>();
                    for (int i = 0; i < 20; i++) {
                        if (newList.size() + currentSize >= TOTAL_COUNTER) {
                            break;
                        }
                        LRecyclerViewModel item = new LRecyclerViewModel();
                        item.setName("item" + (currentSize + i) + "");
                        newList.add(item);
                    }
                    addDataAll(newList);
                    break;
                case -3:
                    mRecyclerView.refreshComplete(REQUEST_COUNT);
                    notifyDataSetChanged();
                    mRecyclerView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                        @Override
                        public void reload() {
                            requestData();
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public int bindLayout() {
        return R.layout.act_lrecyclenview;
    }

    @Override
    public void initView(View view) {
        mRecyclerView = $(R.id.lrcyclenview_lr);
        REQUEST_COUNT = 20;

        xianBt = $(R.id.lrcyclenview_xian);
        wangBt = $(R.id.lrcyclenview_wang);
    }

    @Override
    public void addListener() {
        wangBt.setOnClickListener(this);
        xianBt.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        if (v == wangBt) {//设置为网格
            setLayout(Layout.GRID);
        }
        if (v == xianBt) {//设置为线性
            setLayout(Layout.LINEAR);
        }
    }

    @Override
    public void doBusiness() {
        mDataAdapter = new MyLRecyclerviewAdapter(list, mActivity);
        initLRecycerview(mRecyclerView, mDataAdapter);
    }

    @Override
    public void onRefreshs() {
        requestData();
    }

    @Override
    public void onLoadMores() {
        requestData();
    }

    /**
     * 模拟请求网络
     */
    private void requestData() {
        Log.d(TAG, "requestData");
        new Thread() {

            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //模拟一下网络请求失败的情况
                if (NetworkUtils.isNetAvailable(getApplicationContext())) {
                    mHandler.sendEmptyMessage(-1);
                } else {
                    mHandler.sendEmptyMessage(-3);
                }
            }
        }.start();
    }

    private void notifyDataSetChanged() {
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }
}
