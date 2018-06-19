package com.yq.eie.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yq.eie.R;
import com.yq.eie.adapter.BaseViewHolder;
import com.yq.eie.http.response.GankBean;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 轮播图
 */
public class ItemOneHolder extends BaseViewHolder {

    @BindView(R.id.iv_one)
    ImageView ivOne;
    @BindView(R.id.tv_title_one)
    TextView tvTitleOne;

    public ItemOneHolder(ViewGroup viewGroup) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_everyday_one, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(List<GankBean.ResultBean> list, int position) {
        this.list = list;
        if ("福利".equals(list.get(0).getType())) {
            tvTitleOne.setVisibility(View.GONE);
            ivOne.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            ImgLoadUtil.displayEspImage(list.get(0).getUrl(), ivOnePhoto, 1);
            Glide.with(ivOne.getContext())
                    .load(list.get(0).getUrl())
                    .crossFade(1500)
                    .placeholder(R.drawable.img_two_bi_one)
                    .error(R.drawable.img_two_bi_one)
                    .into(ivOne);
        } else {
            tvTitleOne.setVisibility(View.VISIBLE);
            setDes(list, 0, tvTitleOne);
            displayRandomImg(1, 0, ivOne, list);
        }
    }

    @OnClick({R.id.ll_one})
    void onClick(View view) {
        int index = -1;
        switch (view.getId()) {
            case R.id.ll_one:
                index = 0;
                break;
        }
        goToDetails(list.get(index).getUrl());
    }

}
