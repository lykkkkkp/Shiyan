package com.jnu.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class StatisticFragment extends Fragment {
    public static Button buttonBill;
    private TabLayout tabLayout;
    private ViewPager2 taskViewPager;

    public StatisticFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static StatisticFragment newInstance(String param1, String param2) {
        StatisticFragment fragment = new StatisticFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        buttonBill=view.findViewById(R.id.bill_button);
        StatisticFragment.buttonBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在这里写您想要的功能，例如弹出一个Toast提示
                Intent intent = new Intent(requireActivity(), BillActivity.class);
                startActivity(intent);
            }
        });

        tabLayout = view.findViewById(R.id.time_tab_layout);
        taskViewPager = view.findViewById(R.id.statistic_view_pager);
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
                        return new Dayfragment();
                    case 1:
                        return new Weekfragment();
                    case 2:
                        return new Monthfragment();
                    case 3:
                        return new Yearfragment();
                    default:
                        return null;
                }
            }

            @Override
            public int getItemCount() {
                // 返回子fragment的数量
                return 4;
            }
        });
        // 设置tablayout和viewpager2的联动
        new TabLayoutMediator(tabLayout, taskViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                // 根据位置设置tab的标题
                switch (position) {
                    case 0:
                        tab.setText("日");
                        break;
                    case 1:
                        tab.setText("周");
                        break;
                    case 2:
                        tab.setText("月");
                        break;
                    case 3:
                        tab.setText("年");
                        break;
                }
            }
        }).attach();
    }
}
