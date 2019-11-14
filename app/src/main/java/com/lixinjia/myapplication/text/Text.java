package com.lixinjia.myapplication.text;

import com.lixinjia.myapplication.model.BaseModel;

/**
 * 作者：忻佳
 * 日期：2019-10-24
 * 描述：
 */
public final class Text extends BaseModel {
    private int index;
    private String str;

    public Text(){
    }
    public Text(int index, String str){
        this.index = index;
        this.str = str;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String testInvoke(int age,String name){
        return name;
    }
}
