package com.yq.eie.fragments;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.yq.eie.R;
import com.yq.eie.base.BaseListFragment;
import com.yq.eie.entity.DetaisInfo;
import com.yq.eie.entity.Folder;
import com.yq.eie.utils.MusicUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by yinqi on 2017/9/12.
 * 本地音乐列表界面
 */

public class LocalFolderFragment extends BaseListFragment<Folder> {

    @Override
    protected void initAdapter() {
        adapter = new CommonAdapter<Folder>(getActivity(), R.layout.item_artist_album, datas) {
            @Override
            protected void convert(ViewHolder holder, final Folder folder, int position) {
                ImageView iv = holder.getView(R.id.iv_icon);
                iv.setScaleType(ImageView.ScaleType.CENTER);
                iv.setImageResource(R.mipmap.icon_directory);
//                holder.setImageResource(R.id.iv_icon, R.mipmap.icon_directory);
                holder.setText(R.id.tv_title, folder.getName());
                holder.setText(R.id.tv_subTitle, String.valueOf(folder.getCount()) + "首");
                holder.setText(R.id.tv_subTitle1, folder.getPath());
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DetaisInfo detaisInfo = new DetaisInfo(3, folder.getName(), folder.getPath());
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
    protected List<Folder> doInBackgroud() {
        return MusicUtil.getFolders(getActivity());
    }

    @Override
    protected void onResult(List<Folder> result) {
        datas.addAll(result);
        adapter.notifyDataSetChanged();
    }

}
