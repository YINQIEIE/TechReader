package com.yq.eie.fragments;

import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.yq.eie.R;
import com.yq.eie.base.BaseNavListFragment;
import com.yq.eie.entity.Album;
import com.yq.eie.entity.DetaisInfo;
import com.yq.eie.entity.OrderComparator;
import com.yq.eie.utils.BitmapHelper;
import com.yq.eie.utils.MusicUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.Collections;
import java.util.List;

/**
 * Created by yinqi on 2017/9/12.
 * 本地音乐列表界面
 */

public class LocalAlbumFragment extends BaseNavListFragment<Album> {

    @Override
    protected void initAdapter() {
        adapter = new CommonAdapter<Album>(getActivity(), R.layout.item_artist_album, datas) {
            @Override
            protected void convert(ViewHolder holder, final Album album, int position) {
                ImageView ivAlbum = holder.getView(R.id.iv_icon);
                if (!TextUtils.isEmpty(album.getAlbum_art()))
//                    ivAlbum.setImageURI(Uri.parse(album.getAlbum_art()));
//                    ivAlbum.setImageDrawable(Drawable.createFromPath(album.getAlbum_art()));
                    ivAlbum.setImageBitmap(BitmapHelper.getRoundedCornerBitmap(BitmapFactory.decodeFile(album.getAlbum_art()), 100));
                else ivAlbum.setImageResource(R.mipmap.music);
                holder.setText(R.id.tv_title, album.getName());
                holder.setText(R.id.tv_subTitle, album.getArtist());
                holder.setText(R.id.tv_subTitle1, String.valueOf(album.getCount()) + "首");
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DetaisInfo detaisInfo = new DetaisInfo(2, album.getName(), String.valueOf(album.getId()));
                        LocalDetaisFragment fragment = LocalDetaisFragment.newInstance(detaisInfo);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("local"));
                        transaction.addToBackStack(null);
                        transaction.add(R.id.fl_content, fragment).commit();

                    }
                });
            }
        };
    }

    @Override
    protected List<Album> doInBackgroud() {
        return MusicUtil.getAlbums(getActivity());
    }

    @Override
    protected void onResult(List<Album> result) {
        Collections.sort(result, new OrderComparator<Album>());
        for (int i = 0; i < result.size(); i++) {
            if (charMap.get(result.get(i).getFirstChar()) == null)
                charMap.put(result.get(i).getFirstChar(), i);
        }
        datas.addAll(result);
        adapter.notifyDataSetChanged();
    }

}
