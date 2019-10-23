package com.lixinjia.myapplication.activity;

import android.media.MediaRecorder;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lixinjia.myapplication.R;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

/**
 * 作者：忻佳
 * 日期：2019-10-23
 * 描述：
 */
public class MediaRecorderActivity extends BaseActivity {
    private MediaRecorder mMediaRecorder;
    private String fileName;
    private String filePath;
    private TextView mText;

    @Override
    public int bindLayout() {
        return R.layout.act_record;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("MediaRecorder");
        mText = findViewById(R.id.act_record_textview);
    }

    public void play(View view){
        startRecord();
    }
    public void stop(View view){
        stopRecord();
    }

    public void startRecord() {
        // 开始录音
        /* ①Initial：实例化MediaRecorder对象 */
        if (mMediaRecorder == null)
            mMediaRecorder = new MediaRecorder();
        try {
            /* ②setAudioSource/setVedioSource */
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            /*
             * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
             * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
             */
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样 */
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            fileName = DateFormat.format("yyyyMMdd_HHmmss", Calendar.getInstance(Locale.CHINA)) + "";

            final File tmpFile = createFile(fileName);
            filePath = tmpFile + fileName+".m4a";
            /* ③准备 */
            mMediaRecorder.setOutputFile(filePath);
            mMediaRecorder.prepare();
            /* ④开始 */
            mMediaRecorder.start();
            mText.setText("开始录音");
            Log.i("MediaRecorderActivity","开始录音");
        } catch (IllegalStateException e) {
            Log.i("MediaRecorderActivity","call startAmr(File mRecAudioFile) failed!" + e.getMessage());
            mText.setText("出错："+e.getMessage());
        } catch (IOException e) {
            Log.i("MediaRecorderActivity","call startAmr(File mRecAudioFile) failed!" + e.getMessage());
            mText.setText("出错："+e.getMessage());
        }
    }
    public void stopRecord() {
        try {
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
            filePath = "";
            mText.setText("停止录音");
        } catch (RuntimeException e) {
            Log.e("MediaRecorderActivity",e.toString());
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;

            File file = new File(filePath);
            if (file.exists())
                file.delete();

            filePath = "";
            mText.setText("出错："+e.toString());
        }
    }

    private File createFile(String name) {

        String dirPath = Environment.getExternalStorageDirectory().getPath() + "/AudioRecord/";
        File file = new File(dirPath);

        if (!file.exists()) {
            file.mkdirs();
        }

        String filePath = dirPath + name;
        File objFile = new File(filePath);
        if (!objFile.exists()) {
            try {
                objFile.createNewFile();
                return objFile;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;


    }
}
