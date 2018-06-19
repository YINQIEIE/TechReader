package com.yq.eie.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.yq.eie.R;
import com.yq.eie.adapter.ViewPagerAdapter;
import com.yq.eie.base.BaseActivity;
import com.yq.eie.utils.BitmapHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PicViewActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tv_indicator)
    TextView tvIndicator;
    @BindView(R.id.tv_save)
    TextView tvSave;

    private ViewPagerAdapter adapter;
    private List<String> pics;
    private int currentPosition;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pic_view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        initData();
    }

    private void initData() {
        pics = getIntent().getStringArrayListExtra("pics");
        int pos = getIntent().getIntExtra("position", 0);
        if (null == pics)
            return;
        adapter = new ViewPagerAdapter(this, pics);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPositionInfo(position);
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        String url = pics.get(currentPosition);
                        final String cachePath = getGlideCachePath(url);
                        if (TextUtils.isEmpty(cachePath))
                            return;
                        log(cachePath);
                        Bitmap bitmap = BitmapFactory.decodeFile(cachePath);
                        final File saveFile = BitmapHelper.saveBitmap(PicViewActivity.this, bitmap);
                        if (null != saveFile && saveFile.exists()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PicViewActivity.this, "图片已保存至：" + saveFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                                }
                            });
                        } else
                            Toast.makeText(PicViewActivity.this, "图片已保存至", Toast.LENGTH_LONG).show();
                    }
                }.start();
            }
        });
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);
    }

    /**
     * 设置当前位置信息
     *
     * @param position 当前位置
     */
    private void setPositionInfo(int position) {
        currentPosition = position;
        tvIndicator.setText(position + 1 + "/" + pics.size());
        getGlideCachePath("");
    }

    /**
     * 获取 glide 图片缓存路径
     *
     * @param url 图片地址
     * @return glide 缓存路径
     */
    private String getGlideCachePath(String url) {
        String cachePath = null;
        FutureTarget<File> fileFutureTarget = Glide.with(this).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        if (null != fileFutureTarget) {
            try {
                File file = fileFutureTarget.get();
                cachePath = file.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cachePath;
    }

    public static void start(Context context, List<String> pics, int position) {
        Intent intent = new Intent(context, PicViewActivity.class);
        intent.putStringArrayListExtra("pics", (ArrayList) pics);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

}
