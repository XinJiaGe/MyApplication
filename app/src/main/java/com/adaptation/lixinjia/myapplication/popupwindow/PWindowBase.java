package com.adaptation.lixinjia.myapplication.popupwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.adaptation.lixinjia.myapplication.app.App;
import com.adaptation.lixinjia.myapplication.utils.ViewUtil;

/**
 * 作者：李忻佳
 * 时间：2017/6/21
 * 说明：PWindowBase
 */
public class PWindowBase extends PopupWindow implements View.OnClickListener
{

	private View mViewBlur;
	/* 是否模糊 */
	private boolean mIsNeedBlur = false;
	private boolean isDisappear = true;

	public void setmIsNeedBlur(boolean mIsNeedBlur)
	{
		this.mIsNeedBlur = mIsNeedBlur;
	}

	private Context getContext()
	{
		if (getContentView() != null)
		{
			return getContentView().getContext();
		}
		return null;
	}

	public PWindowBase()
	{
		this(false);
	}

	public PWindowBase(boolean isNeedBlur)
	{
		super(App.getApplication());
		setmIsNeedBlur(isNeedBlur);
		baseInit();
	}

	private void baseInit()
	{
		wrapperPopupWindow(this);
	}

	public static void wrapperPopupWindow(PopupWindow pop)
	{
		if (pop != null)
		{
			ColorDrawable dw = new ColorDrawable(0x00ff3333);
			pop.setBackgroundDrawable(dw);
			pop.setWidth(FrameLayout.LayoutParams.MATCH_PARENT);
			pop.setHeight(FrameLayout.LayoutParams.WRAP_CONTENT);

			pop.setFocusable(true);
			pop.setOutsideTouchable(true);
		}
	}

	@Override
	public void onClick(View v)
	{

	}

	@Override
	public void showAsDropDown(View anchor, int xoff, int yoff, int gravity)
	{
		addBlurLayout();
		super.showAsDropDown(anchor, xoff, yoff, gravity);
	}

	@Override
	public void showAsDropDown(View anchor)
	{
		addBlurLayout();
		super.showAsDropDown(anchor);
	}

	@Override
	public void showAsDropDown(View anchor, int xoff, int yoff)
	{
		addBlurLayout();
		super.showAsDropDown(anchor, xoff, yoff);
	}

	@Override
	public void showAtLocation(View parent, int gravity, int x, int y)
	{
		addBlurLayout();
		super.showAtLocation(parent, gravity, x, y);
	}

	public void addBlurLayout()
	{
		if (mIsNeedBlur)
		{
			if (getContext() != null && mViewBlur == null)
			{
				WindowManager mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
				mViewBlur = getBlurLayout(getContext());
				mWindowManager.addView(mViewBlur, getBlurLayoutParams());
			}
		}
	}

	public View getBlurLayout(Context context)
	{
		LinearLayout llBlur = new LinearLayout(context);
		llBlur.setBackgroundColor(Color.parseColor("#77000000"));
		return llBlur;
	}

	public LayoutParams getBlurLayoutParams()
	{
		LayoutParams wmParams = new LayoutParams(LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG, LayoutParams.FLAG_BLUR_BEHIND,
				LayoutParams.FORMAT_CHANGED);
		return wmParams;
	}

	@Override
	public void dismiss()
	{
		if(isDisappear){
			removeBlur();
		}
		super.dismiss();
	}

	public void removeBlur()
	{
		if (getContext() != null && mViewBlur != null)
		{
			WindowManager mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
			mWindowManager.removeView(mViewBlur);
			mViewBlur = null;
		}
	}

}
