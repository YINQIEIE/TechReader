package com.yq.eie.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yq.eie.R;

/**
 * Created by Administrator on 2018/1/11.
 */

public class FooterView extends LinearLayout {

    private ImageView ivLoading;

    public FooterView(Context context) {
        this(context, null);
    }

    public FooterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayoutParams(new LayoutParams(-1, ViewGroup.LayoutParams.WRAP_CONTENT));
        View view = LayoutInflater.from(context).inflate(R.layout.layout_loading, this, false);
        this.addView(view, new LinearLayout.LayoutParams(-1, -1));
        ivLoading = findViewById(R.id.iv_loading);
        setVisibility(GONE);
//        View.inflate(context, R.layout.layout_loading, this);
    }

    public void loadComplete() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                setVisibility(View.GONE);
            }
        }, 1000);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivLoading.getDrawable();
        if (visibility == View.VISIBLE) {
            if (!animationDrawable.isRunning())
                animationDrawable.start();
        } else if (visibility == View.GONE) {
            if (animationDrawable.isRunning())
                animationDrawable.stop();
        }
    }

}
