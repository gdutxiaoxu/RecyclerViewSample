package com.xj.recyclerviewdemo.grid;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xj.library.recyclerView.CommonAdapter;
import com.xj.library.recyclerView.base.ViewHolder;
import com.xj.recyclerviewdemo.R;

import java.util.List;

/**
 * 博客地址：http://blog.csdn.net/gdutxiaoxu
 *
 * @author xujun
 * @time 19-4-17
 */
public class ImageAdapter extends CommonAdapter<Integer> {

    private static final String TAG = "ImageAdapter";

    private int mWidth;

    public ImageAdapter(Context context, List<Integer> datas) {
        super(context, R.layout.item_image, datas);
    }

    public void setWidth(int width) {
        mWidth = width;
    }


    @Override
    protected void onViewHolderCreate(ViewGroup parent, ViewHolder holder, int viewType) {
        super.onViewHolderCreate(parent, holder, viewType);
        View convertView = holder.getConvertView();
        Log.i(TAG, "onViewHolderCreate: mWidth =" + mWidth);
        if (mWidth > 0) {
            convertView.getLayoutParams().width = mWidth;
        }


    }

    @Override
    protected void convert(ViewHolder holder, @DrawableRes Integer integer, int position) {
        ImageView iv = holder.getView(R.id.iv);
        Glide.with(mContext).load(integer).into(iv);

    }
}
