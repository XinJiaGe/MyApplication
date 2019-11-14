package com.lixinjia.myapplication.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jiage.library_im.client.bean.SingleMessage;
import com.jiage.library_im.client.event.CEventCenter;
import com.jiage.library_im.client.event.Events;
import com.jiage.library_im.client.event.I_CEventListener;
import com.jiage.library_im.client.im.IMSClientBootstrap;
import com.jiage.library_im.client.im.MessageProcessor;
import com.jiage.library_im.client.im.MessageType;
import com.jiage.library_im.client.utils.CThreadPoolExecutor;
import com.lixinjia.myapplication.R;

import java.util.UUID;

/**
 * 作者：忻佳
 * 日期：2019-10-23
 * 描述：
 */
public class ImActivity extends BaseActivity implements I_CEventListener {
    private EditText mEdituserid;
    private EditText sendUserId;
    private EditText mEdithost;
    private EditText mEditport;
    private EditText mEdittoken;
    private EditText mEditText;
    private TextView mTextView;
    private Button sendMsg;
    private Button linajie;

    private static final String[] EVENTS = {
            Events.CHAT_SINGLE_MESSAGE
    };

    @Override
    public int bindLayout() {
        return R.layout.act_im;
    }

    @Override
    public void initView(View view) {
        sendUserId = findViewById(R.id.et_sendUserId);
        mEditport = findViewById(R.id.et_port);
        mEdithost = findViewById(R.id.et_host);
        mEdituserid = findViewById(R.id.et_userid);
        mEdittoken = findViewById(R.id.et_token);
        mEditText = findViewById(R.id.et_content);
        mTextView = findViewById(R.id.tv_msg);
        sendMsg = findViewById(R.id.sendMsg);
        linajie = findViewById(R.id.linajie);

        linajie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IMSClientBootstrap.getInstance().init(mEdituserid.getText().toString(), mEdittoken.getText().toString(), "[{\"host\":\""+mEdithost.getText().toString()+"\", \"port\":"+mEditport.getText().toString()+"}]", 1);
                CEventCenter.registerEventListener(ImActivity.this, EVENTS);
            }
        });
        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMsg();
            }
        });
    }

    public void sendMsg() {
        SingleMessage message = new SingleMessage();
        message.setMsgId(UUID.randomUUID().toString());
        message.setMsgType(MessageType.SINGLE_CHAT.getMsgType());
        message.setMsgContentType(MessageType.MessageContentType.TEXT.getMsgContentType());
        message.setFromId(mEdituserid.getText().toString());
        message.setToId(sendUserId.getText().toString());
        message.setTimestamp(System.currentTimeMillis());
        message.setContent(mEditText.getText().toString());

        MessageProcessor.getInstance().sendMsg(message);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CEventCenter.unregisterEventListener(this, EVENTS);
    }
    @Override
    public void onCEvent(String topic, int msgCode, int resultCode, Object obj) {
        switch (topic) {
            case Events.CHAT_SINGLE_MESSAGE: {
                final SingleMessage message = (SingleMessage) obj;
                CThreadPoolExecutor.runOnMainThread(new Runnable() {

                    @Override
                    public void run() {
                        mTextView.setText(message.getContent());
                    }
                });
                break;
            }

            default:
                break;
        }
    }
}
