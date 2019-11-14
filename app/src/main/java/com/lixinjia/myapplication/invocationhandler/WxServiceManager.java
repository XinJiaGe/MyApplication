package com.lixinjia.myapplication.invocationhandler;


public class WxServiceManager extends InvocationHandleMe {
    private static WxServiceManager instance;
    public Device Device;
    public WxServiceManager(){
        super();
    }
    public static WxServiceManager getInstance(){
        if(instance == null){
            synchronized(WxServiceManager.class){
                if(instance == null){
                    instance = new WxServiceManager();
                }
            }
        }
        return instance;
    }
}
