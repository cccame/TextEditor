package com.cccame.texteditor;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.cccame.texteditor.adapter.TextAdapter;
import com.cccame.texteditor.dialog.OpenFileDialog;
import com.cccame.texteditor.utils.StringUtil;
import com.cccame.texteditor.view.MyTextView;

import java.io.File;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.*;
import android.view.*;
import com.cccame.texteditor.utils.WindowUtil;
import com.cccame.texteditor.widget.*;
import android.widget.AdapterView.*;

public class MainActivity extends Activity implements View.OnClickListener
{

	private MyTextView mtv;

	private TextView titleTextView;

	private TextView optionsMenuTextView;

	/*
	 * static{ System.loadLibrary("text_engine"); }
	 */

	private WindowUtil windowUtil;

	MenuFrame menuFrame;
	MenuFrame mfNew;


	MainActivity ma = this;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		windowUtil = new WindowUtil(this);

		initViews();
		initData();
		initListener();


		//setTitle("123");

	}

	private void initViews()
	{
		setContentView(R.layout.activity_main);

		titleTextView = (TextView) findViewById(R.id.activity_main_text_view_title);
		optionsMenuTextView = (TextView) findViewById(R.id.text_view_options_menu);
		mtv = (MyTextView) findViewById(R.id.mtv);

		int width = windowUtil.getScreenWidth() / 3;
		int height = windowUtil.getScreenHeight() / 3;
		// 创建菜单
		menuFrame = new MenuFrame(this, width, height);

		menuFrame.addMenuItem(new MyMenuItem("新建", height / 6));
		menuFrame.addMenuItem(new MyMenuItem("打开", height / 6));
		menuFrame.addMenuItem(new MyMenuItem("保存", height / 6));
		menuFrame.addMenuItem(new MyMenuItem("另存", height / 6));
		menuFrame.addMenuItem(new MyMenuItem("设置", height / 6));
		menuFrame.addMenuItem(new MyMenuItem("退出", height / 6));
		

		mfNew = new MenuFrame(MainActivity.this, width, height / 6 * 2);
		mfNew.addMenuItem(new MyMenuItem("工程", height / 6));
		mfNew.addMenuItem(new MyMenuItem("文件", height / 6));
		menuFrame.setSubMenu(0,mfNew);
	}

	private void initData()
	{
		/*
		 mtv.setTextAdapter(new TextAdapter() {
		 @Override
		 public String getLine(int index)
		 {
		 return StringUtil.cat("第", index, "行数据");
		 }
		 });
		 */
	}

	private void initListener()
	{
		optionsMenuTextView.setOnClickListener(this);

		menuFrame.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int pos, long p4)
				{
					menuFrame.setBackgroundColor(pos,Color.BLACK);
					menuFrame.setTextColor(pos,Color.WHITE);
					
					switch (pos)
					{
							// 新建
						case 0:
							MenuFrame frame = menuFrame.getSubMenu(pos);
							frame.show(windowUtil.getScreenWidth()-menuFrame.getWidth()-frame.getWidth(),menuFrame.getTop());
							break;
							// 打开
						case 1:

							OpenFileDialog ofd = new  OpenFileDialog(MainActivity.this);
							ofd.setOnFileSelectedListener(new OpenFileDialog.OnFileSelectedListener() {

									@Override
									public void onFileSelected(OpenFileDialog dialog, File file, String charset)
									{
										toast(file);
										dialog.dismiss(); 
									}

									@Override
									public void onNothingSelected(OpenFileDialog dialog)
									{ 

									}

								}); ofd.show();

							break;
							// 退出
						case 5:
							break;

					}
				}

			});
	}

	int optionsMenuTextViewClickTimes = 0;

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.text_view_options_menu:

				// 点击次数是偶数
				if (optionsMenuTextViewClickTimes % 2 == 0)
				{
					menuFrame.show(windowUtil.getScreenWidth()-menuFrame.getWidth(), optionsMenuTextView.getBottom());
				}// 点击次数奇数
				else
				{
					menuFrame.hide();
				}
				optionsMenuTextViewClickTimes++;
				break;

		}
	}

	public void toast(Object obj)
	{
		Toast.makeText(getApplicationContext(), obj.toString(), 0).show();
	}

	@Override
	public void setTitle(CharSequence title)
	{
		setTitleMessage(title);
	}

	public void setTitleMessage(Object obj)
	{
		if (obj != null)
		{
			titleTextView.setText(obj.toString());
		}
	}

}
