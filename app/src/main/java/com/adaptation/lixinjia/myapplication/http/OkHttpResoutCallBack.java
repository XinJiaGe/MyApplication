package com.adaptation.lixinjia.myapplication.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 作者：李忻佳
 * 时间：2017/6/26
 * 说明：
 */

public class OkHttpResoutCallBack implements Callback {
    private int mType;
    private Handler mHandle;
    private OkHttpCallback.Callback mCallBack;
    private OkHttpCallback.ImgCallback mImgCallBack;
    private OkHttpCallback.ProgressCallback mDownLoadCallBack;

    public OkHttpResoutCallBack(OkHttpCallback.Callback callback, Handler handler){
        this.mType = 1;
        this.mCallBack = callback;
        this.mHandle = handler;
        mCallBack.onStart();
    }
    public OkHttpResoutCallBack(OkHttpCallback.ImgCallback callback, Handler handler){
        this.mType = 2;
        this.mImgCallBack = callback;
        this.mHandle = handler;
        mImgCallBack.onStart();
    }
    public OkHttpResoutCallBack(OkHttpCallback.ProgressCallback callback, Handler handler){
        this.mType = 3;
        this.mDownLoadCallBack = callback;
        this.mHandle = handler;
        mDownLoadCallBack.onStart();
    }

    @Override
    public void onFailure(Call call, IOException e) {
        switch (mType) {
            case 1:
                mCallBack.onFailure(e);
                break;
            case 2:
                mImgCallBack.onFailure(e);
                break;
            case 3:
                mDownLoadCallBack.onFailure(e);
                break;
        }
        call.cancel();
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        if (response != null && response.isSuccessful()) {
            switch (mType) {
                case 1:
                    onSuccessStringMethod(response.body().string(),call);
                    break;
                case 2:
                    byte[] data = response.body().bytes();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    onSuccessImgMethod(bitmap,call);
                    break;
                case 3:
                    onSuccessDownLoadMethod(call);
                    break;
            }
        }
    }

    /**
     * 处理String请求
     * @param resout
     */
    private void onSuccessStringMethod(final String resout, final Call call) {
        mHandle.post(new Runnable() {
            @Override
            public void run() {
                if (mCallBack != null) {
                    try {
                        mCallBack.onSuccess(resout);
                    } catch (Exception e) {
                        e.printStackTrace();
                        mCallBack.onFailure(e);
                    }
                    mCallBack.onFinish();
                    call.cancel();
                }
            }
        });
    }

    /**
     * 处理图片请求
     * @param bitmap
     */
    private void onSuccessImgMethod(final Bitmap bitmap, final Call call) {
        mHandle.post(new Runnable() {
            @Override
            public void run() {
                if (mImgCallBack != null) {
                    try {
                        mImgCallBack.onSuccess(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                        mImgCallBack.onFailure(e);
                    }
                    mImgCallBack.onFinish();
                    call.cancel();
                }
            }
        });
    }

    /**
     * 处理下载请求
     * @param call
     */
    private void onSuccessDownLoadMethod(final Call call){
        mHandle.post(new Runnable() {
            @Override
            public void run() {
                if (mDownLoadCallBack != null) {
                    try {
                        mDownLoadCallBack.onSuccess();
                    } catch (Exception e) {
                        e.printStackTrace();
                        mDownLoadCallBack.onFailure(e);
                    }
                    mDownLoadCallBack.onFinish();
                    call.cancel();
                }
            }
        });
    }
}
