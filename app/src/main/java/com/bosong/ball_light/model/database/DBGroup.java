package com.bosong.ball_light.model.database;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.bosong.ball_light.model.bean.GroupBean;
import com.bosong.ball_light.model.bean.GroupMemberBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Created by mike on 2/4/16.
 */
public class DBGroup {

    private Context mContext;

    private SQLiteDatabase mSQLiteDatabase = null;
    private SharedPreferenceGroup mSharedPreferenceGroup;

    private static final String DATABASE_NAME = "Group.db";
    private static final String TABLE_GROUP = "table_group";
    private static final String TABLE_LIGHT = "table_light";
    private static final String TABLE_RLTV = "table_relative";

    private static final String COLUMN_ID = "_id";// INTEGER PRIMARY KEY
    private static final String COLUMN_LIGHT_NAME = "light_name";// TEXT
    private static final String COLUMN_LIGHT_TYPE = "light_type";// INTEGER
    private static final String COLUMN_LIGHT_ADDR = "light_addr"; //TEXT
    private static final String COLUMN_LIGHT_POSI = "light_position";// INTEGER
    private static final String COLUMN_GROUP_NAME = "group_name";// TEXT
    private static final String COLUMN_GROUP_TYPE = "group_type";// INTEGER


    private static final String CREATE_TABLE_GROUP =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_GROUP +
                    " (" +
                    COLUMN_GROUP_NAME + " TEXT PRIMARY KEY" +
                    ")";

    private static final String CREATE_TABLE_LIGHT =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_LIGHT +
                    " (" +
                    COLUMN_LIGHT_NAME + " TEXT PRIMARY KEY"  + "," +
                    COLUMN_LIGHT_ADDR + " TEXT" + "," +
                    COLUMN_LIGHT_TYPE + " INTEGER" + "," +
                    COLUMN_LIGHT_POSI + " INTEGER" +
                    ")";

    private static final String CREATE_TABLE_RLTV =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_RLTV +
                    " (" +
                    COLUMN_LIGHT_NAME + " TEXT PRIMARY KEY"  + "," +
                    COLUMN_GROUP_NAME + " TEXT" +
                    ")";


    public DBGroup(Context mContext) {
        this.mContext = mContext;
        init();
    }

    private void init() {
        mSharedPreferenceGroup = new SharedPreferenceGroup(mContext);
        boolean isFirst = mSharedPreferenceGroup.getIsFirst();
        try {
            mSQLiteDatabase =
                    mContext.openOrCreateDatabase(DATABASE_NAME, Activity.MODE_PRIVATE, null);
            if (isFirst == true) {
                try {
                    mSQLiteDatabase.execSQL(CREATE_TABLE_GROUP);
                    mSQLiteDatabase.execSQL(CREATE_TABLE_LIGHT);
                    mSQLiteDatabase.execSQL(CREATE_TABLE_RLTV);
                } catch (Exception e) {
                    this.ShowDialog("创建表异常!");
                    Log.d("DBGGroup", e.getMessage());
                }
                try {
                    String str1 =
                            "INSERT INTO " + TABLE_GROUP +
                                    "(\'" + COLUMN_GROUP_NAME + "\') VALUES (\'分组1\')";

                    mSQLiteDatabase.execSQL(str1);
                    String str2 =
                            "INSERT INTO " + TABLE_GROUP +
                                    "(\'" + COLUMN_GROUP_NAME + "\') VALUES (\'分组2\')";

                    mSQLiteDatabase.execSQL(str2);
                } catch (Exception e) {
                    this.ShowDialog("插入数据异常!");
                    Log.d("DBGGroup", e.getMessage());
                }
                //dropTable();
                //dropDatabase();
                mSharedPreferenceGroup.setNotFirst();
            }
        }catch(Exception e){
            this.ShowDialog("打开或创建数据库异常!");
            Log.d("DBGGroup", e.getMessage());
        }
    }


// GROUP OPERATE ==============================================================
        /*
    public void insetGroup(String groupName) {
        try {
            String str =
                    "INSERT INTO " + TABLE_NAME_GROUP +
                            "(" +
                            COLUMN_LIGHT_NAME +
                            "," +
                            COLUMN_LIGHT_TYPE +
                            "," +
                            COLUMN_GROUP_NAME +
                            ")" +

                            " VALUES " +
                            "(" +
                            lightName +
                            "," +
                            lightType +
                            "," +
                            groupName +
                            ")";

            mSQLiteDatabase.execSQL(str);
        } catch(Exception e) {
            this.ShowDialog("插入数据异常：" + e.getMessage());
        }
    }
    */

    public void insertGroup(String groupName) {
        try {
            String str =
                    "INSERT INTO " + TABLE_GROUP +
                            "(" +
                            COLUMN_GROUP_NAME +
                            ")" +
                            " VALUES " +
                            "(\'" +
                            groupName +
                            "\')";

            mSQLiteDatabase.execSQL(str);
        } catch(Exception e) {
            this.ShowDialog("插入数据异常!");
            Log.d("DBGGroup", e.getMessage());
        }
    }

    // 删除分组操作
    public void deleteGroup(String groupName) {
        try {
            String str =
                    "DELETE FROM " + TABLE_GROUP +
                            " WHERE " +
                            //"COLUMN_LIGHT_NAME = " + lightName
                            //+ " AND " +
                            COLUMN_GROUP_NAME + " = \'" + groupName + "\'";
            mSQLiteDatabase.execSQL(str);
        } catch (Exception e) {
            this.ShowDialog(" 删除分组异常!");
            Log.d("DBGGroup", e.getMessage());
        }
    }

    public void deleteGroupLight(int position) {
        try {
            String str =
                    "DELETE FROM " + TABLE_LIGHT +
                            " WHERE " +
                            //"COLUMN_LIGHT_NAME = " + lightName
                            //+ " AND " +
                            COLUMN_LIGHT_POSI + " = \'" + position + "\'";
            mSQLiteDatabase.execSQL(str);
        } catch (Exception e) {
            this.ShowDialog(" 删除分组异常!");
            Log.d("DBGGroup", e.getMessage());
        }
    }

    public void updateGroup(String groupNameOld, String groupNameNew) {
        try {
            String str =
                    "UPDATE " + TABLE_GROUP +
                            " SET " +
                            COLUMN_GROUP_NAME + " = ? " +
                            " WHERE " +
                            COLUMN_GROUP_NAME + " =?";
            Object[] Ob = new Object[] { groupNameNew, groupNameOld};
            mSQLiteDatabase.execSQL(str, Ob);
        } catch (Exception e) {
            this.ShowDialog(" 更新数据异常!");
            Log.d("DBGGroup", e.getMessage());
        }
    }

    public List<GroupBean> selectGroups() {

        List<GroupBean>  mGroupList = new ArrayList<GroupBean>();

        try {
            String sql =
                    "SELECT * FROM " + TABLE_GROUP;
            Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);

            while(cursor.moveToNext()) {
                mGroupList.add(new GroupBean(cursor.getString(cursor.getColumnIndex(COLUMN_GROUP_NAME)), false));
            }
            //mGroupList.add(new GroupBean("未分组", false));
        } catch (Exception e) {
            this.ShowDialog(" 查询数据异常!");
            Log.d("DBGGroup", e.getMessage());
        }

        return mGroupList;
    }


// LIGHT OPERATE ==============================================================
        /*
    public void insetGroup(String groupName) {
        try {
            String str =
                    "INSERT INTO " + TABLE_NAME_GROUP +
                            "(" +
                            COLUMN_LIGHT_NAME +
                            "," +
                            COLUMN_LIGHT_TYPE +
                            "," +
                            COLUMN_GROUP_NAME +
                            ")" +

                            " VALUES " +
                            "(" +
                            lightName +
                            "," +
                            lightType +
                            "," +
                            groupName +
                            ")";

            mSQLiteDatabase.execSQL(str);
        } catch(Exception e) {
            this.ShowDialog("插入数据异常：" + e.getMessage());
        }
    }
    */

    public void insertLight(String lightName, String lightAddr, boolean type, int position) {
        try {
            //int position;
            //position = getGroupCount();
            int lightType;
            if (type == true) {
                lightType = 1;
            } else {
                lightType = 0;
            }
            String str =
                    "INSERT OR REPLACE INTO " + TABLE_LIGHT +
                            "(" +
                            COLUMN_LIGHT_NAME + "," +
                            COLUMN_LIGHT_ADDR + "," +
                            COLUMN_LIGHT_TYPE + "," +
                            COLUMN_LIGHT_POSI +
                            ")" +
                            " VALUES " +
                            "(\'" +
                            lightName + "\',\'" +
                            lightAddr + "\'," +
                            lightType + "," +
                            position +
                            ")";

            mSQLiteDatabase.execSQL(str);
        } catch(Exception e) {
            this.ShowDialog("插入灯数据异常!");
            Log.d("DBGGroup", e.getMessage());
        }
    }

    public void deleteLight(String lightName) {
        try {
            String str =
                    "DELETE FROM " + TABLE_LIGHT +
                            " WHERE " +
                            //"COLUMN_LIGHT_NAME = " + lightName
                            //+ " AND " +
                            COLUMN_LIGHT_NAME + " = " + lightName;
            mSQLiteDatabase.execSQL(str);
        } catch (Exception e) {
            this.ShowDialog(" 删除分组异常!");
            Log.d("DBGGroup", e.getMessage());
        }
    }

    public void updateLightName(String lightNameOld, String lightNameNew) {
        try {
            String str =
                    "UPDATE " + TABLE_LIGHT +
                            " SET " +
                            COLUMN_LIGHT_NAME + " = ? " +
                            " WHERE " +
                            COLUMN_LIGHT_NAME + " =?";
            Object[] Ob = new Object[] { lightNameNew, lightNameOld};
            mSQLiteDatabase.execSQL(str, Ob);
        } catch (Exception e) {
            this.ShowDialog(" 更新数据异常!");
            Log.d("DBGGroup", e.getMessage());
        }
    }

    public void updateLightPosition(String lightName, int position) {
        try {
            String str =
                    "UPDATE " + TABLE_LIGHT +
                            " SET " +
                            COLUMN_LIGHT_POSI + " = ? " +
                            " WHERE " +
                            COLUMN_LIGHT_NAME + " =?";
            Object[] Ob = new Object[] { position, lightName};
            mSQLiteDatabase.execSQL(str, Ob);
        } catch (Exception e) {
            this.ShowDialog(" 更新数据异常!");
            Log.d("DBGGroup", e.getMessage());
        }
    }

    public List<List<GroupMemberBean>> selectLights() {
        boolean type;
        List<List<GroupMemberBean>> mChildList = new ArrayList<List<GroupMemberBean>>();

        try {
            for (int i = 0; i < 100; i++){
                List<GroupMemberBean> mMemberList = new ArrayList<GroupMemberBean>();
                String sql =
                        "SELECT * FROM " + TABLE_LIGHT +
                                " WHERE " +
                                COLUMN_LIGHT_POSI + " = " + i;
                Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
                while(cursor.moveToNext()) {
                    int t = cursor.getInt(cursor.getColumnIndex(COLUMN_LIGHT_TYPE));
                    if (t == 0) {
                        type = false;
                    } else {
                        type = true;
                    }
                    mMemberList.add(new GroupMemberBean(cursor.getString(cursor.getColumnIndex(COLUMN_LIGHT_NAME)), cursor.getString(cursor.getColumnIndex(COLUMN_LIGHT_ADDR)), type, false, false));
                }
                mChildList.add(i, mMemberList);
            }

        } catch (Exception e) {
            this.ShowDialog(" 查询数据异常!");
            Log.d("DBGGroup", e.getMessage());
        }

        return mChildList;
    }

    public List<GroupMemberBean> selectSomeLights(int i) {
        boolean type;
        List<GroupMemberBean> mChildList = new ArrayList<GroupMemberBean>();

        try {
            String sql =
                    "SELECT * FROM " + TABLE_LIGHT +
                            " WHERE " +
                            COLUMN_LIGHT_POSI + " = " + i;
            Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
            while(cursor.moveToNext()) {
                int t = cursor.getInt(cursor.getColumnIndex(COLUMN_LIGHT_TYPE));
                if (t == 0) {
                    type = false;
                } else {
                    type = true;
                }
                mChildList.add(new GroupMemberBean(cursor.getString(cursor.getColumnIndex(COLUMN_LIGHT_NAME)), cursor.getString(cursor.getColumnIndex(COLUMN_LIGHT_ADDR)), type, false, false));
            }
            //mChildList.add(i, mMemberList);
        } catch (Exception e) {
            this.ShowDialog(" 查询数据异常!");
            Log.d("DBGGroup", e.getMessage());
        }

        return mChildList;
    }

    public int getGroupCount() {
        Cursor cursor = mSQLiteDatabase.rawQuery("select count(" + COLUMN_GROUP_NAME + ") from " + TABLE_GROUP,null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }
/*
    public int selecteType(String lightName) {
        try {
            String sql =
                    "SELECT * FROM " + TABLE_LIGHT +
                    " WHERE " +
                    //"COLUMN_LIGHT_NAME = " + lightName
                    //+ " AND " +
                    COLUMN_LIGHT_NAME + " = " + lightName;;
            Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);

            return cursor.getInt(cursor.getColumnIndex(COLUMN_LIGHT_TYPE));

        } catch (Exception e) {
            this.ShowDialog(" 查询数据异常!");
            Log.d("DBGGroup", e.getMessage());
        }
    }

*/
    public void insertRelative(String lightName, String groupName) {
        try {
            String str =
                    "INSERT INTO " + TABLE_RLTV +
                            "(" +
                            COLUMN_LIGHT_NAME + "," +
                            COLUMN_GROUP_NAME +
                            ")" +
                            " VALUES " +
                            "(\'" +
                            lightName + "\',\'" +
                            groupName +
                            "\')";

            mSQLiteDatabase.execSQL(str);
        } catch(Exception e) {
            this.ShowDialog("插入数据异常!");
            Log.d("DBGGroup", e.getMessage());
        }
    }

    public void deleteRelative(String lightName) {
        try {
            String str =
                    "DELETE FROM " + TABLE_RLTV +
                            " WHERE " +
                            //"COLUMN_LIGHT_NAME = " + lightName
                            //+ " AND " +
                            COLUMN_LIGHT_NAME + " = " + lightName;
            mSQLiteDatabase.execSQL(str);
        } catch (Exception e) {
            this.ShowDialog(" 删除分组异常!");
            Log.d("DBGGroup", e.getMessage());
        }
    }

    public void updateRelative(String groupNameOld, String groupNameNew) {
        try {
            String str =
                    "UPDATE " + TABLE_RLTV +
                            " SET " +
                            COLUMN_GROUP_NAME + " = ? " +
                            " WHERE " +
                            COLUMN_LIGHT_NAME + " =?";
            Object[] Ob = new Object[] { groupNameNew, groupNameOld};
            mSQLiteDatabase.execSQL(str, Ob);
        } catch (Exception e) {
            this.ShowDialog(" 更新数据异常!");
            Log.d("DBGGroup", e.getMessage());
        }
    }

    public Map selectRelative() {

        Map mMap = new HashMap();

        try {
            String sql =
                    "SELECT * FROM " + TABLE_RLTV;
            Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);

            while(cursor.moveToNext()) {
                mMap.put(cursor.getString(cursor.getColumnIndex(COLUMN_LIGHT_NAME)), cursor.getString(cursor.getColumnIndex(COLUMN_GROUP_NAME)));
            }

        } catch (Exception e) {
            this.ShowDialog(" 查询数据异常!");
            Log.d("DBGGroup", e.getMessage());
        }

        return mMap;
    }


// MEMBER OPERATE =============================================================

    // 删除灯操作
    private void DeleteMember(String lightName) {
        try {
            String str =
                    "DELETE FROM " + TABLE_LIGHT +
                            " WHERE " +
                            "COLUMN_LIGHT_NAME = " + lightName;
                            //+ " AND " +
                            //"COLUMN_GROUP_NAME = " + groupName;
            mSQLiteDatabase.execSQL(str);
        } catch (Exception e) {
            this.ShowDialog(" 删除灯异常!");
            Log.d("DBGGroup", e.getMessage());
        }
    }

    // 移动分组操作
    private void MoveGroup(String lightName, String groupName) {
        try {
            String str =
                    "UPDATE " + TABLE_LIGHT +
                            " SET " +
                            COLUMN_GROUP_NAME + " = ? " +
                            " WHERE " +
                            COLUMN_LIGHT_NAME + " =?";
            Object[] Ob = new Object[] { groupName, lightName};
            mSQLiteDatabase.execSQL(str, Ob);
        } catch (Exception e) {
            this.ShowDialog(" 移动分组异常!");
            Log.d("DBGGroup", e.getMessage());
        }
    }

    private void UpdateData(String lightName, String groupName) {
        try {
            String str =
                    "UPDATE " + TABLE_LIGHT +
                            " SET " +
                            COLUMN_GROUP_NAME + " = ? " +
                            " WHERE " +
                            COLUMN_LIGHT_NAME + " =?";
            Object[] Ob = new Object[] { groupName, lightName};
            mSQLiteDatabase.execSQL(str, Ob);
        } catch (Exception e) {
            this.ShowDialog(" 更新数据异常!");
            Log.d("DBGGroup", e.getMessage());
        }
    }

    private void SelectData() {
        try {
            String sql =
                    "SELECT * FROM " + TABLE_LIGHT;
            Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
            if (cursor != null) {

            }
        } catch (Exception e) {
            this.ShowDialog(" 查询数据异常!");
            Log.d("DBGGroup", e.getMessage());
        }
    }





    private void dropTable(){
        try{
            String sql = "DROP TABLE "+TABLE_GROUP;
            mSQLiteDatabase.execSQL(sql);
        }catch(Exception e){
            this.ShowDialog(" 删除表异常!");
            Log.d("DBGGroup", e.getMessage());
        }
    }

    private void dropDatabase(){
        try{
            mContext.deleteDatabase(DATABASE_NAME);
        }catch(Exception e){
            this.ShowDialog(" 删除数据库异常!");
            Log.d("DBGGroup", e.getMessage());
        }
    }

    private void ShowDialog(String msg) {
        new AlertDialog.Builder(mContext).setTitle(" 提示 ").setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

}


