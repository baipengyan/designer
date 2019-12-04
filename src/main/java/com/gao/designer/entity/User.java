package com.gao.designer.entity;

import java.sql.Timestamp;

public class User {
    private int uid;
    private String username;
    private String    ding_userid;
    private String moible;
    private String avatar;
    private String create_time;
    private int    lastloginTime;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDingUserid() {
        return ding_userid;
    }

    public void setDingUserid(String dingUserid) {
        this.ding_userid = dingUserid;
    }

    public String getMoible() {
        return moible;
    }

    public void setMoible(String moible) {
        this.moible = moible;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getLastloginTime() {
        return lastloginTime;
    }

    public void setLastloginTime(int lastloginTime) {
        this.lastloginTime = lastloginTime;
    }
}
