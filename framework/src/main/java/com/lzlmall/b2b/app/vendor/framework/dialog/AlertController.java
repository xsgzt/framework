package com.lzlmall.b2b.app.vendor.framework.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by ty on 2017/6/12.
 * 具体的构造器
 */

public class AlertController {
    private AlertDialog mDialog;
    private Window mWindow;
    DialogViewHelper mViewHelper;

    public AlertController(AlertDialog dialog, Window window) {
        mDialog = dialog;
        mWindow = window;
    }

    public AlertDialog getDialog() {
        return mDialog;
    }

    public Window getWindow() {
        return mWindow;
    }

    public void setViewHelper(DialogViewHelper mViewHelper) {
        this.mViewHelper = mViewHelper;
    }

    /**
     * 设置文字
     *
     * @param viewId
     * @param charSequence
     */
    public void setText(int viewId, CharSequence charSequence) {
        mViewHelper.setText(viewId, charSequence);
    }

    /**
     * 设置点击事件
     *
     * @param viewId
     * @param onClickListener
     */
    public void setOnClickLisenter(int viewId, View.OnClickListener onClickListener) {
        mViewHelper.setOnClickLisenter(viewId, onClickListener);
    }

    /**
     * getview
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        return mViewHelper.getView(viewId);
    }

    //存储参数
    public static class AlertParams {
        public Context mContext;
        public int mStyleThem;
        //点击外面是否可以取消  默认是可以取消的
        public boolean mCancelable = true;
        //取消监听
        public DialogInterface.OnCancelListener mOnCancelListener;
        //消失监听
        public DialogInterface.OnDismissListener mOnDismissListener;
        //按键监听
        public DialogInterface.OnKeyListener mOnKeyListener;
        //设置布局
        public View mView;
        public int mLayoutId = 0;
        //缓存文本的集合
        public SparseArray<CharSequence> textArray = new SparseArray<>();
        //存储点击事件的结合
        public SparseArray<View.OnClickListener> onClickArray = new SparseArray<>();
        //设置显示的宽度
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        //设置显示的高度
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        //设置动画
        public int mAnimation;
        //默认显示在中间
        public int mGravity = Gravity.CENTER;


        public AlertParams(Context context, int styleThem) {
            mContext = context;
            mStyleThem = styleThem;

        }

        /**
         * 绑定数据
         *
         * @param mAlert
         */
        public void apply(AlertController mAlert) {
            //我们要把布局给创建出来
            DialogViewHelper viewHelper = null;
            if (mView != null) {
                viewHelper = new DialogViewHelper(mView);
            }
            if (mLayoutId != 0) {
                viewHelper = new DialogViewHelper(mContext, mLayoutId);
            }
            if (viewHelper == null) {
                throw new IllegalArgumentException("大哥设置布局呀！");
            }
            mAlert.setViewHelper(viewHelper);
            //设置布局
            mAlert.getDialog().setContentView(viewHelper.getContentView());

            //设置文本
            for (int i = 0; i < textArray.size(); i++) {
                mAlert.setText(textArray.keyAt(i), textArray.valueAt(i));
            }

            //设置点击事件
            for (int i = 0; i < onClickArray.size(); i++) {
                mAlert.setOnClickLisenter(onClickArray.keyAt(i), onClickArray.valueAt(i));
            }

            //设置其他
            Window window = mAlert.getWindow();
            window.setWindowAnimations(mAnimation);

            window.setGravity(mGravity);

            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            window.setAttributes(params);
            //设置自定义效果

        }
    }
}
