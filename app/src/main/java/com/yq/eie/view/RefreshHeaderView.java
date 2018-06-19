package com.yq.eie.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yq.eie.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2018/1/11.
 * 刷新头部
 */

public class RefreshHeaderView extends LinearLayout {

    private ImageView ivLoading;
    private TextView tv_msg;
    private int actualHeight = 0;
    private int maxHeight = 0;
    private int currentState = 0;
    private LinearLayout ll_header;
    private ViewGroup.LayoutParams headerLayoutParams;
    private AnimationDrawable animationDrawable;

    public RefreshHeaderView(Context context) {
        this(context, null);
    }

    public RefreshHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayoutParams(new LayoutParams(-1, ViewGroup.LayoutParams.WRAP_CONTENT));
        View view = LayoutInflater.from(context).inflate(R.layout.layout_refresh, this, false);
        this.addView(view);
        ll_header = view.findViewById(R.id.ll_header);
        headerLayoutParams = ll_header.getLayoutParams();
        ivLoading = findViewById(R.id.iv_loading);
        tv_msg = findViewById(R.id.tv_msg);
        animationDrawable = (AnimationDrawable) ivLoading.getDrawable();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (maxHeight == 0) {
            actualHeight = getMeasuredHeight();
            maxHeight = (int) (actualHeight * 1.5f);
            headerLayoutParams.height = 0;
            ll_header.setLayoutParams(headerLayoutParams);
        }
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
     * 设置刷新头部 height
     *
     * @param deltaY 要改变的距离
     */
    public void onMove(int deltaY) {
        Log.i("recyclerView deltaY", "deltaY1 = " + deltaY);
        headerLayoutParams.height += deltaY;
        if (headerLayoutParams.height >= actualHeight) {//高度达到实际高度时设置为松开刷新状态，并且达到最大高度时，高度不再增加
            if (headerLayoutParams.height >= maxHeight)
                headerLayoutParams.height = maxHeight;
            setState(STATE_RELEASE_TO_REFRESH);
        } else {
            if (headerLayoutParams.height < 0)
                headerLayoutParams.height = 0;
            Log.i("recyclerView", "height < actualHeight");
            setState(STATE_NORMAL);
        }
        Log.i("recyclerView", "pull ..." + maxHeight + " headerHeight = " + headerLayoutParams.height);
        ll_header.setLayoutParams(headerLayoutParams);
    }

    public void onActionUp() {
        if (isReadyToRefresh()) {
            setState(STATE_REFRESHING);
            onRefreshStart();
        } else
            startReleaseAnim();
    }

    /**
     * 刷新头部复位动画，即减小刷新头部高度的动画
     */
    private void startReleaseAnim() {
        Log.i("touch", "headerLayoutParams.height = " + headerLayoutParams.height + " height =" + getHeight());
        ValueAnimator valueAnimator = getHeightAnim();
        if (isRefreshing()) {//当前处于刷新状态，即执行了刷新方法，复位动画延迟 500ms 执行
            valueAnimator.setStartDelay(500);
        }
        valueAnimator.start();
    }

    @NonNull
    private ValueAnimator getHeightAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(getHeight(), 0);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                headerLayoutParams.height = (int) (float) animation.getAnimatedValue();
                ll_header.setLayoutParams(headerLayoutParams);
                if (headerLayoutParams.height == 0)
                    setState(STATE_NORMAL);
            }
        });
        return valueAnimator;
    }

    private void setState(@StateRange int state) {
        currentState = state;
        switch (state) {
            case STATE_NORMAL://正常
                reset();
                break;
            case STATE_RELEASE_TO_REFRESH:
                onReleaseToRefresh();
                break;
            case STATE_REFRESH_START:
                onRefreshStart();
                break;
            case STATE_REFRESHING:
                onRefreshing();
                break;
        }
    }

    /**
     * 设置成最初的状态
     * 动画停止，文字修改为 “下拉刷新”
     * 对应状态 {@link #STATE_NORMAL}
     */
    private void reset() {
        if (animationDrawable.isRunning())
            animationDrawable.stop();
        tv_msg.setText("下拉刷新");
    }

    /**
     * 释放刷新阶段
     * 对应状态 {@link #STATE_RELEASE_TO_REFRESH}
     */
    private void onReleaseToRefresh() {
        tv_msg.setText("松开刷新");
    }

    /**
     * 刷新动作开始
     * 对应状态 {@link #STATE_REFRESH_START}
     */
    private void onRefreshStart() {
        animationDrawable.start();
    }

    /**
     * 正在刷新
     * 对应状态 {@link #STATE_REFRESHING}
     */
    private void onRefreshing() {
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

    /**
     * 松手后是否可以刷新，即当前状态是否为释放刷新状态
     *
     * @return true 释放刷新
     */
    public boolean isReadyToRefresh() {
        return currentState == STATE_RELEASE_TO_REFRESH;
    }

    /**
     * 是否处于正在刷新状态
     * 包括调用刷新完成方法{@link #refreshComplete()}后刷新完成后头部高度变为 0 的过程
     *
     * @return true 正在刷新
     */
    public boolean isRefreshing() {
        return currentState == STATE_REFRESHING;
    }

    public static final int STATE_NORMAL = 0;
    public static final int STATE_RELEASE_TO_REFRESH = 1;
    public static final int STATE_REFRESH_START = 2;
    public static final int STATE_REFRESHING = 3;

    @IntDef(value = {STATE_NORMAL, STATE_RELEASE_TO_REFRESH, STATE_REFRESH_START, STATE_REFRESHING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface StateRange {
    }

}
