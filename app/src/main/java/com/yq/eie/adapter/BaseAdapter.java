package com.yq.eie.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseAdapter<DATA, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected Context mContext;
    protected List<DATA> data;

    public BaseAdapter(Context mContext, List<DATA> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(getItemLayout(), parent, false);
        return getViewHolder(itemView);
    }

    public abstract VH getViewHolder(View view);

    @LayoutRes
    public abstract int getItemLayout();

    @Override
    public int getItemCount() {
        if (data == null)
            throw new IllegalStateException("data is null!");
        return data.size();
    }


}
