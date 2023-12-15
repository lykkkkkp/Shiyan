package com.jnu.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

    public class TaskDetailsActivity extends AppCompatActivity {
        private int position=-1;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_task_details);

            Intent intent=getIntent();
            if(null!=intent){
                String name=intent.getStringExtra("name");
                if(null!=name){
                    int point=intent.getIntExtra("point",0);
                    position = intent.getIntExtra("position", -1);
                    EditText editTextTaskName=findViewById(R.id.edittext_task_name);
                    editTextTaskName.setText(name);
                    EditText editTextTaskPoint=findViewById(R.id.edittext_task_point);
                    editTextTaskPoint.setText(Integer.toString(point));
                }
            }

            Button buttonOk=findViewById(R.id.button_task_ok);
            buttonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent();
                    EditText editTextTaskName=findViewById(R.id.edittext_task_name);
                    EditText editTextTaskPoint=findViewById(R.id.edittext_task_point);
                    intent.putExtra("name",editTextTaskName.getText().toString());
                    intent.putExtra("point",editTextTaskPoint.getText().toString());
                    intent.putExtra("position",position);
                    setResult(Activity.RESULT_OK,intent);
                    TaskDetailsActivity.this.finish();
                }
            });
        }
    }
