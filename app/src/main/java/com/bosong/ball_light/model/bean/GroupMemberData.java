package com.bosong.ball_light.model.bean;

/**
 * Created by mike on 2/21/16.
 */
public class GroupMemberData {
    private String lightName;
    private boolean lightType;

    public void setLightName(String lightName) {
        this.lightName = lightName;
    }

    public void setLightType(boolean lightType) {
        this.lightType = lightType;
    }

    public String getLightName() {
        return lightName;
    }

    public boolean getLightType() {
        return lightType;
    }
}
