/*
 * Copyright (c) 2016. BiliBili Inc.
 */

package com.oceancx.androidlib.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by oceancx on 16/5/25.
 */
public class ViewWrapper extends FrameLayout{
    View mChild;

    public ViewWrapper(Context context) {
        super(context);
    }

    public ViewWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 1) {
            mChild= getChildAt(0);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mChild != null) {
            Rect bounds = new Rect(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + getMeasuredWidth() + getPaddingRight(), getPaddingTop() + getMeasuredHeight() + getPaddingBottom());
            TouchDelegate delegate = new TouchDelegate(bounds, mChild);
            setTouchDelegate(delegate);
        }
    }
}