package com.xilaiya.customviewtest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by bellnett on 17/2/9.
 */

/**
 * Created by bellnett on 17/2/9.
 */
public class CustomViewGroup extends FrameLayout {

    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;
    private int viewsWidth;//所有子View的宽度之和（在该例子中仅代表宽度最大的那个子View的宽度）
    private int viewsHeight;//所有子View的高度之和
    private int viewGroupWidth = 0;//ViewGroup算上padding之后的宽度
    private int viewGroupHeight = 0;//ViewGroup算上padding之后的高度
    private int marginLeft;
    private int marginTop;
    private int marginRight;
    private int marginBottom;

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewsWidth = 0;
        viewsHeight = 0;
        marginLeft = 0;
        marginTop = 0;
        marginRight = 0;
        marginBottom = 0;
        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            viewsHeight += childView.getMeasuredHeight();
            viewsWidth = Math.max(viewsWidth, childView.getMeasuredWidth());
            marginLeft = Math.max(0, lp.leftMargin);//在本例中找出最大的左边距
            marginTop += lp.topMargin;//在本例中求出所有的上边距之和
            marginRight = Math.max(0, lp.rightMargin);//在本例中找出最大的右边距
            marginBottom += lp.bottomMargin;//在本例中求出所有的下边距之和
        }
        /* 用于处理ViewGroup的wrap_content情况 */
        viewGroupWidth = paddingLeft + viewsWidth + paddingRight + marginLeft + marginRight;
        viewGroupHeight = paddingTop + viewsHeight + paddingBottom + marginTop + marginBottom;
        setMeasuredDimension(measureWidth(widthMeasureSpec, viewGroupWidth), measureHeight
                (heightMeasureSpec, viewGroupHeight));
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        if (b) {
            int childCount = getChildCount();
            int mTop = paddingTop;
            for (int j = 0; j < childCount; j++) {
                View childView = getChildAt(j);
                MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
                int mLeft = paddingLeft + lp.leftMargin;
                mTop += lp.topMargin;
                childView.layout(mLeft, mTop, mLeft + childView.getMeasuredWidth(), mTop + childView.getMeasuredHeight());
                mTop += (childView.getMeasuredHeight() + lp.bottomMargin);
            }
        }
    }

    private int measureWidth(int measureSpec, int viewGroupWidth) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = specSize;
                break;
            case MeasureSpec.AT_MOST:
                /* 将剩余宽度和所有子View + padding的值进行比较，取小的作为ViewGroup的宽度 */
                result = Math.min(viewGroupWidth, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    private int measureHeight(int measureSpec, int viewGroupHeight) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = specSize;
                break;
            case MeasureSpec.AT_MOST:
                /* 将剩余高度和所有子View + padding的值进行比较，取小的作为ViewGroup的高度 */
                result = Math.min(viewGroupHeight, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public void setMyPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
    }
}
