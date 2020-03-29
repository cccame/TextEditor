package com.cccame.texteditor.utils;

import android.content.*;
import android.content.res.*;
import android.util.*;

public class WindowUtil {
	
	private Context context;

	private Resources resources;

	private DisplayMetrics dm;

	public WindowUtil(Context context) {
		this.context = context;
		this.resources = context.getResources();
		this.dm = resources.getDisplayMetrics();
	}

	public int getScreenWidth() {
		return this.dm.widthPixels;
	}

	public int getScreenHeight() {
		return this.dm.heightPixels;
	}
}
