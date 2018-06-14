package com.lixinjia.myapplication.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * 作者：李忻佳
 * 日期：2018/1/4/004.
 * 说明：
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, final ImageView imageView) {
        ImageOptions imageOptions = new ImageOptions.Builder()
//                .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
//                .setRadius(DensityUtil.dip2px(5))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
//                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
//                .setLoadingDrawableId(R.mipmap.ic_launcher)
//                .setFailureDrawableId(R.mipmap.ic_launcher)
                .setFadeIn(true) //淡入效果
//                .setCircular(true) //设置图片显示为圆形
                .setSquare(true) //设置图片显示为正方形
//                .setIgnoreGif(false) //忽略Gif图片
                .build();

        x.image().bind(imageView, String.valueOf(path), imageOptions);
    }
}
