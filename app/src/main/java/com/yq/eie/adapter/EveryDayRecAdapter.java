package com.yq.eie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.yq.eie.adapter.viewholder.ItemOneHolder;
import com.yq.eie.adapter.viewholder.ItemThreeHolder;
import com.yq.eie.adapter.viewholder.ItemTwoHolder;
import com.yq.eie.adapter.viewholder.TitleHolder;
import com.yq.eie.http.response.GankBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/3.
 */

public class EveryDayRecAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<List<GankBean.ResultBean>> data;
    private SparseArray<View> mHeaderViews;
    private int headePos = -1;

    public EveryDayRecAdapter(Context mContext, List<List<GankBean.ResultBean>> data) {
        this.mContext = mContext;
        this.data = data;
        mHeaderViews = new SparseArray<>();
        for (int i = 0; i < data.size(); i++) {
            Log.i("adapter", data.get(i).size() + "" + data.get(i).get(0).getName());
        }
    }

    @Override
    public int getItemCount() {
        Log.i("getItemCount", data.size() + "");
//        return mHeaderViews.size() > 0 ? data.size() + mHeaderViews.size() : data.size();
        return mHeaderViews.size() + data.size();//和上面等效
    }

    @Override
    public int getItemViewType(int position) {
        int pos = position - mHeaderViews.size();
        if (mHeaderViews.size() > 0 && position < mHeaderViews.size()) return ItemType.TYPE_BANNER;
        else if (!TextUtils.isEmpty(data.get(pos).get(0).getName())) {
            return ItemType.TYPE_TITLE;
        } else if (data.get(pos).size() == 1) {
            return ItemType.TYPE_ITEM_ONE;
        } else if (data.get(pos).size() == 2) {
            return ItemType.TYPE_ITEM_TWO;
        } else if (data.get(pos).size() == 3) {
            return ItemType.TYPE_ITEM_THREE;
        }
        return super.getItemViewType(pos);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ItemType.TYPE_BANNER:
                return new SimpleViewHolder(mHeaderViews.get(++headePos));
            case ItemType.TYPE_TITLE:
                return new TitleHolder(parent);
            case ItemType.TYPE_ITEM_ONE:
                return new ItemOneHolder(parent);
            case ItemType.TYPE_ITEM_TWO:
                return new ItemTwoHolder(parent);
            case ItemType.TYPE_ITEM_THREE:
                return new ItemThreeHolder(parent);
            default:
                return new SimpleViewHolder(mHeaderViews.get(++headePos));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SimpleViewHolder) return;
        else
            ((BaseViewHolder) holder).onBindViewHolder(data.get(position - mHeaderViews.size()), position - mHeaderViews.size());
    }

    public interface ItemType {
        int TYPE_BANNER = 0;
        int TYPE_TITLE = 1;
        int TYPE_ITEM_ONE = 2;
        int TYPE_ITEM_TWO = 3;
        int TYPE_ITEM_THREE = 4;
    }

    /**
     * 每个 item 都必须有 ViewHolder
     */
    private class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void addHeaderView(View view) {
        mHeaderViews.append(mHeaderViews.size(), view);
    }
}
