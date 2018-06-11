package com.youth.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import com.youth.banner.transformer.AccordionTransformer;
import com.youth.banner.transformer.BackgroundToForegroundTransformer;
import com.youth.banner.transformer.CubeInTransformer;
import com.youth.banner.transformer.CubeOutTransformer;
import com.youth.banner.transformer.DefaultTransformer;
import com.youth.banner.transformer.DepthPageTransformer;
import com.youth.banner.transformer.FlipHorizontalTransformer;
import com.youth.banner.transformer.FlipVerticalTransformer;
import com.youth.banner.transformer.ForegroundToBackgroundTransformer;
import com.youth.banner.transformer.RotateDownTransformer;
import com.youth.banner.transformer.RotateUpTransformer;
import com.youth.banner.transformer.ScaleInOutTransformer;
import com.youth.banner.transformer.StackTransformer;
import com.youth.banner.transformer.TabletTransformer;
import com.youth.banner.transformer.ZoomInTransformer;
import com.youth.banner.transformer.ZoomOutSlideTransformer;
import com.youth.banner.transformer.ZoomOutTranformer;

public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;//向左
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;//向左左右压缩
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;//向左从小到大
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;//向左从大到小
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;//3D矩形里面旋转
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;//3D矩形外面旋转
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;//重叠从小到大翻转
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;//以中心左右翻转
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;//以中心上下翻转
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;//以下方为中心旋转
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;//以上方为中心旋转
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;//向左上下压缩
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;//重叠翻转
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;//中心缩小
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;//中心放大
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;//缩小一点平移再放大
}
