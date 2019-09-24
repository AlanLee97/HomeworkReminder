package com.homeworkreminder.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.homeworkreminder.R;
import com.homeworkreminder.entity.UserInfo;
import com.homeworkreminder.utils.MyApplication;
import com.homeworkreminder.utils.networkUtil.MyGson;
import com.homeworkreminder.utils.networkUtil.VolleyInterface;
import com.homeworkreminder.utils.networkUtil.VolleyUtil;
import com.homeworkreminder.utils.userUtil.CheckUserInfoUtil;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class ModifyPasswordActivity extends AppCompatActivity {
    private EditText etPhoneNum;
    private QMUIRoundButton btnSendSMS;
    private EditText etSmsCode;
    private EditText etNewPassword;
    private QMUIRoundButton modifyPassword;

    private String phoneNum;
    private String smsCode;

    private String url = "http://www.nibuguai.cn/index.php/index/user/api_modifyPasswordDoWith";
    private Map<String, String> paramMap = new HashMap<>();
    private String uid;
    private MyApplication app = (MyApplication) getApplication();
    private String newPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);

        if(Build.VERSION.SDK_INT >= 24) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        initView();

        getUid();

        sendSMS();

        modify();

        //test();
    }

    public void test(){
        modifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid = getUid();
                newPassword = etNewPassword.getText().toString();

                useVolleyPOST2(url, uid, newPassword);
            }
        });
    }

    public String getUid(){
        CheckUserInfoUtil checkUserInfoUtil = new CheckUserInfoUtil(getApplicationContext());
        String uid = checkUserInfoUtil.readUserInfo("uid");
        System.out.println("uid = " + uid);
        return uid;
    }

    private void modify() {
        modifyPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                phoneNum = etPhoneNum.getText().toString();
                smsCode = etSmsCode.getText().toString();
                uid = getUid();
                newPassword = etNewPassword.getText().toString();

                if (phoneNum.equals("") || smsCode.equals("") || newPassword.equals("")){
                    Toast.makeText(ModifyPasswordActivity.this, "请填写完整内容", Toast.LENGTH_SHORT).show();
                    return;
                }

                //验证短信验证码
                BmobSMS.verifySmsCode(phoneNum, smsCode, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null){//验证成功
                            Toast.makeText(ModifyPasswordActivity.this, "验证成功", Toast.LENGTH_SHORT).show();

                            useVolleyPOST2(url, uid, newPassword);

                        }else {//验证失败
                            Toast.makeText(ModifyPasswordActivity.this, "验证码验证失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void useVolleyPost(final String url, Map<String, String> paramMap) {
        VolleyUtil.volleyPOST(ModifyPasswordActivity.this,
                url, "107", paramMap,
                new VolleyInterface(getApplicationContext(),
                        VolleyInterface.mListener,
                        VolleyInterface.mErrorListener) {
            @Override
            public void onMySuccess(String result) {
                System.out.println(url);
                System.out.println("result: " + result);
                Toast.makeText(ModifyPasswordActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ModifyPasswordActivity.this, MainActivity.class));
                finish();

            }

            @Override
            public void onMyError(VolleyError error) {
                Toast.makeText(ModifyPasswordActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void sendSMS() {
        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phoneNum = etPhoneNum.getText().toString();
                if (phoneNum.equals("")){
                    Toast.makeText(ModifyPasswordActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                }else {
                    BmobSMS.requestSMSCode(phoneNum, "", new QueryListener<Integer>() {
                        @Override
                        public void done(Integer integer, BmobException e) {
                            if (e == null){
                                Toast.makeText(ModifyPasswordActivity.this, "短信发送成功，smsId:" + integer, Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ModifyPasswordActivity.this, "发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });
    }

    private void initView() {
        etPhoneNum = (EditText) findViewById(R.id.et_phone_num);
        btnSendSMS = (QMUIRoundButton) findViewById(R.id.btn_sendSMS);
        etSmsCode = (EditText) findViewById(R.id.et_sms_code);
        etNewPassword = (EditText) findViewById(R.id.et_new_password);
        modifyPassword = (QMUIRoundButton) findViewById(R.id.modify_password);
    }



    /**
     * post请求
     * @param url url
     */
    private void useVolleyPOST2(String url, final String uid, final String password) {
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

                        Toast.makeText(ModifyPasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ModifyPasswordActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ModifyPasswordActivity.this, MainActivity.class));
                        finish();
                    }
                },
                //参数4：请求失败的监听事件
                new com.android.volley.Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ModifyPasswordActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                    }
                }){//StringRequest方法体的左大括号

            //StringRequest方法体中重写getParams()方法
            //设置请求参数的方法
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //设置请求参数信息
                Map<String, String> map = new HashMap<>();
                map.put("uid",uid);
                map.put("password",password);
                return map;
            }

        };//StringRequest方法体的右大括号

        //3、将请求添加到队列
        requestQueue.add(stringRequest);
    }

}
