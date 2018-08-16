package com.lixinjia.myapplication.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.jdsjlzx.ItemDecoration.GridItemDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.adapter.lrecyclerview.BaseRecyclerAdapter;

import java.util.List;

/**
 * 作者：李忻佳
 * 日期：2018/6/21
 * 说明：
 */

public abstract class BaseLRecycerviewActivity<T> extends BaseActivity {
    //服务器端一共多少条数据
    protected static int TOTAL_COUNTER = 65;//如果服务器没有返回总数据或者总页数，这里设置为最大值比如10000，什么时候没有数据了根据接口返回判断
    // 每一页展示多少条数据
    protected static int REQUEST_COUNT = 10;
    //已经获取到多少条数据了
    protected static int mCurrentCounter = 0;
    //列数
    protected int gridSpanCount = 2;
    private LRecyclerView mRecyclerView;
    protected BaseRecyclerAdapter mBaseRecyclerAdapter;
    protected LRecyclerViewAdapter mLRecyclerViewAdapter;

    protected void initLRecycerview(LRecyclerView lRecyclerView, BaseRecyclerAdapter baseRecyclerAdapter) {
        this.mRecyclerView = lRecyclerView;
        this.mBaseRecyclerAdapter = baseRecyclerAdapter;
        init();
    }

    private void init() {
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mBaseRecyclerAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);
        setLayout(Layout.LINEAR);
        //下拉刷新功能
        setPullRefreshEnabled(true);
        //自动加载更多功能  设置尾部会失效
        setLoadMoreEnabled(true);
        //设置分割线
        GridItemDecoration divider = new GridItemDecoration.Builder(this)
                .setHorizontal(R.dimen.default_divider_height)
                .setVertical(R.dimen.default_divider_height)
                .setColorResource(R.color.stroke)
                .build();
        //设置分割线
        addItemDecoration(divider);
        //设置下拉刷新进度样式
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.Pacman);
        //设置上拉刷新进度样式
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        //设置头部加载颜色
        mRecyclerView.setHeaderViewColor(R.color.colorAccent, R.color.dark, R.color.white);
        //设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.colorAccent, R.color.dark ,R.color.white);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint("拼命加载中","没有更多了","网络不给力啊，点击再试一次吧");
        //下拉刷新回调
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBaseRecyclerAdapter.clear();
                mLRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
                mCurrentCounter = 0;
                onRefreshs();
            }
        });
        //上拉加载回调
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < TOTAL_COUNTER) {
                    onLoadMores();
                } else {
                    //设置是否已加载全部
                    mRecyclerView.setNoMore(true);
                }
            }
        });
        //网络出错回调
        mRecyclerView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
            @Override
            public void reload() {
                mRecyclerView.refreshComplete(REQUEST_COUNT);
                mLRecyclerViewAdapter.notifyDataSetChanged();
                reloads();
            }
        });
        //开始刷新
        mRecyclerView.refresh();
    }

    //设置尾部，自动加载更多会失效
    protected void addFooterView(View view) {
        mLRecyclerViewAdapter.addFooterView(view);
    }

    //设置头部
    protected void addHeaderView(View view) {
        mLRecyclerViewAdapter.addHeaderView(view);
    }

    //设置布局
    protected void setLayout(Layout layout) {
        switch (layout) {
            case LINEAR:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case GRID:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridSpanCount));
                mRecyclerView.setAdapter(mLRecyclerViewAdapter);//必须重新setAdapter
                break;
        }
    }

    //设置分割线
    protected void addItemDecoration(GridItemDecoration divider) {
        mRecyclerView.addItemDecoration(divider);
    }

    //禁用下拉刷新功能
    protected void setPullRefreshEnabled(boolean enabled) {
        mRecyclerView.setPullRefreshEnabled(enabled);
    }

    //禁用自动加载更多功能  设置尾部会失效
    protected void setLoadMoreEnabled(boolean enabled) {
        mRecyclerView.setLoadMoreEnabled(enabled);
    }

    //下拉刷新
    public abstract void onRefreshs();

    //上拉加载
    public abstract void onLoadMores();

    //网络出错回调
    public void reloads(){}

    //添加数据
    public void addDataAll(List<T> list) {
        mBaseRecyclerAdapter.addAll(list);
        mCurrentCounter += list.size();
        mRecyclerView.refreshComplete(REQUEST_COUNT);
    }

    protected enum Layout {
        LINEAR,//线性
        GRID //网格
    }
}
