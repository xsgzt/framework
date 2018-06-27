package com.lzlmall.b2b.app.vendor.framework.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzlmall.b2b.app.vendor.framework.utils.StatusBarUtils;

/**
 * Created by Administrator on 2018/4/8.
 * <p>
 * 标题的基类
 */

public abstract class BaseNavigtionBar<PARAMS extends BaseNavigtionBar.Builder.BaseNavigtionParams> implements INavigationBar {
    public PARAMS mParams;
    private View navigationView;

    public BaseNavigtionBar(PARAMS params) {
        mParams = params;
        //创建布局绑定布局
        createAndBindData();
    }

    /**
     * 创建布局绑定布局
     */
    protected void createAndBindData() {

        if (mParams.mViewGroup == null) {
            ViewGroup viewGroup = (ViewGroup) ((Activity) mParams.mContext).getWindow().getDecorView();
            mParams.mViewGroup = viewGroup;
        }
        navigationView = LayoutInflater.from(mParams.mContext).inflate(bindLayoutId(), mParams.mViewGroup, false);
//        navigationView.setPadding(0, StatusBarUtils.getStatusBarHeight(mParams.mContext),0,0);
//        navigationView.setPadding(0, 200,0,0);
        ViewGroup childAt = (ViewGroup) mParams.mViewGroup.getChildAt(0);
        childAt.addView(navigationView, 0);
        //绑定数据
        apply();
    }


    /**
     * 查找控件
     */
    protected <T extends View> T viewById(int viewID) {
        return navigationView.findViewById(viewID);
    }

    /**
     * 设置文字
     */
    public void setText(int viewId, CharSequence text) {
        if (text == null) {
            return;
        }
        TextView textView = viewById(viewId);
        textView.setVisibility(View.VISIBLE);
        textView.setText(text);
    }

    /**
     * 设置图片
     */
    public void setImage(int viewId, int imageResouce) {
        if (imageResouce == 0) {
            return;
        }

        ImageView imageView = viewById(viewId);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(imageResouce);
    }


    public void setOnClick(int viewId, View.OnClickListener listener) {
        if (listener == null) {
            return;
        }
        viewById(viewId).setOnClickListener(listener);

    }

    /**
     * SHE
     */
    public void setViewVisibility(int viewId, int visibility) {
        if (visibility == 0x000013) {
            return;
        }
        View view = viewById(viewId);
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

    /**
     * 利用构建者模式去构建标题
     */
    public static abstract class Builder {
        BaseNavigtionParams p;


        public Builder(Context context, ViewGroup viewGroup) {
            p = new BaseNavigtionParams(context, viewGroup);
        }

        /**
         * 构建头部实例 具体交给子类去实现
         *
         * @return
         */
        public abstract BaseNavigtionBar build();

        /**
         * 参数存储
         */
        public static class BaseNavigtionParams {
            public Context mContext;
            public ViewGroup mViewGroup;

            public BaseNavigtionParams(Context context, ViewGroup viewGroup) {
                mContext = context;
                mViewGroup = viewGroup;
            }
        }


    }


}
