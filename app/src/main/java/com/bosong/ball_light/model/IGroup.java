package com.bosong.ball_light.model;

import com.bosong.ball_light.model.bean.GroupBean;
import com.bosong.ball_light.model.bean.GroupMemberBean;

import java.util.List;

/**
 * Created by mike on 2/4/16.
 */
public interface IGroup {
    List<GroupBean> initGroup();
    List<List<GroupMemberBean>> initLight();
    List<GroupMemberBean> selectSomeLights(int position);
    void addGroup(GroupBean mGroupBean);
    void delGroup(GroupBean mGroupBean);
    void delGroupLight(int position);
    void uptGroup(GroupBean oldGroupBean, GroupBean newGroupBean);
    void addLight(GroupMemberBean mGroupMemberBean, int position);
    void delLight(GroupMemberBean mGroupMemberBean);
    void uptLight(GroupMemberBean oldGroupMemberBean, GroupMemberBean newGroupMemberBean);
    void uptLightPosition(GroupMemberBean mGroupMemberBean, int position);
    void moveGroup(GroupMemberBean mGroupMemberBean, List<GroupMemberBean> mList);
    int getGroupCount();
}
