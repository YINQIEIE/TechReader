package com.yq.eie.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.yq.eie.R;
import com.yq.eie.activity.MovieTop250Activity;
import com.yq.eie.adapter.MovieAdapter;
import com.yq.eie.base.BaseLoadFragment;
import com.yq.eie.http.RetrofitManager;
import com.yq.eie.http.response.MovieBean;
import com.yq.eie.utils.ConstantsImageUrl;
import com.yq.eie.utils.ImgLoadUtil;
import com.yq.eie.view.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by Administrator on 2018/1/5.
 */

public class MovieFragment extends BaseLoadFragment {

    @BindView(R.id.rv_content)
    XRecyclerView rvContent;
    private MovieAdapter adapter;
    List<MovieBean.SubjectsBean> data;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rvContent.setPullToRefreshEnabled(false);
        rvContent.setLoadMoreEnabled(false);
        HeaderHolder holder = new HeaderHolder();
        rvContent.addHeaderView(holder.headerView);
        data = new ArrayList<>();
        adapter = new MovieAdapter(getActivity(), data);
        rvContent.setLayoutManager(new LinearLayoutManager(getActivity()));


        LayoutAnimationController animationController = new LayoutAnimationController(getAnimationSetFromLeft());
        rvContent.setLayoutAnimation(animationController);

    }

    public static AnimationSet getAnimationSetFromLeft() {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateX1 = new TranslateAnimation(RELATIVE_TO_SELF, -1.0f, RELATIVE_TO_SELF, 0.1f,
                RELATIVE_TO_SELF, 0, RELATIVE_TO_SELF, 0);
        translateX1.setDuration(300);
        translateX1.setInterpolator(new DecelerateInterpolator());
        translateX1.setStartOffset(0);

        TranslateAnimation translateX2 = new TranslateAnimation(RELATIVE_TO_SELF, 0.1f, RELATIVE_TO_SELF, -0.1f,
                RELATIVE_TO_SELF, 0, RELATIVE_TO_SELF, 0);
        translateX2.setStartOffset(300);
        translateX2.setInterpolator(new DecelerateInterpolator());
        translateX2.setDuration(50);

        TranslateAnimation translateX3 = new TranslateAnimation(RELATIVE_TO_SELF, -0.1f, RELATIVE_TO_SELF, 0f,
                RELATIVE_TO_SELF, 0, RELATIVE_TO_SELF, 0);
        translateX3.setStartOffset(350);
        translateX3.setInterpolator(new DecelerateInterpolator());
        translateX3.setDuration(50);

        animationSet.addAnimation(translateX1);
        animationSet.addAnimation(translateX2);
        animationSet.addAnimation(translateX3);
        animationSet.setDuration(400);

        return animationSet;
    }

    @Override
    protected void getData() {
        if (!isDataLoaded)
            showLoadingDialog();
        Call<MovieBean> hotMovies = RetrofitManager.getDouBanHttpService(getActivity()).getHotMovies();

        hotMovies.enqueue(new Callback<MovieBean>() {
            @Override
            public void onResponse(Call<MovieBean> call, Response<MovieBean> response) {
                dismissLoadingDialog();
                MovieBean body = response.body();
                if (null != body) {
                    log(response.body().toString());
                    data.addAll(body.getSubjects());
                    rvContent.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
                    rvContent.scheduleLayoutAnimation();
                }
            }

            @Override
            public void onFailure(Call<MovieBean> call, Throwable t) {
                dismissLoadingDialog();
                log(t.getStackTrace());
            }
        });
    }

    private class HeaderHolder {

        View headerView;

        public HeaderHolder() {
            headerView = LayoutInflater.from(getActivity()).inflate(R.layout.header_movie, null);
            ButterKnife.bind(this, headerView);
            ImgLoadUtil.displayRandom(3, ConstantsImageUrl.ONE_URL_01, (ImageView) headerView.findViewById(R.id.iv_img));
            headerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent();
                    startNewActivity(MovieTop250Activity.class);
                }
            });
        }


    }

}
