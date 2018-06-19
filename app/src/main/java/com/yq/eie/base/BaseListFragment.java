package com.yq.eie.base;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yq.eie.R;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yinqi on 2017/9/12.
 */

public abstract class BaseListFragment<T> extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    protected List<T> datas = new ArrayList<>();
    protected MultiItemTypeAdapter<T> adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        initAdapter();
        recyclerView.setAdapter(adapter);
        new MyTask().execute();
    }

    protected abstract void initAdapter();

    public class MyTask extends AsyncTask<Void, Void, List<T>> {

        @Override
        protected List<T> doInBackground(Void... voids) {
            return doInBackgroud();
        }

        @Override
        protected void onPostExecute(List<T> datas) {
            onResult(datas);
        }

    }

    protected abstract List<T> doInBackgroud();

    protected abstract void onResult(List<T> result);

}
