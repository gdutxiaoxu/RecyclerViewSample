package com.xj.recyclerviewdemo;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xj.library.utils.DisplayUtils;

import java.util.List;

public class LinearSampleActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_sample);
        int ll_root = R.id.ll_root;
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        int i = DisplayUtils.dp2px(this, 25);
        Rect rect = new Rect(0, i, i, 0);

        int j = DisplayUtils.dp2px(this, 15);
        Rect firstAndLastRect = new Rect(j, i, i, 0);
        HorizontalSpacesDecoration spacesDecoration = new HorizontalSpacesDecoration(rect, firstAndLastRect);
        mRecyclerView.addItemDecoration(spacesDecoration);
        List<Integer> integers = DataUtils.produceImageList(R.mipmap.ty1, 30);
        ImageAdapter imageAdapter = new ImageAdapter(this, integers);
        mRecyclerView.setAdapter(imageAdapter);
    }
}
