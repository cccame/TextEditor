package com.cccame.texteditor.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cccame.texteditor.R;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class OpenFileDialog extends Dialog {

	// private View parentFileView;
	private ListView filesListView;
	private ImageView parentFileImageView;
	private TextView parentFileTextView;

	private TextView selectedFileTextView;
	private Spinner charsetSpinner;

	private Button openButton;
	private Button cancelButton;

	private File currFile;
	private File rootFile;
	private File selectedFile;
	private File[] currFileChildFiles;

	private FileAdapter fileAdapter;
	private OnFileSelectedListener onFileSelectedListener;

	private ArrayAdapter charsetSpinnerAdapter;

	private FileNameComparator fileNameComparator;

	private OpenFileDialog dialog = this;

	private int screenWidth = 0;
	private int screenHeight = 0;

	public OpenFileDialog(Context context) {
		super(context);
		setContentView(R.layout.dialog_open_file);

		initData(context);
		initView();
		initListener();

		// 显示“没有选择文件”信息
		selectedFileTextView.setText(getContext().getResources().getString(
				R.string.no_files_selected));

		int width = screenWidth / 5 * 4;
		int height = screenHeight / 5 * 4;

		// 设置打开文件对话框宽度和高度
		setWidthAndHeight(width, height);

	}

	private void initData(Context context) {
		// 获取屏幕的宽度
		screenWidth = context.getResources().getDisplayMetrics().widthPixels;
		// 获取屏幕的高度
		screenHeight = context.getResources().getDisplayMetrics().heightPixels;

		// 使rootFile指向的文件目录成为根目录，一但用户浏览的目录为此目录，则无法进入rootFile的父目录，点击返回键也无效进入rootFile的父目录
		rootFile = Environment.getExternalStorageDirectory();
		currFile = rootFile;

		Log.e("123", "sdcard目录" + rootFile.listFiles().length);
		fileNameComparator = new FileNameComparator();

		// 将currFile的子文件存至数组，
		currFileChildFiles = currFile.listFiles();
		// 对File数组根据文件名进行排序
		Arrays.sort(currFileChildFiles, fileNameComparator);

		// 初始化字符编码下拉列表
		charsetSpinnerAdapter = ArrayAdapter.createFromResource(getContext(),
				R.array.spinner_charset, android.R.layout.simple_spinner_item);
		charsetSpinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

	}

	private void initView() {

		filesListView = (ListView) findViewById(R.id.dialog_open_file_list_view_files);

		selectedFileTextView = (TextView) findViewById(R.id.dialog_open_file_text_view_select_file);

		charsetSpinner = (Spinner) findViewById(R.id.dialog_open_file_spinner_charset);
		cancelButton = (Button) findViewById(R.id.dialog_open_file_button_cancel);
		openButton = (Button) findViewById(R.id.dialog_open_file_button_open);

		// 当按下返回键，打开文件对话框消失
		// setCancelable(true);

		fileAdapter = new FileAdapter();
		// 文件ListView设置适配器
		filesListView.setAdapter(fileAdapter);

		// 编码下拉列表设置适配器
		charsetSpinner.setAdapter(charsetSpinnerAdapter);

	}

	/*
	 * parentFileView.setOnClickListener(new View.OnClickListener(){
	 * 
	 * @Override public void onClick(View view){
	 * 
	 * selectedFile = null; currFile = currFile.getParentFile();
	 * currFileChildFiles = currFile.listFiles(); if(currFileChildFiles !=
	 * null){ Arrays.sort(currFileChildFiles,new FileComparator()); }
	 * fileAdapter.notifyDataSetChanged(); if(currFile.getParentFile() != null){
	 * parentFileView.setVisibility(View.VISIBLE);
	 * parentFileImageView.setBackgroundResource(R.drawable.folder_parent);
	 * parentFileTextView.setText(".."); }else
	 * parentFileView.setVisibility(View.GONE);
	 * 
	 * if(selectedFile == null){ // 显示“没有选择文件”信息
	 * selectedFileTextView.setText(getContext
	 * ().getResources().getString(R.string.no_files_selected)); }else{
	 * selectedFileTextView.setText(selectedFile.getName()); } } });
	 */

	private void initListener() {
		filesListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> view, View p2, int pos,
					long p4) {
				if (currFileChildFiles[pos].isDirectory()) {
					selectedFile = null;
					currFile = currFileChildFiles[pos];
					currFileChildFiles = currFile.listFiles();
					Arrays.sort(currFileChildFiles, fileNameComparator);
					fileAdapter.notifyDataSetChanged();
				} else {
					selectedFile = currFileChildFiles[pos];
				}

				if (selectedFile == null) {
					// 显示“没有选择文件”信息
					selectedFileTextView.setText(getContext().getResources()
							.getString(R.string.no_files_selected));
				} else {
					selectedFileTextView.setText(selectedFile.getName());
				}
			}
		});

		// 点击 确定
		openButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				if (onFileSelectedListener != null) {
					if (selectedFile != null) {
						String charset = charsetSpinner.getSelectedItem()
								.toString();
						onFileSelectedListener.onFileSelected(dialog,
								selectedFile, charset);
					} else {
						onFileSelectedListener.onNothingSelected(dialog);
					}
				}
			}
		});

		// 点击 取消
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				dialog.dismiss();
			}

		});

		charsetSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view,
					int i, long l) {
				charsetSpinner.setSelection(i);
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
				charsetSpinner.setSelection(0);
			}
		});

	}

	public void setBackground(int a, int r, int g, int b) {
		Window dialogWindow = getWindow();
	}

	public void setWidthAndHeight(int width, int height) {
		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL
				| Gravity.CENTER_VERTICAL);
		lp.width = width;
		lp.height = height;
		dialogWindow.setAttributes(lp);
	}

	public void setCurrentFile(File file) {
		currFile = file;
	}

	static class ViewHolder {
		TextView fileNameView;
		ImageView fileImageView;
	}

	public void setOnFileSelectedListener(
			OnFileSelectedListener onFileSelectedListener) {
		this.onFileSelectedListener = onFileSelectedListener;
	}

	public interface OnFileSelectedListener {
		void onFileSelected(OpenFileDialog dialog, File file, String charset);

		void onNothingSelected(OpenFileDialog dialog);
	}

	private class FileAdapter extends BaseAdapter {

		public FileAdapter() {
		}

		public FileAdapter(File file) {
		}

		public void update(File file) {

		}

		@Override
		public int getCount() {
			if (currFileChildFiles != null) {
				return currFileChildFiles.length;
			}
			return 0;
		}

		@Override
		public Object getItem(int p1) {
			return null;
		}

		@Override
		public long getItemId(int pos) {
			return pos;
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(
						R.layout.layout_item_file, null);
				viewHolder = new ViewHolder();
				viewHolder.fileImageView = (ImageView) convertView
						.findViewById(R.id.layout_item_file_image_view_file_icon);
				viewHolder.fileNameView = (TextView) convertView
						.findViewById(R.id.layout_item_file_text_view_file_name);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			if (currFileChildFiles[pos].isDirectory()) {
				viewHolder.fileImageView
						.setBackgroundResource(R.drawable.folder);
			} else {
				viewHolder.fileImageView
						.setBackgroundResource(R.drawable.file_type_unknown);
			}

			viewHolder.fileNameView.setText(currFileChildFiles[pos].getName());
			return convertView;

		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// toast("返回");
			if (currFile.getAbsolutePath().equals(rootFile.getAbsolutePath())) {
				this.dismiss();
			} else {
				File parentFile = currFile.getParentFile();
				if (parentFile != null) {
					currFile = parentFile;
					currFileChildFiles = currFile.listFiles();
					Arrays.sort(currFileChildFiles, fileNameComparator);
					fileAdapter.notifyDataSetChanged();
				}
			}
		}

		return true;
	}

	public void toast(Object obj) {
		Toast.makeText(getContext(), obj.toString(), 0).show();
	}

	private class FileNameComparator implements Comparator<File> {

		@Override
		public int compare(File fileBefore, File fileBehind) {
			if (fileBefore.isDirectory() && fileBehind.isFile())
				return -1;
			else if (fileBefore.isFile() && fileBehind.isDirectory())
				return 1;
			return fileBefore.getName().compareToIgnoreCase(
					fileBehind.getName());
		}
	}

}
