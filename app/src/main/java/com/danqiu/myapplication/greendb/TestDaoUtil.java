package com.danqiu.myapplication.greendb;

import android.content.Context;

import com.danqiu.myapplication.greendao.TestBean;

import java.util.List;

/**
 * Created by Administrator on 2019/1/25.
 */

public class TestDaoUtil {
    private DaoManager mManager;
    public TestDaoUtil(Context context){
        mManager = DaoManager.getInstance();
        mManager.init(context);

    }


    /**
     * 插入数据  如果没有，侧先创建表
     */
    public boolean insert(TestBean obj){
        boolean flag = false;
        flag = mManager.getDaoSession().getTestBeanDao().insert(obj) != -1;
        return flag;
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<TestBean> queryAll(){
        return mManager.getDaoSession().getTestBeanDao().loadAll();
    }

}
