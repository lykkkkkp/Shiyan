package com.jnu.student;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.student.data.DataBank;
import com.jnu.student.data.Reward;
import com.jnu.student.data.TaskFinishedData;

import java.util.ArrayList;

public class RewardFragment extends Fragment {

    private RecyclerView recycleviewReward;
    public static ArrayList<Reward> RewardList; // 任务列表
    private static RewardFragment.RewardAdapter RewardAdapter;
    public TaskFinishedData finishedData;
    public static Button buttonAdd;
    private TextView total_point;
    public RewardFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RewardFragment newInstance(String param1, String param2) {
        RewardFragment fragment = new RewardFragment();
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
        total_point.setText("目前总积分："+point);
    }
    @Override
    public void onDestroy () {
        super.onDestroy ();
        // 在这里写您想要在FragmentA被销毁时执行的操作，例如打印一条日志
        Log.d ("RewardFragment", "RewardFragment is destroyed");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_reward, container, false);

        buttonAdd=rootView.findViewById(R.id.add_reward_button);
        total_point=rootView.findViewById(R.id.total_point);
        int point2=(new DataBank().LoadFinishedDataItems(requireActivity())).getPoint();
        total_point.setText("目前总积分："+point2);
        RewardFragment.buttonAdd.setOnClickListener(new View.OnClickListener() { // 通过MyApp.button引用按钮对象
            @Override
            public void onClick(View v) {
                // 在这里写您想要的功能，例如弹出一个Toast提示
                Intent intent = new Intent(requireActivity(), RewardDetailsActivity.class);
                addRewardLauncher.launch(intent);
            }
        });

        RecyclerView mainRecyclerView = rootView.findViewById(R.id.recycleview_reward);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        RewardList =new DataBank().LoadRewardItems(requireActivity());
        if(0== RewardList.size()){
            RewardList.add(new Reward("打游戏", 200));
        }


        RewardAdapter = new RewardFragment.RewardAdapter(RewardList);
        mainRecyclerView.setAdapter(RewardAdapter);

        registerForContextMenu(mainRecyclerView);

        addRewardLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String name = data.getStringExtra("name");
                        String point1 = data.getStringExtra("point");

                        int point=Integer.parseInt(point1);
                        RewardList.add(new Reward(name,point));
                        RewardAdapter.notifyItemInserted(RewardList.size());

                        new DataBank().SavaRewardItems(requireActivity(), RewardList);

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );
        updateRewardLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        int position=data.getIntExtra("position",0);
                        String name = data.getStringExtra("name");
                        String point1 = data.getStringExtra("point");

                        int point = Integer.parseInt(point1);
                        Reward reward = RewardList.get(position);
                        reward.setName(name);
                        reward.setPoint(point);
                        RewardAdapter.notifyItemChanged(position);

                        new DataBank().SavaRewardItems(requireActivity(), RewardList);

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );
        return rootView;
    }
    ActivityResultLauncher<Intent> addRewardLauncher;
    ActivityResultLauncher<Intent> updateRewardLauncher;
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Intent intent = new Intent(requireActivity(), RewardDetailsActivity.class);
                addRewardLauncher.launch(intent);
                break;
            case 1:
                AlertDialog.Builder builder=new AlertDialog.Builder(requireActivity());
                builder.setTitle("Delete Data");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RewardList.remove(item.getOrder());
                        RewardAdapter.notifyItemRemoved(item.getOrder());

                        new DataBank().SavaRewardItems(requireActivity(), RewardList);
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
                Intent intentUpdate = new Intent(requireActivity(), RewardDetailsActivity.class);
                Reward reward = RewardList.get(item.getOrder());
                intentUpdate.putExtra("name", reward.getName());
                intentUpdate.putExtra("point", reward.getPoint());
                intentUpdate.putExtra("position",item.getOrder());
                updateRewardLauncher.launch(intentUpdate);
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.ViewHolder> {

        private final ArrayList<Reward> rewardArrayList; // 任务列表

        public RewardAdapter(ArrayList<Reward> rewardList) {
            this.rewardArrayList = rewardList;
        }

        @NonNull
        @Override
        public RewardFragment.RewardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reward, viewGroup, false);
            return new RewardFragment.RewardAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
            viewHolder.getCheckBox().setChecked(false);
            viewHolder.getCheckBox().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishedData=new DataBank().LoadFinishedDataItems(requireActivity());
                    if(finishedData.setPoint(4,RewardList.get(position).getPoint())) {
                        finishedData.addFinishedDataItem(4,RewardList.get(position).getName(), (-1)*RewardList.get(position).getPoint());
                        new DataBank().SavaFinishedDataItems(requireActivity(), finishedData);

                        RewardList.remove(viewHolder.getAdapterPosition());
                        RewardFragment.RewardAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                        new DataBank().SavaRewardItems(requireActivity(), RewardList);

                        int point=(new DataBank().LoadFinishedDataItems(requireActivity())).getPoint();
                        total_point.setText("目前总积分："+point);
                    }
                    else{
                        AlertDialog.Builder builder=new AlertDialog.Builder(requireActivity());
                        builder.setTitle("提示");
                        builder.setMessage("积分币不足！");
                        builder.create().show();
                        viewHolder.getCheckBox().setChecked(false);
                    }
                }
            });
            viewHolder.getRewardName().setText(rewardArrayList.get(position).getName());
            viewHolder.getRewardPoint().setText("-"+rewardArrayList.get(position).getPoint());
        }

        @Override
        public int getItemCount() {
            return rewardArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView rewardName;
            private final TextView rewardPoint;
            private CheckBox checkBox;

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("具体操作");
                menu.add(0, 0, this.getAdapterPosition(), "添加" + this.getAdapterPosition());
                menu.add(0, 1, this.getAdapterPosition(), "删除" + this.getAdapterPosition());
                menu.add(0, 2, this.getAdapterPosition(), "修改" + this.getAdapterPosition());
            }

            public ViewHolder(View RewardView) {
                super(RewardView);

                checkBox=RewardView.findViewById(R.id.reward_checkbox);
                rewardName = RewardView.findViewById(R.id.reward_name);
                rewardPoint = RewardView.findViewById(R.id.reward_point);
                RewardView.setOnCreateContextMenuListener(this);
            }

            public TextView getRewardName() {
                return rewardName;
            }

            public TextView getRewardPoint() {
                return rewardPoint;
            }

            public CheckBox getCheckBox(){return checkBox;}
        }
    }
}