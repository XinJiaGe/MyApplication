package com.adaptation.lixinjia.myapplication.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import com.adaptation.lixinjia.myapplication.utils.ViewUtil
import com.zhy.autolayout.utils.AutoUtils

/**
 * 作者：李忻佳
 * 时间：2017/6/22
 * 说明：BaseKotlinAdapter
 */
abstract class BaseKotlinAdapter<T>(var mListModel: List<T>, var mActivity: Activity?) : android.widget.BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val layoutId = getLayoutId()
            convertView = ViewUtil.getResId(mActivity,layoutId)
            //对于listview，注意添加这一行，即可在item上使用高度
            AutoUtils.autoSize(convertView!!)
        }
        bindData(position, convertView, parent, getItem(position) as T)
        return convertView
    }

    override fun getItem(position: Int): T? {
        if (isPositionLegal(position)) {
            return mListModel?.get(position)
        } else {
            return null
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mListModel?.size
    }

    fun isPositionLegal(position: Int): Boolean {
        return position >= 0 && position < mListModel?.size
    }

    abstract fun getLayoutId(): Int

    abstract fun bindData(position: Int, convertView: View, parent: ViewGroup, model: T)
}