package com.jnu.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TaskFragment extends Fragment {
    private RecyclerView.Adapter fragmentAdapter;
    private TabLayout tabLayout;
    private ViewPager2 taskViewPager;
    public DailyTaskFragment dailyTaskFragment;
    public WeeklyTaskFragment weeklyTaskFragment;
    public NormalTaskFragment normalTaskFragment;

    public static Button buttonAdd; // 创建一个按钮对象
    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment newInstance(String param1, String param2) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        dailyTaskFragment=new DailyTaskFragment();
//        weeklyTaskFragment=new WeeklyTaskFragment();
//        normalTaskFragment=new NormalTaskFragment();
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        buttonAdd=view.findViewById(R.id.add_task_button);
        tabLayout = view.findViewById(R.id.task_tab_layout);
        taskViewPager = view.findViewById(R.id.task_view_pager);
        return view;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        taskViewPager.setAdapter(new FragmentStateAdapter(getChildFragmentManager(), getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                // 根据位置返回不同的子fragment
                switch (position) {
                    case 0:
                        return new DailyTaskFragment();
                    case 1:
                        return new WeeklyTaskFragment();
                    case 2:
                        return new NormalTaskFragment();
                    default:
                        return null;
                }
            }

            @Override
            public int getItemCount() {
                // 返回子fragment的数量
                return 3;
            }
        });
        // 设置tablayout和viewpager2的联动
        new TabLayoutMediator(tabLayout, taskViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                // 根据位置设置tab的标题
                switch (position) {
                    case 0:
                        tab.setText("日常任务");
                        break;
                    case 1:
                        tab.setText("每周任务");
                        break;
                    case 2:
                        tab.setText("普通任务");
                        break;
                }
            }
        }).attach();
    }
}