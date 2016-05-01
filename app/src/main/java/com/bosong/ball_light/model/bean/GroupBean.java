package com.bosong.ball_light.model.bean;

/**
 * Created by mike on 1/18/16.
 */
public class GroupBean {
    private String name;
    private boolean status; // 0-off;1-on

    public GroupBean(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public String getName(){
        return name;
    }

    public boolean getStatus(){
        return status;
    }
}
