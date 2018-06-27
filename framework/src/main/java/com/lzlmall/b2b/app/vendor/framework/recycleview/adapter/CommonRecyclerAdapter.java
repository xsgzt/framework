package com.lzlmall.b2b.app.vendor.framework.recycleview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2018/4/26.
 */

public abstract class CommonRecyclerAdapter<DATA> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected List<DATA> mData;
    protected LayoutInflater mLayoutInflater;
    protected int mItemlayout;
    protected MultiTypeSupport mMultiTypeSupport;
    private OnItemClickListener mOnItemClickListener;
    private OnLongClickListener mOnLongClickListener;

    public CommonRecyclerAdapter(Context context, List<DATA> data, MultiTypeSupport<DATA> multiTypeSupport) {
        this(context, data, -1);
        mMultiTypeSupport = multiTypeSupport;
    }

    public CommonRecyclerAdapter(Context context, List<DATA> data, int itemlayout) {
        //传数据
        mContext = context;
        mData = data;
        mItemlayout = itemlayout;
        mLayoutInflater = LayoutInflater.from(context);
    }


    //这里我们要支持多布局  后台要设置一个type来区分条目的类型
    //根据位置显示不同的type
    @Override
    public int getItemViewType(int position) {
        //这里没有办法确定标志位  只能 暴露出去
        if (mMultiTypeSupport == null) {
            return super.getItemViewType(position);
        }
        return mMultiTypeSupport.getLayoutId(mData.get(position), position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据不同的type 创建hodler
        // 多布局支持
        if (mMultiTypeSupport != null) {
            mItemlayout = viewType;
        }

        View itemView = mLayoutInflater.inflate(mItemlayout, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //利用holder 绑定数据  这个要把交出去
        //设置点击事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
        }
        if (mOnLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mOnLongClickListener.onLongClick(position);
                }
            });
        }
        convert(holder, position, mData.get(position));
    }

    protected abstract void convert(ViewHolder holder, int position, DATA data);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        mOnLongClickListener = onLongClickListener;
    }
}
