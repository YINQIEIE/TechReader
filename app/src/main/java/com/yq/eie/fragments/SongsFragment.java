package com.yq.eie.fragments;


import android.annotation.TargetApi;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yq.eie.R;
import com.yq.eie.base.BaseFragment;
import com.yq.eie.entity.Song;
import com.yq.eie.utils.BitmapHelper;
import com.yq.eie.utils.MusicUtil;

import java.util.List;

import butterknife.BindView;

public class SongsFragment extends BaseFragment {

    @BindView(R.id.tv_content)
    TextView tv;
    @BindView(R.id.iv)
    ImageView iv;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_songs;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "hello", Toast.LENGTH_LONG).show();
                getSongs();

            }
        });
//        iv.setImageBitmap(BitmapHelper.getRoundCornerBitmapWithBorder(getResources(), R.mipmap.icon_avater, 14));

        iv.setImageBitmap(BitmapHelper.blurBitmap(getActivity().getApplicationContext(), BitmapFactory.decodeResource(getResources(), R.mipmap.icon_avater), 6));
        getSongs();
    }

    private void getSongs() {
        List<Song> songs = MusicUtil.getSongList(getActivity());
        if (songs.isEmpty()) {
            log("songs is empty");
            return;
        }
        for (Song song : songs) {
            log(song.toString());
        }
    }

}

