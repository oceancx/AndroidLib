package com.oceancx.androidlib.main;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oceancx.androidlib.R;
import com.oceancx.androidlib.ui.widget.MultiLineLayout;

/**
 * Created by bilibili on 2016/6/15.
 */
public class MultiLineLayoutActivity extends AppCompatActivity {

    MultiLineLayout multiLineLayout;
    MultiLineLayout multiLineLayout2;

    String[] tags = {"国企", "金融", "IT", "大学生", "其他学生", "媒体", "餐饮", "零售", "自由职业", "教育", "医药", "汽车", "制造业", "摄影", "运动", "丽人", "美食", "旅游", "电影", "k歌", "温泉", "美容 spa", "摄影", "运动健身", "商品", "单身", "热恋", "结婚", "有宝宝", "国企", "金融", "IT", "大学生", "其他学生", "媒体", "餐饮", "零售", "自由职业", "教育", "医药", "汽车", "制造业", "摄影", "运动", "丽人", "美食", "旅游", "电影", "k歌", "温泉", "美容 spa", "摄影", "运动健身", "商品", "单身", "热恋", "结婚", "有宝宝", "国企", "金融", "IT", "大学生", "其他学生", "媒体", "餐饮", "零售", "自由职业", "教育", "医药", "汽车", "制造业", "摄影", "运动", "丽人", "美食", "旅游", "电影", "k歌", "温泉", "美容 spa", "摄影", "运动健身", "商品", "单身", "热恋", "结婚", "有宝宝"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multilinelayout_activity);

        multiLineLayout = (MultiLineLayout) findViewById(R.id.multi_line1);
        multiLineLayout2 = (MultiLineLayout) findViewById(R.id.multi_line2);

        multiLineLayout.setWeightSum((float) 6.1);
        multiLineLayout2.setWeightSum(5.9f);
        for (int i = 0; i < tags.length; i++) {
            TextView tag = new TextView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            lp.setMargins(10, 30, 10, 0);
            tag.setLayoutParams(lp);
            tag.setText(tags[i]);
            tag.setTextSize(14);
            tag.setGravity(Gravity.CENTER);
            tag.setBackgroundDrawable(getResources().getDrawable(R.drawable.tag_tv_bg2));
            tag.setTextColor((int) (Math.random() * 0xffffff) | 0xff000000);

            multiLineLayout.addView(tag);

            tag = new TextView(this);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            lp.setMargins(10, 30, 10, 0);
            tag.setLayoutParams(lp);
            tag.setText(tags[i]);
            tag.setTextSize(14);
            tag.setGravity(Gravity.CENTER);
            tag.setBackgroundDrawable(getResources().getDrawable(R.drawable.tag_tv_bg2));
            tag.setTextColor((int) (Math.random() * 0xffffff) | 0xff000000);

            multiLineLayout2.addView(tag);
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
