package com.xsgzt.framework.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * Created by Administrator on 2017/11/15 0015.
 * 帧动画ImageView
 */

public class FrameAnimationImageView extends ImageView {
    private int drawbleResouce;
    private boolean start = true;
    private AnimationDrawable animation;

    public FrameAnimationImageView(Context context) {
        this(context, null);
    }

    public FrameAnimationImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FrameAnimationImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setFrameAnimationResouce(int drawbleResouce) {
        this.drawbleResouce = drawbleResouce;
    }

    /**
     * 开启动画
     */
    public void startAnimation() {
        setImageResource(drawbleResouce);
        animation = (AnimationDrawable) getDrawable();
        animation.start();


    }

    /**
     * 停止动画
     */
    public void stopAnimation() {
        animation.stop();
        start = false;
    }


}
