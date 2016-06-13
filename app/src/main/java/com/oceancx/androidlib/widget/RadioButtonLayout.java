/*
 * Copyright (c) 2016. BiliBili Inc.
 */

package com.oceancx.androidlib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

/**
 * Created by oceancx on 16/6/2.
 * 子视图为RadioButton
 */
public class RadioButtonLayout extends FrameLayout implements CompoundButton.OnCheckedChangeListener {

    boolean LockCheck = false;

    public RadioButtonLayout(Context context) {
        super(context);
    }

    public RadioButtonLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadioButtonLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0)
            setChildrenListener(this);
    }

    private void setChildrenListener(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child instanceof CompoundButton) {
                ((CompoundButton) child).setOnCheckedChangeListener(this);
            } else if (child instanceof ViewGroup) {
                setChildrenListener((ViewGroup) child);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (LockCheck) return;
        LockCheck = true;
        setChildrenCheckStates(this, buttonView, isChecked);
        LockCheck = false;

    }

    private void setChildrenCheckStates(View view, CompoundButton buttonView, boolean isChecked) {
        if (view == buttonView && isChecked) {
            buttonView.setChecked(true);
        } else if (view instanceof CompoundButton) {
            ((CompoundButton) view).setChecked(false);
        } else if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
                setChildrenCheckStates(((ViewGroup) view).getChildAt(i), buttonView, isChecked);
        }
    }


}
