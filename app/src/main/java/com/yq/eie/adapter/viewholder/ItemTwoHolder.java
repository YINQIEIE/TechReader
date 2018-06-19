package com.yq.eie.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yq.eie.R;
import com.yq.eie.adapter.BaseViewHolder;
import com.yq.eie.http.response.GankBean;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 轮播图
 */
public class ItemTwoHolder extends BaseViewHolder {


    @BindView(R.id.iv_one)
    ImageView ivOne;
    @BindView(R.id.tv_title_one)
    TextView tvTitleOne;
    @BindView(R.id.ll_one)
    LinearLayout llOne;
    @BindView(R.id.iv_two)
    ImageView ivTwo;
    @BindView(R.id.tv_title_two)
    TextView tvTitleTwo;
    @BindView(R.id.ll_two)
    LinearLayout llTwo;

    public ItemTwoHolder(ViewGroup viewGroup) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_everyday_two, viewGroup, false));
    }

    @OnClick({R.id.ll_one, R.id.ll_two})
    void onClick(View view) {
        int index = -1;
        switch (view.getId()) {
            case R.id.ll_one:
                index = 0;
                break;
            case R.id.ll_two:
                index = 1;
                break;
        }
        goToDetails(list.get(index).getUrl());
    }

    @Override
    public void onBindViewHolder(List<GankBean.ResultBean> list, int position) {
        this.list = list;
        displayRandomImg(2, 0, ivOne, list);
        displayRandomImg(2, 1, ivTwo, list);
        setDes(list, 0, tvTitleOne);
        setDes(list, 1, tvTitleTwo);
    }


}
