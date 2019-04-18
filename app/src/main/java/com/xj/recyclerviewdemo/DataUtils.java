package com.xj.recyclerviewdemo;

import android.support.annotation.DrawableRes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jun xu on 19-4-10.
 */
public class DataUtils {

    public static List<String> produceList(int count, String key) {
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            datas.add(key + ":Fragment item :" + i);
        }
        return datas;
    }

    public static List<Integer> produceImageList(@DrawableRes int imageRes, int count) {
        ArrayList<Integer> datas = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            datas.add(imageRes);
        }
        return datas;
    }


    public static List<Integer> produceImageList(int count) {
        ArrayList<Integer> datas = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            datas.add(R.mipmap.ty1);
        }
        return datas;
    }
}
