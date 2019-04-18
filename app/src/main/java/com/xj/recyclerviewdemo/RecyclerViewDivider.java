package com.xj.recyclerviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


/**
 * 博客地址：http://blog.csdn.net/gdutxiaoxu
 *
 * @author xujun
 * @time 19-4-17
 */
public class RecyclerViewDivider extends RecyclerView.ItemDecoration {

    private static final String TAG = "RecyclerViewDivider";

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private static final int DIVIDER_COLOR = Color.parseColor("#efefef");

    private Paint mPaint;
    private Drawable mDivider;
    private int mDividerHeight = 1;

    //The direction of the list：LinearLayoutManager.VERTICAL or LinearLayoutManager.HORIZONTAL
    private int mOrientation;
    private int mLeftOffset;
    private int mRightOffset;
    private boolean mIsShowLastDivider;
    private int mDividerColor;
    private int mStart = 0;
    private int mEnd = 0;


    public RecyclerViewDivider(Context context) {
        this(context, LinearLayoutManager.VERTICAL, 1, DIVIDER_COLOR);
    }

    public RecyclerViewDivider(Context context, int orientation, int dividerHeight, int dividerColor) {
        this(context, orientation);

        mDividerColor = dividerColor;
        mDividerHeight = dividerHeight;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public RecyclerViewDivider(Context context, int orientation) {
        if (orientation != LinearLayoutManager.VERTICAL
                && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("Please input right parameter");
        }

        mOrientation = orientation;

        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    public RecyclerViewDivider(Context context, int orientation, int drawableId) {
        this(context, orientation);

        mDivider = ContextCompat.getDrawable(context, drawableId);
        mDividerHeight = mDivider.getIntrinsicHeight();
    }

    /**
     * Set the offset of the horizontal split line
     *
     * @param leftOffset
     * @param rightOffset
     */
    public void setHorizontaloffset(int leftOffset, int rightOffset) {
        mLeftOffset = leftOffset;
        mRightOffset = rightOffset;
    }

    /**
     * Set whether to display the last split line, not displayed by default
     *
     * @param isShowLastDivider
     */
    public void setShowLastDivider(boolean isShowLastDivider) {
        mIsShowLastDivider = isShowLastDivider;
    }

    /**
     * split line display range
     * <p>
     * fisrt = 0 + start;
     * last = childSize - 1 - end - mIsShowLastDivider ? 0 : 1
     *
     * @param start
     * @param end
     */
    public void setDividerStartAndEndOffsetCount(int start, int end) {
        mStart = start;
        mEnd = end;
    }

    public void setDividerHeight(int dividerHeight) {
        mDividerHeight = dividerHeight;
    }

    public void setDividerColor(int dividerColor) {
        mDividerColor = dividerColor;
        if (mPaint != null) {
            mPaint.setColor(mDividerColor);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mDividerHeight);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVerticalDivider(c, parent);
        } else {
            drawHorizontalDivider(c, parent);
        }
    }

    //Draw item dividing line
    private void drawVerticalDivider(Canvas canvas, RecyclerView parent) {
        // mLeftOffset 为自己设置的左边偏移量
        final int left = parent.getPaddingLeft() + mLeftOffset;
        // mRightOffset 为设置的右边偏移量
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight() + mRightOffset;
        final int childSize = parent.getChildCount();

        if (childSize <= 0) {
            return;
        }

        // 从第一个 item 开始绘制
        int first = mStart;
        // 到第几个 item 绘制结束
        int last = childSize - mEnd - (mIsShowLastDivider ? 0 : 1);
        Log.d(TAG, " last = " + last + " childSize =" + childSize + "left = " + left);

        if (last <= 0) {
            return;
        }

        for (int i = first; i < last; i++) {
            drawableVerticalDivider(canvas, parent, left, right, i, mDividerHeight);
        }

    }

    private void drawableVerticalDivider(Canvas canvas, RecyclerView parent, int left, int right, int i, int dividerHeight) {
        final View child = parent.getChildAt(i);

        if (child == null) {
            return;
        }

        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        final int top = child.getBottom() + layoutParams.bottomMargin;
        final int bottom = top + dividerHeight;

        // 适配 drawable
        if (mDivider != null) {
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }

        // 适配分割线
        if (mPaint != null) {
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    // Draw vertical item dividing line
    private void drawHorizontalDivider(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();

        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDividerHeight;

            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }

            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

}