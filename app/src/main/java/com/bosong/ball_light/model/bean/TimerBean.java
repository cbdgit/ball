package com.bosong.ball_light.model.bean;

/**
 * Created by Jones on 2016/1/17.
 */
public class TimerBean {
    private String timer_titlle;
    private String timer_open;
    private String timer_close;
    private String timer_group;
    private boolean linkOr;   //0-未连接；1-已连接

    private int openId;
    private int setId;
    private int deleteId;
    private int switcherStatus;//timer开关状态，默认1为开

    public int getSwitcherStatus() {
        return switcherStatus;
    }

    public void setSwitcherStatus(int switcherStatus) {
        this.switcherStatus = switcherStatus;
    }

    public int getDeleteId() {
        return deleteId;
    }

    public void setDeleteId(int deleteId) {
        this.deleteId = deleteId;
    }

    public void setLinkOr(boolean linkOr){
        this.linkOr = linkOr;
    }
    public boolean getLinkOr(){
        return linkOr;
    }

    public TimerBean() {

    }
    public TimerBean(int switcherStatus, int deleteId, int openId, int setId, String timer_titlle, String timer_open, String timer_close, String timer_group) {
        this.timer_titlle = timer_titlle;
        this.timer_open = timer_open;
        this.timer_close = timer_close;
        this.timer_group = timer_group;
        this.openId = openId;
        this.setId = setId;
        this.deleteId = deleteId;
        this.switcherStatus = switcherStatus;
    }

    public int getOpenId() {
        return openId;
    }

    public void setOpenId(int openId) {
        this.openId = openId;
    }

    public int getSetId() {
        return setId;
    }

    public void setSetId(int setId) {
        this.setId = setId;
    }

    public String getTimer_titlle() {
        return timer_titlle;
    }

    public void setTimer_titlle(String timer_titlle) {
        this.timer_titlle = timer_titlle;
    }

    public String getTimer_open() {
        return timer_open;
    }

    public void setTimer_open(String timer_open) {
        this.timer_open = timer_open;
    }

    public String getTimer_close() {
        return timer_close;
    }

    public void setTimer_close(String timer_close) {
        this.timer_close = timer_close;
    }

    public String getTimer_group() {
        return timer_group;
    }

    public void setTimer_group(String timer_group) {
        this.timer_group = timer_group;
    }

}
