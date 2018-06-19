package com.yq.eie;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoadingDialog extends Dialog {

    @BindView(R.id.iv_loading)
    ImageView ivLoading;
    @BindView(R.id.loadingView)
    LinearLayout loadingView;
    private AnimationDrawable drawable;

    public LoadingDialog(@NonNull Context context) {
        this(context, 0);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, R.style.LoadingDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loading);
        ButterKnife.bind(this);
        drawable = (AnimationDrawable) ivLoading.getDrawable();
    }

    @Override
    public void show() {
        super.show();
        drawable.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        drawable.stop();
    }

}
