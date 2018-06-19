package com.yq.eie.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yq.eie.R;
import com.yq.eie.adapter.BaseViewHolder;
import com.yq.eie.http.response.GankBean;
import com.yq.eie.utils.CommonUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 轮播图
 */
public class TitleHolder extends BaseViewHolder {

    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.iv_title_type)
    ImageView ivTitleType;
    @BindView(R.id.tv_title_type)
    TextView tvTitleType;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.ll_title_more)
    LinearLayout llTitleMore;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.ll)
    LinearLayout ll;

    public TitleHolder(ViewGroup viewGroup) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_everyday_title, null));
    }

    public void onBindViewHolder(List<GankBean.ResultBean> list, int position) {
        int index = 0;
        String title = list.get(0).getName();
        tvTitleType.setText(title);
        if ("Android".equals(title)) {
            ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_android));
            index = 0;
        } else if ("福利".equals(title)) {
            ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_meizi));
            index = 1;
        } else if ("IOS".equals(title)) {
            ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_ios));
            index = 2;
        } else if ("休息视频".equals(title)) {
            ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_movie));
            index = 2;
        } else if ("拓展资源".equals(title)) {
            ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_source));
            index = 2;
        } else if ("瞎推荐".equals(title)) {
            ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_xia));
            index = 2;
        } else if ("前端".equals(title)) {
            ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_qian));
            index = 2;
        } else if ("App".equals(title)) {
            ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_app));
            index = 2;
        }

        if (position != 0) {
            viewLine.setVisibility(View.VISIBLE);
        } else {
            viewLine.setVisibility(View.GONE);
        }
    }

}
