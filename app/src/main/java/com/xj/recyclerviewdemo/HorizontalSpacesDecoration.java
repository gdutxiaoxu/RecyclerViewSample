package com.xj.recyclerviewdemo;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 博客地址：http://blog.csdn.net/gdutxiaoxu
 *
 * @author xujun
 * @time 19-4-17
 */
public class HorizontalSpacesDecoration extends RecyclerView.ItemDecoration {

    private final Rect firstAndlastRect;
    private final Rect space;

    public HorizontalSpacesDecoration(Rect space, Rect firstAndlastRect) {
        this.space = space;
        this.firstAndlastRect = firstAndlastRect;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int childCount = parent.getAdapter().getItemCount();

        if (itemPosition == 0) {
            outRect.left = firstAndlastRect.left;
            outRect.right = firstAndlastRect.right;
            outRect.bottom = firstAndlastRect.bottom;
            outRect.top = firstAndlastRect.top;
            return;
        }

        if (itemPosition == childCount - 1) {
            outRect.left = 0;
            outRect.right = firstAndlastRect.left;
            outRect.bottom = firstAndlastRect.bottom;
            outRect.top = firstAndlastRect.top;
            return;
        }

        setOutRect(outRect, space);
    }

    private void setOutRect(Rect outRect, Rect rect) {
        outRect.left = rect.left;
        outRect.right = rect.right;
        outRect.bottom = rect.bottom;
        outRect.top = rect.top;
    }
}