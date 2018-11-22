package com.lixinjia.myapplication.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.http.OkHttpCallback;
import com.lixinjia.myapplication.http.OkHttpManager;

/**
 * 作者：李忻佳
 * 时间：2017/6/26
 * 说明：
 */

public class OkHttpActivity extends BaseActivity {
    private Button mBtGet;
    private TextView mTvText;
    private ImageView mIvImg;
    private Button mBtImg;
    private EditText mEt;
    private Button mBtDownload;
    private Button mBtUpload;

    @Override
    public int bindLayout() {
        return R.layout.activity_ok_http;
    }

    @Override
    public void initView(View view) {
        mEt = $(R.id.act_okHttp_et);
        mBtGet = $(R.id.act_okHttp_bt_get);
        mBtImg = $(R.id.act_okHttp_bt_img);
        mBtUpload = $(R.id.act_okHttp_bt_upload);
        mBtDownload = $(R.id.act_okHttp_bt_download);
        mIvImg = $(R.id.act_okHttp_iv_img);
        mTvText = $(R.id.act_okHttp_tv_text);

        mTitle.setCenterText("OKHttp");
    }

    @Override
    public void addListener() {
        mBtGet.setOnClickListener(this);
        mBtImg.setOnClickListener(this);
        mBtDownload.setOnClickListener(this);
        mBtUpload.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        if(v == mBtGet){
            OkHttpManager.getInstance().requestInterface("https://www.baidu.com/", new OkHttpCallback.Callback() {
                @Override
                public void onFailure(Exception e) {
                    mTvText.setText(e.getMessage());
                }

                @Override
                public void onSuccess(String result) {
                    mTvText.setText(result);
                }
            });
        }
        if(v == mBtImg){
            OkHttpManager.getInstance().requestInterfaceImg("http://cartoon.zwbk.org/ImageUploadTK/63468890451334875012523622145.jpg", new OkHttpCallback.ImgCallback() {
                @Override
                public void onFailure(Exception e) {
                    mTvText.setText(e.getMessage());
                }

                @Override
                public void onSuccess(Bitmap bitmap) {
                    mIvImg.setImageBitmap(bitmap);
                }
            });
        }
        if(v == mBtDownload){
//            OkHttpManager.getInstance().requestInterfaceDownload("https://757100b0b3f5c21c6207c48fd535c499.dd.cdntips.com/imtt.dd.qq.com/16891/0FA332757820B0B6CCBA57FCCD15B4D6." +
//                    "apk?mkey=5bda43ffdd0c2a87&f=0c27&fsname=com.tencent.mobileqq_7.8.8_942.apk&csr=1bbd&cip=221.12.12.114&proto=https", new OkHttpCallback.ProgressCallback() {
//                @Override
//                public void onFailure(Exception e) {
//                    mTvText.setText("下载失败");
//                }
//
//                @Override
//                public void onSuccess() {
//                    mTvText.setText("下载成功");
//                }
//
//                @Override
//                public void onResponseProgress(long bytesRead, long contentLength, boolean done) {
//                    mTvText.setText("下载中  "+bytesRead+" - "+contentLength+" - "+done);
//                }
//            });
        }
        if(v == mBtUpload){
        }
    }

    @Override
    public void doBusiness() {

    }
}
