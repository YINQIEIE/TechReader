package com.yq.eie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yq.eie.R;
import com.yq.eie.activity.WebViewActivity;
import com.yq.eie.http.response.GankBean;
import com.yq.eie.utils.ImgLoadUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/10.
 * 干货定制
 */

public class AndroidAdapter extends RecyclerView.Adapter<AndroidAdapter.ItemHolder> {

    private Context mContext;
    private List<GankBean.ResultBean> data;

    public AndroidAdapter(Context mContext, List<GankBean.ResultBean> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gank_child, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        //占位图会导致图片失真
//        Glide.with(mContext).load(data.get(position).getUrl()).crossFade(1000).into(holder.imageView);
//        ImgLoadUtil.displayImage(mContext, data.get(position).getUrl(), holder.imageView);
        onBind(holder, position);
    }

    private void onBind(ItemHolder holder, int position) {
        final GankBean.ResultBean gank = data.get(position);
        if ("福利".equals(gank.getType())) {//福利只显示图片
            holder.ivWelfare.setVisibility(View.VISIBLE);
            holder.llAndroid.setVisibility(View.GONE);
            ImgLoadUtil.displayImage(mContext, data.get(position).getUrl(), holder.ivWelfare);
        } else {
            holder.ivWelfare.setVisibility(View.GONE);
            holder.llAndroid.setVisibility(View.VISIBLE);
            holder.tvAndroidDes.setText(gank.getDesc());
        }
        if (gank.getImages() != null && !gank.getImages().isEmpty() && !TextUtils.isEmpty(gank.getImages().get(0))) {
            holder.ivAndroidPic.setVisibility(View.VISIBLE);
            ImgLoadUtil.displayGif(gank.getImages().get(0), holder.ivAndroidPic);
        } else {
            holder.ivAndroidPic.setVisibility(View.GONE);
        }
        holder.tvWho.setText(TextUtils.isEmpty(gank.getWho()) ? "佚名" : gank.getWho());
        holder.tvTime.setText(gank.getCreatedAt().substring(0, 10));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                WebViewActivity.loadUrl(mContext, gank.getUrl(), gank.getName());
                WebViewActivity.loadUrl(mContext, gank);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_welfare)
        ImageView ivWelfare;//整张图片
        @BindView(R.id.tv_android_des)
        TextView tvAndroidDes;//android文章内容
        @BindView(R.id.iv_android_pic)
        ImageView ivAndroidPic;//对应图片
        @BindView(R.id.ll_android)
        LinearLayout llAndroid;//android 文章部分布局
        @BindView(R.id.tv_who)
        TextView tvWho;//作者
        @BindView(R.id.tv_content)
        TextView tvContent;//
        @BindView(R.id.tv_time)
        TextView tvTime;//时间

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


}
