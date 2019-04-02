package com.lixinjia.myapplication.activity

import android.view.View
import com.lixinjia.myapplication.R
import com.wefika.calendar.manager.CalendarManager
import kotlinx.android.synthetic.main.activity_calendar.*
import org.joda.time.LocalDate


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
        val manager = CalendarManager(LocalDate.now(), CalendarManager.State.MONTH, LocalDate.parse("2018-05-05"), LocalDate.now().plusYears(2))
        actCalendar.init(manager)
        actCalendar.setListener {
            showToast("${it.year}  ${it.monthOfYear}  ${it.dayOfMonth}")
        }
    }

}
