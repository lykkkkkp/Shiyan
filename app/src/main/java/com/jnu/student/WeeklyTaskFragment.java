package com.jnu.student;

import static com.jnu.student.RewardFragment.RewardList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.student.data.DataBank;
import com.jnu.student.data.TaskFinishedData;
import com.jnu.student.data.WeeklyTask;

import java.util.ArrayList;

public class WeeklyTaskFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private RecyclerView mainRecyclerView;
    private static ArrayList<WeeklyTask> weeklyTaskList; // 任务列表
    private static WeeklyTaskFragment.TaskAdapter TaskAdapter;
    public TaskFinishedData finishedData;
    private TextView total_point;
    public int flag=0;


    public WeeklyTaskFragment() {
        // Required empty public constructor
    }


    public static WeeklyTaskFragment newInstance(String param1, String param2) {
        WeeklyTaskFragment fragment = new WeeklyTaskFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onResume(){
        super.onResume();
        int point=(new DataBank().LoadFinishedDataItems(requireActivity())).getPoint();
        total_point.setText("目前总任务币："+point);
        flag=1;
        TaskFragment.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在这里写您想要的功能，例如弹出一个Toast提示
                Intent intent = new Intent(requireActivity(), TaskDetailsActivity.class);
                addTaskLauncher.launch(intent);
            }
        });
    }
    @Override
    public void onPause(){
        super.onPause();
        flag=0;
        TaskFragment.buttonAdd.setOnClickListener(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_weekly_task, container, false);


        mainRecyclerView = rootView.findViewById(R.id.recycleview_weekly_task);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        weeklyTaskList =new DataBank().LoadWeeklyTaskItems(requireActivity());
        if(0== weeklyTaskList.size()){
            weeklyTaskList.add(new WeeklyTask("学习", 200, false));
        }
        total_point=TaskFragment.view.findViewById(R.id.total_point);

        TaskAdapter = new WeeklyTaskFragment.TaskAdapter(weeklyTaskList);
        mainRecyclerView.setAdapter(TaskAdapter);

        registerForContextMenu(mainRecyclerView);

        addTaskLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String name = data.getStringExtra("name");
                        String point1 = data.getStringExtra("point");

                        int point=Integer.parseInt(point1);
                        weeklyTaskList.add(new WeeklyTask(name,point,false));
                        TaskAdapter.notifyItemInserted(weeklyTaskList.size());

                        new DataBank().SavaWeeklyTaskItems(requireActivity(), weeklyTaskList);

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );
        updateTaskLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        int position=data.getIntExtra("position",0);
                        String name = data.getStringExtra("name");
                        String point1 = data.getStringExtra("point");

                        int point = Integer.parseInt(point1);
                        WeeklyTask task= weeklyTaskList.get(position);
                        task.setName(name);
                        task.setPoint(point);
                        TaskAdapter.notifyItemChanged(position);

                        new DataBank().SavaWeeklyTaskItems(requireActivity(), weeklyTaskList);

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );
        return rootView;
    }
    ActivityResultLauncher<Intent> addTaskLauncher;
    ActivityResultLauncher<Intent> updateTaskLauncher;

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(flag==1)
            switch (item.getItemId()) {
                case 0:
                    Intent intent = new Intent(requireActivity(), TaskDetailsActivity.class);
                    addTaskLauncher.launch(intent);
                    break;
                case 1:
                    AlertDialog.Builder builder=new AlertDialog.Builder(requireActivity());
                    builder.setTitle("Delete Data");
                    builder.setMessage("Are you sure?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            weeklyTaskList.remove(item.getOrder());
                            TaskAdapter.notifyItemRemoved(item.getOrder());

                            new DataBank().SavaWeeklyTaskItems(requireActivity(), weeklyTaskList);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                    break;
                case 2:
                    Intent intentUpdate = new Intent(requireActivity(), TaskDetailsActivity.class);
                    WeeklyTask task= weeklyTaskList.get(item.getOrder());
                    intentUpdate.putExtra("name",task.getName());
                    intentUpdate.putExtra("point",task.getPoint());
                    intentUpdate.putExtra("position",item.getOrder());
                    updateTaskLauncher.launch(intentUpdate);
                    break;
                //            default:
                //                return super.onContextItemSelected(item);
            }
        return false;
    }
    public class TaskAdapter extends RecyclerView.Adapter<WeeklyTaskFragment.TaskAdapter.ViewHolder> {

        private final ArrayList<WeeklyTask> weeklytaskArrayList; // 任务列表

        public TaskAdapter(ArrayList<WeeklyTask> weeklyTaskList) {
            this.weeklytaskArrayList = weeklyTaskList;
        }

        @NonNull
        @Override
        public WeeklyTaskFragment.TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
            return new WeeklyTaskFragment.TaskAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull WeeklyTaskFragment.TaskAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
            viewHolder.getCheckBox().setChecked(false);
            viewHolder.getCheckBox().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishedData=new DataBank().LoadFinishedDataItems(requireActivity());
                    if(weeklyTaskList != null && position < weeklyTaskList.size() && RewardList != null && position < RewardList.size()) {
                        finishedData.addFinishedDataItem(2,weeklyTaskList.get(position).getName(), weeklyTaskList.get(position).getPoint());
                        new DataBank().SavaFinishedDataItems(requireActivity(), finishedData);
                    }

                    weeklyTaskList.remove(viewHolder.getAdapterPosition());
                    WeeklyTaskFragment.TaskAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    new DataBank().SavaWeeklyTaskItems(requireActivity(), weeklyTaskList);
                    int point=(new DataBank().LoadFinishedDataItems(requireActivity())).getPoint();
                    total_point.setText("目前总任务币："+point);
                }
            });
            viewHolder.getTaskName().setText(weeklytaskArrayList.get(position).getName());
            viewHolder.getTaskPoint().setText("+"+weeklytaskArrayList.get(position).getPoint());
        }

        @Override
        public int getItemCount() {
            return weeklytaskArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView taskName;
            private final TextView taskPoint;
            private CheckBox checkBox;

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("具体操作");
                menu.add(0, 0, this.getAdapterPosition(), "添加" + this.getAdapterPosition());
                menu.add(0, 1, this.getAdapterPosition(), "删除" + this.getAdapterPosition());
                menu.add(0, 2, this.getAdapterPosition(), "修改" + this.getAdapterPosition());
            }

            public ViewHolder(View TaskView) {
                super(TaskView);

                checkBox=TaskView.findViewById(R.id.task_checkbox);
                taskName = TaskView.findViewById(R.id.task_name);
                taskPoint = TaskView.findViewById(R.id.task_point);
                TaskView.setOnCreateContextMenuListener(this);
            }

            public TextView getTaskName() {
                return taskName;
            }

            public TextView getTaskPoint() {
                return taskPoint;
            }
            public CheckBox getCheckBox(){return checkBox;}


        }
    }
}