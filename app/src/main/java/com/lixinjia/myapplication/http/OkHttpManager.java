package com.lixinjia.myapplication.http;

import android.os.Handler;
import android.os.Looper;

import com.blankj.utilcode.util.Utils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 作者：李忻佳
 * 时间：2017/6/26
 * 说明：
 */

public class OkHttpManager {
    private static OkHttpManager instance;
    private Handler handler;
    private OkHttpClient client;

    private OkHttpManager() {
        File sdcache = Utils.getContext().getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        client = builder.build();
        handler = new Handler(Looper.getMainLooper());
    }

    //采用单例模式获取对象
    public static OkHttpManager getInstance() {
        if (null == instance) {
            synchronized (OkHttpManager.class) {
                if (null == instance) {
                    instance = new OkHttpManager();
                }
            }
        }
        return instance;
    }

    /**
     * Get请求
     * @param url
     * @param callback
     */
    public void requestInterface(String url, final OkHttpCallback.Callback callback) {
        requestInterface(url,null,callback);
    }
    /**
     * Pos请求
     * @param url
     * @param formBody 参数
     * @param callback
     */
    public void requestInterface(String url, RequestBody formBody, final OkHttpCallback.Callback callback) {
        Request.Builder request = new Request.Builder().url(url);
        if(formBody!=null){
            request.post(formBody);
        }
        client.newCall(request.build()).enqueue(new OkHttpResoutCallBack(callback,handler));
    }
    /**
     * 请求图片
     * @param url
     * @param callback
     */
    public void requestInterfaceImg(String url,  final OkHttpCallback.ImgCallback callback) {
        Request.Builder request = new Request.Builder().url(url);
        client.newCall(request.build()).enqueue(new OkHttpResoutCallBack(callback,handler));
    }

    /**
     * 下载
     * @param url
     * @param callback
     */
    public void requestInterfaceDownload(String url, final OkHttpCallback.ProgressCallback callback){
        Request.Builder request = new Request.Builder().url(url);
        ProgressHelper.addProgressResponseListener(client, new ProgressResponseBody.ProgressResponseListener() {
            @Override
            public void onResponseProgress(final long bytesRead, final long contentLength, final boolean done) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(callback!=null){
                            callback.onResponseProgress(bytesRead,contentLength,done);
                        }
                    }
                });
            }
        }).newCall(request.build()).enqueue(new OkHttpResoutCallBack(callback,handler));
    }
    /**
     * 上传
     * @param url
     * @param callback
     */
    public void requestInterfaceUpload(String url,RequestBody formBody, final OkHttpCallback.ProgressCallback callback){
        Request.Builder request = new Request.Builder().url(url);
        request.post(ProgressHelper.addProgressRequestListener(formBody, new ProgressRequestBody.ProgressRequestListener() {
            @Override
            public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {

            }
        }));
        client.newCall(request.build()).enqueue(new OkHttpResoutCallBack(callback,handler));
    }
}
