package com.yq.eie.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yq.eie.R;
import com.yq.eie.adapter.Top250MovieAdapter;
import com.yq.eie.base.BaseActivity;
import com.yq.eie.http.RetrofitManager;
import com.yq.eie.http.response.MovieBean;
import com.yq.eie.view.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieTop250Activity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_movie)
    XRecyclerView rvMovie;

    private int start = 0;
    private int count = 21;

    private List<MovieBean.SubjectsBean> data = new ArrayList<>();
    private Top250MovieAdapter adapter;
    private boolean isDataLoaded = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBar();
        initRv();
        getData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_movie_top_250;
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("豆瓣电影Top250");
        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRv() {
        adapter = new Top250MovieAdapter(this, data);
        rvMovie.setLayoutManager(new GridLayoutManager(this, 3));
        rvMovie.setAdapter(adapter);
        rvMovie.setRefreshListener(new XRecyclerView.RefreshListener() {
            @Override
            public void refresh() {
                start = 0;
                count = 21;
                getData();
            }

            @Override
            public void load() {
                start += count;
                count += count;
                getData();
            }
        });
    }

    /**
     * 获取 top 250 电影信息
     */
    private void getData() {
        if (!isDataLoaded) {
            showLoadingDialog();
            isDataLoaded = true;
        }
        Call<MovieBean> movieTop250Call = RetrofitManager.getDouBanHttpService(this).getMovieTop250(start, count);
        movieTop250Call.enqueue(new Callback<MovieBean>() {
            @Override
            public void onResponse(Call<MovieBean> call, Response<MovieBean> response) {
                dismissLoadingDialog();
                rvMovie.refreshComplete();
                MovieBean movieBean = response.body();
                if (null != movieBean) {
                    if (start == 0)
                        data.clear();
                    data.addAll(movieBean.getSubjects());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieBean> call, Throwable t) {
                dismissLoadingDialog();
                rvMovie.refreshComplete();
                if (start > 0) {
                    start -= count;
                    count -= count;
                }
            }
        });

    }


}
