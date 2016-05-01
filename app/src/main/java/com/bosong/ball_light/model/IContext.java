package com.bosong.ball_light.model;

import com.bosong.ball_light.model.bean.ContextMemberBean;

import java.util.List;

/**
 * Created by mike on 3/6/16.
 */
public interface IContext {
    List<ContextMemberBean> initContext();
    void insertContext(byte[] img, String name);
    void deleteContext(String name);
}
