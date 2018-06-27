package com.lzlmall.b2b.app.vendor.framework.bannerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzlmall.b2b.app.vendor.framework.R;

/**
 * Created by Administrator on 2018/4/25.
 */

public class BannerView extends RelativeLayout {
    private Context mContext;
    private BannerAdapter mAdapter;
    private BannerViewPage mBannerVp;
    private TextView mBannerDescTv;
    private LinearLayout mDotContainerView;
    private int mDotSize = 8;
    private int mDotDistance = 2;
    private int mDotGravity = Gravity.RIGHT;
    private Drawable mIndicatorFocusDrawable;
    private Drawable mIndicatorNormalDrawable;
    private int mBottomColor = Color.parseColor("#22222222");
    private float mWidthProportion = 2;
    private float mHeightProportion = 1;
    private int mCurrentPosition = 0;


    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //包含两个 模块  指示器  bannerviewpage   填充BannerView  配置指示
        // 把布局加载到这个View里面
        mContext = context;
        inflate(context, R.layout.ui_banner_layout, this);
        initAttribute(attrs);
        intView();
    }

    private void initAttribute(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);
        // 获取点的位置
        mDotGravity = array.getInt(R.styleable.BannerView_dotGravity, mDotGravity);
        // 获取点的颜色（默认、选中）
        mIndicatorFocusDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorFocus);
        if (mIndicatorFocusDrawable == null) {
            // 如果在布局文件中没有配置点的颜色  有一个默认值
            mIndicatorFocusDrawable = new ColorDrawable(Color.RED);
        }
        mIndicatorNormalDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorNormal);
        if (mIndicatorNormalDrawable == null) {
            // 如果在布局文件中没有配置点的颜色  有一个默认值
            mIndicatorNormalDrawable = new ColorDrawable(Color.WHITE);
        }
        // 获取点的大小和距离20.
        mDotSize = (int) array.getDimension(R.styleable.BannerView_dotSize, dip2px(mDotSize));
        mDotDistance = (int) array.getDimension(R.styleable.BannerView_dotDistance, dip2px(mDotDistance));
        // 获取底部的颜色24.
        mBottomColor = array.getColor(R.styleable.BannerView_bottomColor, mBottomColor);
        // 获取宽高比例27.
        mWidthProportion = array.getFloat(R.styleable.BannerView_withProportion, mWidthProportion);
        mHeightProportion = array.getFloat(R.styleable.BannerView_heightProportion, mHeightProportion);
        array.recycle();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 8.自适应高度 动态指定高度
        if (mHeightProportion == 0 || mWidthProportion == 0) {
            return;
        }
        // 动态指定宽高  计算高度
        int width = getMeasuredWidth();
        // 计算高度
        int height = (int) (width * mHeightProportion / mWidthProportion);
        // 指定宽高
        getLayoutParams().height = height;
        setMeasuredDimension(width, height);
    }

    private void intView() {
        mBannerVp = (BannerViewPage) findViewById(R.id.banner_vp);
        mBannerDescTv = (TextView) findViewById(R.id.banner_desc_tv);
        mDotContainerView = (LinearLayout) findViewById(R.id.dot_container);
        RelativeLayout Reindcation = (RelativeLayout) findViewById(R.id.re_indcation);
        Reindcation.setBackgroundColor(mBottomColor);
    }

    /**
     * 4.设置适配器
     */

    public void setAdapter(BannerAdapter adapter) {
        mAdapter = adapter;
        initDotIndicator();
        mBannerVp.setAdapter(adapter);
        mBannerVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageSelect(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // 6.初始化的时候获取第一条的描述
        String firstDesc = mAdapter.getBannerDesc(0);
        mBannerDescTv.setText(firstDesc);
    }

    private void pageSelect(int position) {
        try {
            // 6.1 把之前亮着的点 设置为默认
            DotIndicatorView oldIndicatorView = (DotIndicatorView)
                    mDotContainerView.getChildAt(mCurrentPosition);
            oldIndicatorView.setDrawable(mIndicatorNormalDrawable);


            // 6.2 把当前位置的点 点亮  position 0 --> 2的31次方
            mCurrentPosition = position % mAdapter.getCount();
            DotIndicatorView currentIndicatorView = (DotIndicatorView)
                    mDotContainerView.getChildAt(mCurrentPosition);
            currentIndicatorView.setDrawable(mIndicatorFocusDrawable);

            // 6.3设置广告描述
            String bannerDesc = mAdapter.getBannerDesc(mCurrentPosition);
            mBannerDescTv.setText(bannerDesc);
        }catch (Exception e) {

        }

    }

    /**
     * 4.开始滚动
     */
    public void startRoll() {
        mBannerVp.startRoll();
    }

    public void stopRoll() {
        mBannerVp.stopRoll();
    }
    /**
     * 刷新adapter
     */
    public void notifyDataSetChanged() {
        mBannerVp.notifyDataSetChanged();
    }

    //2.5． 初始化点的指示器
    public void initDotIndicator() {
        // 让点的位置在右边
        mDotContainerView.setGravity(mDotGravity);

        for (int i = 0; i < mAdapter.getCount(); i++) {
            // 不断的往点的指示器添加圆点
            DotIndicatorView indicatorView = new DotIndicatorView(mContext);
            // 设置大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dip2px(mDotSize), dip2px(mDotSize));
            // 设置左右间距
            params.leftMargin = params.rightMargin = dip2px(mDotDistance);
            indicatorView.setLayoutParams(params);
            if (i == 0) {
                // 选中位置
                indicatorView.setDrawable(mIndicatorFocusDrawable);
            } else {
                // 未选中的
                indicatorView.setDrawable(mIndicatorNormalDrawable);
            }


            mDotContainerView.addView(indicatorView);
        }
    }

    /* 5.把dip转成px */
    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }





}
