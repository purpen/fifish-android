package com.qiyuan.fifish.ui.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qiyuan.fifish.R;

/**
 * @author lilin
 * created at 2016/10/12 11:51
 */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        Paint paint = new Paint();
        paint.setColor(parent.getContext().getResources().getColor(R.color.color_ddd));
        int count = parent.getChildCount();
        for (int i=0;i<count;i++){
            View child = parent.getChildAt(i);
            float x = child.getX();
            float y = child.getY();
            int width = child.getWidth();
            int height = child.getHeight();
            c.drawLine(x, y, x + width, y, paint);
            c.drawLine(x, y, x, y + height, paint);
            c.drawLine(x + width, y, x + width, y + height, paint);
            c.drawLine(x, y + height, x + width, y + height, paint);
        }
        super.onDraw(c, parent, state);
    }
}
