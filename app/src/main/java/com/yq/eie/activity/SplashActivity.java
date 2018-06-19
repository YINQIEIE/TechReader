package com.yq.eie.activity;

import android.os.Bundle;
import android.os.Handler;

import com.yq.eie.MainActivity;
import com.yq.eie.R;
import com.yq.eie.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startNewActivity(MainActivity.class);
                finish();
                setStartTransaction();
            }
        }, 3000);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setStartTransaction() {
        overridePendingTransition(R.anim.activity_zoom_in, R.anim.activity_zoom_out);
    }

    @Override
    protected void setFinishTransaction() {

    }
}
