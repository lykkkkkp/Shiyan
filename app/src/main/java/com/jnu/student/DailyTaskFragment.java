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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.student.data.DailyTask;
import com.jnu.student.data.DataBank;
import com.jnu.student.data.TaskFinishedData;

import java.util.ArrayList;

public class DailyTaskFragment extends Fragment {

    private RecyclerView mainRecyclerView;
    private static ArrayList<DailyTask> dailyTaskList; // 任务列表
    private static DailyTaskFragment.TaskAdapter TaskAdapter;
    public TaskFinishedData finishedData;
    private TextView total_point;
    public int flag=0;

    public DailyTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DailyTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyTaskFragment newInstance(String param1, String param2) {
        DailyTaskFragment fragment = new DailyTaskFragment();
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
        View rootView= inflater.inflate(R.layout.fragment_daily_task, container, false);


        mainRecyclerView = rootView.findViewById(R.id.Recycleview_daily_task);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mainRecyclerView.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));

        dailyTaskList =new DataBank().LoadDailyTaskItems(requireActivity());
        if(0== dailyTaskList.size()){
            dailyTaskList.add(new DailyTask("学习", 100, false));
            dailyTaskList.add(new DailyTask("语文作业", 100, false));
            dailyTaskList.add(new DailyTask("数学作业", 100, false));
            dailyTaskList.add(new DailyTask("英语作业", 100, false));
            dailyTaskList.add(new DailyTask("全部作业", 50, false));
        }
        total_point=TaskFragment.view.findViewById(R.id.total_point);
        TaskAdapter = new DailyTaskFragment.TaskAdapter(dailyTaskList);
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
                        dailyTaskList.add(new DailyTask(name,point,false));
                        TaskAdapter.notifyItemInserted(dailyTaskList.size());

                        new DataBank().SavaDailyTaskItems(requireActivity(), dailyTaskList);

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
                        DailyTask dailyTask = dailyTaskList.get(position);
                        dailyTask.setName(name);
                        dailyTask.setPoint(point);
                        TaskAdapter.notifyItemChanged(position);

                        new DataBank().SavaDailyTaskItems(requireActivity(), dailyTaskList);

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
                            dailyTaskList.remove(item.getOrder());
                            TaskAdapter.notifyItemRemoved(item.getOrder());

                            new DataBank().SavaDailyTaskItems(requireActivity(), dailyTaskList);
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
                    DailyTask dailyTask = dailyTaskList.get(item.getOrder());
                    intentUpdate.putExtra("name", dailyTask.getName());
                    intentUpdate.putExtra("point", dailyTask.getPoint());
                    intentUpdate.putExtra("position",item.getOrder());
                    updateTaskLauncher.launch(intentUpdate);
                    break;
                //            default:
                //                return super.onContextItemSelected(item);
            }
        return false;
    }

    public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

        private ArrayList<DailyTask> dailyTaskArrayList; // 任务列表

        public TaskAdapter(ArrayList<DailyTask> dailyTaskList) {
            this.dailyTaskArrayList = dailyTaskList;
        }

        @NonNull
        @Override
        public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
            viewHolder.getCheckBox().setChecked(false);
            viewHolder.getCheckBox().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = viewHolder.getAdapterPosition();
                    if (position >= 0 && position < dailyTaskList.size()) {
                        finishedData = new DataBank().LoadFinishedDataItems(requireActivity());
                        if (finishedData.setPoint(1, dailyTaskArrayList.get(position).getPoint())) {
                            finishedData.addFinishedDataItem(1, dailyTaskArrayList.get(position).getName(), dailyTaskArrayList.get(position).getPoint());
                            new DataBank().SavaFinishedDataItems(requireActivity(), finishedData);
                        }

                        dailyTaskList.remove(position);
                        TaskAdapter.notifyItemRemoved(position);
                        new DataBank().SavaDailyTaskItems(requireActivity(), dailyTaskList);
                        int point = (new DataBank().LoadFinishedDataItems(requireActivity())).getPoint();
                        total_point.setText("目前总任务币：" + point);

                    }
                }
            });
            viewHolder.getTaskName().setText(dailyTaskArrayList.get(position).getName());
            viewHolder.getTaskPoint().setText("+"+dailyTaskArrayList.get(position).getPoint());
        }

        @Override
        public int getItemCount() {
            return dailyTaskArrayList.size();
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