package com.oceancx.androidlib.main;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oceancx.androidlib.R;
import com.oceancx.androidlib.ui.widget.MultiLineLayout;

/**
 * Created by bilibili on 2016/6/15.
 */
public class ViewTestActivity extends AppCompatActivity {

    LinearLayout multiLineLayout;

    View view;

    int groupAnimCount = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_test);
        multiLineLayout = (LinearLayout) findViewById(R.id.multi_line);

        for (int i = 0; i < multiLineLayout.getChildCount(); i++) {
            multiLineLayout.getChildAt(i).setTag(i);
            multiLineLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    int anipos = pos;
                    for (; pos < multiLineLayout.getChildCount(); pos++) {
                        ObjectAnimator obj = ObjectAnimator.ofFloat(multiLineLayout.getChildAt(pos), "rotationX", 0, 90, -90, 0);
                        obj.setDuration(300);
                        obj.setStartDelay((pos - anipos) * 100);
                        obj.setRepeatCount(0);
                        obj.setRepeatMode(ValueAnimator.RESTART);
                        obj.start();
                    }
                }
            });
        }
//        view = findViewById(R.id.fg);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "clickc", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//

    }
}
