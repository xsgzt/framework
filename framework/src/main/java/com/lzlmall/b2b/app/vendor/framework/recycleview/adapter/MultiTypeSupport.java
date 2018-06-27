package com.lzlmall.b2b.app.vendor.framework.recycleview.adapter;

/**
 * Created by Administrator on 2018/4/26.
 * 多布局支持
 */

public interface MultiTypeSupport<DATA> {
    int getLayoutId(DATA item, int position);
}
