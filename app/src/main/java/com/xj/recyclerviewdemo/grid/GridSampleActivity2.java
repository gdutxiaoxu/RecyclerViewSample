package com.xj.recyclerviewdemo.grid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xj.library.utils.DisplayUtils;
import com.xj.recyclerviewdemo.DataUtils;
import com.xj.recyclerviewdemo.GridDividerItemDecoration;
import com.xj.recyclerviewdemo.R;

import java.util.List;

public class GridSampleActivity2 extends AppCompatActivity {


    public static final int SPAN_COUNT = 3;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_sample);
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, SPAN_COUNT);

        mRecyclerView.setLayoutManager(layoutManager);
        int firstAndLastColumnW = DisplayUtils.dp2px(this, 15);
        int firstRowTopMargin = DisplayUtils.dp2px(this, 15);
        GridDividerItemDecoration gridDividerItemDecoration =
                new GridDividerItemDecoration(this, firstAndLastColumnW, firstRowTopMargin, firstRowTopMargin);
        gridDividerItemDecoration.setFirstRowTopMargin(firstRowTopMargin);
        gridDividerItemDecoration.setLastRowBottomMargin(firstRowTopMargin);
        int itemWidth = (DisplayUtils.getScreenWidth(this)
                - DisplayUtils.dp2px(this, 20) * (SPAN_COUNT - 1) - firstAndLastColumnW * 2) / SPAN_COUNT;

        mRecyclerView.addItemDecoration(gridDividerItemDecoration);
        List<Integer> imageList = DataUtils.produceImageList(30);
        ImageAdapter imageAdapter = new ImageAdapter(this, imageList);
        imageAdapter.setWidth(itemWidth);
        mRecyclerView.setAdapter(imageAdapter);
    }
}
