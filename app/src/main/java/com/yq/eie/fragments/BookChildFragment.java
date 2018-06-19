package com.yq.eie.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.yq.eie.R;
import com.yq.eie.adapter.BookAdapter;
import com.yq.eie.base.BaseLoadFragment;
import com.yq.eie.entity.BookBean;
import com.yq.eie.entity.BookInfo;
import com.yq.eie.http.RetrofitManager;
import com.yq.eie.view.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookChildFragment extends BaseLoadFragment {

    @BindView(R.id.srf)
    SwipeRefreshLayout srf;
    @BindView(R.id.rv_book)
    XRecyclerView rvBook;

    private String type;
    private int start = 0, count = 20;
    private BookAdapter adapter;
    private List<BookBean> bookList = new ArrayList<>();
    private BookInfo bookInfo;

    public static BaseLoadFragment createInstance(String type) {
        BookChildFragment fragment = new BookChildFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_child;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        srf.setColorSchemeColors(getResources().getColor(R.color.themeColor));
        srf.setRefreshing(true);
        rvBook.setPullToRefreshEnabled(false);
        adapter = new BookAdapter(getActivity(), bookList);
        rvBook.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        rvBook.setAdapter(adapter);
        srf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                start = 0;
                getData();
            }
        });
        rvBook.setRefreshListener(new XRecyclerView.RefreshListener() {
            @Override
            public void refresh() {

            }

            @Override
            public void load() {
                if (start == bookInfo.getTotal()) {
                    refreshComplete();
                    showToast("已加载全部");
                    return;
                }
                start += count;
                getData();
            }
        });
    }

    @Override
    protected void getData() {
        getBookInfo();
    }

    private void getBookInfo() {
        if (TextUtils.isEmpty(type))
            type = getArguments().getString("type", "综合");
        final Call<BookInfo> bookCall = RetrofitManager.getDouBanHttpService(getActivity()).getBook(type, start, count);
        bookCall.enqueue(new Callback<BookInfo>() {
            @Override
            public void onResponse(Call<BookInfo> call, Response<BookInfo> response) {
                refreshComplete();
                if (start == 0)
                    bookList.clear();
                bookInfo = response.body();
                log(bookInfo);
                bookList.addAll(bookInfo.getBooks());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BookInfo> call, Throwable t) {
                refreshComplete();
                if (start > 0) {
                    start -= 20;
                }
            }
        });
    }

    private void refreshComplete() {
        rvBook.refreshComplete();
        srf.setRefreshing(false);
    }

}
