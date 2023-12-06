package com.jnu.student.data;

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
//            // 创建一个Calendar对象，初始化为当前的日期和时间
//            Calendar c = Calendar.getInstance();
//            // 使用SimpleDateFormat对象来格式化日期和时间的显示方式
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            // 输出当前的日期和时间
//            System.out.println("当前的日期和时间：" + sdf.format(c.getTime()));
//            // 输出当前的月份，注意月份的值是从0开始的，所以需要加1
//            System.out.println("当前的月份：" + (c.get(Calendar.MONTH) + 1));
//            // 输出当前的几号
//            System.out.println("当前的几号：" + c.get(Calendar.DAY_OF_MONTH));
//            // 输出当前的星期几，注意星期的值是从1开始的，且周日是1，周一是2，以此类推
//            System.out.println("当前的星期几：" + c.get(Calendar.DAY_OF_WEEK));
//            // 输出当前的小时，使用24小时制
//            System.out.println("当前的小时：" + c.get(Calendar.HOUR_OF_DAY));
//            // 输出当前的分钟
//            System.out.println("当前的分钟：" + c.get(Calendar.MINUTE));
//            // 输出当前的秒数
//            System.out.println("当前的秒数：" + c.get(Calendar.SECOND));
        }

        public int getClasses() {return classes;}

        public String getItemName() {return itemName;}
        public int getItemPoint() {return itemPoint;}

        public void setItemName(String itemName) {this.itemName = itemName;}

        public void setItemPoint(int itemPoint) {this.itemPoint = itemPoint;}

        public void setClassName(int classes) {this.classes = classes;}
    }
    public boolean addFinishedDataItem(int classes,String name, int point){
        if(setPoint(point)){
            itemArraylist.add(new Item(classes,name,point));
            return true;
        }
        else{return false;}
    }
    public boolean setPoint(int i) {
        int temp_point=point+i;
        if(temp_point<0){
            return false;
        }
        else {
            point+=i;
            return true;
        }
    }

    public int getPoint() {
        return point;
    }
}
