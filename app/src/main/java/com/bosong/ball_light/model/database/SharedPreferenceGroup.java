package com.bosong.ball_light.model.database;

import com.bosong.ball_light.model.bean.GroupMemberBean;
import com.bosong.ball_light.model.bean.GroupMemberData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mike on 2/10/16.
 */
public class SharedPreferenceGroup {

    private static final String FILE_NAME = "ball_light";

    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPreferences;


    public SharedPreferenceGroup(Context mContext) {
        this.mContext = mContext;
        init();
    }

    private void init() {
        mSharedPreferences =  mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        //mEditor.putBoolean("isFirst", true);
    }

    public void setNotFirst(){
        mEditor.putBoolean("isFirst", false);
        mEditor.commit();
    }
    public boolean getIsFirst() {
        return mSharedPreferences.getBoolean("isFirst", true);
    }

    public void setNotContextFirst(){
        mEditor.putBoolean("isContextFirst", false);
        mEditor.commit();
    }
    public boolean getIsContextFirst() {
        return mSharedPreferences.getBoolean("isContextFirst", true);
    }
}
