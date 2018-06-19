package com.yq.eie.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yq.eie.R;
import com.yq.eie.adapter.MyFragmentAdapter;
import com.yq.eie.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

public class GankFragment extends BaseFragment {

    @BindView(R.id.tabLayout_gank)
    TabLayout tabLayoutGank;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindArray(R.array.titles_gank)
    String[] titles;

    private MyFragmentAdapter fragmentAdapter;
    private List<Fragment> fragments;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        fragments = new ArrayList<>();
        fragments.add(new EveryDayRecFragment());
        fragments.add(new WelfareFragment());
        fragments.add(new GankChildFragment());
        fragments.add(new AndroidFragment());
        fragmentAdapter = new MyFragmentAdapter(getChildFragmentManager(), fragments, titles);
        vpContent.setAdapter(fragmentAdapter);
        vpContent.setOffscreenPageLimit(fragments.size());
        tabLayoutGank.setupWithViewPager(vpContent);
    }


}
