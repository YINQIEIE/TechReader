package com.yq.eie.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.yq.eie.R;

import java.util.List;

/**
 * 图片浏览页面适配器
 */
public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    //界面列表
    private List<String> uriList;

    public ViewPagerAdapter(Context mContext, List<String> uriList) {
        if (uriList == null) throw new IllegalArgumentException("list is null");
        this.mContext = mContext;
        this.uriList = uriList;
    }

    /**
     * 获得当前界面数
     */
    @Override
    public int getCount() {
        return uriList.size();
    }

    /**
     * 判断是否由对象生成界面
     */
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

    /**
     * 销毁position位置的界面
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 初始化position位置的界面
     */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_pic_view, container, false);
//        PhotoView photoView = new PhotoView(mContext);
//        photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        displayPhoto(position, view);
        container.addView(view);
        return view;
    }

    private void displayPhoto(final int position, final View view) {
        final PhotoView photoView = view.findViewById(R.id.iv);
        final ProgressBar pb_loading = view.findViewById(R.id.pb_loading);
        final TextView tv_error = view.findViewById(R.id.tv_error);
        final DrawableRequestBuilder<String> requestBuilder = Glide.with(mContext).load(uriList.get(position))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        pb_loading.setVisibility(View.GONE);
                        tv_error.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        pb_loading.setVisibility(View.GONE);
                        return false;
                    }
                });
        tv_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestBuilder.into(photoView);
            }
        });
        requestBuilder.into(photoView);
    }

}
