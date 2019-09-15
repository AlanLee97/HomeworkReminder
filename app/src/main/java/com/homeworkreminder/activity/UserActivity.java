package com.homeworkreminder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.homeworkreminder.R;
import com.homeworkreminder.utils.MyApplication;
import com.homeworkreminder.utils.userUtil.CheckUserInfoUtil;

import cn.bmob.v3.util.V;

public class UserActivity extends AppCompatActivity {
    private TextView tvUserinfoUsername;
    private TextView tvUserinfoNickname;
    private TextView tvUserinfoSchool;
    private TextView tvUserinfoMajor;
    private TextView tvUserinfoClass;
    private Button btnLogout;

    private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initView();

        app = (MyApplication) getApplication();




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                startActivity(new Intent(UserActivity.this, EditUserInfoActivity.class));
            }
        });

        logout();
    }



    private String TAG = "user_state";

    private void logout() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckUserInfoUtil userInfoUtil = new CheckUserInfoUtil(getApplicationContext());
                userInfoUtil.writeUserInfo("false", "register");
                userInfoUtil.writeUserInfo("false", "login");
                startActivity(new Intent(UserActivity.this, MainActivity.class));
            }
        });
    }

    private void initView() {


        tvUserinfoUsername = (TextView) findViewById(R.id.tv_userinfo_username);
        tvUserinfoNickname = (TextView) findViewById(R.id.tv_userinfo_nickname);
        tvUserinfoSchool = (TextView) findViewById(R.id.tv_userinfo_school);
        tvUserinfoMajor = (TextView) findViewById(R.id.tv_userinfo_major);
        tvUserinfoClass = (TextView) findViewById(R.id.tv_userinfo_class);
        btnLogout = (Button) findViewById(R.id.btn_logout);

    }
}
