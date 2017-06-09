package com.adaptation.lixinjia.myapplication.activity

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Button
import com.adaptation.lixinjia.myapplication.R
import org.jetbrains.anko.*

class KotlinDSLActivity : BaseKotlinActivity() {
    private var button1: Button? = null
    private var button2: Button? = null


    override fun bindView(): View? {
        return createView(applicationContext)
    }
    override fun bindLayout(): Int {
        return 0
    }

    override fun addListener(): List<View?> {
        return listOf(button1,button2)
    }

    override fun widgetClick(view: View?) {
        when (view){
            button1 -> showToast("button1")
            button2 -> showToast("button2")
        }
    }

    override fun doBusiness() {
        mTitle?.setCenterText("DSL布局")
    }

    fun createView(context: Context): View {
        return context.verticalLayout{
            button1 = button("1"){
                id = R.id.actKotlinDSLBt1
                backgroundColor = Color.parseColor("#B0E11E")
            }.lparams(matchParent, wrapContent){
                leftMargin = dip(5)
                rightMargin = dip(10)
            }
            button2 = button("2"){
                id = R.id.actKotlinDSLBt2
                backgroundColor = Color.parseColor("#FF00FF")
            }.lparams(matchParent, wrapContent){
                leftMargin = dip(20)
                rightMargin = dip(30)
                topMargin = dip(50)
            }
        }
    }
}

