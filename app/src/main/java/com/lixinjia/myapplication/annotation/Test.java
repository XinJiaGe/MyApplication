package com.lixinjia.myapplication.annotation;

/**
 * 作者：忻佳
 * 日期：2019-10-23
 * 描述：
 */

@TaseAnnotation(name = "Test",id = 3)
public class Test {
    @TaseAnnotation(name = "text",id = 4)
    private String text;

    @TaseAnnotation(name = "paaa",id = 5)
    private void paaa(){

    }
}
