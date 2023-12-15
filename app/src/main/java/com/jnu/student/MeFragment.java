package com.jnu.student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class MeFragment extends Fragment {
    private ImageView imgAvatar;
    private TextView txtUsername, txtUserBio, txtTaskCoins;
    private Button btnEditProfile, btnHelpSupport, btnAbout, btnFeedback, btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        imgAvatar = view.findViewById(R.id.img_avatar);
        txtUsername = view.findViewById(R.id.txt_username);
        txtUserBio = view.findViewById(R.id.txt_user_bio);
        btnEditProfile = view.findViewById(R.id.btn_edit_profile);
        btnHelpSupport = view.findViewById(R.id.btn_help_support);
        btnAbout = view.findViewById(R.id.btn_about);
        btnFeedback = view.findViewById(R.id.btn_feedback);
        btnLogout = view.findViewById(R.id.btn_logout);

        // 初始化数据和事件监听器
        initializeData();
        setupClickListeners();

        return view;
    }

    private void initializeData() {
        // 示例实现 - 在实际应用中需要根据实际数据源来加载数据
        txtUsername.setText("刘友奎");
        txtUserBio.setText("好好学习，天天向上");
    }

    private void setupClickListeners() {
        imgAvatar.setOnClickListener(v -> {
            // 启动一个活动或对话框来更改头像
            Intent intent = new Intent(getActivity(), AvatarChangeActivity.class);
            startActivity(intent);
        });
        // 处理
        btnEditProfile.setOnClickListener(v -> {
            // 处理编辑个人资料的逻辑，例如打开一个编辑资料的界面
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(intent);
        });

        btnHelpSupport.setOnClickListener(v -> {
            // 显示帮助和支持信息
            // 可以是一个新的 Fragment 或者一个对话框
            showHelpSupportDialog();
        });

        btnAbout.setOnClickListener(v -> {
            // 显示关于页面
            // 可以是一个新的 Fragment 或者一个对话框
            showAboutDialog();
        });

        btnFeedback.setOnClickListener(v -> {
            // 启动一个活动或对话框来收集用户反馈
            Intent intent = new Intent(getActivity(), FeedbackActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            // 处理用户退出逻辑
            // 这可能包括清除用户数据、更新UI、启动登录活动等
            performLogout();
        });
    }


    private void showHelpSupportDialog() {
        // 创建和显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("帮助与支持");
        builder.setMessage("这里是帮助与支持信息。");
        builder.setPositiveButton("确定", null);
        builder.show();
    }
    // 示例方法：显示关于对话框
    private void showAboutDialog() {
        // 创建和显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("关于");
        builder.setMessage("应用版本：1.0\n版权所有 © YourCompany");
        builder.setPositiveButton("确定", null);
        builder.show();
    }

    // 示例方法：处理用户退出
    private void performLogout() {
        // 清除用户数据，例如从 SharedPreferences 移除用户信息
        SharedPreferences preferences = getActivity().getSharedPreferences("YourAppPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("username");
        editor.remove("password");
        editor.apply();

        // 重定向用户到登录界面
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
