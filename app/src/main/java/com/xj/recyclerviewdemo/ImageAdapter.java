package com.xj.recyclerviewdemo;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xj.library.recyclerView.CommonAdapter;
import com.xj.library.recyclerView.base.ViewHolder;

import java.util.List;

/**
 * 博客地址：http://blog.csdn.net/gdutxiaoxu
 *
 * @author xujun
 * @time 19-4-17
 */
public class ImageAdapter extends CommonAdapter<Integer> {
    public ImageAdapter(Context context, List<Integer> datas) {
        super(context, R.layout.item_image, datas);
    }

    @Override
    protected void convert(ViewHolder holder, @DrawableRes Integer integer, int position) {
        ImageView iv = holder.getView(R.id.iv);
        Glide.with(mContext).load(integer).into(iv);

    }
}
