package com.jnu.student;

import static android.content.Intent.getIntent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RewardDetailsActivity extends AppCompatActivity {
    private int position=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_details);

        Intent intent=getIntent();
        if(null!=intent){
            String name=intent.getStringExtra("name");
            if(null!=name){
                int point=intent.getIntExtra("point",0);
                position = intent.getIntExtra("position", -1);
                EditText editTextRewardName=findViewById(R.id.edittext_reward_name);
                editTextRewardName.setText(name);
                EditText editTextRewardPoint=findViewById(R.id.edittext_reward_point);
                editTextRewardPoint.setText(Integer.toString(point));
            }
        }

        Button buttonOk=findViewById(R.id.button_reward_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                EditText editTextRewardName=findViewById(R.id.edittext_reward_name);
                EditText editTextRewardPoint=findViewById(R.id.edittext_reward_point);
                intent.putExtra("name",editTextRewardName.getText().toString());
                intent.putExtra("point",editTextRewardPoint.getText().toString());
                intent.putExtra("position",position);
                setResult(Activity.RESULT_OK,intent);
                RewardDetailsActivity.this.finish();
            }
        });
    }
}
