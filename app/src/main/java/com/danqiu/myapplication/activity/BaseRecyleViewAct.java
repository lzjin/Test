package com.danqiu.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.utils.IntentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lzj on 2019/4/16
 * Describe ：RecyleView结合BaseRecyclerViewAdapterHelper使用
 */
public class BaseRecyleViewAct extends AppCompatActivity {
    @BindView(R.id.bt1)
    Button bt1;
    @BindView(R.id.bt2)
    Button bt2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_recleview);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt1, R.id.bt2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                IntentUtil.IntenToActivity(this,RecycleViewActivity.class);
                break;
            case R.id.bt2:
                IntentUtil.IntenToActivity(this,RecycleViewAdapterHelperAct.class);
                break;
        }
    }
}
