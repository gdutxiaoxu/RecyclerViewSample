package com.xj.recyclerviewdemo;

import android.content.Context;
import android.widget.TextView;

import com.xj.library.recyclerView.CommonAdapter;
import com.xj.library.recyclerView.base.ViewHolder;

import java.util.List;

/**
 * Created by jun xu on 19-4-10.
 */
public class TextAdapter extends CommonAdapter<String> {

    public TextAdapter(Context context, List<String> datas) {
        super(context, R.layout.item_divider, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        TextView tvMsg = holder.getView(R.id.tv_msg);
        tvMsg.setText(s);

    }
}
