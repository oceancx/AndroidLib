/*
 * Copyright (c) 2016. BiliBili Inc.
 */

package com.oceancx.androidlib.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * Created by oceancx on 16/6/2.
 */
public class CompoundButtonLayout extends FrameLayout {
    CompoundButton cb;

    public CompoundButtonLayout(Context context) {
        super(context);
    }

    public CompoundButtonLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public CompoundButtonLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private CompoundButton findCompoundButton(View view) {
        ArrayList<View> views = new ArrayList<>();
        views.add(view);
        while (!views.isEmpty()) {
            View c = views.remove(0);
            if (c instanceof CompoundButton) {
                return (CompoundButton) c;
            } else if (c instanceof ViewGroup) {
                ViewGroup fa = (ViewGroup) c;
                for (int i = 0; i < fa.getChildCount(); i++) {
                    views.add(fa.getChildAt(i));
                }
            }
        }
        return null;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            cb = findCompoundButton(this);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (cb != null) {
            Rect bounds = new Rect(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + getMeasuredWidth() + getPaddingRight(), getPaddingTop() + getMeasuredHeight() + getPaddingBottom());
            TouchDelegate delegate = new TouchDelegate(bounds, cb);
            setTouchDelegate(delegate);
        }

    }


}
