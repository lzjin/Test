package com.danqiu.myapplication.greendb;

import android.content.Context;

import com.danqiu.myapplication.bean.ClassBean;
import com.danqiu.myapplication.greendao.UserBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Administrator on 2018/9/26.
 * 用于完成对某一张数据表的具体操作——ORM操作。
 */

public class ClassDaoUtil {
    private DaoManager mManager;
    public ClassDaoUtil(Context context){
        mManager = DaoManager.getInstance();
        mManager.init(context);

    }


    /**
     * 插入数据  如果没有，侧先创建表
     */
    public boolean insert(ClassBean obj){
        boolean flag = false;
        flag = mManager.getDaoSession().getClassBeanDao().insert(obj) != -1;
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     */
    public boolean insertList(final List<ClassBean> listObj) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (ClassBean obj : listObj) {
                        mManager.getDaoSession().getClassBeanDao().insertOrReplace(obj);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改   一条数据
     */
    public boolean update(ClassBean obj){
        boolean flag = false;
        try {
            mManager.getDaoSession().getClassBeanDao().update(obj);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除  单条记录
     */
    public boolean delete(ClassBean obj){
        boolean flag = false;
        try {
            mManager.getDaoSession().getClassBeanDao().delete(obj);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除  单条记录
     */
    public boolean deleteID(long obj){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().getClassBeanDao().deleteByKey(obj);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除  多条记录
     */
    public boolean deleteList(List<ClassBean> listObj){
        boolean flag = false;
        try {
            //按照id删除
            for (ClassBean obj : listObj) {
                mManager.getDaoSession().getClassBeanDao().delete(obj);
            }
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有记录
     * @return
     */
    public boolean deleteAll(){
        boolean flag = false;
        try {
            mManager.getDaoSession().getClassBeanDao().deleteAll();
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<ClassBean> queryAll(){
        return mManager.getDaoSession().getClassBeanDao().loadAll();
    }

    /**
     * 根据主键id查询记录
     * @param key
     * @return
     */
    public ClassBean queryById(long key){
        return mManager.getDaoSession().getClassBeanDao().load(key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<ClassBean> queryNative(String sql, String[] conditions){
        return mManager.getDaoSession().getClassBeanDao().queryRaw( sql, conditions);
    }

    /**
     * 条件查询  使用queryBuilder
     */
    public List<ClassBean> queryName(String name){
        QueryBuilder<ClassBean> queryBuilder = mManager.getDaoSession().getClassBeanDao().queryBuilder();
        //.where(UserDao.Properties.Id.le(10)).build().list();//id小于10的
        return queryBuilder.where(UserBeanDao.Properties.Username.eq(name)).list();
    }
}
