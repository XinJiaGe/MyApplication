package com.lixinjia.myapplication.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.rxjava.RxAsynchronous;
import com.lixinjia.myapplication.utils.SDToast;

import io.reactivex.ObservableEmitter;

/**
 * 作者：李忻佳
 * 日期：2018/9/19
 * 说明：环信
 */

public class HuanXinActivity extends BaseActivity {
    private EditText username;
    private EditText pwd;
    private EditText sendEt;
    private Button login;
    private Button send;

    @Override
    public int bindLayout() {
        return R.layout.act_huanxing;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("环信");
        username = $(R.id.act_huanxing_username);
        pwd = $(R.id.act_huanxing_pwd);
        login = $(R.id.act_huanxing_login);
        send = $(R.id.act_huanxing_send);
        sendEt = $(R.id.act_huanxing_sendEt);
    }

    @Override
    public void addListener() {
        login.setOnClickListener(this);
        send.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        if(v == login){
            new RxAsynchronous<Boolean>() {
                @Override
                public Boolean onSubscribe(ObservableEmitter<Boolean> observable) {
                    EMClient.getInstance().login(username.getText().toString(),pwd.getText().toString(),new EMCallBack() {//回调
                        @Override
                        public void onSuccess() {
                            Log.e("login","登录成功");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showToast("登录成功");
                                }
                            });
                            //保证进入主页面后本地会话和群组都 load 完毕
                            EMClient.getInstance().groupManager().loadAllGroups();
                            EMClient.getInstance().chatManager().loadAllConversations();
                        }

                        @Override
                        public void onProgress(int progress, String status) {

                        }

                        @Override
                        public void onError(int code, final String message) {
                            Log.e("login","登录失败  "+message);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showToast("登录失败  "+message);
                                }
                            });
                        }
                    });
                    return false;
                }
            };
        }
        if(v == send){
            //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
            EMMessage message = EMMessage.createTxtSendMessage(sendEt.getText().toString(), "1111111111");
            message.setChatType(EMMessage.ChatType.Chat);
            //发送消息
            EMClient.getInstance().chatManager().sendMessage(message);
//            showToast("发送成功");
        }
    }

    @Override
    public void doBusiness() {

    }
}
