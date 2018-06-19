package com.yq.eie.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yq.eie.R;
import com.yq.eie.entity.SortBase;
import com.yq.eie.view.SideNavigationView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;


public abstract class BaseNavListFragment<T extends SortBase> extends BaseListFragment<T> {

    @BindView(R.id.side_nav)
    SideNavigationView side_nav;
    @BindView(R.id.tv_nav)
    TextView tvNav;
    protected Map<String, Integer> charMap = new HashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.layout_list_with_side_nav;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        side_nav.setTv(tvNav);
        side_nav.setMovingListener(new SideNavigationView.GestureListener() {
            @Override
            public void onMoving(int pos, String s) {
                if (null != charMap.get(s)) {
//                    recyclerView.scrollToPosition(charMap.get(s));
                    ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(charMap.get(s), 0);
                }
            }

            @Override
            public void onTouch(int pos, String s) {
                if (null != charMap.get(s))
                    recyclerView.scrollToPosition(charMap.get(s));
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();
                String firstChar = datas.get(firstVisiblePosition).getFirstChar();
                if (null != firstChar)
                    side_nav.setNowChar(firstChar);
            }
        });
    }

}
