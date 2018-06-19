package com.yq.eie.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by yinqi on 2017/9/12.
 */

public class LocalMusicAdapter<T> extends MultiItemTypeAdapter<T> {

    public LocalMusicAdapter(Context context, List<T> datas) {
        super(context, datas);
    }
}
