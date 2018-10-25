package com.danqiu.myapplication.greendb;

import android.content.Context;

import com.danqiu.myapplication.greendao.DaoMaster;
import com.danqiu.myapplication.greendao.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by Administrator on 2018/9/26.
 * 创建数据库、创建数据库表、包含增删改查的操作以及数据库的升级
 */

public class DaoManager {
    private static final String TAG = DaoManager.class.getSimpleName();
    private static final String DB_NAME = "greendaotest";

    private Context context;

    //多线程中要被共享的使用volatile关键字修饰
    private volatile static DaoManager manager;
    private static DaoMaster sDaoMaster;
    private static DaoMaster.DevOpenHelper sHelper;
    private static DaoSession sDaoSession;


    /**
     * 单例模式  获得操作数据库对象
     * @return
     */
    public static DaoManager getInstance(){
        if(manager==null){
            manager=new DaoManager();
        }
        return manager;
    }
    public void init(Context context){
        this.context = context;
    }
    /**
     * 对数据库   添加、删除、修改、查询操作
     */
    public DaoSession getDaoSession(){
        if(sDaoSession == null){
            if(sDaoMaster == null){
                sDaoMaster = getDaoMaster();
            }
            sDaoSession = sDaoMaster.newSession();
        }
        return sDaoSession;
    }
    /**
     * 判断是否有存在数据库，如果没有则创建
     */
    public DaoMaster getDaoMaster(){
        if(sDaoMaster == null) {
            sHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
            sDaoMaster = new DaoMaster(sHelper.getWritableDatabase());
            setDebug();
        }
        return sDaoMaster;
    }
    /**
     * 打开输出日志，默认关闭
     */
    public void setDebug(){
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }
    /**
     * 数据库使用完毕  要关闭
     */
    public void closeConnection(){
        closeHelper();
        closeDaoSession();
    }

    public void closeHelper(){
        if(sHelper != null){
            sHelper.close();
            sHelper = null;
        }
    }

    public void closeDaoSession(){
        if(sDaoSession != null){
            sDaoSession.clear();
            sDaoSession = null;
        }
    }

}
