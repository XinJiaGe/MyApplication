package com.lixinjia.myapplication.utils;

/**
 * 作者：李忻佳
 * 日期：2018/11/9
 * 说明：
 * 你可以在这里将包名、类名、或函数名改成你自定义的,并且在AESAndroid.c文件中更改相应的JNI接口声明（注意两者之间的名称必须完全一致）。
 * 接下来利用交叉编译方法，编译成你自己的SO文件即可。
 */

public class MyAESAlgorithm {
    public synchronized static native int Encrypt(byte[] msg, byte[] key, byte[] cipher, int length);

    public synchronized static native int Decrypt(byte[] cipher, byte[] key, byte[] result, int length);

}
