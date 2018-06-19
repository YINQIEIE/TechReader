package com.yq.eie.adapter.itemviewdelegate;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yq.eie.R;
import com.yq.eie.entity.LocalInfo;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by yinqi on 2017/9/12.
 */

public abstract class LocalDelegateTwo implements ItemViewDelegate<LocalInfo> {

    private Context context;
    private List<LocalInfo> menus;

    private CommonAdapter<LocalInfo> adapter;

    public LocalDelegateTwo(Context context, List<LocalInfo> menus) {
        this.context = context;
        this.menus = menus;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.fragment_local_item_two;
    }

    @Override
    public void convert(ViewHolder holder, LocalInfo localInfo, int position) {
        holder.setText(R.id.tv_title, localInfo.getTitle());
        setMenuRecyclerView((RecyclerView) holder.getView(R.id.rv_menu));
    }

    private void setMenuRecyclerView(RecyclerView recyclerView) {
        adapter = new CommonAdapter<LocalInfo>(context, R.layout.item_songs_menu_rv, menus) {

            @Override
            protected void convert(ViewHolder holder, LocalInfo localInfo, int position) {
                holder.setText(R.id.tv_menu_name, localInfo.getTitle());
                holder.setText(R.id.tv_subTitle1, localInfo.getCount() + "首");
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL) {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                final int pos = parent.getChildAdapterPosition(view);
                Log.i("position", "pos = " + pos);
                if (pos < menus.size() - 1)
                    super.getItemOffsets(outRect, view, parent, state);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
