package com.bosong.ball_light.model.database;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.ThumbnailUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;

import com.bosong.ball_light.R;
import com.bosong.ball_light.model.bean.ContextMemberBean;
import com.bosong.ball_light.model.bean.GroupBean;
import com.bosong.ball_light.model.bean.GroupMemberBean;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Created by mike on 3/6/16.
 */
public class ContextDatabase {
    private Context mContext;

    private SQLiteDatabase mSQLiteDatabase = null;
    private SharedPreferenceGroup mSharedPreferenceGroup;

    private List<ContextMemberBean> mListContext;

    private static final String DATABASE_NAME = "Group.db";
    private static final String TABLE_CONTEXT = "table_context";

    private static final String COLUMN_ID = "_id";// INTEGER PRIMARY KEY
    private static final String COLUMN_CONTEXT_NAME = "context_name";// TEXT
    private static final String COLUMN_CONTEXT_IMAGE = "context_image";// BLOB
    private static final String COLUMN_GROUP_NAME = "group_name";// INTEGER

    private static final String CREATE_TABLE_CONTEXT =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_CONTEXT +
                    " (" +
                    COLUMN_ID + " TEXT PRIMARY KEY" + "," +
                    COLUMN_CONTEXT_NAME + " TEXT" + "," +
                    COLUMN_CONTEXT_IMAGE + " BLOB" +
                    ")";


    public ContextDatabase(Context mContext) {
        this.mContext = mContext;
        init();
    }

    private void init() {
        mSharedPreferenceGroup = new SharedPreferenceGroup(mContext);
        boolean isFirst = mSharedPreferenceGroup.getIsContextFirst();
        try {
            mSQLiteDatabase =
                    mContext.openOrCreateDatabase(DATABASE_NAME, Activity.MODE_PRIVATE, null);
            try {
                mSQLiteDatabase.execSQL(CREATE_TABLE_CONTEXT);
            } catch (Exception e) {
                this.ShowDialog("创建表异常!");
                Log.d("ContextDatabase", e.getMessage());
            }
            //dropTable();
            //dropDatabase();
           if (isFirst == true) {
                try {
                    insertContext(img(R.drawable.home), "回家");
                    insertContext(img(R.drawable.outside), "外出");
                    insertContext(img(R.drawable.dinner), "进餐");
                    insertContext(img(R.drawable.theatre), "影院");
                    insertContext(img(R.drawable.bed), "起床");
                    insertContext(img(R.drawable.sleep), "睡觉");
                    insertContext(img(R.drawable.party), "派对");
                    insertContext(img(R.drawable.birthday), "生日");
                    insertContext(img(R.drawable.celebrate), "庆祝");

                    mSharedPreferenceGroup.setNotContextFirst();

                } catch (Exception e) {
                    this.ShowDialog("插入数据异常!");
                    Log.d("ContextDatabase", e.getMessage());
                }
           }
        }catch(Exception e){
            this.ShowDialog("打开或创建数据库异常!");
            Log.d("ContextDatabase", e.getMessage());
        }
    }

    private byte[] img(int id)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), id);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public long insertContext(byte[] img, String name) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CONTEXT_IMAGE, img);
        cv.put(COLUMN_CONTEXT_NAME, name);
        long result = mSQLiteDatabase.insert(TABLE_CONTEXT, null, cv);
        return result;
    }

    public int deleteContext(String name) {
        String[] args = {name};
        return mSQLiteDatabase.delete(TABLE_CONTEXT, COLUMN_CONTEXT_NAME + " = ?", args);
    }

    public List<ContextMemberBean> selectContexts() {
        mListContext = new ArrayList<ContextMemberBean>();
        try {
            String sql =
                    "SELECT * FROM " + TABLE_CONTEXT;
            Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);

            while(cursor.moveToNext()) {
                byte[] in = cursor.getBlob(cursor.getColumnIndex(COLUMN_CONTEXT_IMAGE));
                Bitmap bitmap = BitmapFactory.decodeByteArray(in, 0, in.length);
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_CONTEXT_NAME));
                mListContext.add(new ContextMemberBean(bitmap, name, false));
            }
        } catch (Exception e) {
            this.ShowDialog(" 查询数据异常!");
            Log.d("ContextDatabase", e.getMessage());
        }
        return mListContext;
    }

    /*
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

    public void insertLight(String lightName, boolean type, int position) {
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
                            COLUMN_LIGHT_TYPE + "," +
                            COLUMN_LIGHT_POSI +
                            ")" +
                            " VALUES " +
                            "(\'" +
                            lightName + "\'," +
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
                    mMemberList.add(new GroupMemberBean(cursor.getString(cursor.getColumnIndex(COLUMN_LIGHT_NAME)), type, false, false));
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
                mChildList.add(new GroupMemberBean(cursor.getString(cursor.getColumnIndex(COLUMN_LIGHT_NAME)), type, false, false));
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
    */
    private void ShowDialog(String msg) {
        new AlertDialog.Builder(mContext).setTitle(" 提示 ").setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }
}
