package com.bosong.ball_light.model.bean;

/**
 * Created by mike on 1/17/16.
 */
public class GroupMemberBean {

    private boolean type;     //0-色温；1-色彩
    private boolean linkOr;   //0-未连接；1-已连接
    private boolean onOrOff;  //0-关；1-开

    private String name;
    private String addr;

    public GroupMemberBean(String name,String addr, boolean type, boolean linkOr, boolean onOrOff){
        this.type = type;
        this.linkOr = linkOr;
        this.onOrOff = onOrOff;
        this.name = name;
        this.addr = addr;
    }

    /******set******/
    public void SetType(boolean type){
        this.type = type;
    }

    public void setLinkOr(boolean linkOr){
        this.linkOr = linkOr;
    }
    public boolean getLinkOr(){
        return linkOr;
    }

    public void setOnOrOff(boolean onOrOff){
        this.onOrOff =  onOrOff;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    /******get******/
    public boolean getType(){
        return type;
    }



    public boolean getOnOrOff(){
        return onOrOff;
    }

    public String getName(){
        return name;
    }

    public String getAddr() {
        return addr;
    }

}
