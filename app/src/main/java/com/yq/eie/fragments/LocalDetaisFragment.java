package com.yq.eie.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yq.eie.R;
import com.yq.eie.entity.DetaisInfo;
import com.yq.eie.entity.Song;
import com.yq.eie.statusbar.StatusBarUtil;
import com.yq.eie.utils.MusicUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by yinqi on 2017/9/14.
 * 点击歌手、专辑、文件夹显示具体包含歌曲信息界面
 */

public class LocalDetaisFragment extends LocalSongsFragment {

    public static final String INFO = "info";
    private DetaisInfo info;

    public static LocalDetaisFragment newInstance(DetaisInfo info) {
        LocalDetaisFragment fragment = new LocalDetaisFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(INFO, info);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_local_detail;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        StatusBarUtil.setColorNoTranslucent(getActivity(), getResources().getColor(R.color.themeColor));
        mContext.setSupportActionBar(toolbar);
        ActionBar actionBar = mContext.getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.actionbar_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.onBackPressed();
            }
        });
        info = (DetaisInfo) getArguments().getSerializable(INFO);
        toolbar.setTitle(info.getTitle());
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected List<Song> doInBackgroud() {
        int type = info.getType();
        String param = info.getParam();
        if (type == 1)//歌手
            return MusicUtil.getSongsByArtistId(mContext, Integer.parseInt(param));
        else if (type == 2)//专辑
            return MusicUtil.getSongsByAlbumId(mContext, Integer.parseInt(param));
        else//文件夹
            return MusicUtil.getSongsByFilePath(mContext, param);
    }


}
