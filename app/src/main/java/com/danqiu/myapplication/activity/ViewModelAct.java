package com.danqiu.myapplication.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.adapter.MyListAdapter;
import com.danqiu.myapplication.bean.ListBean;
import com.danqiu.myapplication.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lzj on 2020/3/19
 * Describe ：更新
 */
public class ViewModelAct extends AppCompatActivity {
    @BindView(R.id.btn_add)
    Button btn_add;
    @BindView(R.id.listview)
    ListView listview;
    MyListAdapter mAdapter;
    MyViewModel model;
    List<ListBean> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmodel);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        list=new ArrayList<>();
        mAdapter=new MyListAdapter(list,this);
        listview.setAdapter(mAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(model!=null){
                    model.getUsers().getValue().get(i).setAge(i);
                    mAdapter.notifyDataSetInvalidated();
                }
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(model!=null){
                    btn_add.setText( model.getNumber().getValue().intValue() +"");
                }
            }
        });
    }

    private void initData() {
        model = ViewModelProviders.of(this).get(MyViewModel.class);
        //观察数据是否改变
        model.getUsers().observe(this, new Observer<List<ListBean>>() {
            @Override
            public void onChanged(@Nullable List<ListBean> systemUserBeans) {
                // update UI
                if(systemUserBeans!=null){
                    list.addAll(systemUserBeans);
                    mAdapter.notifyDataSetInvalidated();
                }
            }
        });
    }


}
