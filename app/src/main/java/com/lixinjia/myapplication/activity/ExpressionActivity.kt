package com.lixinjia.myapplication.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.SimpleAdapter
import com.lixinjia.myapplication.R
import com.lixinjia.myapplication.adapter.BaseViewPageAdapter
import com.lixinjia.myapplication.adapter.ExpressionAdapter
import com.blankj.utilcode.util.KeyboardUtils
import kotlinx.android.synthetic.main.activity_expression.*
import org.jetbrains.anko.onItemClick


class ExpressionActivity : BaseKotlinActivity() {
    val imageIds = IntArray(107)
    var list = arrayListOf<String>()
    var  adapter: ExpressionAdapter? = null

    override fun bindLayout(): Int {
        return R.layout.activity_expression
    }

    override fun doView() {
        mTitle?.setCenterText("表情")
    }
    override fun addListener(): List<View?> {
        return listOf(ActExpressionIv,ActExpressionBt)
    }

    override fun widgetClick(view: View?) {
        when(view){
            ActExpressionIv -> expressionVisible()
            ActExpressionBt -> send()
        }
    }

    override fun doBusiness() {
        bindExpressionAdapter()
        ActExpressionEt.clearFocus()
        ActExpressionLl.visibility = View.GONE
        var viewList = arrayListOf<View>()
        addGridView(viewList,0,106)
        var adapter = BaseViewPageAdapter(viewList)
        ActExpressionVp.adapter = adapter
        ActExpressionEt.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                ActExpressionLl.visibility = View.GONE
            }
        }
    }

    /**
     * 发送
     */
    fun send(){
        list.add(ActExpressionEt.text.toString())
        adapter?.notifyDataSetChanged()
        ActExpressionEt.setText("")
        actExpressionListView.smoothScrollToPosition(actExpressionListView.count - 1);//移动到尾部
    }

    /**
     * 绑定adapter
     */
    fun bindExpressionAdapter(){
        adapter = ExpressionAdapter(list,mActivity)
        actExpressionListView.adapter = adapter
    }

    /**
     * 获取GridView 设置ViewPage的list<View>
     */
    fun addGridView(viewList: ArrayList<View>, start: Int,end: Int){
        var gridView = getExpressionGrid(start,end)
        gridView.onItemClick { adapterView, view, i, l ->
            var bitmap: Bitmap? = null
            bitmap = BitmapFactory.decodeResource(resources, imageIds[i % imageIds.size])
            val imageSpan = ImageSpan(mActivity, bitmap)
            var str: String? = null
            if (i < 10) {
                str = "f00" + i
            } else if (i < 100) {
                str = "f0" + i
            } else {
                str = "f" + i
            }
            val spannableString = SpannableString(str)
            spannableString.setSpan(imageSpan, 0, str?.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            val index = ActExpressionEt.selectionStart//获取光标所在位置
            val edit = ActExpressionEt.editableText//获取EditText的文字
            if (index < 0 || index >= edit.length) {
                edit.append(spannableString)
            } else {
                edit.insert(index, spannableString)//光标所在位置插入文字
            }
        }
        viewList.add(gridView)
    }

    /**
     * 隐藏输入法
     */
    fun expressionVisible(){
        ActExpressionEt.clearFocus()
        when(ActExpressionLl.visibility){
            View.VISIBLE -> ActExpressionLl.visibility = View.GONE
            View.GONE -> ActExpressionLl.visibility = View.VISIBLE
        }
    }

    /**
     * 设置一个GridView
     */
    fun getExpressionGrid(start: Int, end: Int): GridView {
        var viewResId = GridView(this)
        viewResId.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        viewResId.numColumns = 7
        viewResId.horizontalSpacing = 1
        viewResId.verticalSpacing = 1
        viewResId.gravity = Gravity.CENTER
        val listItems = ArrayList<Map<String, Any>>()
        for (i in start..end){
            if (i < 10) {
                val field = R.drawable::class.java.getDeclaredField("f00" + i)
                val resourceId = Integer.parseInt(field.get(null).toString())
                imageIds[i] = resourceId
            } else if (i < 100) {
                val field = R.drawable::class.java.getDeclaredField("f0" + i)
                val resourceId = Integer.parseInt(field.get(null).toString())
                imageIds[i] = resourceId
            } else {
                val field = R.drawable::class.java.getDeclaredField("f" + i)
                val resourceId = Integer.parseInt(field.get(null).toString())
                imageIds[i] = resourceId
            }
            val listItem = HashMap<String, Any>()
            listItem.put("image", imageIds[i])
            listItems.add(listItem)
        }
        val simpleAdapter = SimpleAdapter(this, listItems, R.layout.item_expression_select, arrayOf("image"), intArrayOf(R.id.item_expression_select_imag))
        viewResId.adapter = simpleAdapter
        return viewResId
    }

    /**
     * 点击其他地方关闭输入法
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        KeyboardUtils.clickBlankArea2HideSoftInput(mActivity,ev)
        return super.dispatchTouchEvent(ev)
    }
}
