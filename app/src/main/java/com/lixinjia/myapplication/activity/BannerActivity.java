package com.lixinjia.myapplication.activity;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.adapter.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：李忻佳
 * 日期：2018/1/4/004.
 * 说明：
 */

public class BannerActivity extends BaseActivity {
    private Banner banner;
    private TextSwitcher tv_notice;
    private MarqueeView marqueeView;
    private String[] mAdvertisements;
    private final int HOME_AD_RESULT = 1;
    private int mSwitcherCount = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                // 广告
                case HOME_AD_RESULT:
                    tv_notice.setText(Html.fromHtml(mAdvertisements[mSwitcherCount % mAdvertisements.length]));
                    mSwitcherCount++;
                    mHandler.sendEmptyMessageDelayed(HOME_AD_RESULT, 3000);
                    break;
            }

        }
    };

    @Override
    public int bindLayout() {
        return R.layout.act_banner;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("轮播");
        banner = $(R.id.banner);
        tv_notice = $(R.id.act_banner_text);
        marqueeView = $(R.id.marqueeView);
    }

    @Override
    public void addListener() {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void doBusiness() {
        banner();
        rollUpDown1();
        rollUpDown2();
    }

    /**
     * 方法二
     */
    private void rollUpDown2() {
        final List<String> info = new ArrayList<>();
        info.add("2. 欢迎大家关注我哦！");
        info.add("3. GitHub帐号：sfsheng0322");
        info.add("4. 新浪微博：孙福生微博");
        info.add("5. 个人博客：sunfusheng.com");
        info.add("6. 微信公众号：孙福生");
        marqueeView.startWithList(info);
        // 在代码里设置自己的动画
        marqueeView.startWithList(info, R.anim.anim_bottom_in, R.anim.anim_top_out);
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                showToast(info.get(position));
            }
        });
    }

    /**
     * 方法一
     */
    private void rollUpDown1() {
        tv_notice.setFactory(new ViewSwitcher.ViewFactory() {
            // 这里用来创建内部的视图，这里创建TextView，用来显示文字
            public View makeView() {
                TextView tv = new TextView(getApplicationContext());
                // 设置文字的显示单位以及文字的大小
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, 34);
                tv.setCompoundDrawables(loadDrawable(R.mipmap.ic_launcher),null,null,null);
                return tv;
            }
        });
        tv_notice.setInAnimation(getApplicationContext(), R.anim.enter_bottom);
        tv_notice.setOutAnimation(getApplicationContext(), R.anim.leave_top);
        mAdvertisements = new String[]{"<font size=\"\" color=\"#ff6600\">[账单]</font>  如何进行账单查询？",
                "<font size=\"\" color=\"#ff6600\">[个人中心]</font>  如何查看费率？",
                "<font size=\"\" color=\"#ff6600\">[口碑入住]</font>  如何进行口碑入住？",
                "<font size=\"\" color=\"#ff6600\">[推广]</font>  如何进行推广？"};
        mHandler.sendEmptyMessage(HOME_AD_RESULT);
        tv_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = mSwitcherCount % mAdvertisements.length;
                if (index == 0) {
                    index = mAdvertisements.length;
                }
                showToast(index - 1 + "");
            }
        });
    }

    /**
     * 左右轮播
     */
    private void banner() {
        List<String> images = new ArrayList<>();
        images.add("http://pic.58pic.com/58pic/15/24/50/43Q58PICkj4_1024.jpg");
        images.add("http://img1.3lian.com/2015/a1/105/d/40.jpg");
        images.add("http://pic31.nipic.com/20130728/7447430_145214729000_2.jpg");
        images.add("http://pic21.nipic.com/20120509/3204030_103922160107_2.jpg");
        images.add("http://img.taopic.com/uploads/allimg/140804/240388-140P40P33417.jpg");
        images.add("http://img1.3lian.com/2015/a1/29/d/204.jpg");
        final List<String> titles = new ArrayList<>();
        titles.add("美女1");
        titles.add("美女2");
        titles.add("美女3");
        titles.add("美女4");
        titles.add("美女5");
        titles.add("美女6");
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2500);
//        //设置指示器位置（当banner模式中有指示器时）
//        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                showToast(titles.get(position));
            }
        });
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }
    /**
     *  将资源图片转换为Drawable对象
     * @param ResId
     * @return
     */
    private Drawable loadDrawable(int ResId) {
        Drawable drawable = getResources().getDrawable(ResId);
        drawable.setBounds(0, 0, 50, 50);
        return drawable;
    }
    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
        marqueeView.startFlipping();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
        marqueeView.stopFlipping();
    }
}
