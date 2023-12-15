package com.jnu.student;

import android.graphics.Color;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.util.EventLogTags;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class Dayfragment extends Fragment {
    private LineChart lineChart_day_in, lineChart_day_out, lineChart_day_all;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        // 获取LineChart控件的引用
        lineChart_day_in = view.findViewById(R.id.line_chart_day_in);
        lineChart_day_out = view.findViewById(R.id.line_chart_day_out);
        lineChart_day_all = view.findViewById(R.id.line_chart_day_all);
        // 设置LineChart控件的数据和属性
        setLineChart(lineChart_day_in, "收入");
        setLineChart(lineChart_day_out, "支出");
        setLineChart(lineChart_day_all, "剩余");
        return view;
    }

    // 设置LineChart控件的数据和属性的方法
    private void setLineChart(LineChart lineChart, String label) {
        // 生成随机数据
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            entries.add(new Entry(i, (float) (Math.random() * 100)));
        }
        // 创建一个LineDataSet对象，表示一条折线
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