package com.lzlmall.b2b.app.vendor.framework.recycleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.lzlmall.b2b.app.vendor.framework.R;

/**
 * Created by Administrator on 2018/4/26.
 * 包含空页面   recyclerView的封裝
 */

public class WrapEmptyRecyclerView extends FrameLayout {
    private Context mContext;
    private int mResourceLayoutId;
    private RecyclerView mRecyclerView;
    private View mEmptyView;

    public WrapEmptyRecyclerView(Context context) {
        this(context, null);
    }

    public WrapEmptyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WrapEmptyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        //配置空页面
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WrapEmptyRecyclerView);

        mResourceLayoutId = typedArray.getResourceId(R.styleable.WrapEmptyRecyclerView_emptyPageLayout, R.layout.recyclerview_empty);

        typedArray.recycle();

        intView(context);
    }

    private void intView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleview_layout, this, false);

        mRecyclerView = view.findViewById(R.id.recycle_view);

//        mRecyclerView = new RecyclerView(context);
//        LayoutParams layoutParams = new LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
//        mRecyclerView.setLayoutParams(layoutParams);
        addView(view);

        mEmptyView = LayoutInflater.from(context).inflate(mResourceLayoutId, this, false);
        mEmptyView.setVisibility(INVISIBLE);
        addView(mEmptyView);
    }


    /**
     * 靜態代理
     *
     * @param layoutManager
     */
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(layoutManager);
        }
    }

    /**
     * 靜態代理
     *
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(adapter);
        }
    }


    /**
     * 判斷 是否有數據
     */
    public void isShowEmptyPage() {
        if (mRecyclerView != null) {
            RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
            if (adapter != null) {
                int itemCount = adapter.getItemCount();
                if (itemCount <= 0) {
                    mEmptyView.setVisibility(VISIBLE);
                } else {
                    mEmptyView.setVisibility(INVISIBLE);
                }

            }
        }


    }


}
