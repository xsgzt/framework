package com.xsgzt;

import android.os.Bundle;

import com.xsgzt.b2c.seller.frameworkdemo.R;
import com.xsgzt.framework.base.BaseActivity;
import com.xsgzt.framework.navigationbar.DefaultNavigationBar;

/**
 * Created by Administrator on 2018/6/27.
 */

public class TestActivity extends BaseActivity {
    @Override
    protected void netErroRefresh() {

    }

    @Override
    protected void parseIntent(Bundle bundle) {

    }

    @Override
    protected void registerButterKnife() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void intTitle() {
        new DefaultNavigationBar.Builder(this).setTitle("ceishiactivity").build();
    }

    @Override
    protected void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
