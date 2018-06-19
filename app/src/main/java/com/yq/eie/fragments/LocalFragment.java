package com.yq.eie.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yq.eie.R;
import com.yq.eie.activity.LocalMusicActivity;
import com.yq.eie.adapter.itemviewdelegate.LocalDelegateOne;
import com.yq.eie.adapter.itemviewdelegate.LocalDelegateTwo;
import com.yq.eie.base.BaseFragment;
import com.yq.eie.entity.Artist;
import com.yq.eie.entity.LocalInfo;
import com.yq.eie.entity.Song;
import com.yq.eie.utils.MusicUtil;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yinqi on 2017/9/11.
 */

public class LocalFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<LocalInfo> infos = new ArrayList<>();
    private MultiItemTypeAdapter multiAdapter;
    private List<LocalInfo> menus_one;
    private List<LocalInfo> menus_two;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        infos.add(new LocalInfo(getString(R.string.local_music), 0, R.mipmap.music_icn_local));
        infos.add(new LocalInfo(getString(R.string.artist), 0, R.mipmap.music_icn_artist));
        infos.add(new LocalInfo(getString(R.string.recent_play), 0, R.mipmap.music_icn_recent));
        infos.add(new LocalInfo(getString(R.string.download_manage), 0, R.mipmap.music_icn_dld));
        infos.add(new LocalInfo("我的歌单", 0, 0));
        infos.add(new LocalInfo("我的收藏歌单", 0, 0));

        //几个 title
        multiAdapter = new MultiItemTypeAdapter(getActivity(), infos);
        multiAdapter.addItemViewDelegate(new LocalDelegateOne());

        //我的歌单具体信息设置
        menus_one = new ArrayList<>();
        menus_one.add(new LocalInfo("流行", 100, R.mipmap.icon_menu_created));
        menus_one.add(new LocalInfo("摇滚", 100, R.mipmap.icon_menu_created));
        menus_one.add(new LocalInfo("古典", 100, R.mipmap.icon_menu_created));
        multiAdapter.addItemViewDelegate(new LocalDelegateTwo(getActivity(), menus_one) {
            @Override
            public boolean isForViewType(LocalInfo item, int position) {
                return position == 4;
            }
        });

        //收藏的歌单具体信息设置
        menus_two = new ArrayList<>();
        menus_two.add(new LocalInfo("歌单一", 100, R.mipmap.icon_menu_fav));
        menus_two.add(new LocalInfo("歌单二", 100, R.mipmap.icon_menu_fav));
        multiAdapter.addItemViewDelegate(new LocalDelegateTwo(getActivity(), menus_two) {
            @Override
            public boolean isForViewType(LocalInfo item, int position) {
                return position == 5;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(multiAdapter);
        multiAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (position < 2) {
                    Intent intent = new Intent(getActivity(), LocalMusicActivity.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        //获取歌曲信息
        new GetSongsTask(getActivity()).execute();
    }

    public class GetSongsTask extends AsyncTask<Void, Void, List<Song>> {

        private Context context;

        public GetSongsTask(Context context) {
            this.context = context;
        }

        @Override
        protected List<Song> doInBackground(Void... voids) {
            List<Artist> artists = MusicUtil.getArtists(getActivity());
            infos.get(1).setCount(artists.size());
            for (Artist artist : artists) {
                log(artist.toString());
            }
            return MusicUtil.getSongList(context);
        }

        @Override
        protected void onPostExecute(List<Song> songs) {
            infos.get(0).setCount(songs.size());
            multiAdapter.notifyItemChanged(0);
            multiAdapter.notifyItemChanged(1);

        }

    }


}
