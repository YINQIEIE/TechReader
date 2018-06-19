package com.yq.eie.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.yq.eie.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2018/1/11.
 * 刷新头部
 */

public class ClassicRefreshHeaderView extends BaseRefreshHeaderView {

    private ImageView ivLoading;
    private TextView tv_msg;
    private TextView tv_time;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ClassicRefreshHeaderView(Context context) {
        this(context, null);
    }

    public ClassicRefreshHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_refresh_with_arrow;
    }

    @Override
    protected void init() {
        ivLoading = findViewById(R.id.iv_loading);
        tv_msg = findViewById(R.id.tv_msg);
        tv_time = findViewById(R.id.tv_time);
    }

    /**
     * 设置成最初的状态
     * 动画停止，文字修改为 “下拉刷新”
     * 对应状态 {@link #STATE_NORMAL}
     */
    public void reset() {
        ivLoading.setRotation(0);
        tv_msg.setText("下拉刷新");
        setLastRefreshTime();
    }

    private void setLastRefreshTime() {
        tv_time.setText(String.format(Locale.getDefault(), "上次刷新时间：%s", sdf.format(new Date())));
    }

    /**
     * 释放刷新阶段
     * 对应状态 {@link #STATE_RELEASE_TO_REFRESH}
     */
    public void onReleaseToRefresh() {
        ivLoading.setRotation(180);
        tv_msg.setText("松开刷新");
    }

    /**
     * 刷新动作开始
     * 对应状态 {@link #STATE_REFRESH_START}
     */
    public void onRefreshStart() {
    }

    /**
     * 正在刷新
     * 对应状态 {@link #STATE_REFRESHING}
     */
    @Override
    public void onRefreshing() {
        tv_msg.setText("正在刷新...");
    }


    /**
     * 刷新完成回调方法，不对应状态
     * 头部高度变为 0 （即）对应动画执行完后状态修改为 {@link #STATE_NORMAL}
     */
    public void refreshComplete() {
        tv_msg.setText("刷新成功");
        startReleaseAnim();
    }

}
