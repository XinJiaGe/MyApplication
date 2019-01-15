package com.lixinjia.myapplication.activity

import android.view.View
import com.lixinjia.myapplication.R
import kotlinx.android.synthetic.main.act_acrcloud.*

/**
 * 作者：李忻佳
 * 日期：2018/12/8
 * 说明：
 */

class ACRCloudActivity : BaseActivity() {
    override fun bindLayout(): Int {
        return R.layout.act_acrcloud
    }

    override fun initView(view: View) {
        actAcrcloudtv.text = MyImei()
        actAcrcloudbt.setOnClickListener(this)
    }

    override fun widgetClick(v: View?) {
        when(v){
            actAcrcloudbt -> actAcrcloudtv.text = MyImei()
        }
    }

    fun MyImei(): String {
        return "真正IMei"
    }
}
