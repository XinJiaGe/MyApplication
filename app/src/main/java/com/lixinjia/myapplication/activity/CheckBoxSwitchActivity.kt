package com.lixinjia.myapplication.activity

import android.view.View

import com.lixinjia.myapplication.R
import com.suke.widget.SwitchButton
import kotlinx.android.synthetic.main.act_checkboxswitch.*

/**
 * 作者：李忻佳
 * 日期：2018/11/22
 * 说明：

<attr name="sb_shadow_radius" format="reference|dimension"/>       阴影半径
<attr name="sb_shadow_offset" format="reference|dimension"/>       阴影偏移
<attr name="sb_shadow_color" format="reference|color"/>            阴影颜色
<attr name="sb_uncheck_color" format="reference|color"/>           关闭颜色
<attr name="sb_checked_color" format="reference|color"/>           开启颜色
<attr name="sb_border_width" format="reference|dimension"/>        边框宽度
<attr name="sb_checkline_color" format="reference|color"/>         开启指示器颜色
<attr name="sb_checkline_width" format="reference|dimension"/>     开启指示器线宽
<attr name="sb_uncheckcircle_color" format="reference|color"/>     关闭指示器颜色
<attr name="sb_uncheckcircle_width" format="reference|dimension"/> 关闭指示器线宽
<attr name="sb_uncheckcircle_radius" format="reference|dimension"/>关闭指示器半径
<attr name="sb_checked" format="reference|boolean"/>               是否选中
<attr name="sb_shadow_effect" format="reference|boolean"/>         是否启用阴影
<attr name="sb_effect_duration" format="reference|integer"/>       动画时间，默认300ms
<attr name="sb_button_color" format="reference|color"/>            按钮颜色
<attr name="sb_show_indicator" format="reference|boolean"/>        是否显示指示器，默认true：显示
<attr name="sb_background" format="reference|color"/>              背景色，默认白色
<attr name="sb_enable_effect" format="reference|boolean"/>         是否启用特效，默认true

 */

class CheckBoxSwitchActivity : BaseActivity() {
    override fun bindLayout(): Int {
        return R.layout.act_checkboxswitch
    }

    override fun initView(view: View) {
        switch_button1.apply {
            isChecked = true
            toggle(false)//动画切换
            setOnCheckedChangeListener({ switchButton: SwitchButton, b: Boolean ->

            })
        }
        switch_button2.apply {
            isChecked = false
            toggle(true)//动画切换
            setShadowEffect(false)//禁用阴影效果
        }
        switch_button3.apply {
            isChecked = false
            toggle(true)//动画切换
            setShadowEffect(true)//禁用阴影效果
            isEnabled = false//禁用按钮
        }
        switch_button4.apply {
            isChecked = false
            toggle(true)//动画切换
            setShadowEffect(true)//禁用阴影效果
            isEnabled = true//禁用按钮
            setEnableEffect(false)//禁用开关动画
        }
        switch_button5.apply {
            isChecked = false
            toggle(true)//动画切换
            setShadowEffect(true)//禁用阴影效果
            isEnabled = true//禁用按钮
            setEnableEffect(true)//禁用开关动画
        }
    }
}
