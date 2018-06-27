package com.lzlmall.b2b.app.vendor.framework.bannerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2018/4/25.
 */

public class BannerViewPage extends ViewPager {
    // 2.实现自动轮播 - 发送消息的msgWhat
    private final int SCROLL_MSG = 0x0011;

    // 2.实现自动轮播 - 页面切换间隔时间
    private int mCutDownTime = 3500;

    private BannerScroller mScroller;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setCurrentItem(getCurrentItem() + 1);
            startRoll();
        }
    };

    private BannerAdapter mAdapter;
    private GestureDetector mGestureDetector;
    private BannerViewPageAdapter bannerViewPageAdapter;

    public BannerViewPage(Context context) {
        this(context, null);
    }

    public BannerViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        //反射拿到scroller
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            mScroller = new BannerScroller(context);
            field.set(this, mScroller);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //飞速滑动
//滚动
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                //飞速滑动
                if (velocityX > 0) {
                    setCurrentItem(getCurrentItem() - 1);
                } else {
                    setCurrentItem(getCurrentItem() + 1);
                }

                return true;
            }


        });

    }

    /**
     * 3.设置切换页面动画持续的时间
     */
    public void setScrollerDuration(int scrollerDuration) {
        mScroller.setScrollerDuration(scrollerDuration);
    }


    public void setAdapter(BannerAdapter adapter) {
        this.mAdapter = adapter;
        bannerViewPageAdapter = new BannerViewPageAdapter();
        setAdapter(bannerViewPageAdapter);
        int value = Integer.MAX_VALUE / 2;
        for (int i = 0; i <= mAdapter.getCount(); i++) {
            value = value + i;
            try {
                if (value % mAdapter.getCount() == 0) {//兼容无线轮播 getCount() != 0
                    break;
                }
            } catch (Exception e) {

            }

        }
        setCurrentItem(value);
    }

    //实现无线轮播
    public void startRoll() {
        if (mAdapter == null) {
            new Throwable("大官人 先设置适配器");
        }
        // 清除消息
        mHandler.removeMessages(SCROLL_MSG);

        mHandler.sendEmptyMessageDelayed(SCROLL_MSG, mCutDownTime);
        Log.e("startRoll: ", "startRoll");
    }

    //停止轮播
    public void stopRoll() {
        if (mAdapter == null) {
            new Throwable("大官人 先设置适配器");
        }
        // 清除消息
        mHandler.removeMessages(SCROLL_MSG);
    }

    /**
     * BannerViewPageAdapter 实现默认的adpter
     */
    public class BannerViewPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            //实现无线密码
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //这个方法要代理出去
            try {
                View bannerView = mAdapter.getView(container, position % mAdapter.getCount());
                //取余数
                container.addView(bannerView);
                return bannerView;
            } catch (Exception e) {

            }

            return null;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }


    /**
     * 刷新adapter
     */
    public void notifyDataSetChanged() {
        bannerViewPageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(SCROLL_MSG);
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }

    private float downX, downY;
    private float dx;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (mGestureDetector.onTouchEvent(event)) {
//            return true;
//        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                stopRoll();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                if (Math.abs(downY) < Math.abs(downX)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }

                dx = moveX - downX;
                super.onTouchEvent(event);
                Log.e("onTouchEvent: ", "dx = " + dx + "" + "Math.abs(downY) = " + Math.abs(downY) + "Math.abs(downX)" + Math.abs(downX));

                break;
            case MotionEvent.ACTION_UP:

                if (dx < -getWidth() / 2) {
                    setCurrentItem(getCurrentItem() + 1);
                }
                if (dx > -getWidth() / 2 && dx < getWidth() / 2) {
                    setCurrentItem(getCurrentItem());
                }
                if (dx > getWidth() / 2) {
                    setCurrentItem(getCurrentItem() - 1);
                }
                startRoll();
                super.onTouchEvent(event);
                break;

        }

        return true;
    }

}
