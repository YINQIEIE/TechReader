package com.yq.eie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yq.eie.R;
import com.yq.eie.activity.WebViewActivity;
import com.yq.eie.http.response.PersonBean;
import com.yq.eie.utils.ImgLoadUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActorsAdapter extends BaseAdapter<PersonBean, ActorsAdapter.ItemHolder> {

    private int directorCount = -1;

    public ActorsAdapter(Context mContext, List<PersonBean> personBeans) {
        super(mContext, personBeans);
        directorCount = personBeans.size();
    }

    public void addActors(List<PersonBean> actors) {
        data.addAll(actors);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_list_cast;
    }

    @Override
    public ItemHolder getViewHolder(View view) {
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        PersonBean bean = data.get(position);
//        ImgLoadUtil.displayImage(mContext, bean.getAvatars().getLarge(), holder.ivAvatar);
        ImgLoadUtil.showImg(holder.ivAvatar, bean.getAvatars().getLarge());
        holder.tvName.setText(bean.getName());
        if (position <= directorCount)
            holder.tvType.setText("导演");
        else
            holder.tvType.setText("演员");
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_avatar)
        ImageView ivAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_type)
        TextView tvType;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebViewActivity.loadUrl(mContext, data.get(getAdapterPosition()).getAlt(), "");
                }
            });
        }

    }


}
