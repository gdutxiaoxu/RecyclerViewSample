package com.xj.recyclerviewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xj.library.utils.DisplayUtils;

import java.util.List;

public class DividerSampleActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divider_sample);
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(layoutManager);
        RecyclerViewDivider recyclerViewDivider = new RecyclerViewDivider(this);
        recyclerViewDivider.setHorizontaloffset(DisplayUtils.dp2px(this, 15), 0);
        recyclerViewDivider.setDividerColor(Color.GRAY);
        recyclerViewDivider.setDividerHeight(DisplayUtils.dp2px(this, 0.5f));
        mRecyclerView.addItemDecoration(recyclerViewDivider);
        List<String> chatList = DataUtils.produceList(30, "chat");
        TextAdapter textAdapter = new TextAdapter(this, chatList);
        mRecyclerView.setAdapter(textAdapter);
    }
}
