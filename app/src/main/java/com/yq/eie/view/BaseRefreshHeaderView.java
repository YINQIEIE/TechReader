package com.yq.eie.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2018/1/11.
 * 刷新头部
 */

public abstract class BaseRefreshHeaderView extends LinearLayout implements RefreshInterface {

    protected int actualHeight = 0;
    protected int maxHeight = 0;
    protected int currentState = 0;
    public static final float ratio = 1.25f;

    public BaseRefreshHeaderView(Context context) {
        this(context, null);
    }

    public BaseRefreshHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setGravity(Gravity.CENTER);
        setLayoutParams(new LayoutParams(-1, ViewGroup.LayoutParams.WRAP_CONTENT));
        View view = LayoutInflater.from(context).inflate(getLayoutId(), this, false);
        this.addView(view);
        init();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void init();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (maxHeight == 0) {
            actualHeight = getMeasuredHeight();
            maxHeight = (int) (actualHeight * 1.5f);
            ViewGroup.LayoutParams headerLayoutParams = getLayoutParams();
            headerLayoutParams.height = 0;
            setLayoutParams(headerLayoutParams);
        }
    }

    /**
     * 设置刷新头部 height
     *
     * @param deltaY 要改变的距离
     */
    public void onMove(int deltaY) {
        Log.i("refresh deltaY", "deltaY1 = " + deltaY / ratio);
        ViewGroup.LayoutParams headerLayoutParams = getLayoutParams();
        headerLayoutParams.height += deltaY / ratio;
        if (headerLayoutParams.height >= actualHeight) {//高度达到实际高度时设置为松开刷新状态，并且达到最大高度时，高度不再增加
            if (headerLayoutParams.height >= maxHeight)
                headerLayoutParams.height = maxHeight;
            setState(STATE_RELEASE_TO_REFRESH);
        } else {
            if (headerLayoutParams.height < 0)
                headerLayoutParams.height = 0;
            setState(STATE_NORMAL);
        }
        Log.i("refresh", "pull ..." + maxHeight + " headerHeight = " + headerLayoutParams.height);
        setLayoutParams(headerLayoutParams);
    }

    public void onActionUp() {
        if (isReadyToRefresh()) {
            setState(STATE_REFRESHING);
        } else
            startReleaseAnim();
    }

    /**
     * 刷新头部复位动画，即减小刷新头部高度的动画
     */
    protected void startReleaseAnim() {
        ValueAnimator valueAnimator = getHeightAnim();
        if (isRefreshing()) {//当前处于刷新状态，即执行了刷新方法，复位动画延迟 500ms 执行
            valueAnimator.setStartDelay(500);
        }
        valueAnimator.start();
    }

    @NonNull
    protected ValueAnimator getHeightAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(getHeight(), 0);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams headerLayoutParams = getLayoutParams();
                headerLayoutParams.height = (int) (float) animation.getAnimatedValue();
                setLayoutParams(headerLayoutParams);
                if (headerLayoutParams.height == 0)
                    setState(STATE_NORMAL);
            }
        });
        return valueAnimator;
    }

    protected void setState(@StateRange int state) {
        currentState = state;
        switch (state) {
            case STATE_NORMAL://正常
                reset();
                break;
            case STATE_RELEASE_TO_REFRESH:
                onReleaseToRefresh();
                break;
            case STATE_REFRESHING:
                onRefreshing();
                break;
        }
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


}
