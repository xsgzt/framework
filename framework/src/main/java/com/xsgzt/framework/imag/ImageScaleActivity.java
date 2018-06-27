package com.xsgzt.framework.imag;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.chrisbanes.photoview.PhotoView;
import com.xsgzt.framework.R;
import com.xsgzt.framework.utils.FrameworkConstant;

/**
 * Created by Administrator on 2018/6/8.
 */

public class ImageScaleActivity extends Activity {

    private String mImagUrl;
    private PhotoView mPvImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_iamgescale);
        parceIntent();

        initView();

    }


    private void initView() {
        mPvImage = (PhotoView) findViewById(R.id.pv_image);
        if (TextUtils.isEmpty(mImagUrl)) {
            mPvImage.setImageResource(R.drawable.ic_launcher);
        }

        Glide.with(this).load(mImagUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mPvImage.setImageBitmap(resource);
            }
        });

    }

    /**
     * 解析意图
     */
    private void parceIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(FrameworkConstant.ACTIVTYPUTBUNDLEKEY);
        mImagUrl = bundle.getString(FrameworkConstant.IMAGE_URL_KEY);

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }


}
