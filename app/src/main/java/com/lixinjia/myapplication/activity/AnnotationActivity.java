package com.lixinjia.myapplication.activity;

import android.view.View;
import android.widget.Toast;

import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.annotation.TaseAnnotation;
import com.lixinjia.myapplication.annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 作者：忻佳
 * 日期：2019-10-23
 * 描述：注解测试
 */
public class AnnotationActivity extends BaseActivity {
    @Override
    public int bindLayout() {
        return R.layout.act_annotation;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("注解测试");
    }

    public void bt1(View view){
        boolean hasAnnotation = Test.class.isAnnotationPresent(TaseAnnotation.class);
        if ( hasAnnotation ) {
            TaseAnnotation testAnnotation = Test.class.getAnnotation(TaseAnnotation.class);
            Toast.makeText(this,"id:"+testAnnotation.id()+"  name:"+testAnnotation.name(),Toast.LENGTH_LONG).show();
        }
    }
    public void bt2(View view){
        try {
            Field a = Test.class.getDeclaredField("text");
            a.setAccessible(true);
            //获取一个成员变量上的注解
            TaseAnnotation taseAnnotation = a.getAnnotation(TaseAnnotation.class);

            if ( taseAnnotation != null ) {
                Toast.makeText(this,"id:"+taseAnnotation.id()+"  name:"+taseAnnotation.name(),Toast.LENGTH_LONG).show();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    public void bt3(View view){
        try {
            String mas = "";
            Method testMethod = Test.class.getDeclaredMethod("paaa");
            if ( testMethod != null ) {
                // 获取方法中的注解
                Annotation[] ans = testMethod.getAnnotations();
                for( int i = 0;i < ans.length;i++) {
                    mas += ans[i].annotationType().getSimpleName()+"\n";
                }
            }
            Toast.makeText(this,mas,Toast.LENGTH_LONG).show();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
