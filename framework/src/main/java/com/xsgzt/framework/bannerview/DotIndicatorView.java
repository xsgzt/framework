package com.xsgzt.framework.bannerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/4/25.
 */

public class DotIndicatorView extends View {
    private Drawable drawable;

    public DotIndicatorView(Context context) {
        this(context, null);
    }

    public DotIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (drawable != null) {
            /*drawable.setBounds(0,0,getMeasuredWidth(),getMeasuredHeight()); drawable.draw(canvas);*/
            // 7.把指示器变成圆形
            // 画圆
            Bitmap bitmap = drawableToBitmap(drawable);
            // 把Bitmap变为圆的
            Bitmap circleBitmap = getCircleBitmap(bitmap);
            // 把圆形的Bitmap绘制到画布上
            canvas.drawBitmap(circleBitmap, 0, 0, null);
        }
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        // 如果是BitmapDrawable类型
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        // 其他类型 ColorDrawable // 创建一个什么也没有的bitmap
        Bitmap outBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        // 创建一个画布
        Canvas canvas = new Canvas(outBitmap);
        // 把drawable化到Bitmap上
        drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        drawable.draw(canvas);
        return outBitmap;
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        // 创建一个Bitmap
        Bitmap circleBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circleBitmap);
        Paint paint = new Paint();
        // 设置抗锯齿
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        // 设置仿抖动
        paint.setDither(true);
        // 在画布上面画个圆
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredWidth() / 2, paint);
        // 取圆和Bitmap矩形的交集
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 再把原来的Bitmap绘制到新的圆上面
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return circleBitmap;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        // 重新绘制View
        invalidate();
    }




}



