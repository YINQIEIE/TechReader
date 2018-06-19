package com.yq.eie.view;

/**
 * Created by Administrator on 2018/4/2.
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HandleExceptionViewPager extends ViewPager {

    public HandleExceptionViewPager(Context context) {
        super(context);
    }

    public HandleExceptionViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }
}