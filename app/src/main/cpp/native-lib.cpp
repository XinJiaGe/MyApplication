#include <jni.h>
#include <string>
#include <android/log.h>
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, "ProjectName", __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG , "ProjectName", __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO  , "ProjectName", __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN  , "ProjectName", __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR  , "ProjectName", __VA_ARGS__)

extern "C"
void
Java_com_adaptation_lixinjia_myapplication_c_CUtile_getCJNI(
        JNIEnv *env,
        jobject This ) {
    //在c代码里面调用java代码里面的方法
    // java 反射
    //1 . 找到java代码的 class文件
    //    jclass      (*FindClass)(JNIEnv*, const char*);
    jclass jniClass = env->FindClass("com/adaptation/lixinjia/myapplication/activity/CtextActivity");
    if(jniClass==0){
//        LOGI("find class error");
        LOGD("--------find class error---------");
        return;
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
Java_com_adaptation_lixinjia_myapplication_c_CUtile_getJavaJNI(
        JNIEnv *env,
        jobject /*This*/ ) {
    std::string hello = "Java 已经调用 C 方法";
    return env->NewStringUTF(hello.c_str());
}