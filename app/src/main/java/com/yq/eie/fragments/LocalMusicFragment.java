package com.yq.eie.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yq.eie.R;
import com.yq.eie.adapter.MyFragmentAdapter;
import com.yq.eie.base.BaseFragment;
import com.yq.eie.base.BaseListFragment;
import com.yq.eie.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * Created by yinqi on 2017/9/12.
 * 本地音乐列表界面
 */

public class LocalMusicFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.vp_local)
    ViewPager vpLocal;

    @BindArray(R.array.titles_local_music)
    String[] titles;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private List<BaseListFragment> fragments = new ArrayList<>();
    private MyFragmentAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_local_music;
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

        tvTitle.setText("我的音乐");

        fragments.add(new LocalSongsFragment());
        fragments.add(new LocalArtistFragment());
        fragments.add(new LocalAlbumFragment());
        fragments.add(new LocalFolderFragment());

        adapter = new MyFragmentAdapter(getChildFragmentManager(), fragments, titles);
        vpLocal.setOffscreenPageLimit(fragments.size() - 1);
        vpLocal.setAdapter(adapter);
        vpLocal.setCurrentItem(mContext.getIntent().getIntExtra("position", 0));
        tabLayout.setupWithViewPager(vpLocal);

    }


}
