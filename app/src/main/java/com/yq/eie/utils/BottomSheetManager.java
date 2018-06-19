package com.yq.eie.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yq.eie.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 */

public class BottomSheetManager {

    public static BottomSheetManager manager = new BottomSheetManager();

    public View getGankKindSheetView(Context context) {
        View sheet = View.inflate(context, R.layout.layout_gank_sheet, null);
        RecyclerView rvKind = sheet.findViewById(R.id.rv_gank_kind);
//                tv.setCompoundDrawables(new BitmapDrawable(getActivity().getResources(), BitmapFactory.decodeResource()));
//                new BottomSheet.Builder(getActivity(), R.style.BottomSheet_StyleDialog).title("选择分类").sheet(R.menu.gank_bottomsheet).listener(new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case R.id.gank_all:
//                                if (isOtherType("全部")) {
//                                    txName.setText("全部");
//                                    mType = "all";// 全部传 all
//                                    mPage = 1;
//                                    mAndroidAdapter.clear();
//                                    SPUtils.putString("gank_cala", "全部");
//                                    showLoading();
//                                    loadCustomData();
//                                }
//                                break;
//                            case R.id.gank_ios:
//                                if (isOtherType("IOS")) {
//                                    txName.setText("IOS");
//                                    mType = "iOS";// 这里有严格大小写
//                                    mPage = 1;
//                                    mAndroidAdapter.clear();
//                                    SPUtils.putString("gank_cala", "IOS");
//                                    showLoading();
//                                    loadCustomData();
//                                }
//                                break;
//                            case R.id.gank_qian:
//                                if (isOtherType("前端")) {
//                                    changeContent(txName, "前端");
//                                }
//                                break;
//                            case R.id.gank_app:
//                                if (isOtherType("App")) {
//                                    changeContent(txName, "App");
//                                }
//                                break;
//                            case R.id.gank_movie:
//                                if (isOtherType("休息视频")) {
//                                    changeContent(txName, "休息视频");
//                                }
//                                break;
//                            case R.id.gank_resouce:
//                                if (isOtherType("拓展资源")) {
//                                    changeContent(txName, "拓展资源");
//                                }
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//                }).show();
        final List<ItemBean> list = new ArrayList<>();
        list.add(new ItemBean("all", "全部", R.drawable.home_title_all));
        list.add(new ItemBean("iOS", "IOS", R.drawable.home_title_ios));
        list.add(new ItemBean("App", "Android", R.drawable.home_title_app));
        list.add(new ItemBean("前端", "前端", R.drawable.home_title_qian));
        list.add(new ItemBean("休息视频", "休息视频", R.drawable.home_title_movie));
        list.add(new ItemBean("拓展资源", "拓展资源", R.drawable.home_title_source));

        CommonAdapter<ItemBean> adapter = new CommonAdapter<ItemBean>(context, R.layout.item_gank_sheet, list) {
            @Override
            protected void convert(ViewHolder holder, ItemBean itemBean, final int position) {
                TextView tv = holder.getView(R.id.tv_kind);
                tv.setText(list.get(position).name);
                //效果不好
//                        tv.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(getActivity().getResources(), BitmapFactory.decodeResource(getActivity().getResources(), list.get(position).resId)), null, null, null);
                ((ImageView) holder.getView(R.id.iv_logo)).setImageResource(list.get(position).resId);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != gankKindItemClickListener)
                            gankKindItemClickListener.onItemClick(list.get(position));
                    }
                });
            }
        };
        rvKind.setLayoutManager(new LinearLayoutManager(context));
        rvKind.setAdapter(adapter);

        return sheet;
    }


    public static class ItemBean {
        String requestName;//请求时的name
        String name;
        int resId;

        public ItemBean(String name, int resId) {
            this.name = name;
            this.resId = resId;
        }

        public ItemBean(String requestName, String name, int resId) {
            this.requestName = requestName;
            this.name = name;
            this.resId = resId;
        }

        public String getRequestName() {
            return requestName;
        }

        public void setRequestName(String requestName) {
            this.requestName = requestName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getResId() {
            return resId;
        }

        public void setResId(int resId) {
            this.resId = resId;
        }
    }

    public interface OnGankKindItemClickListener {
        void onItemClick(ItemBean bean);
    }

    public void setGankKindItemClickListener(OnGankKindItemClickListener gankKindItemClickListener) {
        this.gankKindItemClickListener = gankKindItemClickListener;
    }

    private OnGankKindItemClickListener gankKindItemClickListener;
}
