package com.lixinjia.myapplication.model;

/**
 * 作者：李忻佳
 * 日期：2018/11/1
 * 说明：
 */

public class MainEntity {
    private String name;
    private Class<?> clz;

    public String getName() {
        return name;
    }

    public MainEntity setName(String name) {
        this.name = name;
        return this;
    }

    public Class<?> getClz() {
        return clz;
    }

    public MainEntity setClz(Class<?> clz) {
        this.clz = clz;
        return this;
    }
}
