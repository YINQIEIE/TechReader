package com.yq.eie.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yq.eie.R;

/**
 * Created by Administrator on 2018/1/11.
 * 刷新头部
 * 在 {@link RefreshHeaderView}的基础上修改为继承 {@link BaseRefreshHeaderView}
 */

public class RefreshHeaderViewWithBase extends BaseRefreshHeaderView {

    private ImageView ivLoading;
    private TextView tv_msg;
    private AnimationDrawable animationDrawable;

    public RefreshHeaderViewWithBase(Context context) {
        this(context, null);
    }

    public RefreshHeaderViewWithBase(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_refresh;
    }

    @Override
    protected void init() {
        ivLoading = findViewById(R.id.iv_loading);
        tv_msg = findViewById(R.id.tv_msg);
        animationDrawable = (AnimationDrawable) ivLoading.getDrawable();
    }


    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == View.VISIBLE) {
            if (!animationDrawable.isRunning())
                animationDrawable.start();
        } else if (visibility == View.GONE) {
            if (animationDrawable.isRunning())
                animationDrawable.stop();
        }
    }

    /**
     * 设置成最初的状态
     * 动画停止，文字修改为 “下拉刷新”
     * 对应状态 {@link #STATE_NORMAL}
     */
    public void reset() {
        if (animationDrawable.isRunning())
            animationDrawable.stop();
        tv_msg.setText("下拉刷新");
    }

    /**
     * 释放刷新阶段
     * 对应状态 {@link #STATE_RELEASE_TO_REFRESH}
     */
    public void onReleaseToRefresh() {
        tv_msg.setText("松开刷新");
    }

    /**
     * 刷新动作开始
     * 对应状态 {@link #STATE_REFRESH_START}
     */
    public void onRefreshStart() {
        animationDrawable.start();
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
        animationDrawable.stop();
        startReleaseAnim();
    }

}
