package com.lzlmall.b2b.app.vendor.framework.recycleview.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 2018/4/26.
 */

public class GlideImageLoader extends ImageLoader {

    public GlideImageLoader(String url) {
        super(url);
    }

    @Override
    public void load(ImageView imageView) {
        Glide.with(imageView.getContext()).load(mImageUrl).into(imageView);
    }
}
