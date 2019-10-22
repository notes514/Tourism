package com.example.tourism.utils;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tourism.entity.Constant;
import com.example.tourism.entity.ScenicRegion;

import java.util.ArrayList;

public class DbManger {

    private static MySqliteHelper helper; //建立一个数据库对象

    /**
     *
     * @param context 本类的上下文对象
     * @return
     */
    public static MySqliteHelper getIntance(Context context){
        if (helper == null){
            helper = new MySqliteHelper(context);
        }
        return helper;
    }
    /**
     * 查找方法
     * 返回的是一个Cursor对象
     * selectionArgs 查询条件占位符
     */
    public static Cursor selectSQL(SQLiteDatabase db, String sql, String[] selectionArgs){
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(sql,selectionArgs);
        }
        return cursor;
    }
    /**
     * 删改数据库
     * @param db  数据库对象
     * @param sql 删改语句
     */
    public static void execSQL(SQLiteDatabase db, String sql){
        if (db!=null) {
            if (sql != null && !"".equals(sql)) {
                db.execSQL(sql);
            }
        }
    }

    /**
     * 将Curcor对象转化成list集合
     * @param cursor 游标
     * @return 集合对象
     */
    public static ArrayList<ScenicRegion> cursorToList(Cursor cursor){
        ArrayList<ScenicRegion> list = new ArrayList<>();
        while (cursor.moveToNext()){   //判断游标是否有下一个字段
            //getColumnIndext作用是返回给定字符串的下标(指的是int类型)
            int columnIndex = cursor.getColumnIndex(Constant.SCENIC_REGION_ID);
            //通过下标找到指定value
            int scenicRegionId = cursor.getInt(columnIndex);  // 获取id
            String scenicRegionName = cursor.getString(cursor.getColumnIndex(Constant.SCENIC_REGION_NAME)); //获取时间
            ScenicRegion th_data = new ScenicRegion(scenicRegionId,scenicRegionName,null,null);
            list.add(th_data);
        }
        return list;
    }

}
