package com.yq.eie.fragments;

import com.yq.eie.R;
import com.yq.eie.base.BaseNavListFragment;
import com.yq.eie.entity.OrderComparator;
import com.yq.eie.entity.Song;
import com.yq.eie.utils.MusicUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.Collections;
import java.util.List;

/**
 * Created by yinqi on 2017/9/12.
 * 本地音乐列表界面
 */

public class LocalSongsFragment extends BaseNavListFragment<Song> {

    @Override
    protected void initAdapter() {
        adapter = new CommonAdapter<Song>(getActivity(), R.layout.item_song, datas) {
            @Override
            protected void convert(ViewHolder holder, Song song, int position) {
                holder.setText(R.id.tv_title, song.getSongName());
                holder.setText(R.id.tv_subTitle, song.getArtist());
            }
        };

    }

    @Override
    protected List<Song> doInBackgroud() {
        return MusicUtil.getSongList(getActivity());
    }

    @Override
    protected void onResult(List<Song> result) {
        Collections.sort(result, new OrderComparator<Song>());
        for (int i = 0; i < result.size(); i++) {
            if (charMap.get(result.get(i).getFirstChar()) == null)
                charMap.put(result.get(i).getFirstChar(), i);
        }
        datas.addAll(result);
        adapter.notifyDataSetChanged();
    }


}
