package com.gao.designer.entity;


import java.util.List;

public class User {
    private int id;
    private String username;
    private String dingUserid;
    private String moible;
    private String avatar;
    private String createTime;
    private int    lastloginTime;
    private List<Role> roleList;

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDingUserid() {
        return dingUserid;
    }

    public void setDingUserid(String dingUserid) {
        this.dingUserid = dingUserid;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getLastloginTime() {
        return lastloginTime;
    }

    public void setLastloginTime(int lastloginTime) {
        this.lastloginTime = lastloginTime;
    }

    public void setAll(String username, String ding_userid, String moible, String avatar, String create_time){
        this.username=username;
        this.dingUserid=ding_userid;
        this.moible=moible;
        this.avatar=avatar;
        this.createTime=create_time;
    }


}
