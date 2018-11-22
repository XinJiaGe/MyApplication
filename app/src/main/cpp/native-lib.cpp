#include <jni.h>
#include <string>
#include <android/log.h>
#include <string.h>

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG , "ProjectName", __VA_ARGS__)

extern "C"
void
Java_com_lixinjia_myapplication_utils_CUtile_getCJNI(
        JNIEnv *env,
        jobject This ) {
    //在c代码里面调用java代码里面的方法
    // java 反射
    //1 . 找到java代码的 class文件
    //    jclass      (*FindClass)(JNIEnv*, const char*);
    jclass jniClass = env->FindClass("com/lixinjia/myapplication/activity/CtextActivity");
    if(jniClass==0){
//        LOGI("find class error");
        LOGD("--------find class error---------");
        return ;
    }
    //2 寻找class里面的方法
    jmethodID jmethodID1 = env->GetMethodID(jniClass, "javaLog", "()V");
//    //3 .调用这个方法
    env->CallVoidMethod(This, jmethodID1);
//    std::string hello = "Java 已经调用 C 方法";
//    return env->NewStringUTF(hello.c_str());
        return;

}

extern "C"
jstring
Java_com_lixinjia_myapplication_utils_CUtile_getJavaJNI(
        JNIEnv *env,
        jobject /*This*/ ) {
    std::string hello = "Java 已经调用 C 方法";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
jint
Java_com_lixinjia_myapplication_utils_MyAESAlgorithm_Encrypt(
        JNIEnv *env,
        jclass thiz, jbyteArray msg, jbyteArray key, jbyteArray cipher,
        jint length ) {
//    jbyte * pMsg = (jbyte*) (*env)->GetByteArrayElements(env, msg, 0);
//    jbyte * pKey = (jbyte*) (*env)->GetByteArrayElements(env, key, 0);
//    jbyte *pCipher = (jbyte*) (*env)->GetByteArrayElements(env, cipher, 0);
//
//    if (!pMsg || !pKey || !pCipher) {
//        return -1;
//    }
//    int flag = Encrypt(pMsg, pKey, pCipher, length); //加密函数
//    (*env)->ReleaseByteArrayElements(env, msg, pMsg, 0);
//    (*env)->ReleaseByteArrayElements(env, key, pKey, 0);
//    (*env)->ReleaseByteArrayElements(env, cipher, pCipher, 0);
    return 0;
}
extern "C"
jstring
Java_com_lixinjia_myapplication_utils_MyAESAlgorithm_Decrypt(
        JNIEnv *env,
        jclass thiz, jbyteArray cipher, jbyteArray key, jbyteArray result,
        jint length) {
//    jbyte * pCipher = (jbyte*) (*env)->GetByteArrayElements(env, cipher, 0);
//    jbyte * pKey = (jbyte*) (*env)->GetByteArrayElements(env, key, 0);
//    jbyte *pResult = (jbyte*) (*env)->GetByteArrayElements(env, result, 0);
//
//    if (!pResult || !pKey || !pCipher) {
//        return -1;
//    }
//    int flag = Decrypt(pCipher, pKey, pResult, length); //解密函数
//    (*env)->ReleaseByteArrayElements(env, result, pResult, 0);
//    (*env)->ReleaseByteArrayElements(env, key, pKey, 0);
//    (*env)->ReleaseByteArrayElements(env, cipher, pCipher, 0);
    return 0;
}



