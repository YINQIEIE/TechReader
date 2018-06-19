package com.yq.eie.fragments;

import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.yq.eie.R;
import com.yq.eie.base.BaseNavListFragment;
import com.yq.eie.entity.Artist;
import com.yq.eie.entity.DetaisInfo;
import com.yq.eie.entity.OrderComparator;
import com.yq.eie.utils.MusicUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.Collections;
import java.util.List;

/**
 * Created by yinqi on 2017/9/12.
 * 我的音乐 -> 歌手界面
 */

public class LocalArtistFragment extends BaseNavListFragment<Artist> {

    @Override
    protected void initAdapter() {
        adapter = new CommonAdapter<Artist>(getActivity(), R.layout.item_artist_album, datas) {
            @Override
            protected void convert(ViewHolder holder, final Artist artist, int position) {
                holder.setText(R.id.tv_title, artist.getName());
                holder.setText(R.id.tv_subTitle, artist.getName());
                holder.setText(R.id.tv_subTitle1, String.valueOf(artist.getCount()) + "首");
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DetaisInfo detaisInfo = new DetaisInfo(1, artist.getName(), String.valueOf(artist.getId()));
                        LocalDetaisFragment fragment = LocalDetaisFragment.newInstance(detaisInfo);
                        FragmentTransaction transaction = LocalArtistFragment.this.mContext.getSupportFragmentManager().beginTransaction();
                        transaction.hide(LocalArtistFragment.this.mContext.getSupportFragmentManager().findFragmentByTag("local"));
                        transaction.addToBackStack(null);
                        transaction.add(R.id.fl_content, fragment).commit();

                    }
                });
            }
        };
    }

    @Override
    protected List<Artist> doInBackgroud() {
        return MusicUtil.getArtists(getActivity());
    }

    @Override
    protected void onResult(List<Artist> result) {
        Collections.sort(result, new OrderComparator<Artist>());
        for (int i = 0; i < result.size(); i++) {
            if (charMap.get(result.get(i).getFirstChar()) == null) {
                charMap.put(result.get(i).getFirstChar(), i);
            }
        }
        datas.addAll(result);
        adapter.notifyDataSetChanged();
    }


}
