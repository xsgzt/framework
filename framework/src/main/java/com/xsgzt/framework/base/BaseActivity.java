package com.xsgzt.framework.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.xsgzt.framework.R;
import com.xsgzt.framework.ioc.ShowLoadPage;
import com.xsgzt.framework.utils.AppManagerUtil;
import com.xsgzt.framework.utils.FrameworkConstant;
import com.xsgzt.framework.utils.PreferencesUtil;
import com.xsgzt.framework.utils.ScreenUtils;
import com.xsgzt.framework.utils.StatusBarUtils;
import com.xsgzt.framework.widget.ErrorView;
import com.xsgzt.framework.widget.LoadingView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;


/**
 * Created by Administrator on 2018/4/18.
 * 基类activity
 */

public abstract class BaseActivity extends AppCompatActivity {

    private LoadingView loadingView;
    private ErrorView mErrorView;
    /**
     * WindowManager下的LayoutParams实例
     */
    private WindowManager.LayoutParams mLodingParams, mErrorViewParams;
    private WindowManager mWM;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        StatusBarUtils.setStatusBar(this, R.color.main);


        setContentView(getLayoutId());
        AppManagerUtil.instance().attachActivity(this);
        //绑定控件
        registerButterKnife();
        mWM = getWindowManager();
        //注册EventBus 事件
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        parseIntent();
        intTitle();
        initView();
        initData(savedInstanceState);

        // 统一添加LoadingView
        try {
            Method method = getClass().getDeclaredMethod("initData", Bundle.class);
            ShowLoadPage showLoadingView = method.getAnnotation(ShowLoadPage.class);
            if (showLoadingView != null) {
                initLoadingPage();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


    }


    public void initLoadingPage() {
        loadingView = new LoadingView(this);
        loadingView.setBackgroundResource(R.color.white);
        //把他添加再
        mLodingParams = new WindowManager.LayoutParams();
        // 计算LoadingView的高度 = 屏幕高度 - 状态栏的高度 - 头部的高度
        mLodingParams.height = (int) (ScreenUtils.getScreenHeight(this)
                - ScreenUtils.getStatusHeight(this) - getResources().getDimension(R.dimen.title_height));
        mLodingParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mLodingParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 效果为背景透明
        mLodingParams.format = PixelFormat.RGBA_8888;
        // 在底部显示
        mLodingParams.gravity = Gravity.BOTTOM;
        // 动态的添加到窗体中
        mWM.addView(loadingView, mLodingParams);

    }


    public void removeLoad() {
        if (loadingView != null) {
            mWM.removeView(loadingView);
            loadingView = null;
        }
    }


    /**
     * 显示错误页面
     * 错误就加进来   点击刷新就移除
     */
    public void showErroPage() {
        if (mErrorView != null) {
            return;
        }
        mErrorView = new ErrorView(this);
        mErrorView.setBackgroundResource(R.color.white);
        // 获取窗体管理
        mErrorViewParams = new WindowManager.LayoutParams();
        // 计算LoadingView的高度 = 屏幕高度 - 状态栏的高度 - 头部的高度
        mErrorViewParams.height = (int) (ScreenUtils.getScreenHeight(this)
                - ScreenUtils.getStatusHeight(this) - getResources().getDimension(R.dimen.title_height));
        mErrorViewParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mErrorViewParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 效果为背景透明
        mErrorViewParams.format = PixelFormat.RGBA_8888;
        // 在底部显示
        mErrorViewParams.gravity = Gravity.BOTTOM;
        // 动态的添加到窗体中
        if (!isFinishing()) {
            mWM.addView(mErrorView, mErrorViewParams);

        }

        mErrorView.findViewById(R.id.tv_reflash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //刷新
                removeErroPage();
                netErroRefresh();
                initLoadingPage();
            }
        });


    }

    protected abstract void netErroRefresh();

    /**
     * 移除错误页面
     */
    private void removeErroPage() {
        if (mErrorView != null) {
            mWM.removeView(mErrorView);
            mErrorView = null;
        }
    }


    /**
     * 解析意图
     */
    private void parseIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(FrameworkConstant.ACTIVTYPUTBUNDLEKEY);
        if (bundle == null) {
            return;
        }
        parseIntent(bundle);
    }

    protected abstract void parseIntent(Bundle bundle);

    protected abstract void registerButterKnife();

    public abstract int getLayoutId();

    protected abstract void intTitle();

    protected abstract void initView();

    public abstract void initData(Bundle savedInstanceState);


    protected <T extends View> T viewById(int viewId) {
        return this.findViewById(viewId);
    }

    /**
     * 显示打印 Toast
     *
     * @param text
     */
    protected final void showToast(CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }


    /**
     * 跳转Activity
     *
     * @param clazz
     * @param bundle
     */
    public void startActivity(Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtra(FrameworkConstant.ACTIVTYPUTBUNDLEKEY, bundle);
        }
        startActivity(intent);
    }


    public void startActivityForResult(Class<? extends Activity> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtra(FrameworkConstant.ACTIVTYPUTBUNDLEKEY, bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //OkHttp 的请
        removeLoad();
        removeErroPage();
        AppManagerUtil.instance().detachActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == FrameworkConstant.REQUESTCODE) {
                //由网络错误页面返回来的  刷新


            }

        }

    }

    protected boolean isNetRequestOk(BaseResult baseResult) {
        removeLoad();
        return true;
    }

    /**
     * 缓存数据
     *
     * @param key
     * @param obj
     * @return
     */
    public Object saveParam(String key, Object obj) {
        PreferencesUtil.getInstance().saveParam(key, obj);
        return key;
    }

    /**
     * 获取缓存数据
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public Object getParam(String key, Object defaultObject) {
        return PreferencesUtil.getInstance().getParam(key, defaultObject);
    }


}
