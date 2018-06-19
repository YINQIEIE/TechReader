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
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yq.eie.R;
import com.yq.eie.base.BaseActivity;
import com.yq.eie.entity.BookBean;
import com.yq.eie.utils.ImgLoadUtil;
import com.yq.eie.utils.MovieUtil;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class BookDetailActivity extends BaseActivity {

    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.toolbar_parent)
    Toolbar toolbarParent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.tv_rating)
    TextView tvRating;
    @BindView(R.id.tv_rating_number)
    TextView tvRatingNumber;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.tv_summary)
    TextView tvSummary;
    @BindView(R.id.tv_author_intr)
    TextView tvAuthorIntr;
    @BindView(R.id.tv_catalog)
    TextView tvCatalog;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_pubDate)
    TextView tvPubDate;
    @BindView(R.id.tv_publisher)
    TextView tvPublisher;

    private BookBean bookBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookBean = (BookBean) getIntent().getSerializableExtra("bookInfo");
        initToolBar();
        initBookInfo();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_book_detail;
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(bookBean.getTitle());
        actionBar.setSubtitle(MovieUtil.getFormatString(bookBean.getAuthor()) + " 著");
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
                toolbarParent.setBackgroundColor(changeAlpha(Color.WHITE, ratio * 0.4f));
            }
        });
    }

    private void initBookInfo() {
        Glide.with(this).load(bookBean.getImages().getMedium())
                .error(R.drawable.stackblur_default)
                .bitmapTransform(new BlurTransformation(this, 23, 4)).into(ivBg);
        ImgLoadUtil.displayImageWithoutPlaceHolder(this, bookBean.getImages().getMedium(), ivPhoto);
        tvAuthor.setText("作者：" + bookBean.getAuthor());
        tvRating.setText("评分：" + bookBean.getRating().getAverage());
        tvRatingNumber.setText(String.format("%d人评分", bookBean.getRating().getNumRaters()));
        tvPubDate.setText("出版时间：" + bookBean.getPubdate());
        tvPublisher.setText("出版社：" + bookBean.getPublisher());
        tvSummary.setText(bookBean.getSummary());
        tvAuthorIntr.setText(bookBean.getAuthor_intro());
        tvCatalog.setText(bookBean.getCatalog());
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_more:
                WebViewActivity.loadUrl(this, bookBean.getAlt(), "");
                break;
            default:
                break;
        }
        return true;
    }

    public static void start(Context context, BookBean bean, ImageView ivPhoto) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra("bookInfo", bean);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, ivPhoto, context.getString(R.string.book_transition));
        ActivityCompat.startActivity(context, intent, optionsCompat.toBundle());
    }

}
