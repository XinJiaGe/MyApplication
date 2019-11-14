package com.lixinjia.myapplication.invocationhandler;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * 作者：忻佳
 * 日期：2019-10-25
 * 描述：
 */
public class InvocationHandleMe {
    public InvocationHandleMe(){
        initServcieProxy();
    }

    public Object request(String methodName, Class returnType, Object[] args){

        Log.e("InvocationHandler","methodName:"+methodName+"  returnType:"+returnType.toString()+"  args:"+ Arrays.toString(args));
        return "InvocationHandler";
    }

    public void initServcieProxy() {
        Field[] fields = this.getClass().getFields();
        for (Field field : fields) {
            Class<?>[] interfaces = field.getType().getInterfaces();
            if (this.checkInterfaceBelongs(IService.class, interfaces)) {
                try {
                    BaseService service = new BaseService();
                    Object serviceProxy = Proxy.newProxyInstance(service.getClass().getClassLoader(), new Class[] { field.getType() }, new RPCInvocationHandler(this));
                    field.set(this, serviceProxy);
                } catch (Exception ex){
                    System.out.println(ex.toString());
                }
            }
        }
    }
    private boolean checkInterfaceBelongs(Class interf, Class<?>[] interfaces) {
        if (interfaces.length < 1) return false;
        for (Class one : interfaces) {
            if (interf.equals(one)) return true;
        }
        return false;
    }
}
