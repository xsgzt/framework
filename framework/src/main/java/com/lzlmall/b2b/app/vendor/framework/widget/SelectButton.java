package com.lzlmall.b2b.app.vendor.framework.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.lzlmall.b2b.app.vendor.framework.R;

/**
 * Created by Administrator on 2018/4/27.
 * 选择按钮的封装
 */

public class SelectButton extends Button {
    private SelectBtnClickListener mSelectBtnClickListener;
    private int mOpenResouceId = R.drawable.open;
    private int mCloseResouceId = R.drawable.close;
    private Bitmap mOpenBitmap, mCloseBitmap;
    private Status mCurretstatus = Status.CLOSE;
    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            change();
            if (mSelectBtnClickListener != null) {
                mSelectBtnClickListener.onClick();
            }
        }
    };

    public enum Status {
        CLOSE,
        OPEN;
    }

    public SelectButton(Context context) {
        this(context, null);
    }

    public SelectButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SelectButton);

        mOpenResouceId = typedArray.getResourceId(R.styleable.SelectButton_open_image, mOpenResouceId);
        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(mOpenResouceId);
        mOpenBitmap = drawable.getBitmap();


        mCloseResouceId = typedArray.getResourceId(R.styleable.SelectButton_close_image, mCloseResouceId);
        BitmapDrawable closedrawable = (BitmapDrawable) getResources().getDrawable(mCloseResouceId);
        mCloseBitmap = closedrawable.getBitmap();

        setOnClickListener(clickListener);
        typedArray.recycle();

    }
    //定义状态
    //提供设置状态


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = mOpenBitmap.getWidth();

        int height = mOpenBitmap.getHeight();

        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mCurretstatus) {
            case OPEN:
                canvas.drawBitmap(mOpenBitmap, 0, 0, null);
                break;
            case CLOSE:
                canvas.drawBitmap(mCloseBitmap, 0, 0, null);
                break;
        }


    }

    public void open() {
        mCurretstatus = Status.OPEN;
        invalidate();
    }

    public void close() {
        mCurretstatus = Status.CLOSE;
        invalidate();
    }

    public void change() {
        switch (mCurretstatus) {
            case OPEN:
                close();
                break;
            case CLOSE:
                open();
                break;
        }
    }

    public Status getCurretstatus() {
        return mCurretstatus;
    }


    public void setClickListener(SelectBtnClickListener selectBtnClickListener) {
        mSelectBtnClickListener = selectBtnClickListener;
    }

    public interface SelectBtnClickListener {

        void onClick();
    }

}
