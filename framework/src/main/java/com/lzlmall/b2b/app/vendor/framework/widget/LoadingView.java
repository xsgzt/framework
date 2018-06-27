package com.lzlmall.b2b.app.vendor.framework.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

/**
 * Description:
 */

public class LoadingView extends RelativeLayout implements View.OnClickListener {

    private CircleView mLoadImageInner;
    private CircleView mLoadImageMiddle;
    private CircleView mLoadImageOuter;

    private static final int ANIMATION_DURATION = 300;
    private static final int DISTANCE = 60;

    // 颜色
    private String mColorFF8B4D = "#FF8B4D";
    private String mColor5AB8FF = "#5AB8FF";
    private String mColor5CE242 = "#5CE242";
    private OuterColor mCurrentOuterColor = OuterColor.MiddleColor_5CE242;

    private boolean isAnimator = true;

    // 当前的次数
    private int mCurrentCount = 1;

    private AccelerateDecelerateInterpolator mAccelerateInterpolator;

    private AnimatorSet mInnerAnimatorSet, mOutAnimatorSet;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLoadImageInner = new CircleView(context);
        addCircleImageView(mLoadImageInner, mColorFF8B4D);
        mLoadImageMiddle = new CircleView(context);
        addCircleImageView(mLoadImageMiddle, mColor5AB8FF);
        mLoadImageOuter = new CircleView(context);
        addCircleImageView(mLoadImageOuter, mColor5CE242);
        mAccelerateInterpolator = new AccelerateDecelerateInterpolator();

        initAnimatorSet();

        initShow();
        setOnClickListener(this);
    }

    /**
     * 初始化动画集合
     */
    private void initAnimatorSet() {
        mInnerAnimatorSet = new AnimatorSet();
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mLoadImageInner, "translationX", DISTANCE, 0).setDuration(ANIMATION_DURATION);
        animator1.setInterpolator(mAccelerateInterpolator);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mLoadImageMiddle, "translationX", -DISTANCE, 0).setDuration(ANIMATION_DURATION);
        animator2.setInterpolator(mAccelerateInterpolator);

        mInnerAnimatorSet.playTogether(animator1, animator2);

        mInnerAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isAnimator) return;
                outAnimation();

                if (mCurrentCount % 1 == 0) {
                    // 改变颜色
                    exchangeAnimationColor();
                } else {
                    // 交换颜色
                    String middleColor = mLoadImageMiddle.getBackgroundColor();
                    String innerColor = mLoadImageInner.getBackgroundColor();
                    mLoadImageMiddle.setBackgroundColor(innerColor);
                    mLoadImageInner.setBackgroundColor(middleColor);
                }

                mCurrentCount += 1;
            }
        });


        mOutAnimatorSet = new AnimatorSet();
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(mLoadImageInner, "translationX", 0, DISTANCE).setDuration(ANIMATION_DURATION);
        animator3.setInterpolator(mAccelerateInterpolator);

        ObjectAnimator animator4 = ObjectAnimator.ofFloat(mLoadImageMiddle, "translationX", 0, -DISTANCE).setDuration(ANIMATION_DURATION);
        animator4.setInterpolator(mAccelerateInterpolator);

        mOutAnimatorSet.playTogether(animator3, animator4);

        mOutAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                innerAnimation();
            }
        });

    }

    /**
     * 初始化显示直接分开
     */
    private void initShow() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mLoadImageInner, "translationX", 0, DISTANCE).setDuration(0);
        animator.start();

        animator = ObjectAnimator.ofFloat(mLoadImageMiddle, "translationX", 0, -DISTANCE).setDuration(0);
        animator.start();

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startLoadAnimation();
                    }
                }, 250);
            }
        });
    }

    /**
     * 开启加载动画
     */
    private void startLoadAnimation() {
        innerAnimation();
    }

    private void outAnimation() {
        mOutAnimatorSet.start();
    }

    private void innerAnimation() {
        mInnerAnimatorSet.start();
    }

    /**
     * 改变动画的颜色
     */
    private void exchangeAnimationColor() {
        switch (mCurrentOuterColor) {
            case MiddleColor_FF8B4D:
                mLoadImageInner.setBackgroundColor(Color.parseColor(mColor5CE242));
                mLoadImageMiddle.setBackgroundColor(Color.parseColor(mColorFF8B4D));
                mLoadImageOuter.setBackgroundColor(Color.parseColor(mColor5AB8FF));
                mCurrentOuterColor = OuterColor.MiddleColor_5AB8FF;
                break;
            case MiddleColor_5AB8FF:
                mLoadImageInner.setBackgroundColor(Color.parseColor(mColor5AB8FF));
                mLoadImageMiddle.setBackgroundColor(Color.parseColor(mColor5CE242));
                mLoadImageOuter.setBackgroundColor(Color.parseColor(mColorFF8B4D));
                mCurrentOuterColor = OuterColor.MiddleColor_5CE242;
                break;
            case MiddleColor_5CE242:
                mLoadImageInner.setBackgroundColor(Color.parseColor(mColorFF8B4D));
                mLoadImageMiddle.setBackgroundColor(Color.parseColor(mColor5AB8FF));
                mLoadImageOuter.setBackgroundColor(Color.parseColor(mColor5CE242));
                mCurrentOuterColor = OuterColor.MiddleColor_FF8B4D;
                break;
        }
    }

    @Override
    public void onClick(View v) {

    }


    public enum OuterColor {
        MiddleColor_FF8B4D, MiddleColor_5AB8FF, MiddleColor_5CE242
    }

    private void addCircleImageView(CircleView circleView, String color) {
        LayoutParams params = new LayoutParams(dip2px(10), dip2px(10));
        params.addRule(CENTER_IN_PARENT);
        circleView.setLayoutParams(params);
        circleView.setAlpha(0.8f);
        addView(circleView);
        circleView.setBackgroundColor(color);
    }


    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getContext().getResources().getDisplayMetrics());
    }

    @Override
    public void clearAnimation() {
        mLoadImageInner.clearAnimation();
        mLoadImageMiddle.clearAnimation();
        mOutAnimatorSet.cancel();
        mInnerAnimatorSet.cancel();
        isAnimator = false;
        super.clearAnimation();
    }


    @Override
    protected void onDetachedFromWindow() {
        //LogUtils.e("TAG", "onDetachedFromWindow");
        clearAnimation();
        super.onDetachedFromWindow();
    }
}
