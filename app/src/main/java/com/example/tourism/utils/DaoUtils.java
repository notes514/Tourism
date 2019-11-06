package com.example.tourism.utils;

import android.content.Context;

import com.example.tourism.database.bean.SeachContent;

import java.util.List;

/**
 * 工具类
 */
public class DaoUtils {

    private DaoManger manger;

    /**
     * 初始化工具类
     * 传入上下文
     * @param context
     */
    public DaoUtils(Context context) {
        manger = DaoManger.getInstance();
        manger.init(context);
    }

    /**
     * 完成数据的插入
     *
     * @param object
     * @return
     */
    public void insertData(Object object) {
        manger.getsDaoSession().insert(object);
    }

    /**
     * 插入多条数据，在子线程中操作
     *
     * @param objects
     */
    public boolean insertMultipleStrips(List<Object> objects) {
        boolean flag = false;
        try {
            manger.getsDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (Object obj : objects) {
                        manger.getsDaoSession().insertOrReplace(obj);
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
     * 修改一条数据
     *
     * @param object
     * @return
     */
    public boolean updateData(Object object) {
        boolean flag = false;
        try {
            manger.getsDaoSession().update(object);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除单条记录
     *
     * @param object
     * @return
     */
    public boolean deleteData(Object object) {
        boolean flag = false;
        try {
            manger.getsDaoSession().delete(object);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有记录（数据）
     * @param tClass
     * @return
     */
    public boolean deleteAllData(Class<?> tClass) {
        boolean flag = false;
        try {
            //在子线程中操作
            manger.getsDaoSession().deleteAll(tClass);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有数据，记录
     * @param tClass
     * @return
     */
    public List<?> queryAllData(Class<?> tClass) {
        return manger.getsDaoSession().loadAll(tClass);
    }

    /**
     * 根据主键ID查询数据
     * @param key
     * @return
     */
    public Class queryByClass(Class<?> tClass, long key) {
        return (Class) manger.getsDaoSession().load(tClass, key);
    }

//    /**
//     * 使用native sql进行查询操作
//     */
//    public List<Meizi> queryMeiziByNativeSql(String sql, String[] conditions){
//        return mManager.getDaoSession().queryRaw(Meizi.class, sql, conditions);
//    }
//
//    /**
//     * 使用queryBuilder进行查询
//     * @return
//     */
//    public List<Meizi> queryMeiziByQueryBuilder(long id){
//        QueryBuilder<Meizi> queryBuilder = mManager.getDaoSession().queryBuilder(Meizi.class);
//        return queryBuilder.where(MeiziDao.Properties._id.eq(id)).list();
//    }

}