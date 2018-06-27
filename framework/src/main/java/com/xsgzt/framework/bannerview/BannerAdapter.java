package com.xsgzt.framework.bannerview;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/4/25.
 */

public abstract class BannerAdapter {

    public abstract int getCount();

    public abstract View getView(ViewGroup container, int position);


    public  String getBannerDesc(int position) {
        return null;
    }

}
