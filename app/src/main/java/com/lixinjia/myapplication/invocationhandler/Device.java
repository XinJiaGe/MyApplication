package com.lixinjia.myapplication.invocationhandler;

public interface Device extends IService {

    /**
     * 认证
     * @param userId
     * @param uuid
     * @param pages
     */
    void Auth(String userId, String uuid, String pages);

    void RegisterDevice();

    void ReverseString(String text);

    void TaskStatus(String uuid, int taskId, int status, String progress, String info);//0等待开启，1队列中 2执行中 3执行成功 4执行失败  5执行关闭

    void TaskStatusDetails(String uuid, int taskId, String info);

}
