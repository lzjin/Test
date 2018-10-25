package com.danqiu.myapplication.greendb;

import android.content.Context;

import com.danqiu.myapplication.bean.UserBean;
import com.danqiu.myapplication.greendao.UserBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Administrator on 2018/9/26.
 * 用于完成对某一张数据表的具体操作——ORM操作。
 */

public class UserDaoUtil{
    private DaoManager mManager;
    private UserBeanDao beanDao;

    public UserDaoUtil(Context context){
        mManager = DaoManager.getInstance();
        mManager.init(context);
        //beanDao= mManager.getDaoSession().getUserBeanDao();
    }

    /**
     * 插入数据  如果没有，侧先创建表
     */
    public boolean insert(UserBean obj){
        boolean flag = false;
        flag = mManager.getDaoSession().getUserBeanDao().insert(obj) != -1;
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     */
    public boolean insertList(final List<UserBean> listObj) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (UserBean obj : listObj) {
                        mManager.getDaoSession().getUserBeanDao().insertOrReplace(obj);
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
    public boolean update(UserBean obj){
        boolean flag = false;
        try {
            mManager.getDaoSession().getUserBeanDao().update(obj);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除  单条记录
     */
    public boolean delete(UserBean obj){
        boolean flag = false;
        try {
            mManager.getDaoSession().getUserBeanDao().delete(obj);
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
            mManager.getDaoSession().getUserBeanDao().deleteByKey(obj);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除  多条记录
     */
    public boolean deleteList(List<UserBean> listObj){
        boolean flag = false;
        try {
            //按照id删除
            for (UserBean obj : listObj) {
                mManager.getDaoSession().getUserBeanDao().delete(obj);
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
            mManager.getDaoSession().getUserBeanDao().deleteAll();
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
    public List<UserBean> queryAll(){
        return mManager.getDaoSession().getUserBeanDao().loadAll();
    }

    /**
     * 根据主键id查询记录
     * @param key
     * @return
     */
    public UserBean queryById(long key){
        return mManager.getDaoSession().getUserBeanDao().load(key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<UserBean> queryNative(String sql, String[] conditions){
        return mManager.getDaoSession().getUserBeanDao().queryRaw( sql, conditions);
    }

    /**
     * 条件查询  使用queryBuilder
     */
    public List<UserBean> queryName(String name){
        QueryBuilder<UserBean> queryBuilder = mManager.getDaoSession().getUserBeanDao().queryBuilder();
        //.where(UserDao.Properties.Id.le(10)).build().list();//id小于10的
        return queryBuilder.where(UserBeanDao.Properties.Username.eq(name)).list();
    }
}
