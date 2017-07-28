package com.adaptation.lixinjia.myapplication.http;

import android.graphics.Bitmap;

/**
 * 作者：李忻佳
 * 时间：2017/6/23
 * 说明：
 */

public class OkHttpCallback {
    public static abstract class ImgCallback{
        public void onStart(){};
        public abstract void onFailure(Exception e);
        public abstract void onSuccess(Bitmap bitmap);
        public void  onFinish(){};
    }
    public static abstract class Callback{
        public void onStart(){};
        public abstract void onFailure(Exception e);
        public abstract void onSuccess(String result);
        public void  onFinish(){};
    }
    public static abstract class ProgressCallback{
        public void onStart(){};
        public abstract void onFailure(Exception e);
        public abstract void onSuccess();
        public abstract void onResponseProgress(long bytesRead, long contentLength, boolean done);
        public void  onFinish(){};
    }
}
