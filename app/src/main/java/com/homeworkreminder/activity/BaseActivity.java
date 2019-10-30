package com.homeworkreminder.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.homeworkreminder.utils.userUtil.CheckUserInfoUtil;

import cn.bmob.v3.Bmob;

public class BaseActivity extends AppCompatActivity {
    //APPID
    private static final String BMOB_APPID = "c25d039da1fe8e259a7a49dac8435a6d";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化Bmob
        Bmob.initialize(getApplicationContext(),BMOB_APPID);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
