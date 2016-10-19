package com.qiyuan.fifish.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.bean.Folder;
import com.bean.Image;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "rov.db";
    //此处更改数据库版本号。如果版本号已经改到很高了，看着不顺眼，只需在这改为1或任何你满意的数字，然后运行一遍代码，则old version就变为1或你自改的数字了
    //下次再更改数据库时只要比这次所改的数字大就可以了，如果这次运行时改的是1，则大于1的数字就行
    private final static int VERSION = 5;
    private SQLiteDatabase dbConn = null;

    public MySQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        dbConn = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS rov(_id INTEGER PRIMARY KEY AUTOINCREMENT , path, name, time, myid, isVideo, videoDuration)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS rov");
            onCreate(db);
//            在表中插入名为myid的列
//            String sql = "ALTER TABLE rov ADD myid datatype";
//            db.execSQL(sql);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        super.onDowngrade(db, oldVersion, newVersion);
    }

    public void insert(int id, String path, String name, Long time,int isVideo,long videoDuration) {
        //实例化常量值
        ContentValues cValue = new ContentValues();
        cValue.put("myid", id);
        cValue.put("path", path);
        cValue.put("name", name);
        cValue.put("time", time);
        cValue.put("isVideo", isVideo);
        cValue.put("videoDuration",videoDuration);
        //调用insert()方法插入数据
        dbConn.insert("rov", null, cValue);
    }

    public void delete(Image image) {
//        String path = image.path;
//        //删除条件
//        String whereClause = "path=?";
//        //删除条件参数
//        String[] whereArgs = {String.valueOf(path)};
//        //执行删除
//        dbConn.delete("rov", whereClause, whereArgs);

        String path = image.path;
        //删除SQL语句
        String sql = "delete from rov where path  = \'" + path+"\'";
        //执行SQL语句
        dbConn.execSQL(sql);
    }

    public ArrayList<Image> retQuery() {
        ArrayList<Image> list = new ArrayList<>();
        //查询获得游标
        Cursor cursor = dbConn.query("rov", null, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    boolean isVideo=false;
                    if (cursor.getInt(5) == 0) {
                        isVideo = false;
                    } else if (cursor.getInt(5) == 1) {
                        isVideo = true;
                    }
                    String path= cursor.getString(1);
                    String name=cursor.getString(2);
                    long time = cursor.getLong(3);
                    int id=cursor.getInt(4);
                    long videoDuration = cursor.getLong(6);
                    Image image1 = new Image(id,path, name, time,isVideo,videoDuration);
                    list.add(image1);
                } while (cursor.moveToNext());
            }
        }
        return list;
    }

    /**
     * 查询每一张图片是属于视频还是图片
     * @param image
     * @return
     */
    public boolean queryIsVideo(Image image) {
        boolean isVideo = false;
        String sql = "select isVideo from rov where path  = \'" + image.path+"\'";
        Cursor cursor = dbConn.rawQuery("select * from rov where path =?",new String[]{image.path});
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    int num= cursor.getInt(5);
                    if (num==1) {
                        isVideo = true;
                    }
                } while (cursor.moveToNext());
            }
        }
        return isVideo;
    }
    public boolean query(Image image) {
        boolean have = false;
        //查询获得游标
        Cursor cursor = dbConn.query("rov", null, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    String path = cursor.getString(1);
                    if (image.path.equals(path)) {
                        have = true;
                    }
                } while (cursor.moveToNext());
            }
        }
        return have;
    }

    public Cursor selectCursor(String sql, String[] selectionArgs) {
        return dbConn.rawQuery(sql, selectionArgs);
    }

    // select id,title from 表名
    public int selectCount(String sql, String[] selectionArgs) {
        Cursor cursor = dbConn.rawQuery(sql, selectionArgs);
        int count = cursor.getCount();
        if (cursor != null) {
            cursor.close();
        }
        return count;
    }

    public List<Map<String, String>> selectList(String sql,
                                                String[] selectionArgs) {
        Cursor cursor = dbConn.rawQuery(sql, selectionArgs);
        return cursorToList(cursor);
    }

    public List<Map<String, String>> cursorToList(Cursor cursor) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        while (cursor.moveToNext()) {
            // 对于每一行数据进行操作
            Map<String, String> map = new HashMap<String, String>();
            // 对于每行数据的每列进行操作
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                map.put(cursor.getColumnName(i), cursor.getString(i));
            }
            list.add(map);
        }
        return list;
    }

    public boolean execData(String sql, Object[] bindArgs) {
        try {
            if (bindArgs == null) {
                dbConn.execSQL(sql);
            } else {
                dbConn.execSQL(sql, bindArgs);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void destroy() {
        if (dbConn != null) {
            dbConn.close();
        }
    }
}
