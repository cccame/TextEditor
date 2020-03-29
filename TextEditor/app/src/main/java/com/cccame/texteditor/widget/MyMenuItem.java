package com.cccame.texteditor.widget;
import android.graphics.*;

public class MyMenuItem
{
	String mMenuItemName;
	int mHeight;


	int mMenuItemColor = Color.BLACK;
	int mBackgroundColor = Color.WHITE;

	public MyMenuItem(String menuItemName, int height)
	{
		mMenuItemName = menuItemName;
		mHeight = height;
	}
	
	public String getName(){
		return mMenuItemName;
	}

	public int getHeight(){
		return mHeight;
	}
	public void setMenuItemColor(int color)
	{
		mMenuItemColor = color;
	} 

	public void setBackgroundColor(int color)
	{
		mBackgroundColor = color;
	}


}
