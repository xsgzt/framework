package com.lzlmall.b2b.app.vendor.framework.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.lzlmall.b2b.app.vendor.framework.R;
import com.lzlmall.b2b.app.vendor.framework.ioc.ShowLoadPage;
import com.lzlmall.b2b.app.vendor.framework.utils.FrameworkConstant;
import com.lzlmall.b2b.app.vendor.framework.utils.PreferencesUtil;
import com.lzlmall.b2b.app.vendor.framework.widget.ErrorView;
import com.lzlmall.b2b.app.vendor.framework.widget.LoadingView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/4/18.
 */

public abstract class BaseFragment extends Fragment {
    protected FrameLayout mRootView;
    private LoadingView loadingView;
    private ErrorView mErrorView;
    /**
     * WindowManager下的LayoutParams实例
     */
    private FrameLayout.LayoutParams mLodingParams, mErrorViewParams;
    private WindowManager mWM;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup layoutView = (ViewGroup) inflater.inflate(getLayoutId(), container, false);
        //包裹一层
        mRootView = new FrameLayout(getContext());
        mRootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mRootView.addView(layoutView);

        //注册EventBus 事件
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        registerButterKnife();

        intTitle(); //fragment 要搞个移除title
        initView(mRootView);
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


        return mRootView;
    }


    protected <T extends View> T viewById(int viewId) {
        return this.mRootView.findViewById(viewId);
    }

    protected abstract void registerButterKnife();

    public abstract int getLayoutId();

    protected abstract void intTitle();

    protected abstract void initView(ViewGroup rootView);

    protected abstract void initData(Bundle savedInstanceState);


    /**
     * 开启界面
     */

    public void startActivity(Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (bundle != null) {
            intent.putExtra(FrameworkConstant.ACTIVTYPUTBUNDLEKEY, bundle);
        }
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {

    }

    /**
     * 显示打印 Toast
     *
     * @param text
     */
    protected final void showToast(CharSequence text) {
        if(getActivity() == null) {
            return;
        }
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }


    public void startActivityForResult(Class<? extends Activity> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
        if (bundle != null) {
            intent.putExtra(FrameworkConstant.ACTIVTYPUTBUNDLEKEY, bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    public void initLoadingPage() {
        loadingView = new LoadingView(getContext());
        loadingView.setBackgroundResource(R.color.white);
        //把他添加再
        mLodingParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        // 动态的添加到窗体中
        mRootView.addView(loadingView, mLodingParams);

    }


    public void removeLoad() {
        if (loadingView != null) {
            mRootView.removeView(loadingView);
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
        mErrorView = new ErrorView(getActivity());
        mErrorView.setBackgroundResource(R.color.white);
        // 获取窗体管理
        mErrorViewParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mRootView.addView(mErrorView, mErrorViewParams);
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
            mRootView.removeView(mErrorView);
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
