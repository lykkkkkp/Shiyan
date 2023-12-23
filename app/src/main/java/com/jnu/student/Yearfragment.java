package com.jnu.student;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.jnu.student.data.DataBank;
import com.jnu.student.data.TaskFinishedData;

import java.util.ArrayList;
import java.util.List;

public class Yearfragment extends Fragment {
    private LineChart lineChart_year_in, lineChart_year_out, lineChart_year_all;
    private static TaskFinishedData finishedData;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_year, container, false);
        // 获取LineChart控件的引用
        lineChart_year_in = view.findViewById(R.id.line_chart_year_in);
        lineChart_year_out = view.findViewById(R.id.line_chart_year_out);
        lineChart_year_all = view.findViewById(R.id.line_chart_year_all);
        // 设置LineChart控件的数据和属性
        setLineChart(lineChart_year_in, "收入");
        setLineChart(lineChart_year_out, "支出");
        setLineChart(lineChart_year_all, "结余");
        return view;
    }
    private void setLineChart(LineChart lineChart, String label) {
        finishedData=new DataBank().LoadFinishedDataItems(requireActivity());
        ArrayList<TaskFinishedData.Item> itemArraylist=finishedData.itemArraylist;
        int[] month_in=new int[13];
        int[] month_out=new int[13];
        int[] month_all=new int[13];
        //遍历itemArraylist
        for(int i=0;i<itemArraylist.size();i++){
            //获取itemArraylist中的元素
            TaskFinishedData.Item item=itemArraylist.get(i);
            //获取该元素的日期
            String date=item.month;
            //获取该元素的收支类型
            int classes=item.classes;
            //获取该元素的金额
            int point=item.itemPoint;
            //获取当前日期
            String now_date=""+(itemArraylist.get(0).month.charAt(0)+itemArraylist.get(0).month.charAt(1));
            if(date.equals(now_date)){
                if(classes!=4){
                    month_in[0]+=point;
                }
                else{
                    month_out[0]+=point;
                }
            }
            else{
                if(classes!=4){
                    month_in[Integer.parseInt(date)]+=point;
                }
                else{
                    month_out[Integer.parseInt(date)]+=point;
                }
            }
        }
        for(int i=0;i<13;i++){
            month_all[i]=month_in[i]-month_out[i];
        }
        // 创建一个LineDataSet对象，表示一条折线
        List<Entry> entries = new ArrayList<>();
        if("收入".equals(label)){
            for (int i = 1; i < 13; i++) {
                entries.add(new Entry(i, month_in[i]));
            }
        }
        else if("支出".equals(label)){
            for (int i = 1; i < 13; i++) {
                entries.add(new Entry(i, month_out[i]));
            }
        }
        else{
            for (int i = 1; i < 13; i++) {
                entries.add(new Entry(i, month_all[i]));
            }
        }
        LineDataSet lineDataSet = new LineDataSet(entries, label);
        // 设置折线的颜色和宽度
        lineDataSet.setColor(Color.WHITE);
        lineDataSet.setLineWidth(2f);
        // 创建一个LineData对象，表示LineChart的数据
        LineData lineData = new LineData(lineDataSet);
        // 设置LineChart的数据
        lineChart.setData(lineData);
        // 设置LineChart的描述
        Description description = new Description();
        description.setText(label);
        description.setTextSize(16f);
        description.setTextColor(Color.WHITE);
        lineChart.setDescription(description);
        // 设置LineChart的其他属性，如边界，缩放，动画等
        lineChart.setDrawBorders(true);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.animateX(1000);
    }
}
