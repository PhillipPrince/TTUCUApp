package com.example.ttucuapp;

import java.util.List;

public enum UserDetails {
 INSTANCE;
    private int phone;
    private String name;
    private int exec=0;
    private  int subcom=0;
    private int mission=0;
    private int hike=0;
    private int project=0;
    private int userId=0;

    public List<String> getMinistries() {
        return ministries;
    }

    public void setMinistries(List<String> ministries) {
        this.ministries = ministries;
    }

    List<String> ministries;


    public String getName() {
        return name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    private int groupId=0;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getExec() {
        return exec;
    }

    public void setExec(int exec) {
        this.exec = exec;
    }



    public int getSubcom() {
        return subcom;
    }

    public void setSubcom(int subcom) {
        this.subcom = subcom;
    }

    public int getMission() {
        return mission;
    }

    public void setMission(int mission) {
        this.mission = mission;
    }

    public int getHike() {
        return hike;
    }

    public void setHike(int hike) {
        this.hike = hike;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public int getElders() {
        return elders;
    }

    public void setElders(int elders) {
        this.elders = elders;
    }

    int elders=0;
}
