package com.jnu.student;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    //    private String []tabHeaderStrings = {"Shopping items","baidu maps","News"};

    private String []tabHeaderStrings  = {"任务","奖励","统计","我"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycleview);
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        FragmentAdapter fragmentAdapter = new
                FragmentAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(fragmentAdapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) ->
                        tab.setText(tabHeaderStrings[position])
                // 设置TabLayout的标题
        ).attach();
    }
    private class FragmentAdapter extends FragmentStateAdapter {
        private static final int NUM_TABS = 4;
        public FragmentAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new TaskFragment();
                case 1:
                    return new RewardFragment();
                case 2:
                    return new StatisticFragment();
                case 3:
                    return new MeFragment();
                default:
                    return null;
            }
        }
        public int getItemCount() {
            return NUM_TABS;
        }
    }
        }
