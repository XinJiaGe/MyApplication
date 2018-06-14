package com.lixinjia.myapplication.activity

import android.view.View
import com.lixinjia.myapplication.R
import com.lixinjia.myapplication.event.EnumEventTag
import com.sunday.eventbus.SDEventManager
import kotlinx.android.synthetic.main.act_event_bus_text_next.*

class EventBusTextNextActivity : BaseKotlinActivity() {
    override fun bindLayout(): Int {
        return R.layout.act_event_bus_text_next
    }

    override fun addListener(): List<View?> {
        return listOf(ActEventBusTextNextBut)
    }

    override fun doView() {
        mTitle?.setCenterText("EventBusTextNext")
    }
    override fun widgetClick(view: View?) {
        if(view == ActEventBusTextNextBut) {
            SDEventManager.post(EnumEventTag.UPDATE.ordinal)
        }
    }

    override fun doBusiness() {
    }

}
