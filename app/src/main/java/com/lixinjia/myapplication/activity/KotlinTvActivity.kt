package com.lixinjia.myapplication.activity

import android.view.View
import com.lixinjia.myapplication.R
import kotlinx.android.synthetic.main.activity_kotlin.*


class KotlinTvActivity : BaseKotlinActivity()  {
    var stringBuilder = StringBuffer()

    override fun bindLayout() = R.layout.activity_kotlin

    override fun addListener(): List<View?> = listOf(image,holle)

    override fun widgetClick(view: View?) {
        if (view == image){
            showToast("点击了图片")
        }
        if (view == holle){
            showToast("点击了holle")
        }
    }
    override fun doView() {
        mTitle?.setCenterText("Kotlin")
//        var more = MoreView(this);
//        more.setShowAsDropView(mTitle)
//        more.setItemList(arrayListOf("查看源代码"))
//        more.setmItemClickListener { view, position, id ->
//
//        }
//        mTitle?.setRightView(more)
        image.setBackgroundResource(R.mipmap.ic_launcher)
    }

    override fun doBusiness() {
        stringBuilder?.append("使用条件表达式\n")
        fun maxOf(a: Int, b: Int): Int {
            return if (a > b) {
                a
            } else {
                b
            }
        }
        stringBuilder?.append("max of 0 and 42 is ${maxOf(0, 42)}\n")

        stringBuilder?.append("\n")
        stringBuilder?.append("把if当表达式\n")
        fun maxOf2(a: Int, b: Int) = if (a > b) a else b
        stringBuilder?.append("max of 0 and 42 is ${maxOf2(0, 42)}\n")

        stringBuilder?.append("\n")
        stringBuilder?.append("使用值检查并自动转换\n")
        fun getStringLength(obj: Any): Int? {
            if (obj is String) {
                // obj 将会在这个分支中自动转换为 String 类型
                return obj.length
            }
            // obj 在种类检查外仍然是 Any 类型
            return null
        }
        fun printLength(obj: Any) {
            stringBuilder?.append(("'$obj' string length is ${getStringLength(obj) ?: "... err, not a string"} \n"))
        }
        printLength("Incomprehensibilities")
        printLength(1000)
        printLength(listOf(Any()))

        stringBuilder?.append("\n")
        stringBuilder?.append("或者这样\n")
        fun getStringLength2(obj: Any): Int? {
            if (obj !is String) return null
            // obj 将会在这个分支中自动转换为 String 类型
            return obj.length
        }
        fun printLength2(obj: Any) {
            stringBuilder?.append("'$obj' string length is ${getStringLength2(obj) ?: "... err, not a string"} \n")
        }
        printLength2("Incomprehensibilities")
        printLength2(1000)
        printLength2(listOf(Any()))

        stringBuilder?.append("\n")
        stringBuilder?.append("甚至可以这样\n")
        fun getStringLength3(obj: Any): Int? {
            // obj 将会在&&右边自动转换为 String 类型
            if (obj is String && obj.length > 0) {
                return obj.length
            }
            return null
        }
        fun printLength3(obj: Any) {
            stringBuilder?.append("'$obj' string length is ${getStringLength3(obj) ?: "... err, is empty or not a string at all"} \n")
        }
        printLength3("Incomprehensibilities")
        printLength3("")
        printLength3(1000)

        stringBuilder?.append("\n")
        stringBuilder?.append("使用循环\n")
        val items = listOf("apple", "banana", "kiwi")
        for (item in items) {
            stringBuilder?.append("$item ")
        }

        stringBuilder?.append("\n")
        stringBuilder?.append("\n或者这样\n")
        for (index in items.indices) {
            stringBuilder?.append("item at $index is ${items[index]}\n")
        }

        stringBuilder?.append("\n")
        stringBuilder?.append("使用 while 循环\n")
        var index = 0
        while (index < items.size) {
            stringBuilder?.append("item at $index is ${items[index]}\n")
            index++
        }

        stringBuilder?.append("\n")
        stringBuilder?.append("使用 when 表达式\n")
        fun describe(obj: Any): String =
            when (obj) {
                1          -> "One"
                "Hello"    -> "Greeting"
                is Long    -> "Long"
                !is String -> "Not a string"
                else       -> "Unknown"
            }
        stringBuilder?.append(describe(1)+"\n")
        stringBuilder?.append(describe("Hello")+"\n")
        stringBuilder?.append(describe(1000L)+"\n")
        stringBuilder?.append(describe(2)+"\n")
        stringBuilder?.append(describe("other")+"\n")

        stringBuilder?.append("\n")
        stringBuilder?.append("使用ranges\n")
        stringBuilder?.append("检查 in 操作符检查数值是否在某个范围内：\n")
        val x2 = 10
        val y2 = 9
        if (x2 in 1..y2+1) {
            stringBuilder?.append("fits in range\n")
        }

        stringBuilder?.append("\n")
        stringBuilder?.append("检查数值是否在范围外：\n")
        val list = listOf("a", "b", "c")
        if (-1 !in 0..list.lastIndex) {
            stringBuilder?.append("-1 is out of range\n")
        }
        if (list.size !in list.indices) {
            stringBuilder?.append("list size is out of valid list indices range too\n")
        }

        stringBuilder?.append("\n")
        stringBuilder?.append("在范围内迭代：：\n")
        for (x in 1..5) {
            stringBuilder?.append(x)
        }

        stringBuilder?.append("\n")
        stringBuilder?.append("\n或者使用步进：\n")
        for (x in 1..10 step 2) {
            stringBuilder?.append(x)
        }
        stringBuilder?.append("\n")
        for (x in 9 downTo 0 step 3) {
            stringBuilder?.append(x)
        }

        stringBuilder?.append("\n")
        stringBuilder?.append("\n对一个集合进行迭代：\n")
        for (item in items) {
            stringBuilder?.append(item+"")
        }

        stringBuilder?.append("\n")
        stringBuilder?.append("\n使用 in 操作符检查集合中是否包含某个对象\n")
        when {
            "orange" in items -> stringBuilder?.append("juicy")
            "apple" in items -> stringBuilder?.append("apple is fine too")
        }

        stringBuilder?.append("\n")
        stringBuilder?.append("使用lambda表达式过滤和映射集合：\n")
        val fruits = listOf("banana", "avocado", "apple", "kiwi")
        fruits
            .filter { it.startsWith("a") }
            .sortedBy { it }
            .map { it.toUpperCase() }
            .forEach { stringBuilder?.append(it+" ") }


        holle.text = stringBuilder.toString()
    }
}
