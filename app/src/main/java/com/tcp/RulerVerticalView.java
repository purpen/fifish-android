package com.tcp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.IntegerRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

import com.qiyuan.fifish.R;

/**
 * Created by xf on 2016/9/5.
 */

public class RulerVerticalView extends View {

    final String TAG = RulerHorizontalView.class.getSimpleName();

    private int mBeginRange;

    private int mEndRange;

    private int mInnerWidth;

    private int mIndicatePadding;//刻度间距

    private Paint mIndicatePaint;

    private Paint mTextPaint;

    private Paint mChoosedPaint;//被选中线的画笔

    private int mIndicateWidth;//刻度线的宽度

    private float mIndicateScale;

    private int mLastMotionX;

    private boolean mIsDragged;//判断拖动没有

    private boolean mIsAutoAlign = true;//判断自动对齐

    private boolean mIsWithText = true;

    //view
    private int mTextColor;

    private float mTextSize;

    private int mIndicateColor;

    private RulerHorizontalView.OnScaleListener mListener;

    private int mGravity;

    private Rect mIndicateLoc;//容纳刻度尺的矩形


    //滚动相关参数
    private OverScroller mOverScroller;
    private VelocityTracker mVelocityTracker;//速率追踪器，主要用于手势UP时，惯性滑动的距离
    private int mTouchSlop;
    private int mMinimumVelocity;
    private int mMaximumVelocity;


    public RulerVerticalView(Context context) {
        this(context, null);
    }

    public RulerVerticalView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RulerVerticalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RulerHorizontalView);
        mIndicateColor = ta.getColor(R.styleable.RulerHorizontalView_indicateColor, Color.BLACK);
        mTextColor = ta.getColor(R.styleable.RulerHorizontalView_textColor, Color.GRAY);
        mTextSize = ta.getDimension(R.styleable.RulerHorizontalView_textSize, 18);
        mBeginRange = ta.getInt(R.styleable.RulerHorizontalView_begin, 0);
        mEndRange = ta.getInt(R.styleable.RulerHorizontalView_end, 100);
        mIndicateWidth = (int) ta.getDimension(R.styleable.RulerHorizontalView_indicateWidth, 5);
        mIndicatePadding = (int) ta.getDimension(R.styleable.RulerHorizontalView_indicatePadding, 15);
        ta.recycle();

        int[] indices = new int[]{android.R.attr.gravity};//indices 指数；目录；索引
        ta = context.obtainStyledAttributes(attrs, indices);
        mGravity = ta.getInt(ta.getIndex(0), Gravity.RIGHT);
        ta.recycle();

        mIndicateScale = 0.7f;

        initValue();
    }

    private void initValue() {

        mOverScroller = new OverScroller(getContext());
        setOverScrollMode(OVER_SCROLL_ALWAYS);

        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        //getScaledTouchSlop是一个距离，表示滑动的时候，手的移动要大于这个距离才开始移动控件。如果小于这个距离就不触发移动控件
        mTouchSlop = configuration.getScaledTouchSlop();
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();

        mIndicatePaint = new Paint();
        mIndicatePaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);//设置对齐方式
        mTextPaint.setTextSize(mTextSize);
        mInnerWidth = (mEndRange - mBeginRange) * getIndicateWidth();

//      被选中线的画笔
        mChoosedPaint = new Paint();
        mChoosedPaint.setStyle(Paint.Style.FILL);

        mIndicateLoc = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int count = canvas.save();
        for (int value = mBeginRange, position = 0; value <= mEndRange; value++, position++) {
            drawIndicate(canvas, position);
            if (mIsWithText)//是否显示尺子上的数字
                drawText(canvas, position, String.valueOf(value));
        }
        canvas.restoreToCount(count);
    }

    private void drawIndicate(Canvas canvas, int position) {
        computeIndicateLoc(mIndicateLoc, position);
        int left = mIndicateLoc.left;
        int right = mIndicateLoc.right ;
        int top = mIndicateLoc.top + mIndicatePadding;
        int bottom = mIndicateLoc.bottom- mIndicatePadding;

        if (position % 10 != 0) {
                int indicateHeight = right - left;
                if (isAlignLeft()) {
                    right = (int) (left + indicateHeight * mIndicateScale);
                } else {
                    left = (int) (right - indicateHeight * mIndicateScale);
                }
        }

        mIndicatePaint.setColor(mIndicateColor);
        canvas.drawRect(left, top, right, bottom, mIndicatePaint);

        /*被选中线以黑色标记画出，注意它得在刻度画笔mIndicatePaint画了之后画出，
        若次序颠倒，则mIndicatePaint的颜色会盖掉它的颜色*/
        if (position == computeSelectedPosition()) {
            mChoosedPaint.setColor(Color.BLACK);
            canvas.drawRect(left,top,right,bottom,mChoosedPaint);
        }
    }

    private void drawText(Canvas canvas, int position, String text) {
        if (position % 5 != 0)
            return;

        computeIndicateLoc(mIndicateLoc, position);
        int textHeight = computeTextHeight();

        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        int y = (mIndicateLoc.top + mIndicateLoc.bottom) / 2;
        int x = mIndicateLoc.right + textHeight;

        if (!isAlignLeft()) {
            x = mIndicateLoc.left;
            mTextPaint.getTextBounds(text, 0, text.length(), mIndicateLoc);
            x += mIndicateLoc.left / 2;  //增加一些偏移
        }
        canvas.drawText(text, x, y, mTextPaint);
    }

    /**
     * 计算indicate的位置
     *
     * @param outRect
     * @param position
     */
    private void computeIndicateLoc(Rect outRect, int position) {
        if (outRect == null)
            return;

        int height = getHeight(); // 获取控件的高度
        int width=getWidth();
        int indicate = getIndicateWidth(); // 获取一条刻度线的宽度加上其左右两边间距宽度的总宽度
        int top = (indicate * position);
        int bottom = top + indicate;
        int left = getPaddingLeft();
        int right = width - getPaddingRight();

        if (mIsWithText) {
            int textHeight = computeTextHeight();
            if (isAlignLeft())
                left -= textHeight;
            else
                right += textHeight;
        }

        int offsets = getStartOffsets();
        left += offsets;
        right += offsets;
        outRect.set(left, top, right, bottom);
    }

    /**
     * 开始偏移，如果要包含文字的话才需要偏移。
     *
     * @return
     */
    private int getStartOffsets() {
        if (mIsWithText) {
            String text = String.valueOf(mBeginRange);
            int textWidth = (int) mTextPaint.measureText(text, 0, text.length());
            return textWidth / 2;
        }
        return 0;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        initVelocityTrackerIfNotExists();

        mVelocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //
                if (mIsDragged = !mOverScroller.isFinished()) {
                    if (getParent() != null)
                        getParent().requestDisallowInterceptTouchEvent(true);
                }

                if (!mOverScroller.isFinished())
                    mOverScroller.abortAnimation();

                mLastMotionX = (int) event.getY();

                return true;

            case MotionEvent.ACTION_MOVE:

                int curX = (int) event.getY();
                int deltaX = mLastMotionX - curX;

                if (!mIsDragged && Math.abs(deltaX) > mTouchSlop) {
                    if (getParent() != null)
                        getParent().requestDisallowInterceptTouchEvent(true);

                    mIsDragged = true;

                    if (deltaX > 0) {
                        deltaX -= mTouchSlop;
                    } else {
                        deltaX += mTouchSlop;
                    }
                }

                if (mIsDragged) {
                    mLastMotionX = curX;

                    if (getScrollY() <= 0 || getScrollY() >= getMaximumScroll())
                        deltaX *= 0.7;


                    if (overScrollBy(0, deltaX, getScrollX(), getScrollY(), 0, getMaximumScroll(), 0, getHeight(), true)) {
                        mVelocityTracker.clear();
                    }

                }

                break;
            case MotionEvent.ACTION_UP: {
                if (mIsDragged) {
                    mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                    int initialVelocity = (int) mVelocityTracker.getYVelocity();

                    if ((Math.abs(initialVelocity) > mMinimumVelocity)) {
                        fling(-initialVelocity);
                    } else {
                        //alignCenter();
                        sprintBack();
                    }
                }

                mIsDragged = false;
                recycleVelocityTracker();
                break;
            }
            case MotionEvent.ACTION_CANCEL: {

                if (mIsDragged && mOverScroller.isFinished()) {
                    sprintBack();
                }

                mIsDragged = false;

                recycleVelocityTracker();
                break;
            }
        }

        return true;
    }
    private void refreshValues() {
        mInnerWidth = (mEndRange - mBeginRange) * getIndicateWidth();
        invalidateView();

    }

    private int getIndicateWidth() {
        return mIndicateWidth + mIndicatePadding + mIndicatePadding;
    }

    /**
     * 获取最小滚动值。
     *
     * @return
     */
    private int getMinimumScroll() {
        return -(getHeight() - getIndicateWidth()) / 2 + getStartOffsets();
    }

    /**
     * 获取最大滚动值。
     *
     * @return
     */
    private int getMaximumScroll() {
        return mInnerWidth + getMinimumScroll();
    }

    /**
     * 调整indicate，使其居中。
     */
    private void adjustIndicate() {
        if (!mOverScroller.isFinished())
            mOverScroller.abortAnimation();

        int position = computeSelectedPosition();
        int scrollX = getScrollByPosition(position);
        scrollX -= getScrollY();

        if (scrollX != 0) {
            mOverScroller.startScroll(getScrollX(), getScrollY(),0, scrollX);
            invalidateView();
        }
    }

    public void fling(int velocityX) {
        mOverScroller.fling(getScrollX(), getScrollY(),  0,velocityX, 0, 0,getMinimumScroll(), getMaximumScroll(),0,  getHeight() / 2);
        invalidateView();
    }
    //弹回
    public void sprintBack() {
        mOverScroller.springBack(getScrollX(), getScrollY(), 0, 0,getMinimumScroll(), getMaximumScroll());
        invalidateView();
    }


    public void setOnScaleListener(RulerHorizontalView.OnScaleListener listener) {
        if (listener != null) {
            mListener = listener;
        }
    }

    /**
     * 获取position的绝对滚动位置。
     *
     * @param position
     * @return
     */
    private int getScrollByPosition(int position) {
        computeIndicateLoc(mIndicateLoc, position);
        int scrollX = mIndicateLoc.top - getStartOffsets() + getMinimumScroll();
        return scrollX;
    }

    /**
     * 计算当前已选择的位置
     * @return
     */
    public int computeSelectedPosition() {
        int centerX = getScrollY() - getMinimumScroll() + getIndicateWidth() / 2;
        centerX = Math.max(0, Math.min(mInnerWidth, centerX));
        int position = centerX / getIndicateWidth();
        return position;
    }

    public void smoothScrollTo(int position) {
        if (position < 0 || mBeginRange + position > mEndRange)
            return;

        if (!mOverScroller.isFinished())
            mOverScroller.abortAnimation();

        int scrollY = getScrollByPosition(position);
        mOverScroller.startScroll(getScrollX(), getScrollY(), 0, scrollY-getScrollY());
        invalidateView();
    }

    public void smoothScrollToValue(int value) {
        int position = value - mBeginRange;
        smoothScrollTo(position);
    }


    private void onScaleChanged(int scale) {
        if (mListener != null)
            mListener.onScaleChanged(scale);
    }


    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        if (!mOverScroller.isFinished()) {
            final int oldX = getScrollX();
            final int oldY = getScrollY();
            setScrollY(scrollY);
            onScrollChanged(scrollX, scrollY, oldX, oldY);
            if (clampedX) {
                //sprintBack();
            }
        } else {
            super.scrollTo(scrollX, scrollY);
        }

        if (mListener != null) {
            int position = computeSelectedPosition();
            onScaleChanged(position + mBeginRange);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    private int computeTextHeight() {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float textHeight = fontMetrics.descent - fontMetrics.ascent;
        return (int) textHeight;
    }


    private boolean isAlignLeft() {
        return (mGravity & Gravity.LEFT) == Gravity.LEFT;
    }


    public void setGravity(int gravity) {
        this.mGravity = gravity;
        invalidateView();
    }

    @Override
    public void computeScroll() {
        if (mOverScroller.computeScrollOffset()) {
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = mOverScroller.getCurrX();
            int y = mOverScroller.getCurrY();
            overScrollBy(x - oldX, y - oldY, oldX, oldY, 0, getMaximumScroll(), 0, getHeight(), false);
            invalidateView();
        } else if (!mIsDragged && mIsAutoAlign) {
            adjustIndicate();
        }

    }

 /*   @Override
    protected int computeHorizontalScrollRange() {
        return getMaximumScroll();
    }*/

    @Override
    protected int computeVerticalScrollRange() {
        return getMaximumScroll();
    }

    public void invalidateView() {
        if (Build.VERSION.SDK_INT >= 16) {
            postInvalidateOnAnimation();
        } else
            invalidate();
    }


    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    public interface OnScaleListener {
        void onScaleChanged(int scale);

    }

    public void setIndicateWidth(@IntegerRes int indicateWidth) {
        this.mIndicateWidth = indicateWidth;
        refreshValues();
    }

    public void setIndicatePadding(@IntegerRes int indicatePadding) {
        this.mIndicatePadding = indicatePadding;
        refreshValues();
    }

    //让用户选择是否显示刻度尺上的数字
    public void setWithText(boolean withText) {
        this.mIsWithText = withText;
        refreshValues();
    }

    public void setAutoAlign(boolean autoAlign) {
        this.mIsAutoAlign = autoAlign;
        refreshValues();
    }

    public boolean isWithText() {
        return mIsWithText;
    }

    public boolean isAutoAlign() {
        return mIsAutoAlign;
    }
}

