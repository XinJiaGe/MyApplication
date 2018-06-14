package com.lixinjia.myapplication.activity

import android.view.View
import com.lixinjia.myapplication.R
import com.lixinjia.myapplication.event.EnumEventTag
import com.sunday.eventbus.SDBaseEvent
import kotlinx.android.synthetic.main.act_event_bus_text.*

class EventBusTextActivity : BaseKotlinActivity() {
    override fun bindLayout(): Int {
        return R.layout.act_event_bus_text
    }

    override fun addListener(): List<View?> {
        return listOf(ActEventBusTextBt)
    }

    override fun widgetClick(view: View?) {
        if(view == ActEventBusTextBt) startActivity(EventBusTextNextActivity::class.java)
    }
    override fun doView() {
        mTitle?.setCenterText("EventBusText")
    }
    override fun doBusiness() {

    }

    override fun onEventMainThread(event: SDBaseEvent) {
        super.onEventMainThread(event)
        when (EnumEventTag.valueOf(event.tagInt)){
            EnumEventTag.UPDATE -> ActEventBusTextTv.setText("您点击了按钮")
            else -> ActEventBusTextTv.setText("")
        }
    }
}
