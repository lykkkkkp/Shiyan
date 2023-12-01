package com.jnu.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.TextView;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TextView textView = new TextView(this);
        // 设置TextView的文本内容
        //textView.setText(R.id.text_vciew_hellow_world);
        // 将TextView设置为Activity的内容视图
        // 通过资源名称获取字符串资源的ID
        int resId = getResources().getIdentifier("hello_android", "string", getPackageName());
        // 通过资源ID获取字符串值
        String helloAndroid = getResources().getString(resId);
        // 找到TextView
        TextView textView = findViewById(R.id.text_vciew_hellow_world);
        // 设置文本内容
        textView.setText(helloAndroid);
        //setContentView(textView);
    }
}