package com.homeworkreminder.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.homeworkreminder.R;
import com.homeworkreminder.entity.UserInfo;
import com.homeworkreminder.utils.MyApplication;
import com.homeworkreminder.utils.networkUtil.MyGson;
import com.homeworkreminder.utils.userUtil.CheckUserInfoUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    //保存请求的结果
    private String result;

    private EditText etLoginUsername;
    private EditText etLoginPassword;
    private Button btnLogin;
    //private TextView loginReturnResult;

    CheckUserInfoUtil checkUserInfoUtil = new CheckUserInfoUtil(LoginActivity.this);


    //请求的url地址
    private String url = "http://www.nibuguai.cn/index.php/index/User/api_loginDoWith?";
    private String loginState;

    private int id;
    private String mUsername;
    private String nickname = "";
    private String school = "";
    private String major = "";
    private String clazz = "";
    MyApplication app = new MyApplication();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        app = (MyApplication) getApplication();

        if(Build.VERSION.SDK_INT >= 24) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        //初始化view
        initView();

        //登录按钮的监听事件
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入的帐号密码
                String inp_username = etLoginUsername.getText().toString();
                String inp_password = etLoginPassword.getText().toString();

                if (inp_username.equals("") || inp_password.equals("")){
                    Toast.makeText(LoginActivity.this, "帐号、密码不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    //使用Volley框架， 发送POST请求
                    useVolleyPOST(url, inp_username, inp_password);
                }



            }
        });


    }

    /**
     * 初始化View
     */
    private void initView() {
        etLoginUsername = (EditText) findViewById(R.id.et_login_username);
        etLoginPassword = (EditText) findViewById(R.id.et_login_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
    }


    /**
     * post请求
     * @param url url
     * @param username
     * @param password
     */
    private void useVolleyPOST(String url, final String username, final String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final StringRequest stringRequest = new StringRequest(
                //参数1：请求方法
                Request.Method.POST,
                //参数2：请求的url
                url,
                //参数3：请求成功的监听事件
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        result = response;

                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();


                        checkUserInfoUtil.writeUserInfo(response, "userinfo");
                        //app.setSTATE_LOGIN(true);


                        UserInfo userInfo = MyGson.parseJsonByGson(response, UserInfo.class);

                        app.setUserInfo(userInfo);
                        UserInfo userInfo1 = app.getUserInfo();
                        System.out.println("MyApplication->userinfo1:" + userInfo1);


                        UserInfo.DataBean dataBean = userInfo1.getData().get(0);
                        id = dataBean.getId();
//                        mUsername = dataBean.getUsername();
//                        nickname = dataBean.getNickname();
//                        school = dataBean.getSchool();
//                        clazz = dataBean.getClassX();
//                        major = dataBean.getMajor();

                        //将登陆状态写入SharedPreference文件
                        checkUserInfoUtil.writeUserInfo("true","login");

                        //将用户名存入SharedPreference文件
                        String login_username = etLoginUsername.getText().toString();
                        checkUserInfoUtil.writeUserInfo(login_username,"username");

                        checkUserInfoUtil.writeUserInfo("" + id, "uid");

                        String school = dataBean.getSchool();
                        if (school == null){
                            Intent intent = new Intent(LoginActivity.this, EditUserInfoActivity.class);

                            Bundle bundle = new Bundle();

                            nickname = dataBean.getNickname();
                            convertDataToActivity(bundle);
                            intent.putExtras(bundle);

                            startActivity(intent);
                        }
                        else {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }

                        finish();
                    }
                },
                //参数4：请求失败的监听事件
                new com.android.volley.Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                }){//StringRequest方法体的左大括号

            //StringRequest方法体中重写getParams()方法
            //设置请求参数的方法
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //设置请求参数信息
                Map<String, String> map = new HashMap<>();
                map.put("username",username);
                map.put("password",password);
                return map;
            }

        };//StringRequest方法体的右大括号

        //3、将请求添加到队列
        requestQueue.add(stringRequest);
    }


    public void convertDataToActivity(Bundle bundle){
        //bundle.putInt("headImg",headImg);
        bundle.putCharSequence("nickname", nickname);
        bundle.putCharSequence("school", school);
        bundle.putCharSequence("major", major);
        bundle.putCharSequence("class", clazz);
    }
}
