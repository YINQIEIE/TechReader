package com.yq.eie.fragments;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by yinqi on 2017/9/11.
 */

public abstract class GetSongsTask<T> extends AsyncTask<Void, Void, List<T>> {

    private Context context;
    private List<T> datas;

    public GetSongsTask(Context context, List<T> datas) {
        this.context = context;
        this.datas = datas;
    }

    public GetSongsTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<T> doInBackground(Void... voids) {
        return doInBackgroud();
    }

    protected abstract List<T> doInBackgroud();

    @Override
    protected void onPostExecute(List<T> datas) {
        onResult();
    }

    protected abstract void onResult();

}
