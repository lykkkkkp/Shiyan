package com.jnu.student;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView textView1;
    private TextView textView2;
    private Button button;

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
        //int resId = getResources().getIdentifier("hello_android", "string", getPackageName());
        // 通过资源ID获取字符串值
        String helloAndroid = getResources().getString(resId);
        //String helloAndroid = getResources().getString(resId);
        // 找到TextView
        TextView textView = findViewById(R.id.text_vciew_hellow_world);
        //TextView textView = findViewById(R.id.text_vciew_hellow_world);
        // 设置文本内容
        textView.setText(helloAndroid);
        //textView.setText(helloAndroid);
        //setContentView(textView);


        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempText = textView1.getText().toString();
                textView1.setText(textView2.getText());
                textView2.setText(tempText);

                showToast("交换成功");
                showAlertDialog("交换成功");
            }
        });
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}