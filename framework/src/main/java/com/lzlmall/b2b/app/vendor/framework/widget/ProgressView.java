package com.lzlmall.b2b.app.vendor.framework.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.lzlmall.b2b.app.vendor.framework.R;

/**
 * Created by Administrator on 2017/11/15 0015.
 * 進度條
 */

public class ProgressView extends View {
    private int mBanckColor = Color.GRAY;
    private int mProgressColor = Color.parseColor("#FFED78");
    private int mProgressSize = 3;
    private long mProgressMax = 100;
    private int mWidth;
    private int mHeight;
    private Paint mBanckpaint;
    private Paint mProgresspaint;
    private long mProgress = 0;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        mBanckColor = typedArray.getColor(R.styleable.ProgressView_banckColor, mBanckColor);
        mProgressColor = typedArray.getColor(R.styleable.ProgressView_progressColor, mProgressColor);
        mProgressSize = typedArray.getDimensionPixelSize(R.styleable.ProgressView_Progresssize, mProgressSize);
        mProgressMax = typedArray.getInt(R.styleable.ProgressView_ProgressMax, (int) mProgressMax);
        typedArray.recycle();

        initPaint();
    }

    /**
     * 初始化畫筆
     */
    private void initPaint() {
        mBanckpaint = getPaint(mBanckColor);
        mProgresspaint = getPaint(mProgressColor);
    }

    private Paint getPaint(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(dp2px(mProgressSize));
        return paint;
    }

    private float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    //父布局決定 子布局的大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
//        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(mWidth, (int) dp2px(mProgressSize));
    }


    //繪製
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, 0, mWidth, 0, mBanckpaint);
        //长度是  mWidth   mProgressMax     mWidth/mProgressMax   *  mProgress  =  cureenWidth

        canvas.drawLine(0, 0, (float) ((mWidth * 1.0 / mProgressMax) * mProgress), 0, mProgresspaint);
    }

    /**
     * 设置当前当前进度
     */
    public void setprogress(long progress) {
        mProgress = progress;
        invalidate();
    }

    public void setProgressMax(long progressMax){
        mProgressMax = progressMax;
    }

}
