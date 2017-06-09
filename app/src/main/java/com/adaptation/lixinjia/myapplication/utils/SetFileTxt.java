package com.adaptation.lixinjia.myapplication.utils;

import com.adaptation.lixinjia.myapplication.app.App;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者：李忻佳.
 * 时间：2017/1/10.
 * 说明：记录Log
 */

public class SetFileTxt {
    /**
     * 记录数据
     * @param name  文件名
     * @param str   内容
     */
    public static void setDebugFile(final String name, final String str){
        Thread ht = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File file=new File(App.getApplication().getExternalCacheDir().getPath()+name+".log");
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    FileOutputStream out=new FileOutputStream(file,true);
                    StringBuffer sb=new StringBuffer();
                    sb.append(getSysDate()+"   "+str+"\r\n");
                    out.write(sb.toString().getBytes("utf-8"));
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ht.start();
    }

    /**
     * 获取时间字符串。 日期字符串格式： yyyy-MM-dd hh:mm:ss 其中： yyyy 表示4位年。 MM 表示2位月。 dd 表示2位日。
     *
     * @return String " yyyy-MM-dd hh:mm:ss"格式的时间字符串。
     */
    private static String getSysDate() {
        return dateToString(new Date(),"yyyy-MM-dd hh:mm:ss");
    }
    /**
     *
     * dateToString(将时间转换成字符串)
     *
     * @Title: dateToString
     * @param dataDate
     *            日期
     * @param format
     *            格式
     * @return String 返回类型
     * @throws
     */
    private static String dateToString(Date dataDate, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(dataDate);
    }
}
