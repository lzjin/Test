package com.danqiu.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * Created by Administrator on 2018/12/26.
 */

public class TestActivity extends AppCompatActivity {
    @BindView(R.id.tv_Tag)
    TagContainerLayout tvTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagview);
        ButterKnife.bind(this);

        List<String> tags = new ArrayList<>();
        tags.add("语文");
        tags.add("数学");
        tags.add("化学");
        tags.add("生物");
        tags.add("历史");

        tvTag.setTags(tags);
        tvTag.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String tagText) {
                // ...点击事件
                ToastUtil.showShort(TestActivity.this,tagText);
            }
            @Override
            public void onTagLongClick(int position, String text) {
                // ...长按事件
                ToastUtil.showShort(TestActivity.this,text);
            }
        });
    }
}
