package com.adaptation.lixinjia.myapplication.activity;

import android.view.View;

import com.adaptation.lixinjia.myapplication.R;
import com.adaptation.lixinjia.myapplication.adapter.MainAdapter;
import com.adaptation.lixinjia.myapplication.view.AdaptiveHorizontalLayoutView;

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
        horizontal = $(R.id.act_main_horizontal);
    }

    @Override
    public void doView() {
        mTitle.setCenterText("我的");
    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void addListener() {
        horizontal.setOnMyClickListener(new AdaptiveHorizontalLayoutView.onMyClickListener() {
            @Override
            public void onClick(int index, View view) {
                switch (index) {
                    case 0:
                        startActivity(ScreenAdaptationActivity.class);
                        break;
                    case 1:
                        startActivity(MeiZuFingerprintIdentificationActivity.class);
                        break;
                    case 2:
                        startActivity(NativeFingerprintIdentificationActivity.class);
                        break;
                    case 3:
                        startActivity(GameSurfaceActivity.class);
                        break;
                    case 4:
                        startActivity(KotlinActivity.class);
                        break;
                    case 5:
                        startActivity(KotlinDSLActivity.class);
                        break;
                    case 6:
                        startActivity(EventBusTextActivity.class);
                        break;
                    case 7:
                        startActivity(StepActivity.class);
                        break;
                    case 8:
                        startActivity(CtextActivity.class);
                        break;
                    case 9:
                        startActivity(ExpressionActivity.class);
                        break;
                    case 10:
                        startActivity(OkHttpActivity.class);
                        break;
                    case 11:
                        startActivity(CalendarActivity.class);
                        break;
                    case 12:
                        startActivity(BankCardScanningActivity.class);
                        break;
                    case 13:
                        startActivity(MessageRedPointActivity.class);
                        break;
                    case 14:
                        startActivity(XunFeiActivity.class);
                        break;
                    case 15:
                        startActivity(PhotoAlbumPhotoActivity.class);
                        break;
                    case 16:
                        startActivity(AidiActivity.class);
                        break;
                    case 17:
                        startActivity(MessengerActivity.class);
                        break;
                    case 18:
                        startActivity(BannerActivity.class);
                        break;
                    case 19:
                        startActivity(MainSelectActivity.class);
                        break;
                    case 20:
                        startActivity(BluetoothControlActivity.class);
                        break;
                    case 21:
                        startActivity(RichTextActivity.class);
                        break;
                    case 22:
                        startActivity(XmlExtractActivity.class);
                        break;
                }
            }
        });
    }

    @Override
    public void doBusiness() {
        List<String> list = new ArrayList<>();
        list.add("适配屏幕");
        list.add("魅族指纹识别");
        list.add("原生指纹识别");
        list.add("BaseSurfaceView");
        list.add("Kotlin插件");
        list.add("Kotlin DSL布局");
        list.add("EventBus");
        list.add("计步");
        list.add("C/C++");
        list.add("表情");
        list.add("OkHttp");
        list.add("日历");
        list.add("银行卡扫描");
        list.add("消息红点");
        list.add("讯飞服务");
        list.add("相册拍照图片");
        list.add("aidi");
        list.add("Messenger");
        list.add("轮播");
        list.add("主页选择");
        list.add("蓝牙控制");
        list.add("富文本编辑器");
        list.add("xml提取");
        MainAdapter adapter = new MainAdapter(list, mActivity);
        horizontal.setALine(false);
        horizontal.setAdapter(adapter);
        horizontal.startCanvase();
    }
}
