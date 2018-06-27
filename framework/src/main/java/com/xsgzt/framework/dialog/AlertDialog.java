package com.xsgzt.framework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.ViewGroup;

import com.xsgzt.framework.R;


/**
 * Created by ty on 2017/6/12.
 * 万能dialog
 */

public class AlertDialog extends Dialog {
    private AlertController mAlert;

    public AlertDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mAlert = new AlertController(this, getWindow());
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param charSequence
     */
    public void setText(int viewId, CharSequence charSequence) {
        mAlert.setText(viewId, charSequence);
    }


    /**
     * 设置点击事件
     *
     * @param viewId
     * @param onClickListener
     */
    public void setOnClickLisenter(int viewId, View.OnClickListener onClickListener) {
        mAlert.setOnClickLisenter(viewId, onClickListener);
    }

    /**
     * 查找view
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        return mAlert.getView(viewId);
    }


    public static class Builder {
        private AlertController.AlertParams P;

        public Builder(Context context) {
            this(context, R.style.dialog);
        }

        public Builder(Context context, int styleThem) {
            P = new AlertController.AlertParams(context, styleThem);
        }

        //设置布局
        public Builder setContentView(View view) {
            P.mView = view;
            P.mLayoutId = 0;
            return this;
        }

        //设置布局
        public Builder setContentView(int layoutId) {
            P.mView = null;
            P.mLayoutId = layoutId;
            return this;
        }

        //设置其他文本
        public Builder setText(int viewId, CharSequence text) {
            P.textArray.put(viewId, text);
            return this;
        }

        //设置点击事件
        public Builder setOnClickLisenter(int viewId, View.OnClickListener onClickListener) {
            P.onClickArray.put(viewId, onClickListener);
            return this;
        }

        //设置全屏
        public Builder setFullWidth() {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        //设置宽度和高度
        public Builder setWidthAndHeight(int width, int height) {
            P.mWidth = width;
            P.mHeight = height;
            return this;
        }

        //设置默认动画
        public Builder setDefaultAnimations() {
            P.mAnimation = R.style.main_menu_animstyle;
            return this;
        }

        //设置动画
        public Builder setAnimations(int styleAnimation) {
            P.mAnimation = styleAnimation;
            return this;
        }

        //设置显示的位置
        public Builder setGravity(int gravity) {
            P.mGravity = gravity;
            return this;
        }

        /**
         * Sets whether the dialog is cancelable or not.  Default is true.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        /**
         * Sets the callback that will be called if the dialog is canceled.
         * <p>
         * <p>Even in a cancelable dialog, the dialog may be dismissed for reasons other than
         * being canceled or one of the supplied choices being selected.
         * If you are interested in listening for all cases where the dialog is dismissed
         * and not just when it is canceled, see
         * {@link #setOnDismissListener(OnDismissListener) setOnDismissListener}.</p>
         *
         * @return This Builder object to allow for chaining of calls to set methods
         * @see #setCancelable(boolean)
         * @see #setOnDismissListener(OnDismissListener)
         */
        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * Sets the callback that will be called if a key is dispatched to the dialog.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }


        /**
         * Creates an {@link android.app.AlertDialog} with the arguments supplied to this
         * builder.
         * <p>
         * Calling this method does not display the dialog. If no additional
         * processing is needed, {@link #show()} may be called instead to both
         * create and display the dialog.
         */
        public AlertDialog create() {
            // Context has already been wrapped with the appropriate theme.
            AlertDialog dialog = new AlertDialog(P.mContext, P.mStyleThem);
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }

            return dialog;
        }

        /**
         * Creates an {@link android.app.AlertDialog} with the arguments supplied to this
         * builder and immediately displays the dialog.
         * <p>
         * Calling this method is functionally identical to:
         * <pre>
         *     AlertDialog dialog = builder.create();
         *     dialog.show();
         * </pre>
         */
        public AlertDialog show() {
            final AlertDialog dialog = create();
            dialog.show();
            return dialog;
        }


    }
}
