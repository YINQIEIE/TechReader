package com.yq.eie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yq.eie.R;
import com.yq.eie.activity.MovieDetailActivity;
import com.yq.eie.http.response.MovieBean;
import com.yq.eie.utils.ImgLoadUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Top250MovieAdapter extends RecyclerView.Adapter<Top250MovieAdapter.ItemHolder> {

    private Context mContext;
    private List<MovieBean.SubjectsBean> data;

    public Top250MovieAdapter(Context mContext, List<MovieBean.SubjectsBean> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_movie_top_250, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        MovieBean.SubjectsBean bean = data.get(position);
        holder.setBean(bean);
        ImgLoadUtil.displayImage(mContext, bean.getImages().getLarge(), holder.ivPhoto);
        holder.tvName.setText(bean.getTitle());
        holder.tvRate.setText("评分：" + bean.getRating().getAverage());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_rate)
        TextView tvRate;

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

