package com.lixinjia.myapplication.invocationhandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RPCInvocationHandler implements InvocationHandler {

    private InvocationHandleMe serviceManager = null;

    public RPCInvocationHandler(InvocationHandleMe serviceManager) {

        this.serviceManager = serviceManager;

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String methodName = method.getDeclaringClass().getSimpleName() + "." + method.getName();
        if ("Object.toString".equals(methodName)) {
            return method.getDeclaringClass().getName();
        }
        try {
            return this.serviceManager.request(methodName, method.getReturnType(), args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
