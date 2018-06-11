package com.adaptation.lixinjia.myapplication.model;

import android.util.Log;

import java.util.List;

/**
 * 作者：李忻佳
 * 时间：2018/6/5/005.
 * 说明：
 */

public class BBBModel {
    private String name;
    private List<listData> list;
    private String zong;

    public String getZong() {
        Log.e("BBBModel","------开始----- "+name);
        if(list==null) return "0";
        int xs = 0;
        int fz = 0;
        for (listData listData : list) {
            if(listData.getZong()!=null&&!listData.getZong().equals("0")){
                String[] zongs = listData.getZong().split(":");
                xs += Integer.parseInt(zongs[0]);
                fz += Integer.parseInt(zongs[1]);
                Log.e("BBBModel","加班时间："+listData.getZong());
            }
        }
        xs += fz/60;
        fz = fz%60;
        Log.e("BBBModel","------结束----- 总时间："+xs+"小时"+fz+"分钟");
        Log.e("BBBModel","  ");
        return xs+"小时"+fz+"分钟";
    }

    public void setZong(String zong) {
        this.zong = zong;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<listData> getList() {
        return list;
    }

    public void setList(List<listData> list) {
        this.list = list;
    }

    public static class listData{
        private String lastData;
        private String surplus;
        private String zong;
        private String datas;

        public String getZong() {
            return zong;
        }

        public void setZong(String zong) {
            this.zong = zong;
        }

        public String getDatas() {
            return datas;
        }

        public void setDatas(String datas) {
            this.datas = datas;
        }

        public String getLastData() {
            return lastData;
        }

        public void setLastData(String lastData) {
            this.lastData = lastData;
        }

        public String getSurplus() {
            return surplus;
        }

        public void setSurplus(String surplus) {
            this.surplus = surplus;
        }
    }
}
