package com.danqiu.myapplication.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.danqiu.myapplication.BR;
import com.danqiu.myapplication.R;
import com.danqiu.myapplication.bean.DataUserBean;
import com.danqiu.myapplication.databinding.ActivityDatabindBinding;
import com.danqiu.myapplication.databinding.BindItemLayoutBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/18.
 * 数据绑定库   测试界面
 */

public class DataBindingAct extends AppCompatActivity {
    ActivityDatabindBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_databind);
        //ActivityDatabindBinding是layout布局文件名称的大写首字母+Binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_databind);
        initData();
    }

    private void initData() {
        DataUserBean user = new DataUserBean("张三", "男");

        List<String> stringList = new ArrayList<String>();
        stringList.add("数组字符串1");
        stringList.add("数组字符串2");

        List<DataUserBean> listUser = new ArrayList<>();
        listUser.add(new DataUserBean("数组对象李四1", "女"));
        listUser.add(new DataUserBean("数组对象李四2", "女"));


        binding.setListKey(0);
        binding.setListBean(listUser);
        binding.setUser(user);
        binding.setStr("字符串");
        binding.setList(stringList);

        //listview
         MyAdapter adapter = new MyAdapter(listUser, BR.userBean);

         binding.setAdapter(adapter);

        //监听方式一
        binding.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.bt_qie1:
                        binding.setListKey(1);
                        Toast.makeText(DataBindingAct.this, "click", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
        //监听方式二
        binding.setBindListener(new MyBindListener());
    }

    /**
     * 自定监听
     */
    public class MyBindListener {
        //带String的参数，当然也可以去掉
        public void onBindClick(View view) {
            switch (view.getId()) {
                case R.id.bt_qie2:
                    binding.setListKey(1);
                    Toast.makeText(DataBindingAct.this, "click2", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

    public class MyAdapter extends BaseAdapter {
        List<DataUserBean> listUser;
        int variableId;
        public MyAdapter(List<DataUserBean> listUser,int variableId) {
            this.listUser = listUser;
            this.variableId = variableId;
        }

        @Override
        public int getCount() {
            return listUser.size();
        }

        @Override
        public Object getItem(int position) {
            return listUser.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BindItemLayoutBinding itemBinding;
            if (convertView == null) {
                itemBinding = DataBindingUtil.inflate(LayoutInflater.from(DataBindingAct.this), R.layout.bind_item_layout, parent, false);
                convertView = itemBinding.getRoot();
                //convertView.setTag(itemBinding);
            } else {
                 //itemBinding = (BindItemLayoutBinding) convertView.getTag();
                 itemBinding= DataBindingUtil.getBinding(convertView);
            }
            itemBinding.setUserBean((DataUserBean)getItem(position));
             //itemBinding.setVariable(variableId, getItem(position));

            return convertView;

//            ViewDataBinding binding = null;
//           if (convertView == null){
//                 binding =DataBindingUtil.inflate(LayoutInflater.from(DataBindingAct.this), R.layout.bind_item_layout,parent,false);
//           } else {
//                 binding = DataBindingUtil.getBinding(convertView);
//           }
//              binding.setVariable(variableId,listUser.get(position));
//           return binding.getRoot();


        }
    }


}
