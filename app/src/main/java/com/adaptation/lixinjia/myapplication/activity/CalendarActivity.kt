package com.adaptation.lixinjia.myapplication.activity

import android.view.View
import com.adaptation.lixinjia.myapplication.R

class CalendarActivity : BaseKotlinActivity() {
    override fun bindLayout(): Int {
        return R.layout.activity_calendar
    }

    override fun addListener(): List<View?> {
        return emptyList()
    }

    override fun widgetClick(view: View?) {
    }

    override fun doView() {
        mTitle?.setCenterText("日历")
    }
    override fun doBusiness() {
    }

}
