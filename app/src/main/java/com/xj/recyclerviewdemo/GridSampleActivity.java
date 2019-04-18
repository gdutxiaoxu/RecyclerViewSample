package com.xj.recyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xj.library.utils.DisplayUtils;

import java.util.List;

/**
 * 博客地址：http://blog.csdn.net/gdutxiaoxu
 * @author xujun
 * @time 19-4-17
 */
public class GridSampleActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_sample);
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);

        mRecyclerView.setLayoutManager(layoutManager);
        int firstAndLastColumnW = DisplayUtils.dp2px(this, 15);
        int firstRowTopMargin = DisplayUtils.dp2px(this, 15);
        GridDividerItemDecoration gridDividerItemDecoration =
                new GridDividerItemDecoration(this, firstAndLastColumnW, firstRowTopMargin, true, true);
        gridDividerItemDecoration.setFirstRowTopMargin(firstRowTopMargin);
        gridDividerItemDecoration.setLastRowBottomMargin(firstRowTopMargin);
        mRecyclerView.addItemDecoration(gridDividerItemDecoration);
        List<Integer> imageList = DataUtils.produceImageList(30);
        ImageAdapter imageAdapter = new ImageAdapter(this, imageList);

        mRecyclerView.setAdapter(imageAdapter);
    }
}
