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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.homeworkreminder.R;
import com.homeworkreminder.utils.MyApplication;
import com.homeworkreminder.utils.userUtil.CheckUserInfoUtil;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText etRegUsername;
    private EditText etRegPassword;
    private TextView tvToLogin;
    private Button btnRegister;
    //private TextView regReturnResult;
    private String result;  //保存请求的结果
    //请求的url地址
    private String url = "http://www.nibuguai.cn/index.php/index/user/api_registerdowith?";
    MyApplication app = new MyApplication();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        app = (MyApplication) getApplication();

        if(Build.VERSION.SDK_INT >= 24) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        //初始化View
        initView();

        //注册方法
        register();

        //去登录
        toLogin();



    }

    /**
     * 注册方法
     */
    private void register() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入的账号密码
                String inp_username = etRegUsername.getText().toString();
                String inp_password = etRegPassword.getText().toString();

                if (inp_username.equals("") || inp_password.equals("")){
                    Toast.makeText(RegisterActivity.this, "帐号、密码不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    //使用Volley框架，发送POST请求
                    useVolleyPOST(url, inp_username, inp_password);
                }

            }
        });
    }

    public void toLogin(){
        tvToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                finish();
            }
        });
    }

    /**
     * 初始化View
     */
    private void initView() {
        etRegUsername = (EditText) findViewById(R.id.et_reg_username);
        etRegPassword = (EditText) findViewById(R.id.et_reg_password);
        btnRegister = (Button) findViewById(R.id.btn_register);
        //regReturnResult = (TextView) findViewById(R.id.reg_return_result);



        tvToLogin = (TextView) findViewById(R.id.tv_to_login);

    }

    /**
     * POST请求
     * @param url 请求的url
     * @param username 用户名
     * @param password 密码
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

                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                        CheckUserInfoUtil checkUserInfoUtil = new CheckUserInfoUtil(RegisterActivity.this);
                        checkUserInfoUtil.writeUserInfo("true", "register");
                        //app.setSTATE_REGISTER(true);

                        finish();
                    }
                },
                //参数4：请求失败的监听事件
                new com.android.volley.Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
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

}
