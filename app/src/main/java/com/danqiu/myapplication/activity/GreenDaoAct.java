package com.danqiu.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.bean.UserBean;
import com.danqiu.myapplication.greendb.UserDaoUtil;
import com.danqiu.myapplication.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/9/25.
 * 数据库
 */

public class GreenDaoAct extends BaseActivity {
    @BindView(R.id.bt_add)
    Button btAdd;
    @BindView(R.id.bt_addList)
    Button btAddList;
    @BindView(R.id.bt_where)
    Button btWhere;
    @BindView(R.id.bt_whereAll)
    Button btWhereAll;
    @BindView(R.id.bt_alter)
    Button btAlter;
    @BindView(R.id.bt_del)
    Button btDel;
    @BindView(R.id.bt_delAll)
    Button btDelAll;
    @BindView(R.id.tv_return)
    TextView tvReturn;

    UserDaoUtil userDaoUtil;
    boolean isOk = false;
    @BindView(R.id.bt_whereList)
    Button btWhereList;

    @Override
    public void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_greendao);
        ButterKnife.bind(this);
        initSql();
    }

    private void initSql() {
        userDaoUtil = new UserDaoUtil(this);
    }


    @OnClick({R.id.bt_add, R.id.bt_addList, R.id.bt_where, R.id.bt_whereAll,R.id.bt_whereList, R.id.bt_alter, R.id.bt_del, R.id.bt_delAll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_add:
                isOk = userDaoUtil.insert(new UserBean(null, "张三", 20, "男", "成都"));
                tvReturn.setText("单个添加:" + isOk);
                LogUtil.i(TAG,"--------添加----"+isOk);
                break;
            case R.id.bt_addList:
                List<UserBean> list_=new ArrayList<>();
                list_.add(new UserBean(null, "李四", 21, "男", "上海"));
                list_.add(new UserBean(null, "老王", 22, "女", "成都"));
                list_.add(new UserBean(null, "张三", 50, "女", "北京"));
                isOk=userDaoUtil.insertList(list_);
                LogUtil.i(TAG,"--------添加多个----"+isOk);
                break;
            case R.id.bt_where://指定查询唯一
                 UserBean userBean=userDaoUtil.queryById(1);
                if(userBean==null){
                    LogUtil.i(TAG,"--------查询唯一空----");
                }
                else {
                    LogUtil.i(TAG,"--------查询唯一----"+userBean.toString());
                }

                break;
            case R.id.bt_whereList://模糊查询返回数组
                List<UserBean> list=userDaoUtil.queryName("张三");
                LogUtil.i(TAG,"--------模糊查询----"+list);
                break;
            case R.id.bt_whereAll://全部查询
                List<UserBean> list2=userDaoUtil.queryAll();
                LogUtil.i(TAG,"--------查询全部----"+list2);
                break;
            case R.id.bt_alter://修改
                UserBean obj=userDaoUtil.queryById(2);
                 if(obj==null){
                     LogUtil.i(TAG,"--------修改对象不存在----");
                 }
                 else {
                     obj.setUsername("柯基");
                     isOk=  userDaoUtil.update(obj);
                     LogUtil.i(TAG,"--------修改----"+isOk);
                 }

                break;
            case R.id.bt_del:
                isOk=userDaoUtil.delete(userDaoUtil.queryById(1));
                LogUtil.i(TAG,"--------删除----"+isOk);
                break;
            case R.id.bt_delAll:
                isOk=userDaoUtil.deleteAll();
                LogUtil.i(TAG,"--------删除全部----"+isOk);
                break;
        }
    }


}
