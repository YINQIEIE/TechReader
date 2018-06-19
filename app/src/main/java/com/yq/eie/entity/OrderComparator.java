package com.yq.eie.entity;

import android.text.TextUtils;

import java.util.Comparator;

/**
 * Created by yinqi on 2017/9/18.
 */

public class OrderComparator<T extends SortBase> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        String py1 = o1.getFirstChar();
        String py2 = o2.getFirstChar();
        // 判断是否为空""
        if (TextUtils.isEmpty(py1) && TextUtils.isEmpty(py2))
            return 0;
        if (TextUtils.isEmpty(py1))
            return -1;
        if (TextUtils.isEmpty(py2))
            return 1;
        return py1.compareTo(py2);
    }
}
