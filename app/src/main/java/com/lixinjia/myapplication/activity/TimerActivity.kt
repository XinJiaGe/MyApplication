package com.lixinjia.myapplication.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Handler
import android.os.Message
import android.view.View
import com.lixinjia.myapplication.R
import kotlinx.android.synthetic.main.act_timer.*
import java.util.*

/**
 * 作者：李忻佳
 * 日期：2018/11/22
 * 说明：计时器
 */

class TimerActivity : BaseActivity() {
    var number:Int = 0

    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            if (number % 3 === 0) {
                numberswitchview.numberColor = Color.RED
                numberswitchview.numberBGColor = Color.BLACK
            } else {
                numberswitchview.numberColor = Color.rgb(10, 10, 10)
                numberswitchview.numberBGColor = Color.BLUE
            }
            numberswitchview.animateTo(number)
        }
    }

    override fun bindLayout(): Int {
        return R.layout.act_timer
    }

    override fun initView(view: View) {
        val timerTask = object : TimerTask() {
            override fun run() {
                number++
                if (number > 9) {
                    number = 0
                }

                handler.obtainMessage().sendToTarget()
            }
        }
        val timer = Timer()
        timer.schedule(timerTask, 1000, 1000)
    }
}
