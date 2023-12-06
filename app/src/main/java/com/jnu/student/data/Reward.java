package com.jnu.student.data;

import java.io.Serializable;

public class Reward implements Serializable {
    private String name; // 奖励名称
    private int point; // 奖励积分
    public void setName(String s) {
        name=s;
    }

    public void setPoint(int i) {
        point=i;
    }

    public String getName() {
        return name;
    }

    public int getPoint() {
        return point;
    }

    public Reward(String name_, int point_){
        this.name=name_;
        this.point=point_;
    }
}