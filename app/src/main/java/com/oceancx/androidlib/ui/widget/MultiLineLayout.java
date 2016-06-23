package com.oceancx.androidlib.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * MultiLinearLayout
 * 与LinearLayout一行一个布局稍有不同
 * 刚开始horizonal布局
 * 支持Weight的多行线性布局
 * Child 从左往右依次布局 如果长度超过本身长度 就往下布局
 * 每行开始布局的时候 重新设置weightSum 然后支持孩子节点的weight
 * Created by oceancx on 2016/6/13.
 */
public class MultiLineLayout extends LinearLayout {
    /**
     * 用对孩子的weight进行划分
     * 然后 检测每个待测量的孩子节点 如果其Weight ！=0  那么就计算出specSize 用这个spec来测量孩子
     * 然后 还按照之前的布局方法进行布局即可
     */
    ArrayList<Integer> mMaxHeights;

    public MultiLineLayout(Context context) {
        this(context, null);
    }

    public MultiLineLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiLineLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMaxHeights = new ArrayList<>();
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

        int parentSize = MeasureSpec.getSize(widthMeasureSpec);
        int parentMode = MeasureSpec.getMode(widthMeasureSpec);
        /**
         * 求出这个变量 就能确定视图的高度了
         */
        int heightSize = 0;
        int mMaxHeightSeparatorIndex = 0;
        mMaxHeights.clear();
        float mWeightSum = getWeightSum();

        boolean determinedSpecWidth = false;
        int determinedWidth = getPaddingLeft() + getPaddingRight();
        int maxChildHeight = 0;
        int lineWidth = getPaddingLeft() + getPaddingRight();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                /**
                 * 根据weight 更改measureSpec
                 * 有weight的情况下 就按weight测试的布局
                 */
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int childWidthMeasureSpec = widthMeasureSpec;
                double ratio;
                if (lp.weight != 0 && mWeightSum > 0) {
                    ratio = lp.weight * 1.0f / mWeightSum;
                    childWidthMeasureSpec = MeasureSpec.makeMeasureSpec((int) Math.round(parentSize * ratio), parentMode);
                }

                measureChildWithMargins(child, childWidthMeasureSpec, 0, heightMeasureSpec, 0);
                /**
                 * 测量算法：
                 * 1. 确定行宽
                 * 2. 一个一个确定孩子宽度
                 * 3. 按宽度布局，超过行宽就换行
                 */
                int childSpace = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;

                if (!determinedSpecWidth) {
                    determinedWidth += childSpace;
                    if (determinedWidth >= parentSize) {
                        determinedWidth = parentSize;
                        determinedSpecWidth = true;
                    }
                }
                lineWidth += childSpace;
                if (determinedSpecWidth && lineWidth > parentSize) {
                    lineWidth = getPaddingLeft() + getPaddingRight();
                    heightSize += maxChildHeight;
                    int mMaxHeightsSize = mMaxHeights.size();
                    for (int j = mMaxHeightsSize; j < mMaxHeightSeparatorIndex; j++)
                        mMaxHeights.add(maxChildHeight);
                    maxChildHeight = 0;
                    i--;
                } else {
                    maxChildHeight = Math.max(maxChildHeight, lp.topMargin + lp.bottomMargin + child.getMeasuredHeight());
                    mMaxHeightSeparatorIndex++;
                }
            }
        }
        heightSize += maxChildHeight;
        int mMaxHeightsSize = mMaxHeights.size();
        for (int j = mMaxHeightsSize; j < mMaxHeightSeparatorIndex; j++)
            mMaxHeights.add(maxChildHeight);

        heightSize += getPaddingTop() + getPaddingBottom();

        if (determinedSpecWidth) {
            setMeasuredDimension(resolveSize(determinedWidth, widthMeasureSpec), resolveSize(heightSize, heightMeasureSpec));
        } else {
            setMeasuredDimension(resolveSize(lineWidth, widthMeasureSpec), resolveSize(heightSize, heightMeasureSpec));
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int startLeft, startTop;
        startLeft = getPaddingLeft();
        startTop = getPaddingTop();
        int maxHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int childSpace = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                if (startLeft + childSpace + getPaddingRight() > getMeasuredWidth()) {
                    startLeft = getPaddingLeft();
                    startTop += maxHeight;
                    maxHeight = getPaddingTop();
                }
                int layoutLeft, layoutTop;
                layoutLeft = startLeft + lp.leftMargin;
                layoutTop = startTop + mMaxHeights.get(i) - child.getMeasuredHeight() - lp.bottomMargin;
                child.layout(layoutLeft, layoutTop, layoutLeft + child.getMeasuredWidth(), layoutTop + child.getMeasuredHeight());
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
                startLeft += child.getMeasuredWidth();
            }
        }
    }


}
