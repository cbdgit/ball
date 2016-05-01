package com.bosong.ball_light.model.bean;

/**
 * Created by mike on 1/28/16.
 */
public class ColorMemberBean {
    private int colorId;
    private boolean isOnline;

    public ColorMemberBean(int colorId, boolean isOnline) {
        super();
        this.colorId = colorId;
        this.isOnline = isOnline;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

}
