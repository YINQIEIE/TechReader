package com.yq.eie.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.umeng.socialize.UMShareAPI;
import com.yq.eie.R;
import com.yq.eie.base.BaseActivity;
import com.yq.eie.http.response.AppDatabase;
import com.yq.eie.http.response.BlogBean;
import com.yq.eie.http.response.BlogDao;
import com.yq.eie.http.response.GankBean;

import java.lang.reflect.Method;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.parent)
    FrameLayout parent;
    private WebView webView;
    private String webUrl;
    boolean hasLoaded = false;
    private GankBean.ResultBean info;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    private BlogDao blogDao;
    private boolean isCollected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //从底部进入
        overridePendingTransition(R.anim.activity_bottom_in, R.anim.activity_bottom_no);
        initToolbar();
        initWebView();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("加载中...");
        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.actionbar_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web_view;
    }

    private void initWebView() {
        webView = new WebView(this.getApplication());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            webView.setWebContentsDebuggingEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress == 100)
                    progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                toolbar.setTitle(title);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            /**
             * {@link #shouldOverrideUrlLoading 不一定走}
             * @param view
             * @param url
             * @param favicon
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!hasLoaded) {
                    hasLoaded = true;
//                    webUrl = url;
                    view.loadUrl(url);
                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!hasLoaded) {
                    hasLoaded = true;
//                    webUrl = url;
                    view.loadUrl(url);
                }
                return true;
            }
        });
        WebSettings webSettings = webView.getSettings();
        // 网页内容的宽度是否可大于WebView控件的宽度
        webSettings.setLoadWithOverviewMode(false);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        //是否支持 viewport 标签
        webSettings.setUseWideViewPort(true);
//        // 网页内容的宽度是否可大于WebView控件的宽度
//        webSettings.setLoadWithOverviewMode(false);
        //使用 localstorage 和 sessionstorage
//        webSettings.setDomStorageEnabled(true);
//        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//        // WebView是否支持多个窗口。
//        webSettings.setSupportMultipleWindows(true);
        // 缩放比例 1
        webView.setInitialScale(1);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //排版适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        info = (GankBean.ResultBean) getIntent().getExtras().get("info");
        log(info.toString());
        webView.loadUrl(webUrl = info.getUrl());
        log("from intent" + webUrl);
        parent.addView(webView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        blogDao = AppDatabase.getInstance(WebViewActivity.this).getBlogDao();
        isCollected();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setOptionalIconsVisibility(menu);
        getMenuInflater().inflate(R.menu.web_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isCollected)
            menu.findItem(R.id.collection).setTitle("取消收藏");
        else
            menu.findItem(R.id.collection).setTitle("收藏");
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * 利用方式设置 menu item 图标可见
     *
     * @param menu
     */
    private void setOptionalIconsVisibility(Menu menu) {
        try {
            if (null != menu) {
                Method method = MenuBuilder.class.getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                method.setAccessible(true);
                method.invoke(menu, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:

                break;
            case R.id.collection://收藏
                collectBlog();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 收藏博客
     */
    private void collectBlog() {
        //不用 orm 框架
//        DbManager dbManager = new DbManager(this);
//        List<String> list = dbManager.queryAllBlogs();
//        for (int i = 0; i < list.size(); i++) {
//            log(list.get(i));
//        }
//        if (dbManager.addBlog(webUrl))
//            toast("收藏成功");
        final BlogBean blogBean = new BlogBean(info);
        //第一种方法
//        Completable completable = Completable.fromAction(() ->
//                blogDao.insertSingleBlog(blogBean)
//        );
//        mDisposable.add(completable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(() -> {
//                    toast("收藏成功");
//                }, throwable -> {
//                    toast("收藏失败");
//                }));
        //第二种方法
//        mDisposable.add(Observable.just(1).map(integer ->
//                blogDao.insertSingleBlog(blogBean)
//        )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(this::onCollectSuccess, this::onCollectFailed));
        //第三种方法 由于插入方法中返回值类型不确定，不能将返回值设置为 Flowable<Long>
        //所以，在这几种方法中这是最科学的方法了
        if (!isCollected) {
            addBlogToCollection(blogBean);
        } else
            deleteFromCollection();
        //第四种方法 不用 rxjava
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                BlogBean blogBean = new BlogBean(info);
//                log(blogDao.insertSingleBlog(blogBean));
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                List<BlogBean> blogs = blogDao.getAllBlogs();
//                for (int i = 0; i < blogs.size(); i++) {
//                    log(blogs.get(i).toString());
//                }
//            }
//        }.start();
    }

    /**
     * 判断是否已经收藏
     *
     * @return true 已收藏过
     */
    private boolean isCollected() {
        mDisposable.add(blogDao.queryBlogById(info.get_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(blog -> isCollected = true,
                        throwable -> isCollected = false
                )
        );
        return isCollected;
    }

    /**
     * 插入数据方法
     *
     * @param blogBean 收藏的 blog 对象
     */
    private void addBlogToCollection(BlogBean blogBean) {
        mDisposable.add(Observable.create((ObservableOnSubscribe<Long>) e ->
                e.onNext(blogDao.insertSingleBlog(blogBean))
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onCollectSuccess, this::onCollectFailed));
    }

    /**
     * 删除已经收藏的 blog
     */
    private void deleteFromCollection() {
        mDisposable.add(Observable.create((ObservableOnSubscribe<Integer>) e ->
                e.onNext(blogDao.deleteBlogById(info.get_id())))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result > 0) {
                        toast("取消收藏成功~");
                        isCollected = false;
                    }
                }));
    }

    /**
     * 收藏失败
     */
    private void onCollectFailed(Throwable throwable) {
        toast("收藏失败");
        isCollected = false;
    }

    /**
     * 收藏成功
     *
     * @param id id
     */
    private void onCollectSuccess(Long id) {
        if (id > 0) {
            showSnackBar();
            getAllBlogs();
            isCollected = true;
        }
    }

    private void showSnackBar() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.root), "收藏成功", 1500)
                .setAction("添加标签", v -> {
                    toast("添加标签");
                    startNewActivity(TagActivity.class);
                });
        snackbar.setActionTextColor(Color.WHITE);
        ViewGroup.LayoutParams vL = snackbar.getView().getLayoutParams();
        CoordinatorLayout.LayoutParams newLayoutParams = new CoordinatorLayout.LayoutParams(-1, vL.height);
        newLayoutParams.gravity = Gravity.BOTTOM;
        snackbar.getView().setLayoutParams(newLayoutParams);
        ((TextView) snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(Color.WHITE);
        snackbar.show();
    }

    /**
     * 获取已经收藏的所有博客
     */
    private void getAllBlogs() {
        mDisposable.add(blogDao.findAll().subscribeOn(Schedulers.io())
                .subscribe(list ->
                        Observable.fromIterable(list).subscribe(blog -> log(blog))));
    }

    @Override
    protected void onDestroy() {
//        clearCookies();
        super.onDestroy();
        webView.destroy();
        //mDisposable.clear()也可以
        mDisposable.dispose();
    }

    /**
     * 清除 cookies
     * 21 之前用 cookieSyncManager.sync()
     * 21 之后用 cookieManager.flush()
     */
    private void clearCookies() {
//        webView.clearCache(true);
        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(getApplicationContext());
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncManager.sync();
        } else {
            cookieManager.removeAllCookies(null);
            cookieManager.removeSessionCookies(null);
            cookieManager.flush();
        }
//        String cookie = cookieManager.getCookie(webUrl);
//        log("webview cookie destroy" + cookie);
    }

    /**
     * 打开网页:
     *
     * @param mContext 上下文
     * @param mUrl     要加载的网页url
     * @param mTitle   title
     */
    public static void loadUrl(Context mContext, String mUrl, String mTitle) {
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra("url", mUrl);
        intent.putExtra("mTitle", mTitle);
        mContext.startActivity(intent);
    }

    /**
     * 打开对应网址
     *
     * @param mContext
     * @param info
     */
    public static void loadUrl(Context mContext, GankBean.ResultBean info) {
        Intent intent = new Intent(mContext, WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", info);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    protected void setFinishTransaction() {
        //从底部退出
        overridePendingTransition(0, R.anim.activity_bottom_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
