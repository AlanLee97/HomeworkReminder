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
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.homeworkreminder.R;
import com.homeworkreminder.entity.UserInfo;
import com.homeworkreminder.utils.MyApplication;
import com.homeworkreminder.utils.networkUtil.MyGson;
import com.homeworkreminder.utils.networkUtil.VolleyInterface;
import com.homeworkreminder.utils.networkUtil.VolleyUtil;
import com.homeworkreminder.utils.userUtil.CheckUserInfoUtil;

import java.util.List;

import cn.bmob.v3.util.V;

/**
 * 用户界面（已废弃，换成UserFragment）
 */
public class UserActivity extends AppCompatActivity {
    private TextView tvUserinfoUsername;
    private TextView tvUserinfoNickname;
    private TextView tvUserinfoSchool;
    private TextView tvUserinfoMajor;
    private TextView tvUserinfoClass;
    private Button btnLogout;
    private Button btnModifyPassword;

    private String nickname;
    private String school;
    private String major;
    private String clazz;

    MyApplication app = new MyApplication();

    String url = "http://www.nibuguai.cn/index.php/index/user/api_getUserInfoById?id=";
    int uid;

    String registerState = "";
    String loginState = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initView();

        //checkUserState();

        //showData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                getData();
                Intent intent = new Intent(UserActivity.this, EditUserInfoActivity.class);
                Bundle bundle = new Bundle();

                convertDataToActivity(bundle);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        modifyPassword();
        
        logout();
    }

    private void modifyPassword() {
        btnModifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, ModifyPasswordActivity.class));

            }
        });
    }


    private String TAG = "user_state";

    private void logout() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckUserInfoUtil userInfoUtil = new CheckUserInfoUtil(getApplicationContext());
                userInfoUtil.writeUserInfo("false", "register");
                userInfoUtil.writeUserInfo("false", "login");
                userInfoUtil.writeUserInfo("", "uid");
                //app.setSTATE_LOGIN(false);
                startActivity(new Intent(UserActivity.this, IndexActivity.class));

                finish();
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
        btnModifyPassword = (Button) findViewById(R.id.btn_modifyPassword);

    }



    /**
     * 通过Bundle向Activity中传值
     */
    public void convertDataToActivity(Bundle bundle){
        //bundle.putInt("headImg",headImg);
        bundle.putCharSequence("nickname", nickname);
        bundle.putCharSequence("school", school);
        bundle.putCharSequence("major", major);
        bundle.putCharSequence("class", clazz);


    }

    public void getData(){
        nickname = tvUserinfoNickname.getText().toString();
        school = tvUserinfoSchool.getText().toString();
        major = tvUserinfoMajor.getText().toString();
        clazz = tvUserinfoClass.getText().toString();

    }


}
