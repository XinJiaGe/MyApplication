package com.lixinjia.myapplication.activity

import android.view.View
import com.lixinjia.myapplication.R
import kotlinx.android.synthetic.main.activity_step.*
import com.xinjiage.stepdetector.StepDetector


class StepActivity : BaseKotlinActivity() {
    override fun bindLayout(): Int {
        return R.layout.activity_step
    }

    override fun addListener(): List<View?> {
        return listOf()
    }

    override fun widgetClick(view: View?) {
    }


    override fun doBusiness() {
        mTitle?.setCenterText("计步")

        ActStepTvIsStand.setText(when(StepDetector.getInstance().isStandByGoogleStep){true -> "支持自带计步功能" false -> "不支持自带计步功能"})

        StepDetector.getInstance().setmStepListener { count, step ->
            ActStepTvDetector.setText( "" +count +"  "+ step)
        }
    }
}
