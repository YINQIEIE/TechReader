package com.yq.eie.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yq.eie.R;
import com.yq.eie.adapter.ActorsAdapter;
import com.yq.eie.base.BaseActivity;
import com.yq.eie.http.RetrofitManager;
import com.yq.eie.http.response.MovieBean;
import com.yq.eie.http.response.MovieDetailBean;
import com.yq.eie.utils.ImgLoadUtil;
import com.yq.eie.utils.MovieUtil;

import java.util.List;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends BaseActivity {

    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.tv_rating)
    TextView tvRating;
    @BindView(R.id.tv_rating_number)
    TextView tvRatingNumber;
    @BindView(R.id.tv_director)
    TextView tvDirector;
    @BindView(R.id.tv_casts)
    TextView tvCasts;
    @BindView(R.id.tv_genres)
    TextView tvGenres;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.tv_alias)
    TextView tvAlias;
    @BindView(R.id.tv_summary)
    TextView tvSummary;
    @BindView(R.id.rv_casts)
    RecyclerView rvCasts;

    private MovieBean.SubjectsBean subjectsBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subjectsBean = (MovieBean.SubjectsBean) getIntent().getSerializableExtra("movieInfo");
        initToolBar();
        //在 transition 动画结束后再请求信息，否则在上个页面会看到加载 dialog
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                initMovieInfo();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_movie_detail;
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(subjectsBean.getTitle());
        //设置toolbar_layout 是否显示标题
        //true 显示，toolbar 标题会随着页面滑动切换位置
        //false 不显示，标题固定在 toolbar 标题位置不动
        toolbarLayout.setTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        appBar.getTotalScrollRange()此处获取值为 0
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                log("alpha-maxOff = " + appBarLayout.getTotalScrollRange());
                float ratio = (float) Math.abs(verticalOffset) / appBarLayout.getTotalScrollRange();
                //不要标题栏渐变
                toolbar.setBackgroundColor(changeAlpha(Color.WHITE, ratio * 0.4f));
//                if (ratio == 0)
//                    toolbar.setTitleTextColor(Color.WHITE);
//                else
//                    toolbar.setTitleTextColor(changeAlpha(Color.BLACK, ratio));
            }
        });
    }

    private void initMovieInfo() {
        //删除 transition 动画监听，否则会有异常
        setEnterSharedElementCallback(new SharedElementCallback() {
        });
        showLoadingDialog();
        ImgLoadUtil.displayImageWithoutPlaceHolder(this, subjectsBean.getImages().getLarge(), ivPhoto);
        tvRating.setText("评分：" + subjectsBean.getRating().getAverage());
        tvRatingNumber.setText(String.format("%d人评分", subjectsBean.getCollect_count()));
        tvDirector.setText(MovieUtil.getFormatName(subjectsBean.getDirectors()));
        tvCasts.setText(MovieUtil.getFormatName(subjectsBean.getCasts()));
        tvGenres.setText(String.format("主演：%s", MovieUtil.getFormatString(subjectsBean.getGenres())));
//        tvArea.setText(String.format("影片国家/地区：%s",subjectsBean.get));
        Glide.with(this).load(subjectsBean.getImages().getMedium())
                .error(R.drawable.stackblur_default)
                .bitmapTransform(new BlurTransformation(this, 23, 4)).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(ivBg);
        getMovieDetais();
    }

    private void getMovieDetais() {
        Call<MovieDetailBean> movieDetailCall = RetrofitManager.getDouBanHttpService(this).getMovieDetail(subjectsBean.getId());
        movieDetailCall.enqueue(new Callback<MovieDetailBean>() {
            @Override
            public void onResponse(Call<MovieDetailBean> call, Response<MovieDetailBean> response) {
                dismissLoadingDialog();
                MovieDetailBean body = response.body();
                if (null != body) {
                    log(body.toString());
                    tvDate.setText("上映时间：" + body.getYear());
                    tvArea.setText("国家/地区：" + MovieUtil.getFormatString(body.getCountries()));
                    tvAlias.setText(MovieUtil.getFormatString(body.getAka()));
                    tvSummary.setText(body.getSummary());
                    setActors();
                }
            }

            @Override
            public void onFailure(Call<MovieDetailBean> call, Throwable t) {
                dismissLoadingDialog();
            }
        });
    }

    private void setActors() {
        ActorsAdapter adapter = new ActorsAdapter(this, subjectsBean.getDirectors());
        adapter.addActors(subjectsBean.getCasts());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvCasts.setLayoutManager(layoutManager);
        rvCasts.setHasFixedSize(false);
        rvCasts.setAdapter(adapter);
        rvCasts.setVisibility(View.VISIBLE);
    }

    /**
     * 根据百分比改变颜色透明度
     */
    public int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

    @Override
    protected void setTheme() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_more:
                WebViewActivity.loadUrl(this, subjectsBean.getAlt(), "");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context, MovieBean.SubjectsBean bean, ImageView ivPhoto) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("movieInfo", bean);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, ivPhoto, context.getString(R.string.movie_transition));
        ActivityCompat.startActivity(context, intent, optionsCompat.toBundle());
    }

}
