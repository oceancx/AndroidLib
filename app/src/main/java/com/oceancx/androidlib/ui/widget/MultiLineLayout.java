package com.oceancx.androidlib.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.style.TtsSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.oceancx.androidlib.DebugLog;
import com.oceancx.androidlib.R;

/**
 * MultiLinearLayout
 * 与LinearLayout一行一个布局稍有不同
 * 刚开始horizonal布局
 * 支持Weight的多行线性布局
 * Child 从左往右依次布局 如果长度超过本身长度 就往下布局
 * 每行开始布局的时候 重新设置weightSum 然后支持孩子节点的weight
 * Created by bilibili on 2016/6/13.
 */
public class MultiLineLayout extends LinearLayout {
    /**
     * 用对孩子的weight进行划分
     */
    float mWeightSum;

    public MultiLineLayout(Context context) {
        this(context, null);
    }

    public MultiLineLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiLineLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MultiLineLayout, 0, defStyleAttr);
        mWeightSum = a.getFloat(R.styleable.MultiLineLayout_android_weightSum, -1f);
        DebugLog.e("mWeightSum:" + mWeightSum);
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
//            TextView tv = (TextView) getChildAt(i);
//            tv.setText(i + "");
//            tv.setGravity(Gravity.CENTER);
            //    tv.setBackgroundColor((int) ((0xff << 24) + Math.random() * 0xffffff));
        }
    }

    /**
     * 主要作用是确定整个视图的宽高
     * 具体的布局是放在onLayout中的
     * 依次读取每个节点的宽高 然后确定出来measureSpec
     * 最后setDimension
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 这个值代表着横向布局的最大空间
         */
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = 0;
        int lineWidth = 0;
        int totalHeight = 0;
        boolean readEnd = false;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                lineWidth += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                if (lineWidth > specWidth - getPaddingLeft() - getPaddingRight()) {
                    /**
                     * 开始新的一行
                     */
                    lineWidth = 0;
                    readEnd = true;
                    totalHeight += maxHeight;
                    DebugLog.e(i + "\tin Loop:totalHeight:" + totalHeight);
                    maxHeight = 0;
                    i--;
                }
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
                DebugLog.e(i + "\ttotalHeight:" + totalHeight);
            }
        }
        if (readEnd) {
            lineWidth = specWidth;
        }
        totalHeight += maxHeight;
        MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
        totalHeight += getPaddingTop() + getPaddingBottom() + params.topMargin + params.bottomMargin;
        totalHeight = Math.max(totalHeight, getSuggestedMinimumHeight());

        lineWidth += getPaddingLeft() + getPaddingRight();
        lineWidth = Math.max(lineWidth, getSuggestedMinimumWidth());
        DebugLog.e("heigth:" + totalHeight);
        DebugLog.e("heightSize:" + MeasureSpec.getSize(heightMeasureSpec) + " mode:" + MeasureSpec.getMode(heightMeasureSpec));
        setMeasuredDimension(resolveSize(lineWidth, widthMeasureSpec), resolveSize(totalHeight, heightMeasureSpec));
        DebugLog.e("parent width:" + getMeasuredWidth() + " parent height:" + getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        DebugLog.e("left:" + left + " top:" + top + " right:" + right + " bottom:" + bottom);
        int startLeft, startTop;
        startLeft = 0;
        startTop = 0;
        DebugLog.e("startLeft:" + getPaddingLeft() + " startTop:" + getPaddingTop());
        int maxHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (startLeft + child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin + getPaddingRight() > right - left) {
                    startLeft = 0;
                    startTop += maxHeight;
                    maxHeight = 0;
                }
                int layoutLeft, layoutTop;
                layoutLeft = startLeft + lp.leftMargin;
                layoutTop = startTop + lp.topMargin;
                child.layout(layoutLeft, layoutTop, layoutLeft + child.getMeasuredWidth(), layoutTop + child.getMeasuredHeight());
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
                startLeft += child.getMeasuredWidth();
            }
        }
    }


}