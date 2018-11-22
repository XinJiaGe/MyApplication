package com.lixinjia.myapplication.activity

import android.support.v4.app.Fragment
import android.view.View

import com.lixinjia.myapplication.R
import com.lixinjia.myapplication.adapter.FragmentViewPageAdapter
import com.lixinjia.myapplication.fragment.SpringIndicatorFragment
import kotlinx.android.synthetic.main.act_springindicator.*

/**
 * 作者：李忻佳
 * 日期：2018/11/21
 * 说明：SpringIndicator
 */

class SpringIndicatorActivity : BaseKotlinActivity() {
    private val fragments = ArrayList<Fragment>()

    override fun bindLayout(): Int {
        return R.layout.act_springindicator
    }

    override fun addListener(): List<View> {
        return listOf()
    }

    override fun widgetClick(view: View?) {

    }

    override fun doBusiness() {
        for (i in 0..5){
            var fragment = SpringIndicatorFragment()
            fragments.add(fragment)
        }
        var ada = FragmentViewPageAdapter(supportFragmentManager,fragments)
        springindicatorVP.adapter = ada
        springindicatorSI.tabs
        springindicatorSI.setViewPager(springindicatorVP)
    }
}
