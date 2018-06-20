package com.yq.eie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yq.eie.R;
import com.yq.eie.base.BaseActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

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
    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView", "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initTags();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("编辑标签");
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initTags() {
        mFlowLayout.setAdapter(new TagAdapter<String>(Arrays.asList(mVals)) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(TagActivity.this).inflate(R.layout.tag, mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
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

}
