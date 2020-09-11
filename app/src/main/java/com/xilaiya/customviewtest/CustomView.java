package com.xilaiya.customviewtest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bellnett on 17/2/9.
 */
public class CustomView extends View {

    private Paint mPaint;
    private int color;
    private int width;
    private int height;

    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;


    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
        color = array.getColor(R.styleable.CustomView_backgroundColor, Color.BLUE);
        array.recycle();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = measureWidth(widthMeasureSpec);
        height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
/*        super.onDraw(canvas);
        Rect rect = new Rect();
        rect.left = 0;
        rect.top = 0;
        rect.right = width;
        rect.bottom = height;
        canvas.drawRect(rect, mPaint);*/

        super.onDraw(canvas);
        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();
        Rect rect = new Rect();
        rect.left = 0 + paddingLeft;
        rect.top = 0 + paddingTop;
        rect.right = width - paddingRight;
        rect.bottom = height - paddingBottom;
        canvas.drawRect(rect, mPaint);
    }

    private int measureWidth(int measureSpec) {
        int result = 200;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = specSize;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(result, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = 200;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = specSize;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(result, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

}