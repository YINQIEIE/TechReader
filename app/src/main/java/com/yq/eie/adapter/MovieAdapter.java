package com.yq.eie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.yq.eie.R;
import com.yq.eie.activity.MovieDetailActivity;
import com.yq.eie.http.response.MovieBean;
import com.yq.eie.utils.ImgLoadUtil;
import com.yq.eie.utils.MovieUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ItemHolder> {

    private Context mContext;
    private List<MovieBean.SubjectsBean> data;
    private int lastAnimPostion = 0;

    public MovieAdapter(Context mContext, List<MovieBean.SubjectsBean> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_movie, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        if (position > lastAnimPostion) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_rv_item);
            holder.itemView.startAnimation(animation);
        }
        lastAnimPostion = position;
        MovieBean.SubjectsBean bean = data.get(position);
        holder.setBean(bean);
        ImgLoadUtil.displayImage(mContext, bean.getImages().getLarge(), holder.ivPhoto);
        holder.tvTitle.setText(bean.getTitle());
        holder.tvDirectors.setText(MovieUtil.getFormatName(bean.getDirectors()));
        holder.tvCasts.setText(MovieUtil.getFormatName(bean.getCasts()));
        holder.tvGenres.setText(String.format("类型：%s", MovieUtil.getFormatString(bean.getGenres())));
        holder.tvRatingRate.setText(String.format("评分：%s", bean.getRating().getAverage() + ""));
    }

    @Override
    public void onViewDetachedFromWindow(ItemHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_directors)
        TextView tvDirectors;
        @BindView(R.id.tv_casts)
        TextView tvCasts;
        @BindView(R.id.tv_genres)
        TextView tvGenres;
        @BindView(R.id.tv_rating_rate)
        TextView tvRatingRate;

        MovieBean.SubjectsBean bean;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieDetailActivity.start(mContext, bean, ivPhoto);
                }
            });
        }

        public void setBean(MovieBean.SubjectsBean bean) {
            this.bean = bean;
        }

    }

}
