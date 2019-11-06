package com.example.tourism.utils;

import android.content.Context;

import com.example.tourism.database.dao.DaoMaster;
import com.example.tourism.database.dao.DaoSession;


/**
 * 创建数据库、创建数据库表、包含增删改查的操作以及数据库的升级
 *
 */
public class DaoManger {
    //上下文
    private Context context;
    //数据库名称
    private static final String DB_NAME = "greendaotourism";
    //多线程中要被共享的使用volatile关键字修饰
    private volatile static DaoManger manger = new DaoManger();
    private static DaoMaster sDaoMaster;
    private static DaoMaster.DevOpenHelper sHelper;
    private static DaoSession sDaoSession;

    /**
     * 单例模式
     * 获取操作数据库对象
     * @return 返回当前manger
     */
    public static DaoManger getInstance() {
        return manger;
    }

    /**
     * 初始化上下文
     * @param context
     */
    public void init(Context context) {
        this.context = context;
    }

    /**
     * 判断是否存在数据库，如果没有则创建
     * @return
     */
    public DaoMaster getsDaoMaster() {
        if (sDaoMaster == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
            sDaoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return sDaoMaster;
    }

    /**
     * 完成对数据库的添加、删除、修改、查询操作
     * @return 返回
     */
    public DaoSession getsDaoSession() {
        if (sDaoSession == null) {
            if (sDaoMaster == null) {
                sDaoMaster = getsDaoMaster();
            }
            sDaoSession = sDaoMaster.newSession();
        }
        return sDaoSession;
    }

    /**
     * 关闭所有的操作，数据库开启后，使用完毕要关闭
     */
    public void closeConnection(){
        closeHelper();
        closeDaoSession();
    }

    public void closeHelper() {
        if (sHelper != null) {
            sHelper.close();
            sHelper = null;
        }
    }

    public void closeDaoSession() {
        if (sDaoSession != null) {
            sDaoSession.clear();
            sDaoSession = null;
        }
    }

}
