package com.xj.recyclerviewdemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

/**
 * 博客地址：http://blog.csdn.net/gdutxiaoxu
 *
 * @author xujun
 * @time 19-4-17
 */
public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "GridDividerItemDeco";

    private Context mContext;

    private boolean mIsFirstAndLastColumnNeedSpace = false;//第一列和最后一列是否需要指定间隔(默认不指定)
    private int mFirstAndLastColumnW;          //您所需指定的间隔宽度，主要为第一列和最后一列与父控件的间隔；行间距，列间距将动态分配
    private int mFirstRowTopMargin = 0; //第一行顶部是否需要间隔

    private int mLastRowBottomMargin = 0;

    private int mSpanCount = 0;
    private int mScreenW = 0;

    /**
     * @param firstAndLastColumnW           间隔宽度
     * @param isFirstAndLastColumnNeedSpace 第一列和最后一列是否需要间隔
     */
    public GridDividerItemDecoration(Context context, int firstAndLastColumnW, boolean isFirstAndLastColumnNeedSpace) {
        this(context, firstAndLastColumnW, 0, isFirstAndLastColumnNeedSpace, false);
    }

    /**
     * @param firstAndLastColumnW           间隔宽度
     * @param isFirstAndLastColumnNeedSpace 第一列和最后一列是否需要间隔
     * @param firstRowTopMargin             第一行顶部是否需要间隔(根据间隔大小判断)
     */
    public GridDividerItemDecoration(Context context, int firstAndLastColumnW, int firstRowTopMargin, boolean isFirstAndLastColumnNeedSpace) {
        this(context, firstAndLastColumnW, firstRowTopMargin, isFirstAndLastColumnNeedSpace, false);
    }

    /**
     * @param firstAndLastColumnW           间隔宽度
     * @param firstRowTopMargin             第一行顶部是否需要间隔
     * @param isFirstAndLastColumnNeedSpace 第一列和最后一列是否需要间隔
     * @param isLastRowNeedSpace            最后一行是否需要间隔
     */
    public GridDividerItemDecoration(Context context, int firstAndLastColumnW, int firstRowTopMargin, boolean isFirstAndLastColumnNeedSpace, boolean isLastRowNeedSpace) {
        this(context, firstAndLastColumnW, firstRowTopMargin, isFirstAndLastColumnNeedSpace, isLastRowNeedSpace, Color.WHITE);
    }

    /**
     * @param firstAndLastColumnW           间隔宽度
     * @param firstRowTopMargin             第一行顶部是否需要间隔
     * @param isFirstAndLastColumnNeedSpace 第一列和最后一列是否需要间隔
     * @param isLastRowNeedSpace            最后一行是否需要间隔
     */
    public GridDividerItemDecoration(Context context, int firstAndLastColumnW, int firstRowTopMargin, boolean isFirstAndLastColumnNeedSpace, boolean isLastRowNeedSpace, @ColorInt int color) {
        mFirstAndLastColumnW = firstAndLastColumnW;
        this.mIsFirstAndLastColumnNeedSpace = isFirstAndLastColumnNeedSpace;
        this.mContext = context;
        this.mFirstRowTopMargin = firstRowTopMargin;
    }

    public void setFirstRowTopMargin(int firstRowTopMargin) {
        mFirstRowTopMargin = firstRowTopMargin;
    }

    public void setLastRowBottomMargin(int lastRowBottomMargin) {
        mLastRowBottomMargin = lastRowBottomMargin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int top = 0;
        int left;
        int right;
        int bottom;

        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        mSpanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        int maxAllDividerWidth = getMaxDividerWidth(view); //

        int spaceWidth = 0;//首尾两列与父布局之间的间隔
        if (mIsFirstAndLastColumnNeedSpace) {
            spaceWidth = mFirstAndLastColumnW;
        }

        int eachItemWidth = maxAllDividerWidth / mSpanCount;//每个Item left+right
        int dividerItemWidth = (maxAllDividerWidth - 2 * spaceWidth) / (mSpanCount - 1);//item与item之间的距离

        left = (int) (1.0f * itemPosition % mSpanCount * (dividerItemWidth - eachItemWidth) + spaceWidth);
        right = eachItemWidth - left;
        bottom = dividerItemWidth;

        // 首行
        if (mFirstRowTopMargin > 0 && isFirstRow(parent, itemPosition, mSpanCount, childCount)) {//第一行顶部是否需要间隔
            top = mFirstRowTopMargin;
        }

        if (isLastRow(parent, itemPosition, mSpanCount, childCount)) {//最后一行是否需要间隔
            if (mLastRowBottomMargin < 0) {
                bottom = 0;
            } else {
                bottom = mLastRowBottomMargin;
            }
        }

        Log.i(TAG, "getItemOffsets: itemPosition =" + itemPosition + " left = " + left + " top = " + top
                + " right = " + right + " bottom = " + bottom);

        outRect.set(left, top, right, bottom);
    }

    /**
     * 获取Item View的大小，若无则自动分配空间
     * 并根据 屏幕宽度-View的宽度*spanCount 得到屏幕剩余空间
     *
     * @param view
     * @return
     */
    private int getMaxDividerWidth(View view) {
        int itemWidth = view.getLayoutParams().width;
        int itemHeight = view.getLayoutParams().height;

        int screenWidth = getScreenWidth();

        int maxDividerWidth = screenWidth - itemWidth * mSpanCount;

        if (itemHeight < 0 || itemWidth < 0 || (mIsFirstAndLastColumnNeedSpace && maxDividerWidth <= (mSpanCount - 1) * mFirstAndLastColumnW)) {
            view.getLayoutParams().width = getAttachCloumnWidth();
            view.getLayoutParams().height = getAttachCloumnWidth();
            maxDividerWidth = screenWidth - view.getLayoutParams().width * mSpanCount;
        }

        return maxDividerWidth;
    }

    private int getScreenWidth() {
        if (mScreenW > 0) {
            return mScreenW;
        }

        mScreenW = mContext.getResources().getDisplayMetrics().widthPixels > mContext.getResources().getDisplayMetrics().heightPixels
                ? mContext.getResources().getDisplayMetrics().heightPixels : mContext.getResources().getDisplayMetrics().widthPixels;
        return mScreenW;
    }

    /**
     * 根据屏幕宽度和item数量分配 item View的width和height
     *
     * @return
     */
    private int getAttachCloumnWidth() {
        int itemWidth = 0;
        int spaceWidth = 0;
        try {
            int width = getScreenWidth();
            if (mIsFirstAndLastColumnNeedSpace) {
                spaceWidth = 2 * mFirstAndLastColumnW;
            }

            itemWidth = (width - spaceWidth) / mSpanCount - 40;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemWidth;
    }

    /**
     * 判读是否是第一列
     *
     * @param parent
     * @param pos
     * @param spanCount
     * @param childCount
     * @return
     */
    private boolean isFirstColumn(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if (pos % spanCount == 0) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if (pos % spanCount == 0) {// 第一列
                    return true;
                }
            } else {

            }
        }
        return false;
    }

    /**
     * 判断是否是最后一列
     *
     * @param parent
     * @param pos
     * @param spanCount
     * @param childCount
     * @return
     */
    private boolean isLastColumn(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 0) {// 如果是最后一列，则不需要绘制右边
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0) {// 最后一列
                    return true;
                }
            } else {

            }
        }
        return false;
    }

    /**
     * 判读是否是最后一行
     *
     * @param parent
     * @param pos
     * @param spanCount
     * @param childCount
     * @return
     */
    private boolean isLastRow(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int lines = childCount % spanCount == 0 ? childCount / spanCount : childCount / spanCount + 1;
            return lines == pos / spanCount + 1;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {

        }
        return false;
    }

    /**
     * 判断是否是第一行
     *
     * @param parent
     * @param pos
     * @param spanCount
     * @param childCount
     * @return
     */
    private boolean isFirstRow(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos / spanCount + 1) == 1) {
                return true;
            } else {
                return false;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {

        }
        return false;
    }

    /**
     * 获取列数
     *
     * @param parent
     * @return
     */
    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }
}