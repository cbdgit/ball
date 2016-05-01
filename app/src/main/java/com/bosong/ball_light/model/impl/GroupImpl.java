package com.bosong.ball_light.model.impl;

import com.bosong.ball_light.model.IGroup;
import com.bosong.ball_light.model.bean.GroupBean;
import com.bosong.ball_light.model.bean.GroupMemberBean;
import com.bosong.ball_light.model.database.DBGroup;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mike on 2/4/16.
 */
public class GroupImpl implements IGroup {

    private Context mContext;

    private List<String> mGroupNameList = new ArrayList<String>();
    private List<String> mMemberNameList = new ArrayList<String>();

    private List<GroupBean>  mGroupList = new ArrayList<GroupBean>();
    private List<List<GroupMemberBean>> mChildList = new ArrayList<List<GroupMemberBean>>();

    private Map mMemberMap = new HashMap();

    private DBGroup mDBGroup;

    public GroupImpl(Context mContext) {
        this.mContext = mContext;
        mDBGroup = new DBGroup(mContext);
    }

    @Override
    public List<GroupBean> initGroup() {

        mGroupList = mDBGroup.selectGroups();
        mGroupList.add(new GroupBean("未分组", false));
        return mGroupList;
    }

    //List<List<GroupMemberBean>> initGroupMember();
    @Override
    public List<List<GroupMemberBean>> initLight() {

        mChildList = mDBGroup.selectLights();
        mChildList.add(new ArrayList<GroupMemberBean>());

        return mChildList;
    }

    @Override
    public List<GroupMemberBean> selectSomeLights(int position) {
        return mDBGroup.selectSomeLights(position);
    }

    @Override
    public void addGroup(GroupBean mGroupBean) {
        mDBGroup.insertGroup(mGroupBean.getName());
    }

    @Override
    public void delGroup(GroupBean mGroupBean) {
        String groupName = mGroupBean.getName();
        mDBGroup.deleteGroup(groupName);
    }

    @Override
    public void delGroupLight(int position) {
        mDBGroup.deleteGroupLight(position);
    }

    @Override
    public void uptGroup(GroupBean oldGroupBean, GroupBean newGroupBean) {
        mDBGroup.updateGroup(oldGroupBean.getName(), newGroupBean.getName());
    }

    @Override
    public void addLight(GroupMemberBean mGroupMemberBean, int position) {
        mDBGroup.insertLight(mGroupMemberBean.getName(), mGroupMemberBean.getAddr(), mGroupMemberBean.getType(), position);
    }

    @Override
    public void delLight(GroupMemberBean mGroupMemberBean) {

    }

    @Override
    public void uptLight(GroupMemberBean oldGroupMemberBean, GroupMemberBean newGroupMemberBean){
        mDBGroup.updateLightName(oldGroupMemberBean.getName(), newGroupMemberBean.getName());
    }

    @Override
    public void uptLightPosition(GroupMemberBean mGroupMemberBean, int position) {
        mDBGroup.updateLightPosition(mGroupMemberBean.getName(), position);
    }
    @Override
    public void moveGroup(GroupMemberBean mGroupMemberBean, List<GroupMemberBean> mList) {

    }

    @Override
    public int getGroupCount()  {
        return mDBGroup.getGroupCount();
    }
}
