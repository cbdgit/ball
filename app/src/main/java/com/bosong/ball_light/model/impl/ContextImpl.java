package com.bosong.ball_light.model.impl;

import com.bosong.ball_light.model.IContext;
import com.bosong.ball_light.model.database.ContextDatabase;
import com.bosong.ball_light.model.bean.ContextMemberBean;

import android.content.Context;

import java.util.List;

/**
 * Created by mike on 3/6/16.
 */
public class ContextImpl implements IContext {

    private Context mContext;
    private ContextDatabase mContextDatabase;

    public ContextImpl(Context mContext) {
        this.mContext = mContext;
        mContextDatabase = new ContextDatabase(mContext);
    }
    public List<ContextMemberBean> initContext() {
        return mContextDatabase.selectContexts();
    }

    public void insertContext(byte[] img, String name){
        mContextDatabase.insertContext(img, name);
    }

    public void deleteContext(String name) {
        mContextDatabase.deleteContext(name);
    }
}
