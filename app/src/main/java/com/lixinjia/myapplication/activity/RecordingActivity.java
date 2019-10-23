package com.lixinjia.myapplication.activity;

import android.view.View;

import com.lixinjia.myapplication.R;

/**
 * 作者：忻佳
 * 日期：2019-10-23
 * 描述：录音
 */
public class RecordingActivity extends BaseActivity {
    @Override
    public int bindLayout() {
        return R.layout.act_recording;
    }

    @Override
    public void initView(View view) {

    }

    public void AudioRecord(View view){
        startActivity(AudioRecordActivity.class);
    }
    public void MediaRecorder(View view){
        startActivity(MediaRecorderActivity.class);
    }
}
