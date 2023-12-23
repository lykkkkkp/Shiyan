package com.jnu.student.data;

import static com.jnu.student.RewardFragment.RewardList;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
        public String year;
        public String month;
        public String date;
        public String day;
        public String single_hour;
        public String time;

        Item(int classes_,String name_,int point_){
            this.classes=classes_;
            this.itemName=name_;
            this.itemPoint=point_;
            // 创建一个Calendar对象，初始化为当前的日期和时间
            Calendar c = Calendar.getInstance();
            // 使用SimpleDateFormat对象来格式化日期和时间的显示方式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 输出当前的日期和时间
            System.out.println("当前的日期和时间：" + sdf.format(c.getTime()));
            //年份
            year="" + c.get(Calendar.YEAR);
            // 输出当前的月份，注意月份的值是从0开始的，所以需要加1
            month="" + (c.get(Calendar.MONTH) + 1);
            // 输出当前的几号
            date="" + c.get(Calendar.DAY_OF_MONTH);
            // 输出当前的星期几，注意星期的值是从1开始的，且周日是1，周一是2，以此类推
            day="" + c.get(Calendar.DAY_OF_WEEK);
            // 输出当前的小时，使用24小时制
            single_hour=""+c.get(Calendar.HOUR_OF_DAY);
            time=c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH) + 1)+"-"+c.get(Calendar.DAY_OF_MONTH)
                    +" "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND);
        }

        public int getClasses() {return classes;}

        public String getItemName() {return itemName;}
        public int getItemPoint() {return itemPoint;}
        public String getTime(){return time;}
        public String getMonth(){return month;}
        public String getDate(){return date;}
        public String getDay(){return day;}
        public String getSingle_hour(){return single_hour;}

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
