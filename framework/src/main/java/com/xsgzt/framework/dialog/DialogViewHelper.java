package com.xsgzt.framework.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by ty on 2017/6/12
 * 操作界面的辅助类.
 */

public class DialogViewHelper {
    private Context mContext;
    private View contentView;
    private SparseArray<WeakReference<View>> mViewReferences;

    public DialogViewHelper(Context context, int layoutId) {
        mContext = context;
        contentView = LayoutInflater.from(context).inflate(layoutId, null);
        mViewReferences = new SparseArray<>();
    }

    public DialogViewHelper(View view) {
        contentView = view;
        mViewReferences = new SparseArray<>();
    }

    public View getContentView() {
        return contentView;
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param charSequence
     */
    public void setText(int viewId, CharSequence charSequence) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setText(charSequence);
        }

    }


    /**
     * 设置点击事件
     *
     * @param viewId
     * @param onClickListener
     */
    public void setOnClickLisenter(int viewId, View.OnClickListener onClickListener) {
        View view = getView(viewId);
        view.setOnClickListener(onClickListener);
    }

    /**
     * 查找view
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        WeakReference<View> viewWeakReference = mViewReferences.get(viewId);
        View view = null;
        if (viewWeakReference == null) {
            view = contentView.findViewById(viewId);
            if (view != null) {
                mViewReferences.put(viewId, new WeakReference<View>(view));
            }

        } else {
            view = viewWeakReference.get();
        }

        return (T) view;
    }
}
