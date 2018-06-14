package com.lixinjia.myapplication.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixinjia.myapplication.R
import com.lixinjia.myapplication.utils.ExpressionUtil



/**
 * 作者：李忻佳
 * 时间：2017/6/22
 * 说明：
 */
class ExpressionAdapter(mListModel: List<String>, mActivity: Activity?) : BaseKotlinAdapter<String>(mListModel, mActivity) {
    override fun bindData(position: Int, convertView: View, parent: ViewGroup, model: String) {
        val text = convertView.findViewById<TextView>(R.id.ItemExpressionTv)
        val zhengze = "f0[0-9]{2}|f10[0-7]"
        val spannableString = ExpressionUtil.getExpressionString(mActivity, model, zhengze)
        text.text = spannableString
    }

    override fun getLayoutId(): Int {
        return R.layout.item_expression
    }

}