package com.yq.eie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yq.eie.R;
import com.yq.eie.activity.BookDetailActivity;
import com.yq.eie.entity.BookBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookAdapter extends BaseAdapter<BookBean, BookAdapter.ItemHolder> {

    public BookAdapter(Context mContext, List<BookBean> bookBeans) {
        super(mContext, bookBeans);
    }

    @Override
    public ItemHolder getViewHolder(View view) {
        return new ItemHolder(view);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_book_list;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        BookBean bookBean = data.get(position);
        holder.bookBean = bookBean;
        Glide.with(mContext).load(bookBean.getImages().getMedium()).into(holder.ivPhoto);
//        ImgLoadUtil.displayImage(mContext, bookBean.getImage(), holder.ivPhoto);
        holder.tvName.setText(bookBean.getTitle());
        holder.tvRate.setText(String.format("评分：%s", bookBean.getRating().getAverage()));
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_rate)
        TextView tvRate;

        BookBean bookBean;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BookDetailActivity.start(mContext, bookBean, ivPhoto);
                }
            });
        }
    }


}
