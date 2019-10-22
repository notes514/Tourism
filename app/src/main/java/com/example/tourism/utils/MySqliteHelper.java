package com.example.tourism.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tourism.entity.Constant;

public class MySqliteHelper extends SQLiteOpenHelper {

    /**
     *
     * @param context 上下文对象
     * @param name 创建数据库名字
     * @param factory 工厂
     * @param version 版本
     */
    //构造函数
    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public MySqliteHelper(Context context){
        super(context, Constant.DATABASE_NAME,null,Constant.DATABASE_VERSION);
    }
    /*
    创建数据库时使用的函数
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table "+Constant.TABLE_NAME+"("+
                Constant.SCENIC_REGION_ID+" Integer ,"+
                Constant.SCENIC_REGION_NAME+" varchar(50))";
        sqLiteDatabase.execSQL(sql);

    }
    /**
     *  更新数据库时调用
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
