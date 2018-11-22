package com.lixinjia.myapplication.activity

import android.view.View
import com.lixinjia.myapplication.R
import kotlinx.android.synthetic.main.act_switchanimation.*

/**
 * 作者：李忻佳
 * 日期：2018/11/21
 * 说明：切换动画
 */

class SwitchAnimationActivity : BaseKotlinActivity() {
    override fun bindLayout(): Int {
        return R.layout.act_switchanimation
    }

    override fun addListener(): List<View> {
        return listOf(switchanimationSpringIndicator,checkBoxSwitch)
    }

    override fun widgetClick(view: View?) {
        when (view) {
            switchanimationSpringIndicator -> {
                startActivity(SpringIndicatorActivity::class.java)
            }
            checkBoxSwitch -> {
                startActivity(CheckBoxSwitchActivity::class.java)
            }
        }
    }

    override fun doBusiness() {

    }
}
