package com.tcp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class DivViewRight extends View {
	private static final String TAG = "DivViewLeft";
	private TextPaint mTextPaint;
	private final float density;
	private final int DIV_LEN = 5;
	
	public DivViewRight(Context context) {
		super(context);
		density = getResources().getDisplayMetrics().density;
		init(null, 0);
	}

	public DivViewRight(Context context, AttributeSet attrs) {
		super(context, attrs);
		density = getResources().getDisplayMetrics().density;
		init(attrs, 0);
	}

	public DivViewRight(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		density = getResources().getDisplayMetrics().density;
		init(attrs, defStyle);
	}

	
	private void init(AttributeSet attrs, int defStyle) {
		mTextPaint = new TextPaint();
		mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setTextAlign(Paint.Align.LEFT);
		mTextPaint.setColor(Color.BLACK);
		mTextPaint.setStrokeWidth(1*density);
		mTextPaint.setTextSize(18*density);
		//mTextPaint.setStyle(Style.STROKE);
	}


	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int contentWidth = getWidth();
		int contentHeight = getHeight();
		int xRight = 0;
		int xDivLine = (int)(xRight + DIV_LEN * density);
		canvas.drawLine(xRight,2*density,xDivLine,2*density,mTextPaint);
		canvas.drawLine(xDivLine,2*density,xDivLine,contentHeight-2*density,mTextPaint);
		canvas.drawLine(xDivLine,contentHeight-2*density,xRight,contentHeight-2*density,mTextPaint);		
	}
}
