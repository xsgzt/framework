package com.xsgzt.framework.bannerview;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by Administrator on 2018/4/25.
 */

public class BannerScroller extends Scroller {
    private int scrollerDuration = 2000;

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    //反射拿到


    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, scrollerDuration);




    }

    public void setScrollerDuration(int scrollerDuration) {
        this.scrollerDuration = scrollerDuration;
    }
}
