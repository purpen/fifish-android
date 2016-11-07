package com.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**使ImageView的高恒等于ImageView的宽,以ImageView的宽为准的正方形 */
public class SquaredImageView extends ImageView {
  public SquaredImageView(Context context) {
    super(context);
  }

  public SquaredImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
  }
}
