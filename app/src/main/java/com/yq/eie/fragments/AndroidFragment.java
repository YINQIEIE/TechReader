package com.yq.eie.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.yq.eie.R;
import com.yq.eie.adapter.AndroidAdapter;
import com.yq.eie.base.BaseLoadFragment;
import com.yq.eie.http.RetrofitManager;
import com.yq.eie.http.response.GankBean;
import com.yq.eie.view.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 干货订制
 */
public class AndroidFragment extends BaseLoadFragment {

    @BindView(R.id.rv_ganks)
    XRecyclerView rvGanks;
    private List<GankBean.ResultBean> list;
    private AndroidAdapter adapter;
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank_child;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        list = new ArrayList<>();
        adapter = new AndroidAdapter(getActivity(), list);
        rvGanks.setLayoutManager(new LinearLayoutManager(getActivity()));
//        View mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.header_item_gank_child, null);
//        rvGanks.addHeaderView(mHeaderView);
//        rvGanks.setPullToRefreshEnabled(false);
//        rvGanks.setPullToRefreshHeader(new RefreshHeaderViewWithBase(getActivity()));
        rvGanks.setAdapter(adapter);
        rvGanks.setRefreshListener(new XRecyclerView.RefreshListener() {
            @Override
            public void refresh() {
                page = 1;
                getData();
            }

            @Override
            public void load() {
                page += 1;
                getData();
            }
        });

    }

    @Override
    protected void getData() {
        if (!isDataLoaded)
            showLoadingDialog();
        final Call<GankBean<List<GankBean.ResultBean>>> photoCall = RetrofitManager.getGankHttpService().getGankInfo("Android", page, 10);
        photoCall.enqueue(new Callback<GankBean<List<GankBean.ResultBean>>>() {
            @Override
            public void onResponse(Call<GankBean<List<GankBean.ResultBean>>> call, Response<GankBean<List<GankBean.ResultBean>>> response) {
                rvGanks.refreshComplete();//设置加载更多布局不可见
                dismissLoadingDialog();
                log(response.body().toString());
                List<GankBean.ResultBean> data = response.body().getResults();
                if (page == 1) list.clear();
                list.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GankBean<List<GankBean.ResultBean>>> call, Throwable t) {
                rvGanks.refreshComplete();//设置加载更多布局不可见
                dismissLoadingDialog();
                if (page > 1) page--;
            }
        });

    }


}
