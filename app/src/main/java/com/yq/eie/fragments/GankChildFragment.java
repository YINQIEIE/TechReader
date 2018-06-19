package com.yq.eie.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.common.base.Strings;
import com.yq.eie.R;
import com.yq.eie.adapter.AndroidAdapter;
import com.yq.eie.base.BaseLoadFragment;
import com.yq.eie.http.RetrofitManager;
import com.yq.eie.http.response.GankBean;
import com.yq.eie.utils.BottomSheetManager;
import com.yq.eie.utils.SPUtil;
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
public class GankChildFragment extends BaseLoadFragment {

    @BindView(R.id.rv_ganks)
    XRecyclerView rvGanks;
    private List<GankBean.ResultBean> list;
    private AndroidAdapter adapter;
    private int page = 1;
    private View mHeaderView;
    private String gankKind;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank_child;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        list = new ArrayList<>();
        adapter = new AndroidAdapter(getActivity(), list);
        rvGanks.setPullToRefreshEnabled(false);
        rvGanks.setLayoutManager(new LinearLayoutManager(getActivity()));
        addHeaderView();
        rvGanks.setAdapter(adapter);
        rvGanks.setRefreshListener(new XRecyclerView.RefreshListener() {
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

    private void addHeaderView() {
        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.header_item_gank_child, null);
        rvGanks.addHeaderView(mHeaderView);
        initHeader(mHeaderView);
    }

    private void initHeader(View mHeaderView) {
        final TextView tv_kind = mHeaderView.findViewById(R.id.tv_kind);
        gankKind = SPUtil.getStringValByKey(getActivity(), "gank_kind");
        tv_kind.setText(TextUtils.isEmpty(gankKind) ? "Android" : gankKind);
        final View view = mHeaderView.findViewById(R.id.ll_choose_catalogue);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
                View sheet = BottomSheetManager.manager.getGankKindSheetView(getActivity());
                BottomSheetManager.manager.setGankKindItemClickListener(new BottomSheetManager.OnGankKindItemClickListener() {
                    @Override
                    public void onItemClick(BottomSheetManager.ItemBean bean) {
                        if (gankKind.equals(bean.getName())) return;
                        gankKind = bean.getRequestName();
                        tv_kind.setText(bean.getName());
                        SPUtil.saveString(getActivity(), "gank_kind", gankKind);//保存选择的种类
                        page = 1;
                        getData();
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(sheet);
                bottomSheetDialog.show();
            }
        });
    }


    @Override
    protected void getData() {
        if (!isDataLoaded)
            showLoadingDialog();
        gankKind = SPUtil.getStringValByKey(getActivity(), "gank_kind");
        gankKind = Strings.isNullOrEmpty(gankKind) ? "Android" : gankKind;
        final Call<GankBean<List<GankBean.ResultBean>>> photoCall = RetrofitManager.getGankHttpService().getGankInfo(gankKind, page, 10);
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
