package com.lixinjia.myapplication.activity

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.View
import com.acrcloud.rec.sdk.ACRCloudClient
import com.acrcloud.rec.sdk.ACRCloudConfig
import com.acrcloud.rec.sdk.IACRCloudListener
import com.lixinjia.myapplication.R
import kotlinx.android.synthetic.main.act_acrcloud.*
import java.io.File
import com.iflytek.cloud.resource.Resource.setText
import com.iflytek.cloud.resource.Resource.setText
import org.json.JSONException
import org.json.JSONObject
import org.apache.commons.logging.LogFactory.release




/**
 * 作者：李忻佳
 * 日期：2018/12/8
 * 说明：
 */

class ACRCloudActivity : BaseActivity(), IACRCloudListener {
    //申请录音权限
    private var GET_RECODE_AUDIO = 1
    private var PERMISSION_AUDIO = arrayOf(Manifest.permission.RECORD_AUDIO)
    private var mClient: ACRCloudClient? = null
    private var mConfig: ACRCloudConfig? = null

    private var mProcessing = false
    private var initState = false

    private var path = ""

    private var startTime: Long = 0
    private var stopTime: Long = 0

    override fun onResult(result: String?) {
        if (mClient != null) {
            mClient!!.cancel()
            mProcessing = false
        }

        var tres = "\n"

        try {
            val j = JSONObject(result)
            val j1 = j.getJSONObject("status")
            val j2 = j1.getInt("code")
            if (j2 == 0) {
                val metadata = j.getJSONObject("metadata")
                //
                if (metadata.has("humming")) {
                    val hummings = metadata.getJSONArray("humming")
                    for (i in 0 until hummings.length()) {
                        val tt = hummings.get(i) as JSONObject
                        val title = tt.getString("title")
                        val artistt = tt.getJSONArray("artists")
                        val art = artistt.get(0) as JSONObject
                        val artist = art.getString("name")
                        tres = tres + (i + 1) + ".  " + title + "\n"
                    }
                }
                if (metadata.has("music")) {
                    val musics = metadata.getJSONArray("music")
                    for (i in 0 until musics.length()) {
                        val tt = musics.get(i) as JSONObject
                        val title = tt.getString("title")
                        val artistt = tt.getJSONArray("artists")
                        val art = artistt.get(0) as JSONObject
                        val artist = art.getString("name")
                        tres = tres + (i + 1) + ".  Title: " + title + "    Artist: " + artist + "\n"
                    }
                }
                if (metadata.has("streams")) {
                    val musics = metadata.getJSONArray("streams")
                    for (i in 0 until musics.length()) {
                        val tt = musics.get(i) as JSONObject
                        val title = tt.getString("title")
                        val channelId = tt.getString("channel_id")
                        tres = tres + (i + 1) + ".  Title: " + title + "    Channel Id: " + channelId + "\n"
                    }
                }
                if (metadata.has("custom_files")) {
                    val musics = metadata.getJSONArray("custom_files")
                    for (i in 0 until musics.length()) {
                        val tt = musics.get(i) as JSONObject
                        val title = tt.getString("title")
                        tres = tres + (i + 1) + ".  Title: " + title + "\n"
                    }
                }
                tres = tres + "\n\n" + result
            } else {
                tres = result!!
            }
        } catch (e: JSONException) {
            tres = result!!
            e.printStackTrace()
        }
        actAcrcloudResult.text = tres
    }

    override fun onVolumeChanged(p0: Double) {
        val time = (System.currentTimeMillis() - startTime) / 1000
        actAcrcloudVolume.text = "volume:\n\nRecord Time: $time s"
    }

    override fun bindLayout(): Int {
        return R.layout.act_acrcloud
    }

    override fun initView(view: View) {
        actAcrcloudStart.setOnClickListener(this)
        actAcrcloudStop.setOnClickListener(this)
        actAcrcloudCancel.setOnClickListener(this)
        verifyAudioPermissions(this)
    }

    override fun doView() {
        mConfig = ACRCloudConfig()
        mClient = ACRCloudClient()
        mConfig?.apply {
            acrcloudListener = this@ACRCloudActivity
            context = this@ACRCloudActivity
            host = "identify-cn-north-1.acrcloud.com"
            dbPath = path // offline db path, you can change it with other path which this app can access.
            accessKey = "c3efd7d6f57d6f8abb82310058980872"
            accessSecret = "vUY6AV7nm3RTbiVymnmPFHokkuLv8hi5hWDG1AQD"
            protocol = ACRCloudConfig.ACRCloudNetworkProtocol.PROTOCOL_HTTP // PROTOCOL_HTTPS
            reqMode = ACRCloudConfig.ACRCloudRecMode.REC_MODE_REMOTE
            //reqMode = ACRCloudConfig.ACRCloudRecMode.REC_MODE_LOCAL;
            //reqMode = ACRCloudConfig.ACRCloudRecMode.REC_MODE_BOTH;
            // If reqMode is REC_MODE_LOCAL or REC_MODE_BOTH,
        }
        // the function initWithConfig is used to load offline db, and it may cost long time.
        mClient?.apply {
            initState = initWithConfig(mConfig)
            if (initState) {
                //this.startPreRecord(3000); //start prerecord, you can call "this.mClient.stopPreRecord()" to stop prerecord.
            }
        }
    }

    override fun widgetClick(v: View?) {
        when (v) {
            actAcrcloudStart -> start()
            actAcrcloudStop -> stop()
            actAcrcloudCancel -> cancel()
        }
    }

    override fun doBusiness() {
        path = (Environment.getExternalStorageDirectory().toString() + "/acrcloud/model")
        val file = File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
    }

    private fun start() {
        if (!this.initState) {
            showToast("init error")
            return
        }

        if (!mProcessing) {
            mProcessing = true
            actAcrcloudVolume.text = ""
            actAcrcloudResult.text = ""
            if (mClient == null || !mClient!!.startRecognize()) {
                mProcessing = false
                actAcrcloudResult.text = "start error!"
            }
            startTime = System.currentTimeMillis()
        }
    }

    private fun stop() {
        if (mProcessing && this.mClient != null) {
            mClient!!.stopRecordToRecognize()
        }
        mProcessing = false

        stopTime = System.currentTimeMillis()
    }

    private fun cancel() {
        if (mProcessing && mClient != null) {
            mProcessing = false
            mClient!!.cancel()
            actAcrcloudTime.text = ""
            actAcrcloudResult.text = ""
        }
    }

    /*
    * 申请录音权限*/
    private fun verifyAudioPermissions( activity: Activity) {
        var permission = ActivityCompat.checkSelfPermission(activity,
        Manifest.permission.RECORD_AUDIO)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSION_AUDIO, GET_RECODE_AUDIO);
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("MainActivity", "release")
        if (mClient != null) {
            mClient!!.release()
            initState = false
            mClient = null
        }
    }
}
