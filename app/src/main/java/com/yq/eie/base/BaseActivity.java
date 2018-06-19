package com.yq.eie.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.yq.eie.LoadingDialog;
import com.yq.eie.R;
import com.yq.eie.statusbar.StatusBarUtil;

import butterknife.ButterKnife;

/**
 * Created by yinqi on 2017/9/9.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();
    private Intent intent;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        setTheme();
        intent = new Intent();
    }

    protected void setTheme() {
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.themeColor));
    }

    protected abstract int getLayoutResId();

    /**
     * 日志打印
     *
     * @param content 日志内容
     */
    protected void log(Object content) {
        Log.i(TAG, String.valueOf(content));
    }

    protected void startNewActivity(Class<?> clazz) {
        intent.setClass(this, clazz);
        startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        setStartTransaction();
    }

    protected void setStartTransaction() {
        overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);
    }


    @Override
    public void finish() {
        super.finish();
        setFinishTransaction();
    }

    protected void setFinishTransaction() {
        overridePendingTransition(R.anim.activity_left_in, R.anim.activity_right_out);
    }

    protected void showLoadingDialog() {
        if (null == loadingDialog)
            loadingDialog = new LoadingDialog(this);
        if (!loadingDialog.isShowing())
            loadingDialog.show();
    }

    protected void dismissLoadingDialog() {
        if (null != loadingDialog && loadingDialog.isShowing())
            loadingDialog.dismiss();
    }

    Toast toast;

    protected void toast(String content) {
        if (null == toast)
            toast = Toast.makeText(this, content, Toast.LENGTH_LONG);
        else {
//            toast.cancel();
            toast.setText(content);
        }
        toast.show();
    }

}
