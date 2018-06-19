package com.yq.eie.view;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.yq.eie.view.RefreshHeaderView.STATE_REFRESH_START;

/**
 * Created by yinqi on 2018/4/27.
 */

public interface RefreshInterface {
    int STATE_NORMAL = 0;
    int STATE_RELEASE_TO_REFRESH = 1;
    int STATE_REFRESHING = 2;

    @IntDef(value = {STATE_NORMAL, STATE_RELEASE_TO_REFRESH, STATE_REFRESHING})
    @Retention(RetentionPolicy.SOURCE)
    @interface StateRange {
    }

    void reset();

    void onReleaseToRefresh();

    void onRefreshing();

    void refreshComplete();
}
