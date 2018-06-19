package com.yq.eie.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yq.eie.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yinqi on 2017/9/9.
 */

public abstract class BaseFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();
    Unbinder unbinder;
    protected Intent intent;
    protected AppCompatActivity mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    protected abstract int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 日志打印
     *
     * @param content
     */
    public void log(String content) {
        Log.i(TAG, content);
    }

    public void log(Object content) {
        log(String.valueOf(content));
    }

    protected void showToast(String toast) {
        Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
    }

    protected void startNewActivity(Class<?> clazz) {
        intent.setClass(getActivity(), clazz);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);
    }

}
