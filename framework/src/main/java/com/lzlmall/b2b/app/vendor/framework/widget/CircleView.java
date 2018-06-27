package com.lzlmall.b2b.app.vendor.framework.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 */

public class CircleView extends View {
    private Paint mPaint;

    private String mCurrentColor;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mPaint);
    }

    @Override
    public void setBackgroundColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    public void setBackgroundColor(String color) {
        mCurrentColor = color;
        setBackgroundColor(Color.parseColor(mCurrentColor));
    }

    public String getBackgroundColor(){
        return mCurrentColor;
    }
}
