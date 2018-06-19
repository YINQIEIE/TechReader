package com.yq.eie.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/1/9.
 */

public class WrapAdapter extends RecyclerView.Adapter {

    private RecyclerView.Adapter adapter;
    protected SparseArray<View> headerViews;
    protected SparseArray<View> footerViews;
    private int headerPosition = -1;
    private int footerPosition = -1;

    public WrapAdapter(@NonNull RecyclerView.Adapter adapter, SparseArray<View> headerViews, SparseArray<View> footerViews) {
        if (adapter == null)
            throw new IllegalArgumentException("the adapter should not be null!");
        this.adapter = adapter;
        this.headerViews = headerViews;
        this.footerViews = footerViews;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            //设置头部和正常 item 所占的列数
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return isHeaderPosition(position) || isFooterPosition(position) ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        if (params != null
                && params instanceof StaggeredGridLayoutManager.LayoutParams
                && (isHeaderPosition(holder.getLayoutPosition()) || isFooterPosition(holder.getLayoutPosition()))) {
            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) params;
            lp.setFullSpan(true);
        }
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount() + headerViews.size() + footerViews.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position))
            return ItemType.ITEM_HEADER;
        else if (position >= headerViews.size() && position < headerViews.size() + adapter.getItemCount())
            return ItemType.ITEM_NORMAL;
        else
            return ItemType.ITEM_FOOTER;
    }

    private boolean isHeaderPosition(int pos) {
        return (headerViews.size() > 0 && pos < headerViews.size());
    }

    private boolean isFooterPosition(int pos) {
        return pos >= headerViews.size() + adapter.getItemCount();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ItemType.ITEM_HEADER:
                headerPosition++;
                return new SimpleViewHolder(headerViews.valueAt(headerPosition));
            case ItemType.ITEM_NORMAL:
                return adapter.onCreateViewHolder(parent, viewType);
            case ItemType.ITEM_FOOTER:
                footerPosition++;
                return new SimpleViewHolder(footerViews.valueAt(footerPosition));

        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (position >= headerViews.size()) {
            int adjPosition = position - headerViews.size();
            int adapterCount = adapter.getItemCount();
            if (adjPosition < adapterCount) {
                return adapter.getItemId(adjPosition);
            }
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ItemType.ITEM_HEADER || getItemViewType(position) == ItemType.ITEM_FOOTER)
            return;
        if (position - headerViews.size() < adapter.getItemCount())
            adapter.onBindViewHolder(holder, position - headerViews.size());
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        if (adapter != null) {
            adapter.unregisterAdapterDataObserver(observer);
        }
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
    }

    /**
     * 每个 item 都必须有 ViewHolder
     */
    private class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface ItemType {
        int ITEM_HEADER = 0;//头部
        int ITEM_NORMAL = 1;//正常
        int ITEM_FOOTER = 2;//底部
    }


}
