package com.yq.eie.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;
import com.yq.eie.MainActivity;
import com.yq.eie.R;
import com.yq.eie.activity.WebViewActivity;
import com.yq.eie.adapter.EveryDayRecAdapter;
import com.yq.eie.base.BaseLoadFragment;
import com.yq.eie.http.RetrofitManager;
import com.yq.eie.http.response.BannerBean;
import com.yq.eie.http.response.GankBean;
import com.yq.eie.utils.ConstantsImageUrl;
import com.yq.eie.utils.ImgLoadUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/1/3.
 * 每日推荐
 */

public class EveryDayRecFragment extends BaseLoadFragment {

    @BindView(R.id.rv)
    RecyclerView rv;

    List<List<GankBean.ResultBean>> data;

    //    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.iv_loading)
    ImageView ivLoading;
    @BindView(R.id.loadingView)
    LinearLayout loadingView;

    private EveryDayRecAdapter gankAdapter;
    private List<String> orderList = Arrays.asList("Android", "福利", "iOS", "休息视频", "瞎推荐", "前端");
    private View headerView;
    private List<BannerBean.PicBean> bannerUrls;
    private AnimationDrawable drawable;
    private Calendar calendar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_every_day;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initHeaderView();
        data = new ArrayList<>();
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        gankAdapter = new EveryDayRecAdapter(getActivity(), data);
        gankAdapter.addHeaderView(headerView);
        rv.setAdapter(gankAdapter);
        drawable = (AnimationDrawable) ivLoading.getDrawable();
        if (!drawable.isRunning())
            drawable.start();
    }

    private void initHeaderView() {
//        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.header_item_everyday, null);
        HeaderViewHolder headerViewHolder = new HeaderViewHolder();
        banner = headerViewHolder.banner;

    }

    @Override
    protected void getData() {
        calendar = Calendar.getInstance();
//        getContent();
        getBannerInfo();
    }

    private void getBannerInfo() {
        Call<BannerBean> bannerCall = RetrofitManager.getBannerHttpService().getBannerUrls();
        bannerCall.enqueue(new Callback<BannerBean>() {
            @Override
            public void onResponse(Call<BannerBean> call, Response<BannerBean> response) {
                BannerBean body = response.body();
                bannerUrls = body.getPic();
                log(body);
                for (int i = 0; i < bannerUrls.size(); i++) {
                    log(bannerUrls.get(i).getRandpic());
                }
                banner.setImages(bannerUrls).start();
            }

            @Override
            public void onFailure(Call<BannerBean> call, Throwable t) {
                log("banner failed" + t.getMessage() + "\n" + t.getCause() + "/n" + t.getStackTrace());
            }
        });
    }

    /**
     * 获取各种资讯
     */
    private void getContent() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        Call<GankBean<Map<String, List<GankBean.ResultBean>>>> gankCall = RetrofitManager.getGankHttpService().getGankIoDay(year, month, day);
        gankCall.enqueue(new Callback<GankBean<Map<String, List<GankBean.ResultBean>>>>() {
            @Override
            public void onResponse(Call<GankBean<Map<String, List<GankBean.ResultBean>>>> call, Response<GankBean<Map<String, List<GankBean.ResultBean>>>> response) {
                GankBean gankBean = response.body();
                if (gankBean.getCategory() == null || gankBean.getCategory().size() == 0) {
                    calendar.add(Calendar.DATE, -1);
                    getContent();
                    return;
                }
                Map<String, List<GankBean.ResultBean>> map = (Map<String, List<GankBean.ResultBean>>) gankBean.getResults();
                String groupName;
                data.clear();
                for (int i = 0; i < orderList.size(); i++) {
                    groupName = orderList.get(i);
                    List<GankBean.ResultBean> list = map.get(groupName);
                    if (null != list)
                        addUrlList(data, list, groupName);
                }
                gankAdapter.notifyDataSetChanged();
                if (drawable.isRunning())
                    drawable.stop();
                rv.setVisibility(View.VISIBLE);
                loadingView.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GankBean<Map<String, List<GankBean.ResultBean>>>> call, Throwable t) {
                log("failure" + t.getMessage() + t.getCause());
            }
        });
    }


    // subList没有实现序列化！缓存时会出错！
    private void addUrlList(List<List<GankBean.ResultBean>> lists, List<GankBean.ResultBean> arrayList, String typeTitle) {
        GankBean.ResultBean bean = new GankBean.ResultBean();
        bean.setName(typeTitle);
        ArrayList<GankBean.ResultBean> androidBeen = new ArrayList<>();
        androidBeen.add(bean);
        lists.add(androidBeen);

        int androidSize = arrayList.size();

        if (androidSize > 0 && androidSize < 4) {

            lists.add(addUrlList(arrayList, androidSize));
        } else if (androidSize >= 4) {

            ArrayList<GankBean.ResultBean> list1 = new ArrayList<>();
            ArrayList<GankBean.ResultBean> list2 = new ArrayList<>();

            for (int i = 0; i < androidSize; i++) {
                if (i < 3) {
                    list1.add(getAndroidBean(arrayList, i, androidSize));
                } else if (i < 6) {
                    list2.add(getAndroidBean(arrayList, i, androidSize));
                }
            }
            lists.add(list1);
            lists.add(list2);
        }
//        for (int i = 0; i < urlList.size(); i++) {
//            log(urlList.get(i));
//        }
    }

    private List<String> urlList = new ArrayList<>();

    private List<GankBean.ResultBean> addUrlList(List<GankBean.ResultBean> arrayList, int androidSize) {
        List<GankBean.ResultBean> tempList = new ArrayList<>();
        for (int i = 0; i < androidSize; i++) {
            GankBean.ResultBean androidBean = new GankBean.ResultBean();
            // 标题
            androidBean.setDesc(arrayList.get(i).getDesc());
            // 类型
            androidBean.setType(arrayList.get(i).getType());
            // 跳转链接
            androidBean.setUrl(arrayList.get(i).getUrl());
            // 随机图的url
            if (androidSize == 1) {
                androidBean.setImageUrl(getImageUrl(ConstantsImageUrl.HOME_ONE_URLS, 1));//一图
            } else if (androidSize == 2) {
                androidBean.setImageUrl(getImageUrl(ConstantsImageUrl.HOME_TWO_URLS, 2));//两图
            } else if (androidSize == 3) {
                androidBean.setImageUrl(getImageUrl(ConstantsImageUrl.HOME_SIX_URLS, 3));//三图
            }
            tempList.add(androidBean);
        }
        return tempList;
    }

    private GankBean.ResultBean getAndroidBean(List<GankBean.ResultBean> arrayList, int i, int androidSize) {

        GankBean.ResultBean resultBean = new GankBean.ResultBean();
        // 标题
        resultBean.setDesc(arrayList.get(i).getDesc());
        // 类型
        resultBean.setType(arrayList.get(i).getType());
        // 跳转链接
        resultBean.setUrl(arrayList.get(i).getUrl());
        // 随机图的url
        if (i < 3) {
            resultBean.setImageUrl(getImageUrl(ConstantsImageUrl.HOME_SIX_URLS, 3));//三小图
        } else if (androidSize == 4) {
            resultBean.setImageUrl(getImageUrl(ConstantsImageUrl.HOME_ONE_URLS, 1));//一图
        } else if (androidSize == 5) {
            resultBean.setImageUrl(getImageUrl(ConstantsImageUrl.HOME_TWO_URLS, 2));//两图
        } else if (androidSize >= 6) {
            resultBean.setImageUrl(getImageUrl(ConstantsImageUrl.HOME_SIX_URLS, 3));//三小图
        }
        return resultBean;
    }

    /**
     * 获取随机位置的图片，去重复
     *
     * @param urls 图片集合
     * @param type 图片类型
     * @return
     */
    private String getImageUrl(String[] urls, int type) {
        String url;
        while (true) {
            url = urls[getRandom(type)];
            if (!urlList.contains(url)) {
                urlList.add(url);
                break;
            }
        }
        return url;
    }

    private int getRandom(int type) {
        int urlLength = 0;
        if (type == 1) {
            urlLength = ConstantsImageUrl.HOME_ONE_URLS.length;
        } else if (type == 2) {
            urlLength = ConstantsImageUrl.HOME_TWO_URLS.length;
        } else if (type == 3) {
            urlLength = ConstantsImageUrl.HOME_SIX_URLS.length;
        }

        Random random = new Random();
        int randomInt = random.nextInt(urlLength);
        return randomInt;
    }

    protected class HeaderViewHolder {

        @BindView(R.id.banner)
        Banner banner;
        @BindView(R.id.tv_xiandu)
        TextView tvXiandu;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.fl_everyday)
        FrameLayout flEveryday;
        @BindView(R.id.tv_movie)
        TextView tvMovie;

        public HeaderViewHolder() {
            headerView = LayoutInflater.from(getActivity()).inflate(R.layout.header_item_everyday, null);
            ButterKnife.bind(this, headerView);
            initBanner();
            setDate();
        }

        private void initBanner() {
            banner.setDelayTime(4000);
            banner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    log("banner >>> " + ((BannerBean.PicBean) path).getRandpic());
                    ImgLoadUtil.displayImage(context, ((BannerBean.PicBean) path).getRandpic(), imageView);
                }
            });
            banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    String detailUrl = bannerUrls.get(position - 1).getCode();
                    if (TextUtils.isEmpty(detailUrl)) {
                        showToast("暂无详情~");
                        return;
                    }
                    intent = new Intent();
                    intent.putExtra("url", detailUrl);
                    startNewActivity(WebViewActivity.class);
                }
            });
        }

        private void setDate() {
            tvDate.setText(String.valueOf(Calendar.getInstance().get(Calendar.DATE)));
        }

        @OnClick({R.id.tv_xiandu, R.id.fl_everyday, R.id.tv_movie})
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_xiandu:
                    intent = new Intent();
                    intent.putExtra("url", "https://gank.io/xiandu");
                    startNewActivity(WebViewActivity.class);
                    break;
                case R.id.fl_everyday:
                    break;
                case R.id.tv_movie:
                    ((MainActivity) getActivity()).changeFragment(2);
                    break;

            }
        }
    }


}
