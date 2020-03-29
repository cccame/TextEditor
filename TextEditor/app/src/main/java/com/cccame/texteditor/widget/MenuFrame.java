package com.cccame.texteditor.widget;
import android.content.*;
import android.view.*;
import android.widget.*;
import android.graphics.*;
import java.util.*;
import android.widget.AdapterView.*;

public class MenuFrame
{
	Context mContext;
	int mWidth;
	int mHeight;
	WindowManager mWindowManager;

	ListView mMenuListView;
	MenuFrameAdapter mMenuFrameAdapter;
	WindowManager.LayoutParams params;
	ArrayList<MyMenuItem> mMenuItemList = new ArrayList<MyMenuItem>();;
	boolean mMenuShowState = false;

	public MenuFrame(Context context, int width, int height)
	{
		mContext = context;
		mWidth = width;
		mHeight = height;
		mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

		mMenuListView = new ListView(context);
		// 必须设置FocusableInTouchMode为true，否则点击无反应
		mMenuListView.setFocusableInTouchMode(true);
		mMenuFrameAdapter = new MenuFrameAdapter();
		mMenuListView.setAdapter(mMenuFrameAdapter);

		params = new WindowManager.LayoutParams();
		params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
			| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
			| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
		// 设置params的format，效果为背景透明
        //params.format = PixelFormat.TRANSPARENT;

		params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
		// 设置宽度,高度
        params.width = width;
        params.height = height;
		params.gravity = Gravity.LEFT | Gravity.TOP;

        params.x = 0;
        params.y = 0;
	}

	public Context getContext()
	{
		return mContext;
	}

	public void setWidthAndHeight(int width, int height)
	{
		mWidth = width;
		mHeight = height;
	}

	public void setBackgroundColor(int color)
	{
		mMenuListView.setBackgroundColor(color);
	}

	public int num;
	public void show(int x, int y)
	{
		if (mMenuShowState == false)
		{
			params.x = x;
			params.y = y;
			mWindowManager.addView(mMenuListView, params);
			mMenuShowState = true;
			num++;
		}

	}


	public void setGravity(int gravity)
	{
		params.gravity = gravity;
		//mWindowManager.updateViewLayout(mMenuListView,params);
	}

	public void hide()
	{
		if (mMenuShowState == true)
		{
			mWindowManager.removeView(mMenuListView);
			mMenuShowState = false;
		}
	}

	public boolean getMenuShowState()
	{
		return mMenuShowState;
	}

	public void addMenuItem(MyMenuItem menuItem)
	{
		if (menuItem != null)
		{
			mMenuItemList.add(menuItem);
			mMenuFrameAdapter.notifyDataSetChanged();
		}
	}


	public void setOnItemClickListener(OnItemClickListener listener)
	{
		if (listener != null)
			mMenuListView.setOnItemClickListener(listener);
	}
	
	class MenuFrameAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			return mMenuItemList.size();
		}

		@Override
		public Object getItem(int p1)
		{
			return null;
		}

		@Override
		public long getItemId(int p1)
		{
			return 0;
		}

		@Override
		public View getView(int pos, View p2, ViewGroup p3)
		{
			TextView tv = new TextView(mContext);
			tv.setGravity(Gravity.CENTER);
			tv.setHeight(mMenuItemList.get(pos).getHeight());
			tv.setText(mMenuItemList.get(pos).getName());
//			tv.setFocusable(false);
//			tv.setClickable(false);
//			tv.setFocusableInTouchMode(false);
			return tv;
		}
	}
}
