package com.jnu.student.data;

import static com.jnu.student.RewardFragment.RewardList;
import java.io.Serializable;
import java.util.ArrayList;

public class TaskFinishedData implements Serializable {
    public ArrayList<Item> itemArraylist;
    public int point=0;

    public TaskFinishedData(){
        itemArraylist=new ArrayList<>();
    }
    public class Item implements Serializable{
        public int classes;
        public String itemName;
        public int itemPoint;
        Item(int classes_,String name_,int point_){
            this.classes=classes_;
            this.itemName=name_;
            this.itemPoint=point_;
        }

        public int getClasses() {return classes;}

        public String getItemName() {return itemName;}
        public int getItemPoint() {return itemPoint;}

        public void setItemName(String itemName) {this.itemName = itemName;}

        public void setItemPoint(int itemPoint) {this.itemPoint = itemPoint;}

        public void setClassName(int classes) {this.classes = classes;}
    }
    public boolean addFinishedDataItem(int classes,String name, int point){
        itemArraylist.add(new Item(classes,name,point));
        return true;
    }
    public boolean setPoint(int classes,int i) {
        if(classes!=4){
            point+=i;
            return true;
        }
        else{
            if(point>=i){
                point-=i;
                return true;
            }
            else return false;
        }
    }

    public int getPoint() {
        return point;
    }
}
