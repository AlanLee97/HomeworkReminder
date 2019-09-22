package com.homeworkreminder.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.homeworkreminder.R;
import com.homeworkreminder.utils.MyApplication;
import com.homeworkreminder.utils.networkUtil.VolleyInterface;
import com.homeworkreminder.utils.networkUtil.VolleyUtil;

public class EditUserInfoActivity extends AppCompatActivity {

    private EditText etEditNickname;
    private EditText etEditSchool;
    private TextView etEditMajor;
    private EditText etEditClass;
    private Button btnEditUserinfo;
    private TextView tvEditJump;
    private String nickname;
    private String school;
    private String major;
    private String clazz;
    private String url = "http://www.nibuguai.cn/index.php/index/user/api_editUserInfoDoWith?";

    private MyApplication app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        app = (MyApplication) getApplication();


        initView();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        receiveDataFormBundle(bundle);

//        etEditNickname.setText(nickname);
//        etEditSchool.setText(school);
//        etEditMajor.setText(major);
//        etEditClass.setText(clazz);

        btnEditUserinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });

        tvEditJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }

    private void sendRequest() {
        getData();
        int uid = app.getUserInfo().getData().get(0).getId();
        url = url + "nickname=" + nickname
                + "&school=" + school
                + "&class=" + clazz
                + "&major=" + major
                + "&id=" + uid;
        VolleyUtil.volleyGET(getApplicationContext(), url, "100",
                new VolleyInterface(getApplicationContext(),VolleyInterface.mListener, VolleyInterface.mErrorListener) {
            @Override
            public void onMySuccess(String result) {
                Toast.makeText(getApplication(), "请求成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditUserInfoActivity.this, MainActivity.class));
            }

            @Override
            public void onMyError(VolleyError error) {
                Toast.makeText(getApplication(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData(){
        nickname = etEditNickname.getText().toString();
        school = etEditSchool.getText().toString();
        major = etEditMajor.getText().toString();
        clazz = etEditClass.getText().toString();

    }

    private void initView() {
        etEditNickname = (EditText) findViewById(R.id.et_edit_nickname);
        etEditSchool = (EditText) findViewById(R.id.et_edit_school);
        etEditMajor = (TextView) findViewById(R.id.et_edit_major);
        etEditClass = (EditText) findViewById(R.id.et_edit_class);
        btnEditUserinfo = (Button) findViewById(R.id.btn_edit_userinfo);


        tvEditJump = (TextView) findViewById(R.id.tv_edit_jump);

    }


    public void receiveDataFormBundle(Bundle bundle){
        etEditNickname.setText(bundle.getCharSequence("nickname"));
        etEditSchool.setText(bundle.getCharSequence("school"));
        etEditMajor.setText(bundle.getCharSequence("major"));
        etEditClass.setText(bundle.getCharSequence("class"));

        System.out.println("nickname:" + bundle.getCharSequence("nickname"));
        System.out.println("school:" + bundle.getCharSequence("school"));
        System.out.println("major:" + bundle.getCharSequence("major"));
        System.out.println("class:" + bundle.getCharSequence("class"));
    }
}
