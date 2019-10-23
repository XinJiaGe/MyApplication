package com.lixinjia.myapplication.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 作者：李忻佳
 * 日期：2019/8/23
 * 描述：文件读取
 */
public class FileUtil {
    /**
     * 将内容写入sd卡中
     * @param filePath 要写入的文件路径
     * @param filename 要写入的文件名
     * @param content  待写入的内容
     * @throws IOException
     */
    public static File writeExternal(String filePath, String filename, String content){
        return writeExternal(filePath, filename, content,false);
    }
    /**
     * 将内容写入sd卡中
     * @param filePath 要写入的文件路径
     * @param filename 要写入的文件名
     * @param content  待写入的内容
     * @param isappend  是否追加
     * @throws IOException
     */
    public static File writeExternal(String filePath, String filename, String content, boolean isappend){
        //获取外部存储卡的可用状态
        String storageState = Environment.getExternalStorageState();
        //判断是否存在可用的的SD Card
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            isFileDirExists(new File(filePath));
            filename = filePath+ filename;
            FileWriter fileWriter = null;
            FileOutputStream outputStream = null;
            try {
                if(isappend){
                    fileWriter = new FileWriter(filename, true);
                    fileWriter.write(content);
                    fileWriter.write("\n");
                    fileWriter.close();
                    fileWriter = null;
                }else {
                    outputStream = new FileOutputStream(filename);
                    outputStream.write(content.getBytes());
                    outputStream.close();
                    outputStream = null;
                }
                Log.e("FileUtil","写入成功  "+filename + "   写入路径:"+content);
                return new File(filename);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("FileUtil","写入异常  "+filename +"  "+e.getMessage());
                return null;
            }finally {
                try {
                    if(fileWriter!=null) {
                        fileWriter.close();
                        fileWriter = null;
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    if(outputStream!=null) {
                        outputStream.close();
                        outputStream = null;
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }else{
            Log.e("FileUtil","写入错误 "+filename +" SD卡不错在");
        }
        return null;
    }

    /**
     * 读取文件内容
     * @param file  文件地址
     * @return
     */
    public static String readExternal(File file) {
        if (file.exists()) {
            StringBuilder sb = new StringBuilder();
            FileInputStream inputStream = null;
            try {
                //打开文件输入流
                inputStream = new FileInputStream(file.getPath());
                byte[] buffer = new byte[1024];
                int len = inputStream.read(buffer);
                //读取文件内容
                while (len > 0) {
                    sb.append(new String(buffer, 0, len));
                    //继续将数据放到buffer中
                    len = inputStream.read(buffer);
                }
                //关闭输入流
                if(inputStream!=null) {
                    inputStream.close();
                    inputStream = null;
                }
                Log.e("FileUtil", "读取成功  " + sb.toString()+"   路径："+file.getPath());
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("FileUtil", "读取失败  " + e.getMessage());
                return null;
            }finally {
                try {
                    if(inputStream!=null) {
                        inputStream.close();
                        inputStream = null;
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        Log.e("FileUtil", "读取失败  文件不存在");
        return null;
    }
    /**
     * 搜索文件
     * @param file : 要搜索的目录
     * @param fileName : 要搜索的文件名字
     * @param list : 搜索完毕后，将结果保存至该集合中
     * @param type : 0 ==>contains  1 ==>endWith  2 ==>startWith  3 ==>equals （默认是0）
     */
    public static void searchFile(File file , String fileName , ArrayList<File> list, int type){
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File childFile : files) {
                    searchFile(childFile, fileName,list,type);
                }
            }
        } else {
            switch (type) {
                case 0:
                    if (file.getName().contains(fileName)){
                        list.add(file);
                    }
                    break;
                case 1:
                    if (file.getName().endsWith(fileName)){
                        list.add(file);
                    }
                    break;
                case 2:
                    if (file.getName().startsWith(fileName)){
                        list.add(file);
                    }
                    break;
                case 3:
                    if (file.getName() == fileName){
                        list.add(file);
                    }
                    break;
            }
        }
    }

    /**
     * 判断文件是否存在，不存在就创建
     *
     * @param file
     * @return
     */
    public static void isFileExists(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断文件夹是否存在，不存在就创建
     *
     * @param file
     * @return
     */
    public static void isFileDirExists(File file) {
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
    }
}
