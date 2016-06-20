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
 * Created by oceancx on 2016/6/13.
 */
public class MultiLineLayout extends LinearLayout {
    /**
     * 用对孩子的weight进行划分
     * 每行的WeightSum  首先 默认每行的weightSum=1
     * 然后 检测每个待测量的孩子节点 如果其Weight ！=0  那么就计算出specSize 用这个spec来测量孩子
     * 然后 还按照之前的布局方法进行布局即可
     * 布局之后weightSum-= childWeight
     * 如果weight>weightSum ，进行换行
     * 换行后weighSum复原
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
        mWeightSum = a.getFloat(R.styleable.MultiLineLayout_android_weightSum, 1f);
        DebugLog.e("mWeightSum:" + mWeightSum);
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            LayoutParams params = (LayoutParams) getChildAt(i).getLayoutParams();
            DebugLog.e("weight:" + params.weight);
            if (params.weight > mWeightSum) {
                mWeightSum = params.weight;
            }
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

        int parentSize = MeasureSpec.getSize(widthMeasureSpec);
        int parentMode = MeasureSpec.getMode(widthMeasureSpec);

        /**
         * 求出这个变量 就能确定视图的高度了
         */
        int heightSize = 0;
        int parentMaxWidth = 0;

        boolean determinedSpecWidth = false;
        int determinedWidth = getPaddingLeft() + getPaddingRight();
        int maxChildHeight = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childWidth = 0;
            int childPadding = 0;
            int childSpace = 0;
            if (child.getVisibility() != GONE) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                /**
                 * 测量算法：
                 * 1. 确定行宽
                 * 2. 一个一个确定孩子宽度
                 * 3. 按宽度布局，超过行宽就换行
                 */
                childWidth = child.getMeasuredWidth();
                childPadding = lp.leftMargin + lp.rightMargin;
                childSpace = childWidth + childPadding;

                if (!determinedSpecWidth) {
                    determinedWidth += childSpace;
                    if (determinedWidth >= parentSize) {
                        determinedWidth = parentSize;
                        determinedSpecWidth = true;
                    }
                }

                maxChildHeight += lp.topMargin + lp.bottomMargin + child.getMeasuredHeight();
                if (determinedSpecWidth) {

                }
            }
        }


        /**
         * 这个值代表着横向布局的最大空间
         */
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = 0;
        int lineWidth = 0;
        int totalHeight = 0;
        boolean readEnd = false;
        float lineWeight = mWeightSum;
        boolean nextLineByWeight = false;
        int measureSpecByWeight;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                /**
                 * 设置
                 */
                nextLineByWeight = false;
                measureSpecByWeight = widthMeasureSpec;
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.weight > 0 && lineWeight >= lp.weight) {
                    float specSize = specWidth * lp.weight * 1.0f / mWeightSum;
                    measureSpecByWeight = MeasureSpec.makeMeasureSpec((int) specSize, MeasureSpec.getMode(widthMeasureSpec));
                    lineWeight -= lp.weight;
                } else if (lp.weight > 0) {
                    lineWeight = mWeightSum;
                    float specSize = specWidth * lp.weight * 1.0f / mWeightSum;
                    measureSpecByWeight = MeasureSpec.makeMeasureSpec((int) specSize, MeasureSpec.getMode(widthMeasureSpec));
                    lineWeight -= lp.weight;
                    nextLineByWeight = true;
                }
                measureChildWithMargins(child, measureSpecByWeight, 0, heightMeasureSpec, 0);
                if (!nextLineByWeight)
                    lineWidth += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                if (lineWidth > specWidth - getPaddingLeft() - getPaddingRight() || nextLineByWeight) {
                    /**
                     * 开始新的一行布局
                     */
                    lineWidth = 0;
                    readEnd = true;
                    totalHeight += maxHeight;
                    maxHeight = 0;
                    i--;
                }
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
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
        setMeasuredDimension(resolveSize(lineWidth, widthMeasureSpec), resolveSize(totalHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int startLeft, startTop;
        startLeft = 0;
        startTop = 0;
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
