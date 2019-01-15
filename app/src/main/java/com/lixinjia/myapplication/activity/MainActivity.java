package com.lixinjia.myapplication.activity;

import android.view.View;

import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.activity.lrecyclerview.MyLRecyclerviewActivity;
import com.lixinjia.myapplication.adapter.MainAdapter;
import com.lixinjia.myapplication.model.MainEntity;
import com.lixinjia.myapplication.view.AdaptiveHorizontalLayoutView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：李忻佳
 * 时间：2017/5/4
 * 说明：MainActivity
 */

public class MainActivity extends BaseActivity {

    private AdaptiveHorizontalLayoutView horizontal;

    @Override
    public int bindLayout() {
        return R.layout.act_main;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("我的");
        horizontal = $(R.id.act_main_horizontal);
    }

    @Override
    public void doBusiness() {
        List<MainEntity> list = new ArrayList<>();
        list.add(new MainEntity().setName("适配屏幕").setClz(ScreenAdaptationActivity.class));
        list.add(new MainEntity().setName("魅族指纹识别").setClz(MeiZuFingerprintIdentificationActivity.class));
        list.add(new MainEntity().setName("原生指纹识别").setClz(NativeFingerprintIdentificationActivity.class));
        list.add(new MainEntity().setName("Kotlin插件").setClz(KotlinActivity.class));
        list.add(new MainEntity().setName("KotlinDSL布局").setClz(KotlinDSLActivity.class));
        list.add(new MainEntity().setName("EventBus").setClz(EventBusTextActivity.class));
        list.add(new MainEntity().setName("计步").setClz(StepActivity.class));
        list.add(new MainEntity().setName("C/C++").setClz(CtextActivity.class));
        list.add(new MainEntity().setName("表情").setClz(ExpressionActivity.class));
        list.add(new MainEntity().setName("OkHttp").setClz(OkHttpActivity.class));
        list.add(new MainEntity().setName("消息红点").setClz(MessageRedPointActivity.class));
//        list.add(new MainEntity().setName("日历").setClz(CalendarActivity.class));
        list.add(new MainEntity().setName("银行卡扫描").setClz(BankCardScanningActivity.class));
        list.add(new MainEntity().setName("讯飞服务").setClz(XunFeiActivity.class));
        list.add(new MainEntity().setName("相册拍照图片").setClz(PhotoAlbumPhotoActivity.class));
        list.add(new MainEntity().setName("aidi").setClz(AidiActivity.class));
        list.add(new MainEntity().setName("Messenger").setClz(MessengerActivity.class));
        list.add(new MainEntity().setName("轮播").setClz(BannerActivity.class));
        list.add(new MainEntity().setName("蓝牙控制").setClz(BluetoothControlActivity.class));
        list.add(new MainEntity().setName("富文本编辑器").setClz(RichTextActivity.class));
        list.add(new MainEntity().setName("xml提取").setClz(XmlExtractActivity.class));
        list.add(new MainEntity().setName("LRecyclerView").setClz(MyLRecyclerviewActivity.class));
        list.add(new MainEntity().setName("环信").setClz(HuanXinActivity.class));
        list.add(new MainEntity().setName("统计图").setClz(SummaryGraphActivity.class));
        list.add(new MainEntity().setName("锁柜(mac)").setClz(LockCabinetActivity.class));
        list.add(new MainEntity().setName("锁柜(广播)").setClz(LockCabinetBroadcastActivity.class));
        list.add(new MainEntity().setName("BaseSurfaceView").setClz(GameSurfaceActivity.class));
        list.add(new MainEntity().setName("切换动画").setClz(SwitchAnimationActivity.class));
        list.add(new MainEntity().setName("计时器动画").setClz(TimerActivity.class));
        list.add(new MainEntity().setName("ACRCloud").setClz(ACRCloudActivity.class));
        MainAdapter adapter = new MainAdapter(list, mActivity);
        horizontal.setALine(false);
        horizontal.setAdapter(adapter);
        horizontal.startCanvase();
    }
}
