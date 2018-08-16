package com.lixinjia.myapplication.activity.lrecyclerview;

import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.ItemDecoration.GridItemDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.github.jdsjlzx.util.WeakHandler;
import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.activity.BaseActivity;
import com.lixinjia.myapplication.adapter.lrecyclerview.MyLRecyclerviewAdapter;
import com.lixinjia.myapplication.model.LRecyclerViewModel;
import com.lixinjia.myapplication.utils.NetworkUtils;
import com.lixinjia.myapplication.view.SampleFooter;
import com.lixinjia.myapplication.view.SampleHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：李忻佳
 * 日期：2018/6/21
 * 说明：LRecyclerview
 */

public class LRecyclerviewActivity extends BaseActivity {
    /**服务器端一共多少条数据*/
    private static final int TOTAL_COUNTER = 34;//如果服务器没有返回总数据或者总页数，这里设置为最大值比如10000，什么时候没有数据了根据接口返回判断

    /**每一页展示多少条数据*/
    private static final int REQUEST_COUNT = 10;

    /**已经获取到多少条数据了*/
    private static int mCurrentCounter = 0;

    private LRecyclerView mRecyclerView;
    private Button xianBt;
    private Button wangBt;

    private List<LRecyclerViewModel> list = new ArrayList<>();
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private MyLRecyclerviewAdapter mDataAdapter;

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
                        item.setName("item"+(currentSize + i)+"");
                        newList.add(item);
                    }
                    addItems(newList);
                    mRecyclerView.refreshComplete(REQUEST_COUNT);
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
        mTitle.setCenterText("LRecyclerview");

        mRecyclerView = $(R.id.lrcyclenview_lr);
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
        if(v == wangBt){//设置为网格
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
            mRecyclerView.setAdapter(mLRecyclerViewAdapter);//必须重新setAdapter
        }
        if(v == xianBt){//设置为线性
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public void doBusiness() {
        mDataAdapter = new MyLRecyclerviewAdapter(list,mActivity);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mDataAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);
        //设置布局
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //禁用下拉刷新功能
        mRecyclerView.setPullRefreshEnabled(true);
        //禁用自动加载更多功能  设置尾部会失效
        mRecyclerView.setLoadMoreEnabled(false);
        //设置分割线
        GridItemDecoration divider = new GridItemDecoration.Builder(this)
                .setHorizontal(R.dimen.default_divider_height)
                .setVertical(R.dimen.default_divider_height)
                .setColorResource(R.color.stroke)
                .build();
        mRecyclerView.addItemDecoration(divider);
        //设置头部
        mLRecyclerViewAdapter.addHeaderView(new SampleHeader(this));
        //获取尾部
        SampleFooter sampleFooter = new SampleFooter(this);
        //点击尾部回调
        sampleFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<LRecyclerViewModel> dataList = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    LRecyclerViewModel itemModel = new LRecyclerViewModel();
                    itemModel.setName("item" + (i + mDataAdapter.getItemCount()));
                    dataList.add(itemModel);
                }
                mDataAdapter.addAll(dataList);
            }
        });
        //设置尾部，自动加载更多会失效
        mLRecyclerViewAdapter.addFooterView(sampleFooter);
        //设置下拉刷新进度样式
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        //设置上拉刷新进度样式
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        //设置头部加载颜色
        mRecyclerView.setHeaderViewColor(R.color.colorAccent, R.color.dark ,R.color.white);
        //设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.colorAccent, R.color.dark ,R.color.white);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint("拼命加载中","我是有底线的","网络不给力啊，点击再试一次吧");
        //下拉刷新回调
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDataAdapter.clear();
                mLRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
                mCurrentCounter = 0;
                requestData();
            }
        });
        //上拉加载回调
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < TOTAL_COUNTER) {
                    // loading more
                    requestData();
                } else {
                    //the end
                    mRecyclerView.setNoMore(true);
                }
            }
        });
        //滑动状态
        mRecyclerView.setLScrollListener(new LRecyclerView.LScrollListener() {
            @Override
            public void onScrollUp() {}
            @Override
            public void onScrollDown() {}
            @Override
            public void onScrolled(int distanceX, int distanceY) {}
            @Override
            public void onScrollStateChanged(int state) {}
        });
        //开始刷新
        mRecyclerView.refresh();
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
                if(NetworkUtils.isNetAvailable(getApplicationContext())) {
                    mHandler.sendEmptyMessage(-1);
                } else {
                    mHandler.sendEmptyMessage(-3);
                }
            }
        }.start();
    }
    private void addItems(List<LRecyclerViewModel> list) {
        mDataAdapter.addAll(list);
        mCurrentCounter += list.size();
    }
    private void notifyDataSetChanged() {
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }
}
