package com.yq.eie.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yq.eie.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yinqi on 2017/9/14.
 */

public abstract class BaseListActivity<T> extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    protected RecyclerView.Adapter adapter;
    protected List<T> datas = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list);
        init();
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        initAdapter();
        recyclerView.setAdapter(adapter);
    }

    protected abstract void initAdapter();
}
