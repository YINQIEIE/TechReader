package com.yq.eie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yq.eie.R;
import com.yq.eie.base.BaseActivity;
import com.yq.eie.db.room.AppDatabase;
import com.yq.eie.db.room.BlogTagDao;
import com.yq.eie.db.room.BlogTagEntity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zhy.view.flowlayout.TagView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TagActivity extends BaseActivity {

    public static final String KEY_TAG = "tag";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_done)
    TextView tvDone;
    @BindView(R.id.flowlayout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.et_tag)
    EditText etTag;
    @BindView(R.id.rv_tags)
    RecyclerView rvTags;
    private List<BlogTagEntity> tagList = new ArrayList<>();
    private List<SpannableString> searchTagList = new ArrayList<>();
    private CommonAdapter rvAdapter;

    private CompositeDisposable mDisposable = new CompositeDisposable();
    private BlogTagDao blogTagDao = AppDatabase.getInstance(this).getTagDao();
    //输入标签时给搜索结果显示字体设置搜索关键字颜色
    SpannableString spannableString;
    ForegroundColorSpan colorSpan;
    //区分是输入还是直接选择已有标签
    private boolean isChooseMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initTags();
        initRv();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("编辑标签");
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initTags() {
        TagAdapter<BlogTagEntity> adapter;
        mFlowLayout.setAdapter(adapter = new TagAdapter<BlogTagEntity>(tagList) {

            @Override
            public View getView(FlowLayout parent, int position, BlogTagEntity tagEntity) {
                TextView tv = (TextView) LayoutInflater.from(TagActivity.this).inflate(R.layout.tag, mFlowLayout, false);
                tv.setText(tagEntity.getTagName());
                return tv;
            }
        });
        mDisposable.add(Observable.create((ObservableOnSubscribe<List<BlogTagEntity>>) e ->
                e.onNext(AppDatabase.getInstance(this).getTagDao().findAll())
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    tagList.addAll(list);
                    adapter.notifyDataChanged();
                }));
        etTag.setOnClickListener(v -> isChooseMode = false);
        mFlowLayout.setOnTagClickListener((view, position, parent) -> {
            isChooseMode = true;
            if (((TagView) view).isChecked())
                changeTag(tagList.get(position).getTagName());
            else
                changeTag("");
            return false;
        });
    }

    private void initRv() {
        rvTags.setLayoutManager(new LinearLayoutManager(this));
        rvAdapter = new CommonAdapter<SpannableString>(this, R.layout.item_tag, searchTagList) {
            @Override
            protected void convert(ViewHolder holder, SpannableString tag, int position) {
                ((TextView) holder.getView(R.id.tv_tag)).setText(tag);
            }
        };
        rvTags.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                changeTag(tagList.get(position).getTagName());
                rvTags.setVisibility(View.GONE);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvTags.setAdapter(rvAdapter);
        colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.green_tag));
    }

    @OnTextChanged(value = R.id.et_tag, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void textChangeMonitor(Editable s) {
        if (isChooseMode) {
            mFlowLayout.onChanged();
            isChooseMode = false;
            return;
        }
        if (TextUtils.isEmpty(s)) {
            mFlowLayout.onChanged();
            rvTags.setVisibility(View.GONE);
            return;
        }
        if (rvTags.getVisibility() == View.GONE)
            rvTags.setVisibility(View.VISIBLE);

        searchTagList.clear();
        for (int i = 0; i < tagList.size(); i++) {
            String tag = tagList.get(i).getTagName();
            int index = tag.indexOf(s.toString());
            if (tag.contains(s)) {
                spannableString = new SpannableString(tag);
                spannableString.setSpan(colorSpan, index, index + s.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                searchTagList.add(spannableString);
            }
        }
        rvAdapter.notifyDataSetChanged();
//        mDisposable.add(blogTagDao.findTagsByName(s.toString())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(list -> {
//                    Observable.fromIterable(list).subscribe(tagEntity -> log(tagEntity.getTagName()));
//                }));
    }

    private void changeTag(String tag) {
        etTag.setText(tag);
        etTag.setSelection(tag.length());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tag;
    }

    @OnClick(R.id.tv_done)
    void done() {
        String tag = etTag.getText().toString().trim();
        if (TextUtils.isEmpty(tag)) {
            toast("请输入或选择标签");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(KEY_TAG, tag);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }
}
