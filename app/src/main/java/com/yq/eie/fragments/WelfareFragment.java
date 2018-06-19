package com.yq.eie.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yq.eie.R;
import com.yq.eie.adapter.WelfareAdapter;
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
 * 福利界面
 */
public class WelfareFragment extends BaseLoadFragment {

    @BindView(R.id.rv_pics)
    XRecyclerView rvPics;
    private List<GankBean.ResultBean> list;
    private WelfareAdapter adapter;
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_welfare;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        list = new ArrayList<>();
        adapter = new WelfareAdapter(getActivity(), list);
        rvPics.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvPics.addItemDecoration(new RecyclerView.ItemDecoration() {

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = 0;
                outRect.bottom = 12;
                if (parent.getChildAdapterPosition(view) % 2 == 0) {//左边一列
                    outRect.left = 12;
                    outRect.right = 6;
                } else {//右边一列
                    outRect.left = 6;
                    outRect.right = 12;
                }
            }
        });
        rvPics.setPullToRefreshEnabled(false);
        rvPics.setAdapter(adapter);
        rvPics.setRefreshListener(new XRecyclerView.RefreshListener() {
            @Override
            public void refresh() {

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
        final Call<GankBean<List<GankBean.ResultBean>>> photoCall = RetrofitManager.getGankHttpService().getGankInfo("福利", page, 10);
        photoCall.enqueue(new Callback<GankBean<List<GankBean.ResultBean>>>() {
            @Override
            public void onResponse(Call<GankBean<List<GankBean.ResultBean>>> call, Response<GankBean<List<GankBean.ResultBean>>> response) {
                dismissLoadingDialog();
                rvPics.refreshComplete();
                GankBean photoGank = response.body();
                list.addAll((List<GankBean.ResultBean>) photoGank.getResults());
                for (int i = 0; i < list.size(); i++) {
                    log("photo url >>> " + list.get(i).getUrl());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GankBean<List<GankBean.ResultBean>>> call, Throwable t) {
                dismissLoadingDialog();
                rvPics.refreshComplete();
                if (page > 1)
                    page--;
            }
        });
    }


}
