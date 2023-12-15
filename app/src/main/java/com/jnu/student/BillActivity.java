package com.jnu.student;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.student.data.DataBank;
import com.jnu.student.data.TaskFinishedData;

public class BillActivity extends AppCompatActivity {
    private RecyclerView mainRecyclerView;
    private static TaskFinishedData finishedData;
    private static BillActivity.BillAdapter BillAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        Button buttonOk=findViewById(R.id.return_statistic_button);
        buttonOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                BillActivity.this.finish();
            }
        });

        mainRecyclerView=findViewById(R.id.Recycleview_bill);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        finishedData=new DataBank().LoadFinishedDataItems(this);

        BillAdapter= new BillActivity.BillAdapter(finishedData);
        mainRecyclerView.setAdapter(BillAdapter);
    }
    public class BillAdapter extends RecyclerView.Adapter<BillActivity.BillAdapter.ViewHolder>{
        private TaskFinishedData finishedDataList;

        public BillAdapter(TaskFinishedData finishedData){
            this.finishedDataList=finishedData;
        }
        @Override
        public BillAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bill, viewGroup, false);
            return new BillActivity.BillAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BillAdapter.ViewHolder viewHolder, int position) {
            viewHolder.getBillName().setText(finishedDataList.itemArraylist.get(position).getItemName());
            if(4 == finishedDataList.itemArraylist.get(position).getClasses()) {
                viewHolder.getBillPoint().setText("-"+finishedDataList.itemArraylist.get(position).getItemPoint());
                viewHolder.getBillPoint().setTextColor(Color.RED);
            }
            else{
                viewHolder.getBillPoint().setText("+"+finishedDataList.itemArraylist.get(position).getItemPoint());
                viewHolder.getBillPoint().setTextColor(Color.BLUE);
            }
        }

        @Override
        public int getItemCount() {
            return finishedDataList.itemArraylist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView BillName;
            private final TextView BillPoint;

            public ViewHolder(View BillView){
                super(BillView);

                BillName=BillView.findViewById(R.id.bill_name);
                BillPoint=BillView.findViewById(R.id.bill_point);
            }

            public TextView getBillName() {
                return BillName;
            }

            public TextView getBillPoint() {
                return BillPoint;
            }
        }
    }
}