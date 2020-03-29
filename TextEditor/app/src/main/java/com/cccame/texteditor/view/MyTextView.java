package com.cccame.texteditor.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.cccame.texteditor.adapter.TextAdapter;


public class MyTextView extends View
{
    private Paint mTextPaint;
    private static float mDefaultTextSize = 50;
    private TextAdapter mTextAdapter;

    public MyTextView(Context context)
	{
        this(context, null);
    }

    public MyTextView(Context context, AttributeSet attrs)
	{
        super(context, attrs);
        init();
    }

    private void init()
	{

        // 创建画笔对象
        mTextPaint = new Paint();

        // 设置颜色为黑色
        mTextPaint.setColor(Color.BLACK);

        // 设置等宽字体
        mTextPaint.setTypeface(Typeface.MONOSPACE);

        // 设置字体的默认大小
        mTextPaint.setTextSize(mDefaultTextSize);
    }

    private int ascent = 0;
    private int descent = 0;
    private int lineHeight = 0;
    private int startLineIndex = 1;
    private float mScrollX = 0;
    private float mScrollY = 0;

    int n = 1;

    @Override
    protected void onDraw(Canvas canvas)
	{
        ascent = -(int) mTextPaint.ascent();
        descent = (int) mTextPaint.descent();
        lineHeight = ascent + descent;

		if (mTextAdapter != null)
		{

			// 更新开始行号
			startLineIndex = updateStartLineIndex();
			mScrollY = 0;
			for (int index = startLineIndex; mScrollY <= getHeight(); index++)
			{

				mScrollY = (ascent * index + descent * (index - 1)) - scrollY;

				String text = mTextAdapter.getLine(index);
				canvas.drawText(text, mScrollX, mScrollY, mTextPaint);

				int lineStartX = 0;

				// 绘制行的横坐标结束位置
				int lineEndX = getScrollX() + getWidth();

				// 绘制行的纵坐标
				float lineY = mScrollY+descent;

				// // 绘制行
				canvas.drawLine(lineStartX, lineY, lineEndX, lineY, mTextPaint);

			}
		}
		

    }

    public int updateStartLineIndex()
	{
        int index = 1;
        index = scrollY / lineHeight;
        int height = index * lineHeight;
        if (scrollY > height)
		{
            index++;
        }
        if (index <= 0)
            index = 1;

        return index;
    }

    private int downX = 0;
    private int downY = 0;
    private int moveX = 0;
    private int moveY = 0;
    private int scrollX = 0;
    private int scrollY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event)
	{
        switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				downX = (int) event.getX();
				downY = (int) event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				moveX = (int) event.getX();
				moveY = (int) event.getY();

				int dx = moveX - downX;
				int dy = moveY - downY;

				scrollY -= dy;

				if (scrollY < 0)
				{
					scrollY = 0;
				}
//			if (newX < 0)
//				newX = 0;

				invalidate();
				title(scrollY + "," + updateStartLineIndex());
				downX = moveX;
				downY = moveY;
				break;
			case MotionEvent.ACTION_UP:
				break;
        }
        return true;
    }



    public void setTextAdapter(TextAdapter textAdapter)
	{
        mTextAdapter = textAdapter;
    }


    public void title(Object obj)
	{
        if (obj != null)
		{
            ((Activity) getContext()).setTitle(obj.toString());
        }
    }
}
