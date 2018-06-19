package com.yq.eie.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
public class ItemThreeHolder extends BaseViewHolder {

    @BindView(R.id.iv_one)
    ImageView ivOne;
    @BindView(R.id.tv_title_one)
    TextView tvTitleOne;
    @BindView(R.id.iv_two)
    ImageView ivTwo;
    @BindView(R.id.tv_title_two)
    TextView tvTitleTwo;
    @BindView(R.id.iv_three)
    ImageView ivThree;
    @BindView(R.id.tv_titl_three)
    TextView tvTitlThree;

    public ItemThreeHolder(ViewGroup viewGroup) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_everyday_three, viewGroup, false));
    }

    @OnClick({R.id.ll_one, R.id.ll_two, R.id.ll_three})
    void onClick(View view) {
        int index = -1;
        switch (view.getId()) {
            case R.id.ll_one:
                index = 0;
                break;
            case R.id.ll_two:
                index = 1;
                break;
            case R.id.ll_three:
                index = 2;
                break;
        }
        goToDetails(list.get(index).getUrl());
    }

    @Override
    public void onBindViewHolder(List<GankBean.ResultBean> list, int position) {
        this.list = list;
        displayRandomImg(3, 0, ivOne, list);
        displayRandomImg(3, 1, ivTwo, list);
        displayRandomImg(3, 2, ivThree, list);
        setDes(list, 0, tvTitleOne);
        setDes(list, 1, tvTitleTwo);
        setDes(list, 2, tvTitlThree);
    }


}
