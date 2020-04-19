package com.cccame.texteditor.widget;
import android.graphics.*;

public class MyMenuItem
{
	String mMenuItemName;
	int mHeight;

	int mMenuItemColor = Color.BLACK;
	int mBackgroundColor = Color.WHITE;

	MenuFrame subMenu = null;
	
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


	public MyMenuItem setSubMenu(MenuFrame mf){
		subMenu = mf;
		return this;
	}
	
	public MenuFrame getSubMenu(){
		return subMenu;
	}
	
	public boolean hasSubMenu(){
		return subMenu != null;
	}
}
