package com.yq.eie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yq.eie.activity.WebViewActivity;
import com.yq.eie.http.response.GankBean;
import com.yq.eie.utils.ImgLoadUtil;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/3.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    private Context mContext;
    protected List<GankBean.ResultBean> list;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    public abstract void onBindViewHolder(List<GankBean.ResultBean> list, int position);

    protected void setDes(List<GankBean.ResultBean> object, int position, TextView textView) {
        textView.setText(object.get(position).getDesc());
    }

    protected void displayRandomImg(int imgNumber, int position, ImageView imageView, List<GankBean.ResultBean> object) {
        ImgLoadUtil.displayRandom(imgNumber, object.get(position).getImageUrl(), imageView);
    }

    protected void goToDetails(String url) {
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(mContext, "暂无详情~", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra("url", url);
        mContext.startActivity(intent);
    }


}
