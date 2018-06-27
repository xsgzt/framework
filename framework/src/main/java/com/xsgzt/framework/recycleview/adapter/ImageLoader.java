package com.xsgzt.framework.recycleview.adapter;

import android.widget.ImageView;

/**
 * Created by Administrator on 2018/4/26.
 */

public abstract class ImageLoader {
    protected String mImageUrl;
    public ImageLoader(String url) {
        mImageUrl = url;
    }

    public abstract void load(ImageView imageView);
}
