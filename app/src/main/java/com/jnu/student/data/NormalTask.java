package com.jnu.student.data;

import java.io.Serializable;

public class NormalTask implements Serializable {
    private String name; // 任务名称
    private int point; // 任务积分
    private boolean done; // 任务是否完成
    public void setName(String s) {
        name=s;
    }

    public void setPoint(int i) {
        point=i;
    }

    public void setDone(boolean b) {
        done=b;
    }

    public String getName() {
        return name;
    }

    public int getPoint() {
        return point;
    }

    public boolean isDone() {
        return done;
    }
    public NormalTask(String name_, int point_, boolean done_){
        this.name=name_;
        this.point=point_;
        this.done=done_;
    }
}