package com.xj.recyclerviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_0:
                startActivity(new Intent(this, DividerSampleActivity.class));
                break;
            case R.id.btn_1:
                startActivity(new Intent(this, GridSampleActivity.class));
                break;
            case R.id.btn_2:
                startActivity(new Intent(this, LinearSampleActivity.class));
                break;
            default:
                break;
        }
    }
}
